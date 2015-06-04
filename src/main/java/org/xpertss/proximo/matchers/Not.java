/**
 * Copyright 2015 XpertSoftware
 * <p/>
 * Created By: cfloersch
 * Date: 6/3/2015
 */
package org.xpertss.proximo.matchers;

import java.io.Serializable;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.xpertss.proximo.ArgumentMatcher;

@SuppressWarnings("unchecked")
public class Not extends ArgumentMatcher implements Serializable {

   private static final long serialVersionUID = 4627373642333593264L;
   private final Matcher first;

   public Not(Matcher first) {
      this.first = first;
   }

   public boolean matches(Object actual) {
      return !first.matches(actual);
   }

   public void describeTo(Description description) {
      description.appendText("not(");
      first.describeTo(description);
      description.appendText(")");
   }
}