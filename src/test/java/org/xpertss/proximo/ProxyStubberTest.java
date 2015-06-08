package org.xpertss.proximo;

import org.junit.Before;
import org.junit.Test;
import org.xpertss.proximo.answers.DoesNothingAnswer;
import org.xpertss.proximo.answers.ForwardCallAnswer;
import xpertss.proximo.Matcher;
import xpertss.proximo.Stubber;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
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
      doCallRealMethod().when(progress).stubbingStarted(any(OngoingStubbing.class));
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

      verify(progress, times(1)).stubbingStarted(any(OngoingStubbing.class));
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
      verify(progress, never()).stubbingStarted(any(OngoingStubbing.class));
      stubber = stubber.doForwardCall();
      verify(progress, never()).stubbingStarted(any(OngoingStubbing.class));
      stubber = stubber.doReturn(1000);
      verify(progress, never()).stubbingStarted(any(OngoingStubbing.class));
      stubber = stubber.doThrow(new RuntimeException());
      verify(progress, never()).stubbingStarted(any(OngoingStubbing.class));
      stubber = stubber.doAnswer(new ForwardCallAnswer());
      verify(progress, never()).stubbingStarted(any(OngoingStubbing.class));
      stubber = stubber.doNothing();
      verify(progress, never()).stubbingStarted(any(OngoingStubbing.class));

      Runnable stubProxy = stubber.when(proxy);
      verify(progress, times(1)).stubbingStarted(any(OngoingStubbing.class));
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


}