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
import java.util.ArrayList;
import java.util.List;

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
      throw throwable.fillInStackTrace();
   }

   public Class<?> getExceptionType()
   {
      return throwable.getClass();
   }

}
