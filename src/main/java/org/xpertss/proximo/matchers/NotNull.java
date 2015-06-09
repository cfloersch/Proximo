/**
 * Copyright 2015 XpertSoftware
 * <p/>
 * Created By: cfloersch
 * Date: 6/3/2015
 */
package org.xpertss.proximo.matchers;

import xpertss.proximo.Matcher;

import java.io.Serializable;

public class NotNull implements Matcher, Serializable {

   private static final long serialVersionUID = 7278261081285153228L;
   public static final NotNull NOT_NULL = new NotNull();

   private NotNull() {

   }

   public boolean matches(Object actual) {
      return actual != null;
   }

}