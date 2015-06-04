/**
 * Copyright 2015 XpertSoftware
 * <p/>
 * Created By: cfloersch
 * Date: 6/3/2015
 */
package org.xpertss.proximo.matchers;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.xpertss.proximo.ArgumentMatcher;


@SuppressWarnings("unchecked")
public class And extends ArgumentMatcher implements Serializable {

   private static final long serialVersionUID = -4624719625691177501L;
   private final List<Matcher> matchers;

   public And(List<Matcher> matchers) {
      this.matchers = matchers;
   }

   public boolean matches(Object actual) {
      for (Matcher matcher : matchers) {
         if (!matcher.matches(actual)) {
            return false;
         }
      }
      return true;
   }

   public void describeTo(Description description) {
      description.appendText("and(");
      for (Iterator<Matcher> it = matchers.iterator(); it.hasNext();) {
         it.next().describeTo(description);
         if (it.hasNext()) {
            description.appendText(", ");
         }
      }
      description.appendText(")");
   }
}
