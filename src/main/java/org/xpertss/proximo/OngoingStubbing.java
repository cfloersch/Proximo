/**
 * Copyright 2015 XpertSoftware
 * <p/>
 * Created By: cfloersch
 * Date: 6/3/2015
 */
package org.xpertss.proximo;

import org.xpertss.proximo.matchers.Array;
import org.xpertss.proximo.util.Utils;
import xpertss.proximo.Answer;
import xpertss.proximo.Matcher;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class OngoingStubbing<T> {

   private final List<Matcher<?>> argMatchers = new LinkedList<>();
   private final Queue<Answer<?>> answers;

   private OngoingStubbing(Queue<Answer<?>> answers)
   {
      this.answers = answers;
   }



   public void reportMatcher(Matcher<?> matcher)
   {
      argMatchers.add(matcher);
   }

   public ProxyRule stub(Object proxy, Method method, Object[] args)
   {
      Matcher[] matchers = argMatchers.toArray(new Matcher[argMatchers.size()]);
      Class[] paramTypes = method.getParameterTypes();

      if(paramTypes.length > matchers.length)
         throw new IllegalArgumentException("argument matcher underflow");
      if(paramTypes.length < matchers.length) {
         if(!method.isVarArgs()) throw new IllegalArgumentException("argument matcher overflow");

         Matcher[] varargs = Arrays.copyOfRange(matchers, paramTypes.length - 1, matchers.length);
         matchers = Arrays.copyOf(matchers, paramTypes.length);
         matchers[paramTypes.length - 1] = new Array(varargs);
      }
      return new ProxyRule(matchers, answers);
   }

   public static <T> OngoingStubbing<T> create(Queue<Answer<?>> answers)
   {
      return new OngoingStubbing<>(Utils.notNull(answers, "answers"));
   }


}
