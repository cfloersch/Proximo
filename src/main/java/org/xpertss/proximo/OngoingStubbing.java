/**
 * Copyright 2015 XpertSoftware
 * <p/>
 * Created By: cfloersch
 * Date: 6/3/2015
 */
package org.xpertss.proximo;

import org.xpertss.proximo.answers.ReturnsAnswer;
import org.xpertss.proximo.answers.ThrowsAnswer;
import org.xpertss.proximo.matchers.AnyVararg;
import org.xpertss.proximo.matchers.Array;
import org.xpertss.proximo.util.Lists;
import org.xpertss.proximo.util.Primitives;
import org.xpertss.proximo.util.Utils;
import xpertss.proximo.Answer;
import xpertss.proximo.Matcher;

import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicLong;

import static java.lang.String.format;

public class OngoingStubbing {

   private static final AtomicLong sequence = new AtomicLong(0);

   private final List<Matcher> matchers = new LinkedList<>();
   private final Queue<Answer<?>> answers;

   private OngoingStubbing(Queue<Answer<?>> answers)
   {
      this.answers = Utils.notNull(answers);
   }



   public void reportMatcher(Matcher<?> matcher)
   {
      matchers.add(matcher);
   }


   public ProxyRule stub(Object proxy, Method method, Object[] args)
   {
      validateAnswers(answers, method);

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



   private void validateAnswers(Queue<Answer<?>> answers, Method method)
   {
      for(Answer answer : answers) {
         if(answer instanceof ReturnsAnswer) {
            validateReturnType(((ReturnsAnswer) answer).getReturnType(), method);
         } else if(answer instanceof ThrowsAnswer) {
            validateExceptionType(((ThrowsAnswer) answer).getExceptionType(), method);
         }
         // TODO Can I use generic types from other Answer impls to validate???
      }
   }

   private void validateReturnType(Class<?> returnType, Method method)
   {
      Class<?> methodReturnType = method.getReturnType();
      if(returnType != null) {
         if(methodReturnType.isPrimitive()) {
            methodReturnType = Primitives.wrapperTypeFor(methodReturnType);
         }
         if(!methodReturnType.isAssignableFrom(returnType))
            throw new IllegalArgumentException(
               format("%s cannot be returned by %s",
                  returnType.getSimpleName(),
                  method.getName()));
      } else if(methodReturnType.isPrimitive()) {
         throw new IllegalArgumentException(
               format("null cannot be returned by %s",
                  method.getName()));
      }
   }

   private void validateExceptionType(Class<?> errorType, Method method)
   {
      if(Utils.isChecked(errorType)) {
         for(Class<?> exType : method.getExceptionTypes()) {
            if(exType.isAssignableFrom(errorType)) return;
         }
         throw new IllegalArgumentException(
            format("checked exception %s cannot be thrown by %s",
               errorType.getSimpleName(),
               method.getName()));
      }
   }



   public static OngoingStubbing create(Queue<Answer<?>> answers)
   {
      return new OngoingStubbing(Utils.notNull(answers, "answers"));
   }

}
