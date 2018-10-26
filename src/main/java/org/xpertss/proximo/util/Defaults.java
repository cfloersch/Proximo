/**
 * Copyright 2015 XpertSoftware
 * <p>
 * Created By: cfloersch
 * Date: 6/7/2015
 */
package org.xpertss.proximo.util;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.xpertss.proximo.util.Primitives.defaultValueForPrimitiveOrWrapper;
import static org.xpertss.proximo.util.Primitives.isPrimitiveOrWrapper;

/**
 *
 */
public class Defaults {

   public static <T> T returnFor(Class<T> clazz)
   {
      if(clazz == List.class) {
         return clazz.cast(Collections.emptyList());
      } else if(clazz == Map.class) {
         return clazz.cast(Collections.emptyMap());
      } else if(clazz == Set.class) {
         return clazz.cast(Collections.emptySet());
      } else if(clazz == Collection.class) {
         return clazz.cast(Collections.emptyList());
      } else if(clazz == String.class) {
         return clazz.cast("");
      } else  if (isPrimitiveOrWrapper(clazz)) {
         return defaultValueForPrimitiveOrWrapper(clazz);
      }
      return null;
   }

   public static String returnString()
   {
      return "";
   }

   public static Map returnMap()
   {
      return Collections.emptyMap();
   }

   public static List returnList()
   {
      return Collections.emptyList();
   }

   public static Set returnSet()
   {
      return Collections.emptySet();
   }

   public static <T> T returnFor(T instance)
   {
      return (instance == null) ? null : (T) returnFor(instance.getClass());
   }

   public static <T> T returnNull()
   {
      return null;
   }
}
