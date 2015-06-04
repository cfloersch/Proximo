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


public class InstanceOf extends ArgumentMatcher<Object> implements Serializable {

   private static final long serialVersionUID = 517358915876138366L;
   private final Class<?> clazz;

   public InstanceOf(Class<?> clazz) {
      this.clazz = clazz;
   }

   public boolean matches(Object actual) {
      return (actual != null) && clazz.isAssignableFrom(actual.getClass());
   }

   public void describeTo(Description description) {
      description.appendText("isA(" + clazz.getName() + ")");
   }
}