package org.xpertss.proximo.util;

/**
 * Created by cfloersch on 6/8/2015.
 */
public class Utils {

   public static Object[] clone(Object[] array)
   {
      return (array != null) ? array.clone() : new Object[0];
   }

   public static <T> T notNull(T obj) {
      if (obj == null) throw new NullPointerException();
      return obj;
   }

   public static <T> T notNull(T obj, String message)
   {
      if (obj == null) throw new NullPointerException(message);
      return obj;
   }

}
