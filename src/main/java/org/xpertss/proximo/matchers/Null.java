/**
 * Copyright 2015 XpertSoftware
 * <p/>
 * Created By: cfloersch
 * Date: 6/3/2015
 */
package org.xpertss.proximo.matchers;

import xpertss.proximo.Matcher;

import java.io.Serializable;


public class Null implements Matcher, Serializable {

   private static final long serialVersionUID = 2823082637424390314L;
   public static final Null NULL = new Null();

   private Null() {
   }

   public boolean matches(Object actual) {
      return actual == null;
   }

}