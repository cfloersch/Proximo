/**
 * Copyright 2015 XpertSoftware
 * <p>
 * Created By: cfloersch
 * Date: 6/7/2015
 */
package org.xpertss.proximo.answers;

import xpertss.proximo.Answer;
import xpertss.proximo.Invocation;

import java.io.Serializable;

/**
 * Forwards the call to the proxied instance.
 */
public class ForwardCallAnswer implements Answer<Object>, Serializable {

   private static final long serialVersionUID = 9057165148930624087L;

   public Object answer(Invocation invocation) throws Throwable
   {
      return invocation.forwardCall();
   }

}
