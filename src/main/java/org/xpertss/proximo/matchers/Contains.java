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


public class Contains extends ArgumentMatcher<String> implements Serializable {

   private static final long serialVersionUID = -1909837398271763801L;
   private final String substring;

   public Contains(String substring) {
      this.substring = substring;
   }

   public boolean matches(Object actual) {
      return actual != null && ((String) actual).contains(substring);
   }

   public void describeTo(Description description) {
      description.appendText("contains(\"" + substring + "\")");
   }
}