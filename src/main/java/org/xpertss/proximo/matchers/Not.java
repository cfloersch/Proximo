/**
 * Copyright 2015 XpertSoftware
 * <p/>
 * Created By: cfloersch
 * Date: 6/3/2015
 */
package org.xpertss.proximo.matchers;

import xpertss.proximo.Matcher;

import java.io.Serializable;


@SuppressWarnings("unchecked")
public class Not implements Matcher, Serializable {

   private static final long serialVersionUID = 4627373642333593264L;
   private final Matcher first;

   public Not(Matcher first) {
      this.first = first;
   }

   public boolean matches(Object actual) {
      return !first.matches(actual);
   }

}