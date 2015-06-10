/**
 * Copyright 2015 XpertSoftware
 * <p/>
 * Created By: cfloersch
 * Date: 6/7/2015
 */
package org.xpertss.proximo;

import org.xpertss.proximo.answers.ForwardCallAnswer;
import org.xpertss.proximo.util.Defaults;
import xpertss.proximo.Answer;
import xpertss.proximo.Invocation;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class ProximoHandler implements InvocationHandler {

   private final Map<Method,Set<ProxyRule>> stubbings = new HashMap<>();
   private final StubbingProgress progress;
   private final Object proxied;
   
   private ProximoHandler(StubbingProgress progress, Object proxied)
   {
      this.progress = progress;
      this.proxied = proxied;
   }




   @Override
   public Object invoke(Object proxy, Method method, Object[] args)
      throws Throwable
   {
      Invocation invocation = new ProxiedInvocation(proxied, proxy, method, args);
      if(progress.isStubbing()) {
         ProxyRule rule = progress.stubbingComplete(proxy, method, args);
         Set<ProxyRule> rules = stubbings.get(method);
         if(rules == null) {
            rules = new TreeSet<>();
            stubbings.put(method, rules);
         }
         rules.add(rule);
         return Defaults.returnFor(method.getReturnType());
      } else {
         Answer<?> answer = find(stubbings.get(method), invocation);
         try {
            return answer.answer(invocation);
         } catch (InvocationTargetException e) {
            throw filter(e.getTargetException());
         } catch (Throwable e) {
            throw filter(e);
         }
      }
   }


   private Answer<?> find(Set<ProxyRule> rules, Invocation invocation)
   {
      if(rules != null) {
         for(ProxyRule rule : rules) {
            if(rule.matches(invocation)) return rule.getAnswer();
         }
      }
      return new ForwardCallAnswer();
   }



   private static Throwable filter(Throwable throwable)
   {
      StackTraceElement[] trace = throwable.getStackTrace();
      List<StackTraceElement> filtered = new ArrayList<>();
      for(StackTraceElement element : trace) {
         if(!element.getClassName().startsWith("org.xpertss.proximo")) {
            filtered.add(element);
         }
      }
      throwable.setStackTrace(filtered.toArray(new StackTraceElement[filtered.size()]));
      return throwable;
   }


   public static ProximoHandler create(StubbingProgress progress, Object proxied)
   {
      return new ProximoHandler(progress, proxied);
   }
}
