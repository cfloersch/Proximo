/**
 * Copyright 2015 XpertSoftware
 * <p/>
 * Created By: cfloersch
 * Date: 6/7/2015
 */
package org.xpertss.proximo.answers;

import xpertss.proximo.Answer;
import xpertss.proximo.Invocation;

import java.io.Serializable;

/**
 * Returns a given value
 */
public class ReturnsAnswer implements Answer<Object>, Serializable {

   private static final long serialVersionUID = -6245608253574215396L;
   private final Object value;

   public ReturnsAnswer(Object value)
   {
      this.value = value;
   }

   public Object answer(Invocation invocation) throws Throwable
   {
      return value;
   }


   public Class<?> getReturnType()
   {
      return (value == null) ? null : value.getClass();
   }


}
