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
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ProximoHandler implements InvocationHandler {

   private final Map<Method,List<ProxyRule>> stubbings = new HashMap<>();
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
         List<ProxyRule> rules = stubbings.get(method);
         if(rules == null) {
            // TODO Order the rules from most specific to least specific
            // TODO Use a set to get rid of duplicates
            // TODO Unfortunately we want the most recently added (sets keep the first added)
            // TODO A Rule is a duplicate if its argument matchers are all the same
            rules = new LinkedList<>();
            stubbings.put(method, rules);
         }
         rules.add(0, rule);
         return Defaults.returnFor(method.getReturnType());
      } else {
         Answer<?> answer = find(stubbings.get(method), invocation);
         try {
            return answer.answer(invocation);
         } catch (InvocationTargetException e) {
            throw e.getTargetException();
         }
      }
   }


   private Answer<?> find(List<ProxyRule> rules, Invocation invocation)
   {
      for(int i = 0; rules != null && i < rules.size(); i++) {
         ProxyRule rule = rules.get(i);
         if(rule.matches(invocation)) return rule.getAnswer();
      }
      return new ForwardCallAnswer();
   }



   public static ProximoHandler create(StubbingProgress progress, Object proxied)
   {
      return new ProximoHandler(progress, proxied);
   }
}
