/**
 * Copyright 2015 XpertSoftware
 * <p/>
 * Created By: cfloersch
 * Date: 6/3/2015
 */
package xpertss.proximo;

import java.lang.reflect.Method;

/**
 * An invocation on a proxy
 */
public interface Invocation {

   /**
    * returns the proxy object
    *
    * @return proxy object
    */
   Object getProxy();


   /**
    * returns the proxied object
    *
    * @return proxied object
    */
   Object getProxied();


   /**
    * returns the method
    *
    * @return method
    */
   Method getMethod();


   /**
    * Returns casted argument using position
    *
    * @param index argument position
    * @param clazz argument type
    * @return casted argument on position
    */
   <T> T getArgumentAt(int index, Class<T> clazz);


   /**
    * returns arguments passed to the method
    *
    * @return arguments
    */
   Object[] getArguments();


   /**
    * Forwards the call to the proxied object
    *
    * @return whatever the real method returns / throws
    * @throws Throwable in case real method throws
    */
   Object forwardCall() throws Throwable;

}
