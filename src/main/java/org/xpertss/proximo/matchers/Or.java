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
public class Or extends ArgumentMatcher implements Serializable {

   private static final long serialVersionUID = 5888739035212283087L;
   private final List<Matcher> matchers;

   public Or(List<Matcher> matchers) {
      this.matchers = matchers;
   }

   public boolean matches(Object actual) {
      for (Matcher matcher : matchers) {
         if (matcher.matches(actual)) {
            return true;
         }
      }
      return false;
   }

   public void describeTo(Description description) {
      description.appendText("or(");
      for (Iterator<Matcher> it = matchers.iterator(); it.hasNext();) {
         it.next().describeTo(description);
         if (it.hasNext()) {
            description.appendText(", ");
         }
      }
      description.appendText(")");
   }
}