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


public class NotNull extends ArgumentMatcher<Object> implements Serializable {

   private static final long serialVersionUID = 7278261081285153228L;
   public static final NotNull NOT_NULL = new NotNull();

   private NotNull() {

   }

   public boolean matches(Object actual) {
      return actual != null;
   }

   public void describeTo(Description description) {
      description.appendText("notNull()");
   }
}