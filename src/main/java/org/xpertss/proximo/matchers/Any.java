/**
 * Copyright 2015 XpertSoftware
 * <p/>
 * Created By: cfloersch
 * Date: 6/3/2015
 */
package org.xpertss.proximo.matchers;

import java.io.Serializable;

import org.hamcrest.Description;
import org.xpertss.proximo.ArgumentMatcher;

@SuppressWarnings("unchecked")
public class Any extends ArgumentMatcher implements Serializable {

   private static final long serialVersionUID = -4062420125651019029L;
   public static final Any ANY = new Any();

   private Any() {}

   public boolean matches(Object actual) {
      return true;
   }

   public void describeTo(Description description) {
      description.appendText("<any>");
   }
}