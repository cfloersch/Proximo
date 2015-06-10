package org.xpertss.proximo;

import org.junit.Before;
import org.junit.Test;
import org.xpertss.proximo.answers.DoesNothingAnswer;
import org.xpertss.proximo.matchers.Any;
import org.xpertss.proximo.matchers.Equals;
import org.xpertss.proximo.matchers.InstanceOf;
import org.xpertss.proximo.util.Lists;
import org.xpertss.proximo.util.Utils;
import xpertss.proximo.Answer;
import xpertss.proximo.Invocation;
import xpertss.proximo.Matcher;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.TreeSet;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by cfloersch on 6/8/2015.
 */
public class ProxyRuleTest {

   private Invocation invocation;
   private Queue<Answer<?>> answers;

   @Before
   public void setUp()
   {
      invocation = mock(Invocation.class);
      answers = new LinkedList<>();
   }

   @Test
   public void testMatchZeroArg()
   {
      when(invocation.getArguments()).thenReturn(null, new Object[0]);

      List<Matcher> matchers = Collections.emptyList();
      ProxyRule rule = new ProxyRule(matchers, answers, 0);

      assertTrue(rule.matches(invocation));
      assertTrue(rule.matches(invocation));
   }

   @Test
   public void testMatchStringArg()
   {
      when(invocation.getArguments()).thenReturn(new Object[] { "hello" }, new Object[] { null });

      List<Matcher> matchers = new ArrayList<>();
      matchers.add(new InstanceOf(String.class));
      ProxyRule rule = new ProxyRule(matchers, answers, 0);

      assertTrue(rule.matches(invocation));
      assertFalse(rule.matches(invocation));
   }

   @Test
   public void testMatchStringIntegerArgs()
   {
      when(invocation.getArguments()).thenReturn(new Object[] { "hello", Integer.valueOf(1) });

      List<Matcher> matchers = new ArrayList<>();
      matchers.add(new InstanceOf(String.class));
      matchers.add(new InstanceOf(Integer.class));
      ProxyRule rule = new ProxyRule(matchers, answers, 0);

      assertTrue(rule.matches(invocation));
   }

   @Test
   public void testMatchFails()
   {
      when(invocation.getArguments()).thenReturn(new Object[]{"hello"});

      List<Matcher> matchers = new ArrayList<>();
      matchers.add(new Equals("Goodbye"));
      ProxyRule rule = new ProxyRule(matchers, answers, 0);

      assertFalse(rule.matches(invocation));
   }

   @Test
   public void testMatchSuccess()
   {
      when(invocation.getArguments()).thenReturn(new Object[]{"hello"});

      List<Matcher> matchers = new ArrayList<>();
      matchers.add(new Equals("hello"));
      ProxyRule rule = new ProxyRule(matchers, answers, 0);

      assertTrue(rule.matches(invocation));
   }


   @Test
   public void testGetAnswerSingle()
   {
      Answer answer = new DoesNothingAnswer();
      answers.offer(answer);

      ProxyRule rule = new ProxyRule(Collections.<Matcher>emptyList(), answers, 0);
      assertSame(answer, rule.getAnswer());
      assertSame(answer, rule.getAnswer());
   }

   @Test
   public void testGetAnswerChained()
   {
      Answer first = new DoesNothingAnswer();
      answers.offer(first);
      Answer second = new DoesNothingAnswer();
      answers.offer(second);

      ProxyRule rule = new ProxyRule(Collections.<Matcher>emptyList(), answers, 0);
      assertSame(first, rule.getAnswer());
      assertSame(second, rule.getAnswer());
      assertSame(second, rule.getAnswer());
   }

   private Queue<Answer<?>> result = new LinkedList<>();


   @Test
   public void testOrderingSameSpecificity()
   {
      // Most recently added should have precedence
      List<Matcher> matchers = new ArrayList<>();
      matchers.add(Any.ANY);

      Set<ProxyRule> rules = new TreeSet<>();
      rules.add(new ProxyRule(matchers, result, 1));
      rules.add(new ProxyRule(matchers, result, 2));

      ProxyRule[] ordered = rules.toArray(new ProxyRule[rules.size()]);

      assertEquals(2, ordered[0].getSequence());
      assertEquals(1, ordered[1].getSequence());
   }

   @Test
   public void testOrderingDifferentSpecificity()
   {
      List<Matcher> ANY = new ArrayList<>();
      ANY.add(Any.ANY);

      List<Matcher> INSTANCE = new ArrayList<>();
      INSTANCE.add(new InstanceOf(String.class));

      Set<ProxyRule> rules = new TreeSet<>();
      rules.add(new ProxyRule(ANY, result, 2));
      rules.add(new ProxyRule(INSTANCE, result, 1));

      ProxyRule[] ordered = rules.toArray(new ProxyRule[rules.size()]);

      assertEquals(1, ordered[0].getSequence());
      assertEquals(2, ordered[1].getSequence());
   }



}