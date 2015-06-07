/**
 * Copyright 2015 XpertSoftware
 * <p/>
 * Created By: cfloersch
 * Date: 6/3/2015
 */
package xpertss.proximo;

/**
 * Allows to choose a method when stubbing
 * <p>
 * Example:
 * <pre class="code"><code class="java">
 *   doThrow(new RuntimeException()).when(proxiedList).clear();
 *
 *   // following throws RuntimeException:
 *   proxiedList.clear();
 * </code></pre>
 *
 * Also useful when stubbing consecutive calls:
 *
 * <pre class="code"><code class="java">
 *   doThrow(new RuntimeException("one")).
 *   doThrow(new RuntimeException("two"))
 *   .when(proxy).someVoidMethod();
 * </code></pre>
 *
 * Read more about those methods:
 * <p>
 * {@link Proximo#doThrow(Throwable)}
 * <p>
 * {@link Proximo#doAnswer(Answer)}
 * <p>
 * {@link Proximo#doNothing()}
 * <p>
 * {@link Proximo#doReturn(Object)}
 * <p>
 * {@link Proximo#doProxyCall()}
 * <p>
 *
 * See examples in javadoc for {@link Proximo}
 */
public interface Stubber {

   /**
    * Allows to choose the method to stub.
    * <p>
    * Example:
    * <pre class="code"><code class="java">
    *   doThrow(new RuntimeException())
    *   .when(proxyList).clear();
    *
    *   //following throws RuntimeException:
    *   proxyList.clear();
    * </code></pre>
    *
    * Read more about those methods:
    * <p>
    * {@link Proximo#doThrow(Throwable)}
    * <p>
    * {@link Proximo#doAnswer(Answer)}
    * <p>
    * {@link Proximo#doNothing()}
    * <p>
    * {@link Proximo#doReturn(Object)}
    * <p>
    *
    *  See examples in javadoc for {@link Proximo}
    *
    * @param proxy The proxy
    * @return select method for stubbing
    */
   <T> T when(T proxy);

   /**
    * Use it for stubbing consecutive calls:
    * <pre class="code"><code class="java">
    *   doThrow(new RuntimeException("one")).
    *   doThrow(new RuntimeException("two"))
    *   .when(proxy).someMethod();
    * </code></pre>
    * See javadoc for {@link Proximo#doThrow(Throwable)}
    *
    * @param toBeThrown to be thrown when the stubbed method is called
    * @return stubber - to select a method for stubbing
    */
   Stubber doThrow(Throwable toBeThrown);


   /**
    * Use it for stubbing consecutive calls:
    * <pre class="code"><code class="java">
    *   doAnswer(answerOne).
    *   doAnswer(answerTwo)
    *   .when(proxy).someMethod();
    * </code></pre>
    * See javadoc for {@link Proximo#doAnswer(Answer)}
    *
    * @param answer to answer when the stubbed method is called
    * @return stubber - to select a method for stubbing
    */
   Stubber doAnswer(Answer answer);

   /**
    * Use it for stubbing consecutive calls:
    * <pre class="code"><code class="java">
    *   doNothing().
    *   doThrow(new RuntimeException("two"))
    *   .when(proxy).someMethod();
    * </code></pre>
    * See javadoc for {@link Proximo#doNothing()}
    *
    * @return stubber - to select a method for stubbing
    */
   Stubber doNothing();

   /**
    * Use it for stubbing consecutive calls.
    * <p>
    * See javadoc for {@link Proximo#doReturn(Object)}
    *
    * @param toBeReturned to be returned when the stubbed method is called
    * @return stubber - to select a method for stubbing
    */
   Stubber doReturn(Object toBeReturned);

   /**
    * Use it for stubbing consecutive calls.
    * <p>
    * See javadoc for {@link Proximo#doProxyCall()}
    *
    * @return stubber - to select a method for stubbing
    */
   Stubber doProxyCall();

}
