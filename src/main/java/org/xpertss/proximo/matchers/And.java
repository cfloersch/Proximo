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
import java.util.List;



@SuppressWarnings("unchecked")
public class And implements Matcher, Serializable {

   private static final long serialVersionUID = -4624719625691177501L;
   private final List<Matcher> matchers;
   private final int specificity;

   public And(List<Matcher> matchers)
   {
      this.matchers = Utils.notNull(matchers);
      this.specificity = Utils.computeSpecificity(matchers);
   }

   public boolean matches(Object actual)
   {
      for (Matcher matcher : matchers) {
         if (!matcher.matches(actual)) {
            return false;
         }
      }
      return true;
   }

   @Override
   public int specificity()
   {
      return specificity;
   }

}
