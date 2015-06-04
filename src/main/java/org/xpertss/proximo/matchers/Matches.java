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


public class Matches extends ArgumentMatcher<Object> implements Serializable {

   private static final long serialVersionUID = 8787704593379472029L;
   private final String regex;

   public Matches(String regex) {
      this.regex = regex;
   }

   public boolean matches(Object actual) {
      return (actual instanceof String) && ((String) actual).matches(regex);
   }

   public void describeTo(Description description) {
      description.appendText("matches(\"" + regex.replaceAll("\\\\", "\\\\\\\\") + "\")");
   }
}