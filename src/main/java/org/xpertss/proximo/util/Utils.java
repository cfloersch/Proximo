/**
 * Copyright 2015 XpertSoftware
 * <p/>
 * Created By: cfloersch
 * Date: 6/7/2015
 */
package org.xpertss.proximo.util;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 *
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


   public static <T> T first(T[] array)
   {
      return (array == null || array.length < 1) ? null : array[0];
   }

   public static <T> T last(T[] array)
   {
      return (array == null || array.length < 1) ? null : array[array.length - 1];
   }


   public static <T> T createProxy(Class<T> interfaceType, InvocationHandler handler)
   {
      if(handler == null) throw new NullPointerException("handler");
      if(interfaceType == null) throw new NullPointerException("interfaceType");
      if(!interfaceType.isInterface()) throw new IllegalArgumentException("interfaceType not an interface");

      Object proxy = Proxy.newProxyInstance(interfaceType.getClassLoader(),
                        new Class[] { interfaceType },
                        handler);
      return interfaceType.cast(proxy);
   }

}
