/**
 * Copyright 2015 XpertSoftware
 * <p/>
 * Created By: cfloersch
 * Date: 6/3/2015
 */
package org.xpertss.proximo;

import xpertss.proximo.Answer;
import xpertss.proximo.Invocation;
import xpertss.proximo.Matcher;
import xpertss.proximo.Stubber;

import java.lang.reflect.Method;

public class StubbingProgress {

   private static final ThreadLocal<OngoigStubbing<?>> progress = new ThreadLocal<>();

   /**
    * Called by Stubber when {@link Stubber#when} is called.
    *
    * @param answer The answer that will be invoked when the stubbed method is
    *               called.
    */
   public <T> void startStubbing(Answer<T> answer)
   {
      progress.set(OngoigStubbing.create(answer));
   }

   public boolean isStubbing()
   {
      return progress.get() != null;
   }

   /**
    * Called when the proxied method is invoked to complete stubbing
    *
    * @param invocation The proxy call.
    */
   public ProxyRule completeStubbing(Invocation invocation)
   {
      Method method = invocation.getMethod();
      Class[] argTypes = method.getParameterTypes();
      // TODO Match argument matchers with arguments
      // else take argument values and wrap in EqualsMatcher
      return null;
   }

   /**
    * Report a matcher being created, presumably for stubbing in method call
    * arguments.
    *
    * @param matcher The matcher that was created.
    */
   public void reportMatcher(Matcher<?> matcher)
   {
      OngoigStubbing<?> stubbing = progress.get();
      if(stubbing != null) stubbing.addArgumentMatcher(matcher);
   }


}
