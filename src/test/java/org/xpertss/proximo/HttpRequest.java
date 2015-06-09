package org.xpertss.proximo;

import java.io.Reader;

/**
 * Created by cfloersch on 6/9/2015.
 */
public interface HttpRequest {

   /**
    * Test zero arg method with string return
    */
   String getRemoteUser();

   /**
    * Test void return with multiple string arguments
    */
   void login(String username, String passwored);

   /**
    * Test zero arg void method
    */
   void logout();

   /**
    * Test single string arg, String return method
    */
   String getParameter(String name);

   /**
    * Test object return zero arg method
    */
   Reader getReader();
}
