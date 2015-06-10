/**
 * Copyright 2015 XpertSoftware
 * <p/>
 * Created By: cfloersch
 * Date: 6/3/2015
 */
package org.xpertss.proximo.matchers;

import xpertss.proximo.Matcher;

import java.io.Serializable;


@SuppressWarnings("unchecked")
public class Any implements Matcher, Serializable {

   private static final long serialVersionUID = -4062420125651019029L;
   public static final Any ANY = new Any();

   private Any() {}

   public boolean matches(Object actual)
   {
      return true;
   }

   @Override
   public int specificity()
   {
      return ANY_SPECIFICITY;
   }

}