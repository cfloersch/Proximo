/**
 * Copyright 2015 XpertSoftware
 * <p>
 * Created By: cfloersch
 * Date: 6/7/2015
 */
package org.xpertss.proximo.matchers;

import org.xpertss.proximo.util.Utils;
import xpertss.proximo.Matcher;

import java.io.Serializable;
import java.util.List;

/**
 *
 */
public class Array implements Matcher, Serializable {

   private final List<Matcher> matchers;
   private final int specificity;

   public Array(List<Matcher> matchers)
   {
      this.matchers = Utils.notNull(matchers);
      this.specificity = Utils.computeSpecificity(matchers);
   }

   @Override
   public boolean matches(Object item)
   {
      if(item != null && item.getClass().isArray()) {
         Object[] items = (Object[]) item;
         if(items.length != matchers.size()) return false;
         for(int i = 0; i < items.length; i++) {
            if(!matchers.get(i).matches(items[i])) return false;
         }
         return true;
      }
      return false;
   }

   @Override
   public int specificity()
   {
      return specificity;
   }
}
