package xpertss.proximo;

import org.junit.Test;
import org.xpertss.proximo.HttpRequest;
import org.xpertss.proximo.IVarargs;
import org.xpertss.proximo.ProximoHandler;

import java.io.Reader;
import java.io.StringReader;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import static org.junit.Assert.*;
import static xpertss.proximo.Matchers.anyChar;
import static xpertss.proximo.Matchers.anyString;
import static xpertss.proximo.Matchers.anyVararg;
import static xpertss.proximo.Matchers.eq;
import static xpertss.proximo.Proximo.doAnswer;
import static xpertss.proximo.Proximo.doNothing;
import static xpertss.proximo.Proximo.doReturn;
import static xpertss.proximo.Proximo.doThrow;

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

   @Test
   public void testStubVoidZeroArg()
   {
      List<String> proxied = new ArrayList<>();

      List<String> proxy = Proximo.proxy(List.class, proxied);
      doNothing().when(proxy).clear();

      proxy.add("Hello");
      assertEquals(1, proxy.size());
      assertEquals(1, proxied.size());

      proxy.clear();
      assertEquals(1, proxy.size());
      assertEquals(1, proxied.size());
   }


   @Test
   public void testStubZeroArgBooleanResult()
   {
      List<String> proxied = new ArrayList<>();

      List<String> proxy = Proximo.proxy(List.class, proxied);
      doNothing().when(proxy).isEmpty();

      assertFalse(proxy.isEmpty());
      assertTrue(proxied.isEmpty());
      proxy.add("Hello");
      assertFalse(proxy.isEmpty());
      assertFalse(proxied.isEmpty());
   }

   @Test
   public void testStubZeroArgNumericResult()
   {
      List<String> proxied = new ArrayList<>();

      List<String> proxy = Proximo.proxy(List.class, proxied);
      doNothing().when(proxy).size();

      assertEquals(0, proxied.size());
      assertEquals(0, proxy.size());
      proxy.add("Hello");
      assertEquals(1, proxied.size());
      assertEquals(0, proxy.size());
   }

   @Test
   public void testStubZeroArgObjectResult()
   {
      List<String> proxied = new ArrayList<>();

      List<String> proxy = Proximo.proxy(List.class, proxied);
      doNothing().when(proxy).iterator();

      assertNull(proxy.iterator());
   }

   @Test
   public void testStubSingleArgumentMethod()
   {
      List<String> proxied = new ArrayList<>();

      List<String> proxy = Proximo.proxy(List.class, proxied);
      doReturn("Goodbye").when(proxy).get(eq(0));

      assertEquals("Goodbye", proxy.get(0));
      proxy.add("Hello");
      assertEquals("Goodbye", proxy.get(0));
      assertEquals("Hello", proxied.get(0));
   }

   @Test
   public void testStubOverrideSingleArgumentMethod()
   {
      List<String> proxied = new ArrayList<>();

      List<String> proxy = Proximo.proxy(List.class, proxied);
      doReturn("Goodbye").when(proxy).get(eq(0));
      doNothing().when(proxy).get(eq(0));

      assertNull(proxy.get(0));
      proxy.add("Hello");
      assertNull(proxy.get(0));
      assertEquals("Hello", proxied.get(0));
   }

   @Test
   public void testStubOverloadingSingleArgumentMethod()
   {
      List<String> proxied = new ArrayList<>();

      List<String> proxy = Proximo.proxy(List.class, proxied);
      doReturn("Goodbye").when(proxy).get(eq(0));
      doNothing().when(proxy).get(eq(1));

      assertEquals("Goodbye", proxy.get(0));
      assertNull(proxy.get(1));
   }

   @Test
   public void testStubZeroArgStringReturnMethod()
   {
      HttpRequest request = new TestHttpRequest();
      HttpRequest proxy = Proximo.proxy(HttpRequest.class, request);

      proxy.login("john", "passwd");
      assertEquals("john", proxy.getRemoteUser());

      doNothing().when(proxy).getRemoteUser();
      assertEquals("", proxy.getRemoteUser());
   }

   @Test
   public void testStubDualArgumentMethodAnyInput()
   {
      HttpRequest request = new TestHttpRequest();
      HttpRequest proxy = Proximo.proxy(HttpRequest.class, request);
      doNothing().when(proxy).login(anyString(), anyString());
      proxy.login("john", "passwd");
      assertNull(proxy.getRemoteUser());
   }

   @Test
   public void testStubDualArgumentMethodSpecificInput()
   {
      HttpRequest request = new TestHttpRequest();
      HttpRequest proxy = Proximo.proxy(HttpRequest.class, request);
      doNothing().when(proxy).login(eq("john"), anyString());
      proxy.login("john", "passwd");
      assertNull(proxy.getRemoteUser());
      proxy.login("fred", "passwd");
      assertEquals("fred", proxy.getRemoteUser());
   }

   @Test
   public void testStubZeroArgObjectReturnMethod()
   {
      HttpRequest request = new TestHttpRequest();
      HttpRequest proxy = Proximo.proxy(HttpRequest.class, request);
      doNothing().when(proxy).getReader();
      assertNull(proxy.getReader());
   }

   @Test
   public void testStubStringInStringOut()
   {
      HttpRequest request = new TestHttpRequest();
      HttpRequest proxy = Proximo.proxy(HttpRequest.class, request);
      assertEquals("test", proxy.getParameter("test"));
      doNothing().when(proxy).getParameter(anyString());
      assertEquals("", proxy.getParameter("test"));
      doReturn("fred").when(proxy).getParameter(anyString());
      assertEquals("fred", proxy.getParameter("test"));
   }

   @Test
   public void testStubChainingsameAnswers()
   {
      HttpRequest request = new TestHttpRequest();
      HttpRequest proxy = Proximo.proxy(HttpRequest.class, request);
      doReturn("fred").doReturn("john").when(proxy).getRemoteUser();
      assertEquals("fred", proxy.getRemoteUser());
      assertEquals("john", proxy.getRemoteUser());
      assertEquals("john", proxy.getRemoteUser());
   }

   @Test
   public void testStubChainingMultipleAnswers()
   {
      HttpRequest request = new TestHttpRequest();
      HttpRequest proxy = Proximo.proxy(HttpRequest.class, request);
      doReturn("fred").doNothing().doForwardCall().when(proxy).getRemoteUser();
      assertEquals("fred", proxy.getRemoteUser());
      assertEquals("", proxy.getRemoteUser());
      assertNull(proxy.getRemoteUser());
      assertNull(proxy.getRemoteUser());
      proxy.login("amy", "passwd");
      assertEquals("amy", proxy.getRemoteUser());
   }


   @Test(expected = UnsupportedOperationException.class)
   public void testDoThrows()
   {
      HttpRequest request = new TestHttpRequest();
      HttpRequest proxy = Proximo.proxy(HttpRequest.class, request);
      doThrow(new UnsupportedOperationException()).when(proxy).getReader();
      proxy.getReader();
   }

   @Test(expected = UnsupportedOperationException.class)
   public void testDoThrowsChaining()
   {
      HttpRequest request = new TestHttpRequest();
      HttpRequest proxy = Proximo.proxy(HttpRequest.class, request);
      doNothing().doThrow(new UnsupportedOperationException()).when(proxy).getReader();
      try {
         assertNull(proxy.getReader());
      } catch(Exception e) {
         fail();
      }
      proxy.getReader();
   }


   @Test
   public void testStubMultiArgVarArgMethod()
   {
      IVarargs joiner = new Joiner();
      IVarargs proxy = Proximo.proxy(IVarargs.class, joiner);
      assertEquals("Chris,Fred", proxy.join(',', "Chris", "Fred"));
      doNothing().when(proxy).join(anyChar(), anyVararg(String.class));
      assertEquals("", proxy.join(',', "Chris", "Fred"));
      doReturn("John").when(proxy).join(anyChar(), anyVararg(String.class));
      assertEquals("John", proxy.join(',', "Chris", "Fred"));
   }

   @Test(expected = IllegalArgumentException.class)
   public void testStubIncompleteMatchers()
   {
      HttpRequest request = new TestHttpRequest();
      HttpRequest proxy = Proximo.proxy(HttpRequest.class, request);
      doReturn("fail").when(proxy).getParameter("anything");
   }

   @Test(expected = IllegalStateException.class)
   public void testCustomAnswerThrowsException()
   {
      HttpRequest request = new TestHttpRequest();
      HttpRequest proxy = Proximo.proxy(HttpRequest.class, request);
      doAnswer(new Answer() {
         @Override
         public Object answer(Invocation invocation) throws Throwable {
            throw new IllegalStateException("not logged in");
         }
      }).when(proxy).getRemoteUser();
      proxy.getRemoteUser();
   }

   @Test
   public void testCustomAnswer()
   {
      HttpRequest request = new TestHttpRequest();
      HttpRequest proxy = Proximo.proxy(HttpRequest.class, request);
      doAnswer(new Answer() {
         @Override
         public Object answer(Invocation invocation) throws Throwable {
            return invocation.getArgumentAt(0, String.class).toUpperCase();
         }
      }).when(proxy).getParameter(eq("test"));
      assertEquals("TEST", proxy.getParameter("test"));
      assertEquals("nottest", proxy.getParameter("nottest"));
   }



   @Test(expected = ClassCastException.class)
   public void testStubInvalidReturnType()
   {
      HttpRequest request = new TestHttpRequest();
      HttpRequest proxy = Proximo.proxy(HttpRequest.class, request);
      doReturn(34).when(proxy).getRemoteUser();
      proxy.getRemoteUser();
   }

   // TODO @Test(expected = ClassCastException.class)
   public void testStubInvalidReturnTypeOnStubbing()
   {
      HttpRequest request = new TestHttpRequest();
      HttpRequest proxy = Proximo.proxy(HttpRequest.class, request);
      doReturn(34).when(proxy).getRemoteUser(); // TODO I want this to throw the class cast exception
   }




   private static class Joiner implements IVarargs {

      @Override
      public String join(char sep, String... args) {
         StringBuilder builder = new StringBuilder();
         for(String arg : args) {
            if(builder.length() > 0) builder.append(sep);
            builder.append(arg);
         }
         return builder.toString();
      }
   }

   public static class TestHttpRequest implements HttpRequest {

      private String user;

      @Override
      public String getRemoteUser()
      {
         return user;
      }

      @Override
      public void login(String username, String passwored)
      {
         this.user = username;
      }

      @Override
      public void logout()
      {
         this.user = null;
      }

      @Override
      public String getParameter(String name)
      {
         return name;
      }

      @Override
      public Reader getReader()
      {
         return new StringReader("data");
      }
   }
}