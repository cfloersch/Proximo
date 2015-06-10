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

   // TODO It would be nice to test if the specified arg is
   // an array and that the component type matches the type
   // specified during construction.

   private static final long serialVersionUID = 1700721373094731555L;
   public static final Matcher ANY_VARARG = new AnyVararg();

   public boolean matches(Object arg) {
      return true;
   }


   // TODO The specificity should be 0 if type is Object.class otherwise it should be 1
   @Override
   public int specificity() { return ANY_SPECIFICITY; }
}