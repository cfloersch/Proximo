/**
 * Copyright 2015 XpertSoftware
 * <p/>
 * Created By: cfloersch
 * Date: 6/3/2015
 */
package org.xpertss.proximo.matchers;

import org.xpertss.proximo.util.Utils;
import xpertss.proximo.Matcher;

import java.io.Serializable;

public class Equals implements Matcher, Serializable {

   private static final long serialVersionUID = -3395637450058086891L;
   private final Object wanted;

   public Equals(Object wanted)
   {
      this.wanted = wanted;
   }

   public boolean matches(Object actual)
   {
      return Equality.areEqual(this.wanted, actual);
   }

   @Override
   public int specificity()
   {
      return Utils.isArray(wanted) ? MULTI_SPECIFICITY : SINGLE_SPECIFICITY;
   }

}