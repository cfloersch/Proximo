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


public class StartsWith extends ArgumentMatcher<String> implements Serializable {

   private static final long serialVersionUID = -5978092285707998431L;
   private final String prefix;

   public StartsWith(String prefix) {
      this.prefix = prefix;
   }

   public boolean matches(Object actual) {
      return actual != null && ((String) actual).startsWith(prefix);
   }

   public void describeTo(Description description) {
      description.appendText("startsWith(\"" + prefix + "\")");
   }
}