/**
 * Copyright 2015 XpertSoftware
 * <p/>
 * Created By: cfloersch
 * Date: 6/3/2015
 */
package org.xpertss.proximo.matchers;

import xpertss.proximo.Matcher;

import java.io.Serializable;
import java.util.List;


@SuppressWarnings("unchecked")
public class Or implements Matcher, Serializable {

   private static final long serialVersionUID = 5888739035212283087L;
   private final List<Matcher> matchers;

   public Or(List<Matcher> matchers)
   {
      this.matchers = matchers;
   }

   public boolean matches(Object actual)
   {
      for (Matcher matcher : matchers) {
         if (matcher.matches(actual)) {
            return true;
         }
      }
      return false;
   }

   @Override
   public int specificity()
   {
      return MULTI_SPECIFICITY;
   }

}