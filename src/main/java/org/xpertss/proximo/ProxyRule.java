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

public class ProxyRule implements Comparable<ProxyRule> {

   private final Queue<Answer<?>> answers;
   private final Matcher[] matchers;
   private final int specificity;
   private final long seq;

   ProxyRule(Matcher[] matchers, Queue<Answer<?>> answers, long seq)
   {
      this.matchers = Utils.notNull(matchers, "matchers");
      this.answers = Utils.notNull(answers, "answers");
      this.seq = seq;
      this.specificity = computeSpecificity(matchers);
   }

   public boolean matches(Invocation invocation)
   {
      Object[] args = invocation.getArguments();
      for(int i = 0; i < matchers.length; i++) {
         if(!matchers[i].matches(args[i])) return false;
      }
      return true;
   }

   public int getSpecificity() { return specificity; }

   public long getSequence() { return seq; }

   public Answer<?> getAnswer()
   {
      return (answers.size() > 1) ? answers.poll() : answers.peek();
   }

   @Override
   public int compareTo(ProxyRule o)
   {
      int value = o.getSpecificity() - getSpecificity();
      return (value != 0) ? value : Utils.safeCast(getSequence() - o.getSequence());
   }


   private static int computeSpecificity(Matcher[] matchers)
   {
      int value = 0;
      for(Matcher matcher : matchers) value += matcher.specificity();
      return value;
   }
}
