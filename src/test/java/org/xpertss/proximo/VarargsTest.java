package org.xpertss.proximo;

import org.junit.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;

/**
 * Created by cfloersch on 6/8/2015.
 */
public class VarargsTest {

   @Test
   public void testVarargsViaProxy() throws Throwable
   {
      InvocationHandler handler = new InvocationHandler() {
         @Override
         public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            System.out.println("Varargs: " + method.isVarArgs());
            Class[] paramTypes = method.getParameterTypes();
            if(paramTypes.length > 0) System.out.printf("Parameter Types (%d)\r\n", paramTypes.length);
            for(Class type : paramTypes) {
               System.out.println("   " + type.getName());
            }
            if(args.length > 0) System.out.printf("Arguments (%d)\r\n", args.length);
            for(Object arg : args) System.out.println("   " + arg);
            return null;
         }
      };

      Class<IVarargs> clazz = IVarargs.class;
      IVarargs proxy = (IVarargs) Proxy.newProxyInstance(clazz.getClassLoader(), new Class<?>[] {clazz}, handler);
      proxy.join(' ', "one", "two", "three");

   }

}
