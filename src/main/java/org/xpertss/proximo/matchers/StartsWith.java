/**
 * Copyright 2015 XpertSoftware
 * <p/>
 * Created By: cfloersch
 * Date: 6/3/2015
 */
package org.xpertss.proximo.matchers;

import xpertss.proximo.Matcher;

import java.io.Serializable;

public class StartsWith implements Matcher, Serializable {

   private static final long serialVersionUID = -5978092285707998431L;
   private final String prefix;

   public StartsWith(String prefix) {
      this.prefix = prefix;
   }

   public boolean matches(Object actual) {
      return actual != null && ((String) actual).startsWith(prefix);
   }

   @Override
   public int specificity() { return VALUE_SPECIFICITY; }
}