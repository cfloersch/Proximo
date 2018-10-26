/**
 * Copyright 2015 XpertSoftware
 * <p>
 * Created By: cfloersch
 * Date: 6/3/2015
 */
package org.xpertss.proximo.matchers;

import xpertss.proximo.Matcher;

import java.io.Serializable;


public class InstanceOf implements Matcher, Serializable {

   private static final long serialVersionUID = 517358915876138366L;
   private final Class<?> clazz;

   public InstanceOf(Class<?> clazz) {
      this.clazz = clazz;
   }

   public boolean matches(Object actual) {
      return (actual != null) && clazz.isAssignableFrom(actual.getClass());
   }

   @Override
   public int specificity() { return (clazz == Object.class) ? ANY_SPECIFICITY : INSTANCE_SPECIFICITY; }
}