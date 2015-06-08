package org.xpertss.proximo;

import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.xpertss.proximo.answers.DoesNothingAnswer;
import org.xpertss.proximo.answers.ForwardCallAnswer;
import org.xpertss.proximo.answers.ReturnsAnswer;
import org.xpertss.proximo.answers.ThrowsAnswer;
import xpertss.proximo.Matcher;
import xpertss.proximo.Stubber;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by cfloersch on 6/8/2015.
 */
public class ProxyStubberTest {

   private Runnable proxy;
   private Runnable runnable;
   private StubbingProgress progress;

   @Before
   public void setUp()
   {
      runnable = mock(Runnable.class);
      progress = mock(StubbingProgress.class);
      doCallRealMethod().when(progress).stubbingStarted(any(Queue.class));
      when(progress.isStubbing()).thenCallRealMethod();


      proxy = (Runnable) Proxy.newProxyInstance(
            Runnable.class.getClassLoader(),
            new Class[]{Runnable.class},
            ProximoHandler.create(progress, runnable));
   }



   @Test
   public void testDoNothing()
   {
      ProxyStubber stubber = new ProxyStubber(progress, new DoesNothingAnswer());
      Runnable stubProxy = stubber.when(proxy);

      verify(progress, times(1)).stubbingStarted(any(Queue.class));
      verify(progress, never()).reportMatcher(any(Matcher.class));
      verify(progress, never()).stubbingComplete(anyObject(), any(Method.class), any(Object[].class));

      stubProxy.run();

      verify(progress, never()).reportMatcher(any(Matcher.class));
      verify(progress, times(1)).stubbingComplete(anyObject(), any(Method.class), any(Object[].class));

   }

   @Test
   public void testChaining()
   {
      Stubber stubber = new ProxyStubber(progress, new DoesNothingAnswer());
      stubber = stubber.doForwardCall();
      stubber = stubber.doReturn(1000);
      stubber = stubber.doThrow(new RuntimeException());
      stubber = stubber.doAnswer(new ForwardCallAnswer());
      stubber = stubber.doNothing();

      verify(progress, never()).stubbingStarted(any(Queue.class));

      doAnswer(new Answer() {
         @Override
         public Object answer(InvocationOnMock invocation)
            throws Throwable
         {
            Queue<xpertss.proximo.Answer> answers = (Queue<xpertss.proximo.Answer>) invocation.getArguments()[0];
            assertEquals(DoesNothingAnswer.class, answers.poll().getClass());
            assertEquals(ForwardCallAnswer.class, answers.poll().getClass());
            assertEquals(ReturnsAnswer.class, answers.poll().getClass());
            assertEquals(ThrowsAnswer.class, answers.poll().getClass());
            assertEquals(ForwardCallAnswer.class, answers.poll().getClass());
            assertEquals(DoesNothingAnswer.class, answers.poll().getClass());

            return null;
         }
      }).when(progress).stubbingStarted(any(Queue.class));

      Runnable stubProxy = stubber.when(proxy);

      verify(progress, times(1)).stubbingStarted(any(Queue.class));
      verify(progress, never()).reportMatcher(any(Matcher.class));
      verify(progress, never()).stubbingComplete(anyObject(), any(Method.class), any(Object[].class));
   }


   @Test(expected = NullPointerException.class)
   public void testDoThrowNullException()
   {
      Stubber stubber = new ProxyStubber(progress, new DoesNothingAnswer());
      stubber.doThrow(null);
   }

   @Test(expected = NullPointerException.class)
   public void testDoAnswerNullException()
   {
      Stubber stubber = new ProxyStubber(progress, new DoesNothingAnswer());
      stubber.doAnswer(null);
   }


   @Test(expected = NullPointerException.class)
   public void testWhenNullProxy()
   {
      Stubber stubber = new ProxyStubber(progress, new DoesNothingAnswer());
      stubber.when(null);
   }

   @Test(expected = IllegalArgumentException.class)
   public void testWhenNotAProxy()
   {
      Stubber stubber = new ProxyStubber(progress, new DoesNothingAnswer());
      stubber.when(new ArrayList());
   }

   @Test(expected = IllegalArgumentException.class)
   public void testWhenNotAProximoProxy()
   {
      Stubber stubber = new ProxyStubber(progress, new DoesNothingAnswer());
      Runnable proxy = (Runnable) Proxy.newProxyInstance(Runnable.class.getClassLoader(), new Class[] { Runnable.class }, new InvocationHandler() {
         @Override public Object invoke(Object proxy, Method method, Object[] args)
            throws Throwable
         {
            return null;
         }
      });
      stubber.when(proxy);
   }

}