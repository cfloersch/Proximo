/**
 * Copyright 2015 XpertSoftware
 * <p/>
 * Created By: cfloersch
 * Date: 6/10/2015
 */
package org.xpertss.proximo.util;

import java.util.ArrayList;
import java.util.List;

public class Lists {


   public static <T> List<T> tail(List<T> source, int start)
   {
      if(start >= source.size()) throw new IllegalArgumentException("invalid start index");
      List<T> list = new ArrayList<>();
      for(int i = start; i < source.size(); i++) {
         list.add(source.get(i));
      }
      return list;
   }

   public static <T> List<T> subList(List<T> source, int start, int end)
   {
      if(end - start < 0) throw new IllegalArgumentException("start after end");
      List<T> list = new ArrayList<>(end - start);
      for(int i = start; i < end; i++) {
         list.add(source.get(i));
      }
      return list;
   }

   public static <T> List<T> join(Iterable<T>... iterables)
   {
      List<T> list = new ArrayList<>();
      for(Iterable<T> iterable : iterables) {
         for(T item : iterable) list.add(item);
      }
      return list;
   }

}
