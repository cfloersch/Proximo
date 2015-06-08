/**
 * Copyright 2015 XpertSoftware
 * <p/>
 * Created By: cfloersch
 * Date: 6/3/2015
 */
package xpertss.proximo;


import org.xpertss.proximo.ProximoHandler;
import org.xpertss.proximo.ProxyStubber;
import org.xpertss.proximo.answers.DoesNothingAnswer;
import org.xpertss.proximo.answers.ForwardCallAnswer;
import org.xpertss.proximo.answers.ReturnsAnswer;
import org.xpertss.proximo.answers.ThrowsAnswer;

import java.lang.reflect.Proxy;

/**
 *
 */
public class Proximo extends Matchers {


   /**
    * Creates a proxy of the specified type for the specified instance.
    * <p/>
    * By default calls to any proxy method will be forwarded to the underlying
    * proxied instance. This behaviour may be overridden on a method by method
    * basis.
    *
    * @param interfaceType interface to proxy
    * @param instance instance to proxy to
    * @return proxy instance
    * @throws NullPointerException if the either the interface type or instance
    *    are {@code null}
    * @throws IllegalArgumentException if interface type is not an interface or
    *    the instance if not assignable to the interface type.
    */
   public static <T> T proxy(Class<T> interfaceType, T instance)
   {
      if(instance == null) throw new NullPointerException("instance");
      if(interfaceType == null) throw new NullPointerException("interfaceType");
      if(!interfaceType.isInterface()) throw new IllegalArgumentException("interfaceType not an interface");

      Object proxy = Proxy.newProxyInstance(
                        interfaceType.getClassLoader(),
                        new Class[] {interfaceType},
                        ProximoHandler.create(PROGRESS, instance));
      return interfaceType.cast(proxy);
   }














   /**
    * Use <code>doNothing()</code> to force method calls to do nothing. This will
    * prevent the call from being forwarded to the proxied instance. If the method
    * has a return type it will result in the default value for the given type being
    * returned.
    * <p/>
    * <ol>
    * <li>Stubbing consecutive calls on a void method:
    * <pre class="code"><code class="java">
    *   doNothing().
    *   doThrow(new RuntimeException())
    *   .when(proxy).someMethod();
    *
    *   //does nothing the first time:
    *   proxy.someMethod();
    *
    *   //throws RuntimeException the next time:
    *   proxy.someMethod();
    * </code></pre>
    * </li>
    * <li>When you want a method to do nothing:
    * <pre class="code"><code class="java">
    *   List list = new LinkedList();
    *   List proxy = proxy(List.class, list);
    *
    *   //let's make clear() do nothing
    *   doNothing().when(proxy).clear();
    *
    *   proxy.add("one");
    *
    *   //clear() does nothing, so the list still contains "one"
    *   proxy.clear();
    * </code></pre>
    * </li>
    * </ol>
    * <p>
    * See examples in javadoc for {@link Proximo} class
    *
    * @return stubber - to select a method for stubbing
    */
   public static Stubber doNothing()
   {
      return new ProxyStubber(PROGRESS, new DoesNothingAnswer());
   }


   /**
    * Enables stubbing methods with return values. Use it when you want the proxy
    * to return a particular value when a particular method is called.
    * <p/>
    * Examples:
    *
    * <pre class="code"><code class="java">
    * <b>doReturn(10)</b>.when(proxy).someMethod();
    *
    * // You can use flexible argument matchers, e.g:
    * <b>doReturn(10)</b>.when(proxy).someMethod(<b>anyString()</b>);
    *
    * // You can chain different behaviors for consecutive method calls.
    * // Last stubbing (e.g: doThrow(new RuntimeException())) determines the
    * //  behavior of further consecutive calls.
    * <b>doReturn(10)</b>
    *  .doThrow(new RuntimeException())
    *  .when(proxy).someMethod(<b>anyString()</b>);
    * </code></pre>
    *
    * For stubbing methods with throwables see: {@link Proximo#doThrow(Throwable)}
    * <p>
    * Once stubbed, the method will always return stubbed value regardless of how
    * many times it is called.
    * <p>
    * See examples in javadoc for {@link Proximo} class
    *
    * @param toBeReturned object instance to be returned when the stubbed method
    *                     is called
    * @return stubber - to select a method for stubbing
    */
   public static Stubber doReturn(Object toBeReturned)
   {
      return new ProxyStubber(PROGRESS, new ReturnsAnswer(toBeReturned));
   }


   /**
    * Use <code>doThrow()</code> when you want to stub a method with an exception.
    * <p/>
    * Example:
    *
    * <pre class="code"><code class="java">
    *   doThrow(new RuntimeException()).when(proxy).someMethod();
    * </code></pre>
    *
    * @param toBeThrown to be thrown when the stubbed method is called
    * @return stubber - to select a method for stubbing
    * @throws NullPointerException if the supplied throwable is {@code null}
    */
   public static Stubber doThrow(Throwable toBeThrown)
   {
      return new ProxyStubber(PROGRESS, new ThrowsAnswer(toBeThrown));
   }





   /**
    * Use <code>doForwardCall()</code> when you want to call the corresponding
    * method on the proxied instance. <b>Beware that methods on proxies call
    * the corresponding method on the proxied instance by default!</b>
    * <p/>
    * However, doForwardCall() comes in handy when stubbing consecutive calls:
    * <p/>
    * <pre class="code"><code class="java">
    *   doForwardCall().
    *   doThrow(new RuntimeException())
    *   .when(proxy).someMethod();
    *
    *   // calls proxied method the first time:
    *   proxy.someMethod();
    *
    *   // throws RuntimeException the next time:
    *   proxy.someMethod();
    * </code></pre>
    * <p/>
    * See examples in javadoc for {@link Proximo} class
    *
    * @return stubber - to select a method for stubbing
    */
   public static Stubber doForwardCall()
   {
      return new ProxyStubber(PROGRESS, new ForwardCallAnswer());
   }






   /**
    * Use <code>doAnswer()</code> when you want to stub a method with a custom
    * {@link Answer}.
    * <p/>
    * Example:
    *
    * <pre class="code"><code class="java">
    *  doAnswer(new Answer() {
    *      public Object answer(InvocationOnMock invocation) {
    *          String arg = invocation.getArgumentAt(0, String.class);
    *          if("foo".equals(arg)) return arg;
    *          return null;
    *      }})
    *  .when(proxy).someMethod();
    * </code></pre>
    * <p>
    * See examples in javadoc for {@link Proximo} class
    *
    * @param answer to answer when the stubbed method is called
    * @return stubber - to select a method for stubbing
    */
   public static Stubber doAnswer(Answer answer)
   {
      if(answer == null) throw new NullPointerException("answer");
      return new ProxyStubber(PROGRESS, answer);
   }

}
