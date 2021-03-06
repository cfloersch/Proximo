/**
 * Copyright 2015 XpertSoftware
 * <p>
 * Created By: cfloersch
 * Date: 6/3/2015
 */
package org.xpertss.proximo.matchers;

import org.xpertss.proximo.util.Utils;

import java.lang.reflect.Array;

public class Equality {

   public static boolean areEqual(Object o1, Object o2) {
      if (o1 == o2 ) {
         return true;
      } else if (o1 == null || o2 == null) {
         return o1 == null && o2 == null;
      } else if (Utils.isArray(o1)) {
         return Utils.isArray(o2) && areArraysEqual(o1, o2);
      } else {
         return o1.equals(o2);
      }
   }

   static boolean areArraysEqual(Object o1, Object o2) {
      return areArrayLengthsEqual(o1, o2)
         && areArrayElementsEqual(o1, o2);
   }

   static boolean areArrayLengthsEqual(Object o1, Object o2) {
      return Array.getLength(o1) == Array.getLength(o2);
   }

   static boolean areArrayElementsEqual(Object o1, Object o2) {
      for (int i = 0; i < Array.getLength(o1); i++) {
         if (!areEqual(Array.get(o1, i), Array.get(o2, i))) return false;
      }
      return true;
   }

}