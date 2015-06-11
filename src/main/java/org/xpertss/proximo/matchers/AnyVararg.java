/**
 * Copyright 2015 XpertSoftware
 * <p/>
 * Created By: cfloersch
 * Date: 6/3/2015
 */
package org.xpertss.proximo.matchers;

import java.io.Serializable;

import org.xpertss.proximo.util.Utils;
import xpertss.proximo.Matcher;

@SuppressWarnings("unchecked")
public class AnyVararg implements Matcher, Serializable {

   private static final long serialVersionUID = 1700721373094731555L;
   private final Class<?> clazz;

   public AnyVararg(Class<?> clazz)
   {
      this.clazz = clazz;
   }

   public boolean matches(Object arg)
   {
      return Utils.isArray(arg) && clazz.isAssignableFrom(arg.getClass().getComponentType());
   }


   @Override
   public int specificity() { return (clazz == Object.class) ? ANY_SPECIFICITY : INSTANCE_SPECIFICITY; }

}