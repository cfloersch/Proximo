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


public class Null extends ArgumentMatcher<Object> implements Serializable {

   private static final long serialVersionUID = 2823082637424390314L;
   public static final Null NULL = new Null();

   private Null() {
   }

   public boolean matches(Object actual) {
      return actual == null;
   }

   public void describeTo(Description description) {
      description.appendText("isNull()");
   }
}