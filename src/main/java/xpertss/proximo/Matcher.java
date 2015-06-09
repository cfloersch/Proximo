/**
 * Copyright 2015 XpertSoftware
 * <p/>
 * Created By: cfloersch
 * Date: 6/7/2015
 */
package xpertss.proximo;


/**
 * A matcher over acceptable values.
 * <p/>
 * Allows creating customized argument matchers.
 */
public interface Matcher<T> {

   /**
    * Evaluates the matcher for argument <var>item</var>.
    * <p/>
    * This method matches against Object, instead of the generic type T. This is
    * because the caller of the Matcher does not know at runtime what the type is
    * (because of type erasure with Java generics). It is down to the implementations
    * to check the correct type.
    *
    * @param item the object against which the matcher is evaluated.
    * @return <code>true</code> if <var>item</var> matches, otherwise <code>false</code>.
    */
   boolean matches(Object item);

}
