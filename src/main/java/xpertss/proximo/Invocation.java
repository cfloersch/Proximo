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
    * calls real method
    * <p/>
    * <b>Warning:</b> depending on the real implementation it might throw exceptions
    *
    * @return whatever the real method returns / throws
    * @throws Throwable in case real method throws
    */
   Object callRealMethod() throws Throwable;

}
