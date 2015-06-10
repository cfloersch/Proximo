/**
 * Copyright 2015 XpertSoftware
 * <p/>
 * Created By: cfloersch
 * Date: 6/3/2015
 */
package org.xpertss.proximo.matchers;

import xpertss.proximo.Matcher;

import java.io.Serializable;



public class Contains implements Matcher, Serializable {

   private static final long serialVersionUID = -1909837398271763801L;
   private final String substring;

   public Contains(String substring) {
      this.substring = substring;
   }

   public boolean matches(Object actual) {
      return actual != null && ((String) actual).contains(substring);
   }

   @Override
   public int specificity() { return VALUE_SPECIFICITY; }
}