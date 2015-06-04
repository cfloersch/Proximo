/**
 * Copyright 2015 XpertSoftware
 * <p/>
 * Created By: cfloersch
 * Date: 6/3/2015
 */
package org.xpertss.proximo;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import xpertss.proximo.Matchers;

/**
 * Allows creating customized argument matchers.
 * <p/>
 * ArgumentMatcher is an hamcrest {@link Matcher} with predefined describeTo() method.
 * <p/>
 * In case of failure, ArgumentMatcher generates description based on <b>class name</b> - to promote
 * meaningful class names.
 * <p/>
 * You can always override describeTo() method and provide detailed description.
 * <p>
 * Use {@link Matchers#argThat} method and pass an instance of hamcrest {@link Matcher}, e.g:
 *
 * <pre class="code"><code class="java">
 * class IsListOfTwoElements extends ArgumentMatcher&lt;List&gt; {
 *     public boolean matches(Object list) {
 *         return ((List) list).size() == 2;
 *     }
 * }
 *
 * List proxy = Proximo.proxy(List.class, list);
 * doReturn(true).when(proxy).addAll(argThat(new IsListOfTwoElements()));
 *
 * proxy.addAll(Arrays.asList(&quot;one&quot;, &quot;two&quot;));
 * </code></pre>
 * <p/>
 * Read more about other matchers in javadoc for {@link Matchers} class
 *
 * @param <T> type of argument
 */
public abstract class ArgumentMatcher<T> extends BaseMatcher<T> {

   private static final long serialVersionUID = -2145234737829370369L;

   /**
    * Returns whether this matcher accepts the given argument.
    * <p>
    * The method should <b>never</b> assert if the argument doesn't match. It
    * should only return false.
    *
    * @param argument
    *            the argument
    * @return whether this matcher accepts the given argument.
    */
   public abstract boolean matches(Object argument);

   /**
    * You might want to override this method to provide more specific description of the
    * matcher (useful when verification failures are reported).
    *
    * @param description the description to which the matcher description is
    * appended.
    */
   public void describeTo(Description description) {
      String className = getClass().getSimpleName();
      description.appendText(className);
   }
}