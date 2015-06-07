/**
 * Copyright 2015 XpertSoftware
 * <p/>
 * Created By: cfloersch
 * Date: 6/3/2015
 */
package org.xpertss.proximo;

import xpertss.proximo.Answer;
import xpertss.proximo.Matcher;

import java.util.LinkedList;
import java.util.List;

public class OngoigStubbing<T> {



   private Answer<T> result;
   private List<Matcher<?>> argMatchers = new LinkedList<>();

   private OngoigStubbing(Answer<T> result)
   {
      this.result = result;
   }


   public void addArgumentMatcher(Matcher<?> matcher)
   {
      argMatchers.add(matcher);
   }



   public Answer<T> getResult()
   {
      return result;
   }

   public Matcher<?>[] getArgumentMatchers()
   {
      return argMatchers.toArray(new Matcher<?>[argMatchers.size()]);
   }



   public static <T> OngoigStubbing<T> create(Answer<T> result)
   {
      return new OngoigStubbing<>(result);
   }


}
