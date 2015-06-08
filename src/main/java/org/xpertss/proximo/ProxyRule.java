/**
 * Copyright 2015 XpertSoftware
 * <p/>
 * Created By: cfloersch
 * Date: 6/7/2015
 */
package org.xpertss.proximo;


import xpertss.proximo.Answer;
import xpertss.proximo.Matcher;

public class ProxyRule<T> {

   private final Matcher[] matchers;
   private final Answer<T> handler;

   private ProxyRule(Matcher[] matchers, Answer<T> handler)
   {
      this.matchers = matchers;
      this.handler = handler;
   }

   public boolean matches(Object[] args)
   {
      // TODO Needs to be much more sophisticated
      // https://github.com/mockito/mockito/blob/master/src/org/mockito/internal/invocation/ArgumentsComparator.java

      if(args.length == matchers.length) {
         for(int i = 0; i < args.length; i++) {
            if(!matchers[i].matches(args[i])) return false;
         }
         return true;
      }
      return false;
   }

   public Answer<T> getAnswer()
   {
      return handler;
   }

}
