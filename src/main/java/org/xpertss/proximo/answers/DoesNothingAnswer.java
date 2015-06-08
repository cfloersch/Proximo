package org.xpertss.proximo.answers;

import org.xpertss.proximo.util.Defaults;
import xpertss.proximo.Answer;
import xpertss.proximo.Invocation;

import java.io.Serializable;

/**
 * Returns the default value for the proxied method's return type
 * but does NOT call the underlying proxied method.
 */
public class DoesNothingAnswer implements Answer<Object>, Serializable {

   private static final long serialVersionUID = 4840880517740698416L;

   public Object answer(Invocation invocation) throws Throwable
   {
      return Defaults.returnFor(invocation.getMethod().getReturnType());
   }

}
