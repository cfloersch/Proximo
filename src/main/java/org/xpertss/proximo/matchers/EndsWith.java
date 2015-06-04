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


public class EndsWith extends ArgumentMatcher<String> implements Serializable {

   private static final long serialVersionUID = 8556443228350129421L;
   private final String suffix;

   public EndsWith(String suffix) {
      this.suffix = suffix;
   }

   public boolean matches(Object actual) {
      return actual != null && ((String) actual).endsWith(suffix);
   }

   public void describeTo(Description description) {
      description.appendText("endsWith(\"" + suffix + "\")");
   }
}