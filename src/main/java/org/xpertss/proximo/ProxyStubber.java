package org.xpertss.proximo;

import org.xpertss.proximo.answers.DoesNothingAnswer;
import org.xpertss.proximo.answers.ForwardCallAnswer;
import org.xpertss.proximo.answers.ReturnsAnswer;
import org.xpertss.proximo.answers.ThrowsAnswer;
import org.xpertss.proximo.util.Utils;
import xpertss.proximo.Answer;
import xpertss.proximo.Stubber;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.LinkedList;
import java.util.Queue;

/**
 *
 */
public class ProxyStubber implements Stubber {

   private final Queue<Answer<?>> answers = new LinkedList<>();
   private final StubbingProgress progress;


   public ProxyStubber(StubbingProgress progress, Answer<?> answer)
   {
      this.progress = Utils.notNull(progress, "progress");
      answers.offer(Utils.notNull(answer, "answer"));
   }

   @Override
   public <T> T when(T proxy)
   {
      InvocationHandler handler = Proxy.getInvocationHandler(Utils.notNull(proxy, "proxy"));
      if(handler instanceof ProximoHandler) {
         progress.stubbingStarted(answers);
         return proxy;
      }
      throw new IllegalArgumentException("not a proximo proxy instance");
   }




   @Override
   public Stubber doThrow(Throwable toBeThrown)
   {
      answers.offer(new ThrowsAnswer(toBeThrown));
      return this;
   }

   @Override
   public Stubber doAnswer(Answer answer)
   {
      if(answer == null) throw new NullPointerException("answer");
      answers.offer(answer);
      return this;
   }

   @Override
   public Stubber doNothing()
   {
      answers.offer(new DoesNothingAnswer());
      return this;
   }

   @Override
   public Stubber doReturn(Object toBeReturned)
   {
      answers.offer(new ReturnsAnswer(toBeReturned));
      return this;
   }

   @Override
   public Stubber doForwardCall()
   {
      answers.offer(new ForwardCallAnswer());
      return this;
   }
}
