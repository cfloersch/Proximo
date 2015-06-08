package org.xpertss.proximo;

import org.junit.Before;
import org.junit.Test;
import org.xpertss.proximo.answers.DoesNothingAnswer;
import org.xpertss.proximo.matchers.Equals;
import org.xpertss.proximo.matchers.InstanceOf;
import xpertss.proximo.Answer;
import xpertss.proximo.Invocation;
import xpertss.proximo.Matcher;

import java.util.LinkedList;
import java.util.Queue;

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

      Matcher[] matchers = new Matcher[0];
      ProxyRule rule = new ProxyRule(matchers, answers);

      assertTrue(rule.matches(invocation));
      assertTrue(rule.matches(invocation));
   }

   @Test
   public void testMatchStringArg()
   {
      when(invocation.getArguments()).thenReturn(new Object[]{"hello"}, new Object[]{null});

      Matcher[] matchers = new Matcher[] {new InstanceOf(String.class) };
      ProxyRule rule = new ProxyRule(matchers, answers);

      assertTrue(rule.matches(invocation));
      assertFalse(rule.matches(invocation));
   }

   @Test
   public void testMatchStringIntegerArgs()
   {
      when(invocation.getArguments()).thenReturn(new Object[]{"hello", Integer.valueOf(1)});

      Matcher[] matchers = new Matcher[] { new InstanceOf(String.class), new InstanceOf(Integer.class) };
      ProxyRule rule = new ProxyRule(matchers, answers);

      assertTrue(rule.matches(invocation));
   }

   @Test
   public void testMatchFails()
   {
      when(invocation.getArguments()).thenReturn(new Object[]{"hello"});

      Matcher[] matchers = new Matcher[] { new Equals("Goodbye") };
      ProxyRule rule = new ProxyRule(matchers, answers);

      assertFalse(rule.matches(invocation));
   }

   @Test
   public void testMatchSuccess()
   {
      when(invocation.getArguments()).thenReturn(new Object[]{"hello"});

      Matcher[] matchers = new Matcher[] { new Equals("hello") };
      ProxyRule rule = new ProxyRule(matchers, answers);

      assertTrue(rule.matches(invocation));
   }


   @Test
   public void testGetAnswerSingle()
   {
      Answer answer = new DoesNothingAnswer();
      answers.offer(answer);

      ProxyRule rule = new ProxyRule(new Matcher[0], answers);
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

      ProxyRule rule = new ProxyRule(new Matcher[0], answers);
      assertSame(first, rule.getAnswer());
      assertSame(second, rule.getAnswer());
      assertSame(second, rule.getAnswer());
   }

}