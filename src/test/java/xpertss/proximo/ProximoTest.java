package xpertss.proximo;

import org.junit.Test;
import org.xpertss.proximo.ProximoHandler;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.concurrent.Callable;

import static org.junit.Assert.*;

/**
 *
 */
public class ProximoTest {

   @Test(expected = NullPointerException.class)
   public void testProxyNullInterfaceType()
   {
      Proximo.proxy(null, new Runnable() {
         @Override public void run() { }
      });
   }

   @Test(expected = NullPointerException.class)
   public void testProxyNullInstance()
   {
      Proximo.proxy(Callable.class, null);
   }

   @Test(expected = IllegalArgumentException.class)
   public void testProxyNotInterface()
   {
      Proximo.proxy(String.class, new String(""));
   }

   @Test
   public void testProxyHappyPath()
   {
      Runnable proxy = Proximo.proxy(Runnable.class, new Runnable() {
         @Override public void run() { }
      });
      InvocationHandler handler = Proxy.getInvocationHandler(proxy);
      assertEquals(ProximoHandler.class, handler.getClass());
   }

}