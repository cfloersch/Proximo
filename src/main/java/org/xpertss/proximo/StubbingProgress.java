/**
 * Copyright 2015 XpertSoftware
 * <p>
 * Created By: cfloersch
 * Date: 6/3/2015
 */
package org.xpertss.proximo;

import xpertss.proximo.Answer;
import xpertss.proximo.Matcher;

import java.lang.reflect.Method;
import java.util.Queue;

public class StubbingProgress {

   private static final ThreadLocal<OngoingStubbing> progress = new ThreadLocal<>();


   public void stubbingStarted(Queue<Answer<?>> answers)
   {
      progress.set(OngoingStubbing.create(answers));
   }

   public boolean isStubbing()
   {
      return progress.get() != null;
   }

   public ProxyRule stubbingComplete(Object proxy, Method method, Object[] args)
   {
      OngoingStubbing stubbing = progress.get();
      progress.remove();
      return stubbing.stub(proxy, method, args);
   }


   /**
    * Report a matcher being created, presumably for stubbing in method call
    * arguments.
    *
    * @param matcher The matcher that was created.
    */
   public void reportMatcher(Matcher<?> matcher)
   {
      OngoingStubbing stubbing = progress.get();
      if(stubbing != null) stubbing.reportMatcher(matcher);
   }


}
