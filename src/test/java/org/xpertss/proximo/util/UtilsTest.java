package org.xpertss.proximo.util;

import org.junit.Test;

import java.io.IOException;
import java.sql.SQLException;

import static org.junit.Assert.*;

/**
 * Copyright 2015 XpertSoftware
 * <p/>
 * Created By: cfloersch
 * Date: 6/12/2015
 */
public class UtilsTest {

   @Test
   public void testIsChecked()
   {
      assertFalse(Utils.isChecked((Class)null));
      assertFalse(Utils.isChecked(String.class));
      assertFalse(Utils.isChecked(Error.class));
      assertFalse(Utils.isChecked(IllegalAccessError.class));
      assertFalse(Utils.isChecked(RuntimeException.class));
      assertFalse(Utils.isChecked(SecurityException.class));

      assertTrue(Utils.isChecked(Throwable.class));
      assertTrue(Utils.isChecked(Exception.class));
      assertTrue(Utils.isChecked(IOException.class));
      assertTrue(Utils.isChecked(SQLException.class));
   }

}