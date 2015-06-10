/**
 * Copyright 2015 XpertSoftware
 * <p/>
 * Created By: cfloersch
 * Date: 6/3/2015
 */
package org.xpertss.proximo.matchers;

import xpertss.proximo.Matcher;

import java.io.Serializable;


public class EndsWith implements Matcher, Serializable {

   private static final long serialVersionUID = 8556443228350129421L;
   private final String suffix;

   public EndsWith(String suffix) {
      this.suffix = suffix;
   }

   public boolean matches(Object actual) {
      return actual != null && ((String) actual).endsWith(suffix);
   }

   @Override
   public int specificity() { return SINGLE_SPECIFICITY; }
}