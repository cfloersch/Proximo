/**
 * Copyright 2015 XpertSoftware
 * <p/>
 * Created By: cfloersch
 * Date: 6/3/2015
 */
package org.xpertss.proximo.matchers;

import xpertss.proximo.Matcher;

import java.io.Serializable;

public class Matches implements Matcher, Serializable {

   private static final long serialVersionUID = 8787704593379472029L;
   private final String regex;

   public Matches(String regex) {
      this.regex = regex;
   }

   public boolean matches(Object actual) {
      return (actual instanceof String) && ((String) actual).matches(regex);
   }

   @Override
   public int specificity() { return SINGLE_SPECIFICITY; }
}