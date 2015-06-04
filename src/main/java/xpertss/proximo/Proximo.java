/**
 * Copyright 2015 XpertSoftware
 * <p/>
 * Created By: cfloersch
 * Date: 6/3/2015
 */
package xpertss.proximo;

public class Proximo extends Matchers {


   /**
    * Create a proxy for the given instance of the specified interface type.
    *
    * @param interfaceType The proxy type
    * @param instance The instance to proxy
    * @return A proxy for the given instance
    */
   public static <T> T proxy(Class<T> interfaceType, T instance)
   {
      return null;
   }


   /**
    * Use <code>doReturn()</code> when you wish to override the return of a proxied method with
    * a static value.
    * <p/>
    * TODO Impl Docs
    * <pre class="code"><code class="java">
    *   List list = new LinkedList();
    *   List proxy = proxy(List.class, list);
    *
    *   // Force a method call to return a static value:
    *   doReturn("foo").when(proxy).get(0);
    * </code></pre>
    * <p/>
    * See examples in javadoc for {@link Proximo} class
    *
    * @param toBeReturned to be returned when the stubbed method is called
    * @return stubber - to select a method for stubbing
    */
   public static Stubber doReturn(Object toBeReturned) {
      return null;
   }





   /**
    * Use <code>doNothing()</code> for forcing void methods to do nothing.
    * <p/>
    * By default all method calls are forwarded to the proxied object. This is
    * helpful when you do not wish the calls to be forwarded to a void method.
    * <p/>
    * Examples:
    * <p/>
    * <ol>
    * <li>Stubbing consecutive calls on a void method:
    * <pre class="code"><code class="java">
    *   doNothing().
    *   doThrow(new RuntimeException())
    *   .when(proxy).someVoidMethod();
    *
    *   //does nothing the first time:
    *   proxy.someVoidMethod();
    *
    *   //throws RuntimeException the next time:
    *   proxy.someVoidMethod();
    * </code></pre>
    * </li>
    * <li>When you do not want calls forwarded to the proxied void method:
    * <pre class="code"><code class="java">
    *   List list = new LinkedList();
    *   List proxy = proxy(List.class list);
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
   public static Stubber doNothing() {
      return null;
   }


   /**
    * Use <code>doAnswer()</code> when you want to stub a method with a custom {@link Answer}.
    * <p/>
    * Example:
    *
    * <pre class="code"><code class="java">
    *  doAnswer(new Answer() {
    *      public Object answer(Invocation invocation) {
    *          String arg = invocation.getArgument(0, String.class);
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
   public static Stubber doAnswer(Answer answer) {
      return null;
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
    * @param toBeThrown exception to be thrown when the stubbed method is called
    * @return stubber - to select a method for stubbing
    */
   public static Stubber doThrow(Throwable toBeThrown) {
      return null;
   }





   /**
    * Use <code>doCallRealMethod()</code> when you want to call the real implementation of a method.
    * <p>
    * As usual you are going to read <b>the partial mock warning</b>:
    * Object oriented programming is more less tackling complexity by dividing the complexity into separate, specific, SRPy objects.
    * How does partial mock fit into this paradigm? Well, it just doesn't...
    * Partial mock usually means that the complexity has been moved to a different method on the same object.
    * In most cases, this is not the way you want to design your application.
    * <p>
    * However, there are rare cases when partial mocks come handy:
    * dealing with code you cannot change easily (3rd party interfaces, interim refactoring of legacy code etc.)
    * However, I wouldn't use partial mocks for new, test-driven & well-designed code.
    * <p/>
    * TODO Redo this doc to indicate its usefulness when chaining as this is the default behavior
    * <p/>
    * Example:
    * <pre class="code"><code class="java">
    *   Foo proxy = proxy(Foo.class);
    *   doCallRealMethod().when(proxy).someVoidMethod();
    *
    *   // this will call the real implementation of Foo.someVoidMethod()
    *   proxy.someVoidMethod();
    * </code></pre>
    * <p>
    * See examples in javadoc for {@link Proximo} class
    *
    * @return stubber - to select a method for stubbing
    */
   public static Stubber doCallRealMethod() {
      return null;
   }


}
