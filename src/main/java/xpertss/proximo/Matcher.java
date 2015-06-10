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
    * Any matcher that matches any supplied object should return this as
    * their specificity.
    */
   public static final int ANY_SPECIFICITY = 0;

   /**
    * Any matcher that checks type but not value should return this as
    * their specificity. This would also include null or not null checks.
    */
   public static final int INSTANCE_SPECIFICITY = 1;

   /**
    * Any matcher that checks a single argument value should return this
    * as their specificity.
    */
   public static final int VALUE_SPECIFICITY = 2;



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

   /**
    * This returns the specificity of the matcher so that rules may be sorted from
    * the most specific to the least specific.
    * @return
    */
   int specificity();

}
