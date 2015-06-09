/**
 * Copyright 2015 XpertSoftware
 * <p/>
 * Created By: cfloersch
 * Date: 6/7/2015
 */
package org.xpertss.proximo;


import org.xpertss.proximo.util.Utils;
import xpertss.proximo.Answer;
import xpertss.proximo.Invocation;
import xpertss.proximo.Matcher;

import java.util.Queue;

public class ProxyRule {

   private final Queue<Answer<?>> answers;
   private final Matcher[] matchers;

   ProxyRule(Matcher[] matchers, Queue<Answer<?>> answers)
   {
      this.matchers = Utils.notNull(matchers, "matchers");
      this.answers = Utils.notNull(answers, "answers");
   }

   public boolean matches(Invocation invocation)
   {
      // TODO Need to be a bit more sophisticated with Varargs which will always be the last matcher
      // in the batch if it exists at all.
      Object[] args = invocation.getArguments();
      for(int i = 0; i < matchers.length; i++) {
         if(!matchers[i].matches(args[i])) return false;
      }
      return true;
   }

   public Answer<?> getAnswer()
   {
      return (answers.size() > 1) ? answers.poll() : answers.peek();
   }

}
