/**
 * Copyright 2015 XpertSoftware
 * <p/>
 * Created By: cfloersch
 * Date: 6/3/2015
 */
package org.xpertss.proximo;

import org.xpertss.proximo.util.Utils;
import xpertss.proximo.Answer;
import xpertss.proximo.Matcher;

import java.lang.reflect.Method;
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
      if(method.getParameterTypes().length != argMatchers.size())
         throw new RuntimeException("insufficient argument matchers for method stubbing");
      return new ProxyRule(argMatchers.toArray(new Matcher[argMatchers.size()]), answers);
   }

   public static <T> OngoingStubbing<T> create(Queue<Answer<?>> answers)
   {
      return new OngoingStubbing<>(Utils.notNull(answers, "answers"));
   }


}
