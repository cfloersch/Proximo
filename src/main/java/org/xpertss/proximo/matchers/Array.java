package org.xpertss.proximo.matchers;

import org.xpertss.proximo.util.Utils;
import xpertss.proximo.Matcher;

import java.io.Serializable;

/**
 *
 */
public class Array implements Matcher, Serializable {

   private final Matcher[] matchers;

   public Array(Matcher[] matchers)
   {
      this.matchers = Utils.notNull(matchers);
   }

   @Override
   public boolean matches(Object item)
   {
      if(item != null && item.getClass().isArray()) {
         Object[] items = (Object[]) item;
         if(items.length != matchers.length) return false;
         for(int i = 0; i < items.length; i++) {
            if(!matchers[i].matches(items[i])) return false;
         }
         return true;
      }
      return false;
   }
}
