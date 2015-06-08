package org.xpertss.proximo.util;

import org.junit.Test;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.*;

/**
 *
 */
public class DefaultsTest {

   @Test
   public void testReturnForBoolean()
   {
      assertEquals(Boolean.FALSE, Defaults.returnFor(boolean.class));
      assertEquals(Boolean.FALSE, Defaults.returnFor(Boolean.class));
   }

   @Test
   public void testReturnForByte()
   {
      Byte b = Byte.valueOf("0");
      assertEquals(b, Defaults.returnFor(byte.class));
      assertEquals(b, Defaults.returnFor(Byte.class));
   }

   @Test
   public void testReturnForChar()
   {
      Character c = Character.valueOf('\u0000');
      assertEquals(c, Defaults.returnFor(char.class));
      assertEquals(c, Defaults.returnFor(Character.class));
   }

   @Test
   public void testReturnForShort()
   {
      Short s = Short.valueOf("0");
      assertEquals(s, Defaults.returnFor(short.class));
      assertEquals(s, Defaults.returnFor(Short.class));
   }

   @Test
   public void testReturnForInt()
   {
      Integer i = Integer.valueOf("0");
      assertEquals(i, Defaults.returnFor(int.class));
      assertEquals(i, Defaults.returnFor(Integer.class));
   }

   @Test
   public void testReturnForILong()
   {
      Long l = Long.valueOf("0");
      assertEquals(l, Defaults.returnFor(long.class));
      assertEquals(l, Defaults.returnFor(Long.class));
   }

   @Test
   public void testReturnForFloat()
   {
      Float f = Float.valueOf("0");
      assertEquals(f, Defaults.returnFor(float.class));
      assertEquals(f, Defaults.returnFor(Float.class));
   }

   @Test
   public void testReturnForDouble()
   {
      Double d = Double.valueOf("0");
      assertEquals(d, Defaults.returnFor(double.class));
      assertEquals(d, Defaults.returnFor(Double.class));
   }

   @Test
   public void testReturnForString()
   {
      String s = "";
      assertEquals(s, Defaults.returnFor(String.class));
      assertEquals(s, Defaults.returnString());
   }

   @Test
   public void testReturnForMap()
   {
      assertEquals(Collections.emptyMap(), Defaults.returnFor(Map.class));
      assertEquals(Collections.emptyMap(), Defaults.returnMap());
   }

   @Test
   public void testReturnForSet()
   {
      assertEquals(Collections.emptySet(), Defaults.returnFor(Set.class));
      assertEquals(Collections.emptySet(), Defaults.returnSet());
   }

   @Test
   public void testReturnForList()
   {
      assertEquals(Collections.emptyList(), Defaults.returnFor(List.class));
      assertEquals(Collections.emptyList(), Defaults.returnList());
   }

   @Test
   public void testReturnForCollection()
   {
      assertEquals(Collections.emptyList(), Defaults.returnFor(Collection.class));
   }

   @Test
   public void testReturnForNull()
   {
      assertNull(Defaults.returnNull());
   }

   @Test
   public void testReturnForInstance()
   {
      assertEquals("", Defaults.returnFor("hello"));
      assertEquals(Integer.valueOf(0), Defaults.returnFor(1));
      assertEquals(Long.valueOf(0), Defaults.returnFor(1L));
      assertEquals(Double.valueOf(0), Defaults.returnFor(1D));
      assertEquals(Float.valueOf(0), Defaults.returnFor(1F));
      assertNull(Defaults.returnFor(new StringBuffer()));
      assertNull(Defaults.returnFor(null));
   }


}