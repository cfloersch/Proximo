/**
 * Copyright 2015 XpertSoftware
 * <p/>
 * Created By: cfloersch
 * Date: 6/3/2015
 */
package org.xpertss.proximo.matchers;

import java.io.Serializable;

import xpertss.proximo.Matcher;

@SuppressWarnings("unchecked")
public class AnyVararg implements Matcher, Serializable {

   private static final long serialVersionUID = 1700721373094731555L;
   public static final Matcher ANY_VARARG = new AnyVararg();

   public boolean matches(Object arg) {
      return true;
   }
}