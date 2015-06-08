package org.xpertss.proximo;

import org.junit.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by cfloersch on 6/8/2015.
 */
public class ProxyTest {

   @Test
   public void testProxy()
   {
      Class<Runnable> clazz = Runnable.class;
      InvocationHandler handler = new InvocationHandler() {
         @Override
         public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            System.out.println(args);
            return null;
         }
      };

      Runnable proxy = (Runnable) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[] {clazz}, handler);
      proxy.run();

   }

}
