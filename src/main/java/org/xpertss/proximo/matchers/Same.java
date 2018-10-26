/**
 * Copyright 2015 XpertSoftware
 * <p>
 * Created By: cfloersch
 * Date: 6/3/2015
 */
package org.xpertss.proximo.matchers;

import xpertss.proximo.Matcher;

import java.io.Serializable;


public class Same implements Matcher, Serializable {

   private static final long serialVersionUID = -1226959355938572597L;
   private final Object wanted;

   public Same(Object wanted)
   {
      this.wanted = wanted;
   }

   public boolean matches(Object actual)
   {
      return wanted == actual;
   }

   @Override
   public int specificity()
   {
      return VALUE_SPECIFICITY;
   }

}