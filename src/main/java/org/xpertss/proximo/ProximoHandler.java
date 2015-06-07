/**
 * Copyright 2015 XpertSoftware
 * <p/>
 * Created By: cfloersch
 * Date: 6/7/2015
 */
package org.xpertss.proximo;

import xpertss.proximo.Invocation;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ProximoHandler implements InvocationHandler {

   private final Map<Method,List<ProxyRule>> stubbings = new HashMap<>();
   private final StubbingProgress progress;
   private final Object proxied;

   public ProximoHandler(StubbingProgress progress, Object proxied)
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
         ProxyRule rule = progress.completeStubbing(invocation);
         if(rule != null) {
            List<ProxyRule> rules = stubbings.get(method);
            if(rules == null) {
               rules = new LinkedList<>();
               stubbings.put(method, rules);
            }
            rules.add(0, rule);
         }
         // TODO Probably ought to return something for the method return type
      } else {
         ProxyRule rule = find(stubbings.get(method), args);
         if(rule != null) {
            return rule.getAnswer().answer(invocation);
         } else {
            return method.invoke(proxied, args);
         }
      }
      return null;
   }


   private ProxyRule find(List<ProxyRule> rules, Object[] args)
   {
      for(int i = 0; rules != null && i < rules.size(); i++) {
         if(rules.get(i).matches(args)) return rules.get(i);
      }
      return null;
   }

}
