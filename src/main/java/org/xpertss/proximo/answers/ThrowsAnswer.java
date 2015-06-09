/**
 * Copyright 2015 XpertSoftware
 * <p/>
 * Created By: cfloersch
 * Date: 6/7/2015
 */
package org.xpertss.proximo.answers;

import org.xpertss.proximo.util.Utils;
import xpertss.proximo.Answer;
import xpertss.proximo.Invocation;

import java.io.Serializable;

/**
 *
 */
public class ThrowsAnswer implements Answer<Object>, Serializable {

   private static final long serialVersionUID = 1128820328555183980L;
   private final Throwable throwable;

   public ThrowsAnswer(Throwable toBeThrown)
   {
      this.throwable = Utils.notNull(toBeThrown, "toBeThrown");
   }

   public Object answer(Invocation invocation) throws Throwable
   {
      // TODO Need to filter out elements of the stack trace which point to this class.
      // Would like the top element to reflect the method being called.
      throw throwable.fillInStackTrace();
   }

   public Throwable getThrowable()
   {
      return throwable;
   }
}
