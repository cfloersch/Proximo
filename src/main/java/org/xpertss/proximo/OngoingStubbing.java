/**
 * Copyright 2015 XpertSoftware
 * <p/>
 * Created By: cfloersch
 * Date: 6/3/2015
 */
package org.xpertss.proximo;

import org.xpertss.proximo.matchers.AnyVararg;
import org.xpertss.proximo.matchers.Array;
import org.xpertss.proximo.util.Lists;
import org.xpertss.proximo.util.Utils;
import xpertss.proximo.Answer;
import xpertss.proximo.Matcher;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicLong;

public class OngoingStubbing<T> {

   private static final AtomicLong sequence = new AtomicLong(0);

   private final List<Matcher> matchers = new LinkedList<>();
   private final Queue<Answer<?>> answers;

   private OngoingStubbing(Queue<Answer<?>> answers)
   {
      this.answers = answers;
   }



   public void reportMatcher(Matcher<?> matcher)
   {
      matchers.add(matcher);
   }


   public ProxyRule stub(Object proxy, Method method, Object[] args)
   {
      Class[] paramTypes = method.getParameterTypes();

      List<Matcher> argMatchers = matchers;
      if(paramTypes.length > argMatchers.size())
         throw new IllegalArgumentException("argument matcher underflow");
      if(method.isVarArgs()) {
         Matcher varArg = argMatchers.get(paramTypes.length - 1);
         if(!(varArg instanceof AnyVararg)) {
            Array varArgArray = new Array(Lists.tail(argMatchers, paramTypes.length - 1));
            argMatchers = Lists.subList(argMatchers, 0, paramTypes.length - 1);
            argMatchers.add(varArgArray);
         }
      } else if(paramTypes.length < matchers.size()) {
         throw new IllegalArgumentException("argument matcher overflow");
      }
      return new ProxyRule(argMatchers, answers, sequence.incrementAndGet());
   }

   public static <T> OngoingStubbing<T> create(Queue<Answer<?>> answers)
   {
      return new OngoingStubbing<>(Utils.notNull(answers, "answers"));
   }


}
