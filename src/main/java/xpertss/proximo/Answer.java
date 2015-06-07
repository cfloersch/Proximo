/**
 * Copyright 2015 XpertSoftware
 * <p/>
 * Created By: cfloersch
 * Date: 6/3/2015
 */
package xpertss.proximo;

/**
 * Generic interface to be used for configuring a proxy's answer.
 * <p/>
 * Answer specifies an action that is executed and a return value that is
 * returned when you interact with the proxy.
 * <p>
 * Example of stubbing a proxy with custom answer:
 *
 * <pre class="code"><code class="java">
 * doAnswer(new Answer() {
 *     Object answer(Invocation invocation) {
 *         String arg = invocation.getArgument(0, String.class);
 *         if("foo".equals(arg)) return arg;
 *         return null;
 *     }
 * }).when(proxy).someMethod(anyString());
 *
 * // Following prints "foo"
 * System.out.println(proxy.someMethod("foo"));
 *
 * // Following prints "null"
 * System.out.println(proxy.someMethod("bar"));
 * </code></pre>
 *
 * @param <T> the type to return.
 */
public interface Answer<T> {

   /**
    * @param invocation the invocation on the proxy.
    * @return the value to be returned
    * @throws Throwable the throwable to be thrown
    */
   T answer(Invocation invocation) throws Throwable;
}