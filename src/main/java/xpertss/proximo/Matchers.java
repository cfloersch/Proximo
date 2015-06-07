/**
 * Copyright 2015 XpertSoftware
 * <p/>
 * Created By: cfloersch
 * Date: 6/3/2015
 */
package xpertss.proximo;

import org.xpertss.proximo.ArgumentMatcher;
import org.xpertss.proximo.StubbingProgress;
import org.xpertss.proximo.matchers.Any;
import org.xpertss.proximo.matchers.AnyVararg;
import org.xpertss.proximo.matchers.Contains;
import org.xpertss.proximo.matchers.EndsWith;
import org.xpertss.proximo.matchers.Equals;
import org.xpertss.proximo.matchers.InstanceOf;
import org.xpertss.proximo.matchers.Matches;
import org.xpertss.proximo.matchers.NotNull;
import org.xpertss.proximo.matchers.Null;
import org.xpertss.proximo.matchers.Same;
import org.xpertss.proximo.matchers.StartsWith;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.xpertss.proximo.util.Primitives.defaultValueForPrimitiveOrWrapper;
import static org.xpertss.proximo.util.Primitives.isPrimitiveOrWrapper;

/**
 * TODO Create some docs which indicate that these are intended to allow the proxy to
 * understand under what set of arguments does a given stubbing apply. Thus we can
 * return a value when its called with argument X and throw an exception when its
 * called with argument Y for example.
 */
@SuppressWarnings("unchecked")
public class Matchers {

   static final StubbingProgress PROGRESS = new StubbingProgress();

   /**
    * Any <code>boolean</code> or non-null <code>Boolean</code>
    * <p>
    * See examples in javadoc for {@link Matchers} class
    *
    * @return <code>false</code>.
    */
   public static boolean anyBoolean() {
      reportMatcher(new InstanceOf(Boolean.class));
      return false;
   }

   /**
    * Any <code>byte</code> or non-null <code>Byte</code>.
    * <p>
    * See examples in javadoc for {@link Matchers} class
    *
    * @return <code>0</code>.
    */
   public static byte anyByte() {
      reportMatcher(new InstanceOf(Byte.class));
      return (byte) 0;
   }

   /**
    * Any <code>char</code> or non-null <code>Character</code>.
    * <p>
    * See examples in javadoc for {@link Matchers} class
    *
    * @return <code>0</code>.
    */
   public static char anyChar() {
      reportMatcher(new InstanceOf(Character.class));
      return '\u0000';
   }

   /**
    * Any int or non-null Integer.
    * <p>
    * See examples in javadoc for {@link Matchers} class
    *
    * @return <code>0</code>.
    */
   public static int anyInt() {
      reportMatcher(new InstanceOf(Integer.class));
      return 0;
   }

   /**
    * Any <code>long</code> or non-null <code>Long</code>.
    * <p>
    * See examples in javadoc for {@link Matchers} class
    *
    * @return <code>0</code>.
    */
   public static long anyLong() {
      reportMatcher(new InstanceOf(Long.class));
      return 0L;
   }

   /**
    * Any <code>float</code> or non-null <code>Float</code>.
    * <p>
    * See examples in javadoc for {@link Matchers} class
    *
    * @return <code>0</code>.
    */
   public static float anyFloat() {
      reportMatcher(new InstanceOf(Float.class));
      return 0F;
   }

   /**
    * Any <code>double</code> or non-null <code>Double</code>.
    * <p>
    * See examples in javadoc for {@link Matchers} class
    *
    * @return <code>0</code>.
    */
   public static double anyDouble() {
      reportMatcher(new InstanceOf(Double.class));
      return 0D;
   }

   /**
    * Any <code>short</code> or non-null <code>Short</code>.
    * <p>
    * See examples in javadoc for {@link Matchers} class
    *
    * @return <code>0</code>.
    */
   public static short anyShort() {
      reportMatcher(new InstanceOf(Short.class));
      return (short) 0;
   }

   /**
    * Matches anything, including null.
    * <p>
    * This is an alias of: {@link #any()} and {@link #any(java.lang.Class)}
    * <p>
    * See examples in javadoc for {@link Matchers} class
    *
    * @return <code>null</code>.
    */
   public static <T> T anyObject() {
      reportMatcher(Any.ANY);
      return (T) null;
   }

   /**
    * Any vararg, meaning any number and values of arguments.
    * <p>
    * Example:
    * <pre class="code"><code class="java">
    *   //stubbing:
    *   doReturn(100).when(proxy).foo(anyVararg());
    *
    *   //prints 100
    *   System.out.println(proxy.foo(1, 2));
    *   //also prints 100
    *   System.out.println(proxy.foo(1, 2, 3, 4));
    * </code></pre>
    * See examples in javadoc for {@link Matchers} class
    *
    * @return <code>null</code>.
    */
   public static <T> T anyVararg() {
      reportMatcher(AnyVararg.ANY_VARARG);
      return (T) null;
   }

   /**
    * Matches any object, including nulls
    * <p>
    * This method doesn't do type checks with the given parameter, it is only there
    * to avoid casting in your code. This might however change (type checks could
    * be added) in a future major release.
    * <p>
    * See examples in javadoc for {@link Matchers} class
    * <p>
    * This is an alias of: {@link #any()} and {@link #anyObject()}
    * <p>
    * @return <code>null</code>.
    */
   public static <T> T any(Class<T> clazz) {
      reportMatcher(Any.ANY);
      return returnFor(clazz);
   }

   /**
    * Matches anything, including nulls
    * <p>
    * Shorter alias to {@link Matchers#anyObject()}
    * <p>
    * See examples in javadoc for {@link Matchers} class
    * <p>
    * This is an alias of: {@link #anyObject()} and {@link #any(java.lang.Class)}
    * <p>
    * @return <code>null</code>.
    */
   public static <T> T any() {
      return anyObject();
   }

   /**
    * Any non-null <code>String</code>
    * <p>
    * See examples in javadoc for {@link Matchers} class
    *
    * @return empty String ("")
    */
   public static String anyString() {
      reportMatcher(new InstanceOf(String.class));
      return "";
   }

   /**
    * Any non-null <code>List</code>.
    * <p>
    * See examples in javadoc for {@link Matchers} class
    *
    * @return empty List.
    */
   public static List anyList() {
      reportMatcher(new InstanceOf(List.class));
      return Collections.emptyList();
   }

   /**
    * Generic friendly alias to {@link Matchers#anyList()}.
    * It's an alternative to &#064;SuppressWarnings("unchecked") to keep code clean of compiler warnings.
    * <p>
    * Any non-null <code>List</code>.
    * <p>
    * This method doesn't do type checks with the given parameter, it is only there
    * to avoid casting in your code. This might however change (type checks could
    * be added) in a future major release.
    * <p>
    * See examples in javadoc for {@link Matchers} class
    *
    * @param clazz Type owned by the list to avoid casting
    * @return empty List.
    */
   public static <T> List<T> anyListOf(Class<T> clazz) {
      return anyList();
   }

   /**
    * Any non-null <code>Set</code>.
    * <p>
    * See examples in javadoc for {@link Matchers} class
    *
    * @return empty Set
    */
   public static Set anySet() {
      reportMatcher(new InstanceOf(Set.class));
      return Collections.emptySet();
   }

   /**
    * Generic friendly alias to {@link Matchers#anySet()}.
    * It's an alternative to &#064;SuppressWarnings("unchecked") to keep code clean of compiler warnings.
    * <p>
    * Any non-null <code>Set</code>.
    * <p>
    * This method doesn't do type checks with the given parameter, it is only there
    * to avoid casting in your code. This might however change (type checks could
    * be added) in a future major release.
    * <p>
    * See examples in javadoc for {@link Matchers} class
    *
    * @param clazz Type owned by the Set to avoid casting
    * @return empty Set
    */
   public static <T> Set<T> anySetOf(Class<T> clazz) {
      return anySet();
   }

   /**
    * Any non-null <code>Map</code>.
    * <p>
    * See examples in javadoc for {@link Matchers} class
    *
    * @return empty Map.
    */
   public static Map anyMap() {
      reportMatcher(new InstanceOf(Map.class));
      return Collections.emptyMap();
   }

   /**
    * Generic friendly alias to {@link Matchers#anyMap()}.
    * It's an alternative to &#064;SuppressWarnings("unchecked") to keep code clean of compiler warnings.
    * <p>
    * Any non-null <code>Map</code>.
    * <p>
    * This method doesn't do type checks with the given parameter, it is only there
    * to avoid casting in your code. This might however change (type checks could
    * be added) in a future major release.
    * <p>
    * See examples in javadoc for {@link Matchers} class
    *
    * @param keyClazz Type of the map key to avoid casting
    * @param valueClazz Type of the value to avoid casting
    * @return empty Map.
    */
   public static <K, V>  Map<K, V> anyMapOf(Class<K> keyClazz, Class<V> valueClazz) {
      return anyMap();
   }

   /**
    * Any non-null <code>Collection</code>.
    * <p>
    * See examples in javadoc for {@link Matchers} class
    *
    * @return empty Collection.
    */
   public static Collection anyCollection() {
      reportMatcher(new InstanceOf(Collection.class));
      return Collections.emptyList();
   }

   /**
    * Generic friendly alias to {@link Matchers#anyCollection()}.
    * It's an alternative to &#064;SuppressWarnings("unchecked") to keep code clean of compiler warnings.
    * <p>
    * Any non-null <code>Collection</code>.
    * <p>
    * This method doesn't do type checks with the given parameter, it is only there
    * to avoid casting in your code. This might however change (type checks could
    * be added) in a future major release.
    * <p>
    * See examples in javadoc for {@link Matchers} class
    *
    * @param clazz Type owned by the collection to avoid casting
    * @return empty Collection.
    */
   public static <T> Collection<T> anyCollectionOf(Class<T> clazz) {
      return anyCollection();
   }

   /**
    * <code>Object</code> argument that implements the given class.
    * <p>
    * See examples in javadoc for {@link Matchers} class
    *
    * @param <T>
    *            the accepted type.
    * @param clazz
    *            the class of the accepted type.
    * @return <code>null</code>.
    */
   public static <T> T isA(Class<T> clazz) {
      reportMatcher(new InstanceOf(clazz));
      return returnFor(clazz);
   }

   /**
    * <code>boolean</code> argument that is equal to the given value.
    * <p>
    * See examples in javadoc for {@link Matchers} class
    *
    * @param value
    *            the given value.
    * @return <code>0</code>.
    */
   public static boolean eq(boolean value) {
      reportMatcher(new Equals(value));
      return false;
   }

   /**
    * <code>byte</code> argument that is equal to the given value.
    * <p>
    * See examples in javadoc for {@link Matchers} class
    *
    * @param value
    *            the given value.
    * @return <code>0</code>.
    */
   public static byte eq(byte value) {
      reportMatcher(new Equals(value));
      return (byte) 0;
   }

   /**
    * <code>char</code> argument that is equal to the given value.
    * <p>
    * See examples in javadoc for {@link Matchers} class
    *
    * @param value
    *            the given value.
    * @return <code>0</code>.
    */
   public static char eq(char value) {
      reportMatcher(new Equals(value));
      return '\u0000';
   }

   /**
    * <code>double</code> argument that is equal to the given value.
    * <p>
    * See examples in javadoc for {@link Matchers} class
    *
    * @param value
    *            the given value.
    * @return <code>0</code>.
    */
   public static double eq(double value) {
      reportMatcher(new Equals(value));
      return 0D;
   }

   /**
    * <code>float</code> argument that is equal to the given value.
    * <p>
    * See examples in javadoc for {@link Matchers} class
    *
    * @param value
    *            the given value.
    * @return <code>0</code>.
    */
   public static float eq(float value) {
      reportMatcher(new Equals(value));
      return 0F;
   }

   /**
    * <code>int</code> argument that is equal to the given value.
    * <p>
    * See examples in javadoc for {@link Matchers} class
    *
    * @param value
    *            the given value.
    * @return <code>0</code>.
    */
   public static int eq(int value) {
      reportMatcher(new Equals(value));
      return 0;
   }

   /**
    * <code>long</code> argument that is equal to the given value.
    * <p>
    * See examples in javadoc for {@link Matchers} class
    *
    * @param value
    *            the given value.
    * @return <code>0</code>.
    */
   public static long eq(long value) {
      reportMatcher(new Equals(value));
      return 0L;
   }

   /**
    * <code>short</code> argument that is equal to the given value.
    * <p>
    * See examples in javadoc for {@link Matchers} class
    *
    * @param value
    *            the given value.
    * @return <code>0</code>.
    */
   public static short eq(short value) {
      reportMatcher(new Equals(value));
      return (short) 0;
   }

   /**
    * Object argument that is equal to the given value.
    * <p>
    * See examples in javadoc for {@link Matchers} class
    *
    * @param value
    *            the given value.
    * @return <code>null</code>.
    */
   public static <T> T eq(T value) {
      reportMatcher(new Equals(value));
      return returnFor(value);
   }


   /**
    * Object argument that is the same as the given value.
    * <p>
    * See examples in javadoc for {@link Matchers} class
    *
    * @param <T>
    *            the type of the object, it is passed through to prevent casts.
    * @param value
    *            the given value.
    * @return <code>null</code>.
    */
   public static <T> T same(T value) {
      reportMatcher(new Same(value));
      return returnFor(value);
   }

   /**
    * <code>null</code> argument.
    * <p>
    * See examples in javadoc for {@link Matchers} class
    *
    * @return <code>null</code>.
    */
   public static Object isNull() {
      reportMatcher(Null.NULL);
      return null;
   }

   /**
    * <code>null</code> argument.
    * The class argument is provided to avoid casting.
    * <p>
    * See examples in javadoc for {@link Matchers} class
    *
    * @param clazz Type to avoid casting
    * @return <code>null</code>.
    */
   public static <T> T isNull(Class<T> clazz) {
      reportMatcher(Null.NULL);
      return (T) null;
   }

   /**
    * Not <code>null</code> argument.
    * <p>
    * alias to {@link Matchers#isNotNull()}
    * <p>
    * See examples in javadoc for {@link Matchers} class
    *
    * @return <code>null</code>.
    */
   public static Object notNull() {
      reportMatcher(NotNull.NOT_NULL);
      return null;
   }

   /**
    * Not <code>null</code> argument, not necessary of the given class.
    * The class argument is provided to avoid casting.
    * <p>
    * alias to {@link Matchers#isNotNull(Class)}
    * <p>
    * See examples in javadoc for {@link Matchers} class
    *
    * @param clazz Type to avoid casting
    * @return <code>null</code>.
    */
   public static <T> T notNull(Class<T> clazz) {
      reportMatcher(NotNull.NOT_NULL);
      return (T) null;
   }

   /**
    * Not <code>null</code> argument.
    * <p>
    * alias to {@link Matchers#notNull()}
    * <p>
    * See examples in javadoc for {@link Matchers} class
    *
    * @return <code>null</code>.
    */
   public static Object isNotNull() {
      return notNull();
   }

   /**
    * Not <code>null</code> argument, not necessary of the given class.
    * The class argument is provided to avoid casting.
    * <p>
    * alias to {@link Matchers#notNull(Class)}
    * <p>
    * See examples in javadoc for {@link Matchers} class
    *
    * @param clazz Type to avoid casting
    * @return <code>null</code>.
    */
   public static <T> T isNotNull(Class<T> clazz) {
      return notNull(clazz);
   }

   /**
    * <code>String</code> argument that contains the given substring.
    * <p>
    * See examples in javadoc for {@link Matchers} class
    *
    * @param substring
    *            the substring.
    * @return empty String ("").
    */
   public static String contains(String substring) {
      reportMatcher(new Contains(substring));
      return "";
   }

   /**
    * <code>String</code> argument that matches the given regular expression.
    * <p>
    * See examples in javadoc for {@link Matchers} class
    *
    * @param regex
    *            the regular expression.
    * @return empty String ("").
    */
   public static String matches(String regex) {
      reportMatcher(new Matches(regex));
      return "";
   }

   /**
    * <code>String</code> argument that ends with the given suffix.
    * <p>
    * See examples in javadoc for {@link Matchers} class
    *
    * @param suffix
    *            the suffix.
    * @return empty String ("").
    */
   public static String endsWith(String suffix) {
      reportMatcher(new EndsWith(suffix));
      return "";
   }

   /**
    * <code>String</code> argument that starts with the given prefix.
    * <p>
    * See examples in javadoc for {@link Matchers} class
    *
    * @param prefix
    *            the prefix.
    * @return empty String ("").
    */
   public static String startsWith(String prefix) {
      reportMatcher(new StartsWith(prefix));
      return "";
   }

   /**
    * Allows creating custom argument matchers.
    * <p>
    * In rare cases when the parameter is a primitive then you <b>*must*</b> use relevant intThat(), floatThat(), etc. method.
    * This way you will avoid <code>NullPointerException</code> during auto-unboxing.
    * <p>
    * See examples in javadoc for {@link ArgumentMatcher} class
    *
    * @param matcher decides whether argument matches
    * @return <code>null</code>.
    */
   public static <T> T argThat(Matcher<T> matcher) {
      reportMatcher(matcher);
      return (T) null;
   }

   /**
    * Allows creating custom <code>Character</code> argument matchers.
    * <p>
    * See examples in javadoc for {@link Matchers} class
    *
    * @param matcher decides whether argument matches
    * @return <code>0</code>.
    */
   public static char charThat(Matcher<Character> matcher) {
      reportMatcher(matcher);
      return '\u0000';
   }

   /**
    * Allows creating custom <code>Boolean</code> argument matchers.
    * <p>
    * See examples in javadoc for {@link Matchers} class
    *
    * @param matcher decides whether argument matches
    * @return <code>false</code>.
    */
   public static boolean booleanThat(Matcher<Boolean> matcher) {
      reportMatcher(matcher);
      return false;
   }

   /**
    * Allows creating custom <code>Byte</code> argument matchers.
    * <p>
    * See examples in javadoc for {@link Matchers} class
    *
    * @param matcher decides whether argument matches
    * @return <code>0</code>.
    */
   public static byte byteThat(Matcher<Byte> matcher) {
      reportMatcher(matcher);
      return (byte) 0;
   }

   /**
    * Allows creating custom <code>Short</code> argument matchers.
    * <p>
    * See examples in javadoc for {@link Matchers} class
    *
    * @param matcher decides whether argument matches
    * @return <code>0</code>.
    */
   public static short shortThat(Matcher<Short> matcher) {
      reportMatcher(matcher);
      return (short) 0;
   }

   /**
    * Allows creating custom <code>Integer</code> argument matchers.
    * <p>
    * See examples in javadoc for {@link Matchers} class
    *
    * @param matcher decides whether argument matches
    * @return <code>0</code>.
    */
   public static int intThat(Matcher<Integer> matcher) {
      reportMatcher(matcher);
      return 0;
   }

   /**
    * Allows creating custom <code>Long</code> argument matchers.
    * <p>
    * See examples in javadoc for {@link Matchers} class
    *
    * @param matcher decides whether argument matches
    * @return <code>0</code>.
    */
   public static long longThat(Matcher<Long> matcher) {
      reportMatcher(matcher);
      return 0L;
   }

   /**
    * Allows creating custom <code>Float</code> argument matchers.
    * <p>
    * See examples in javadoc for {@link Matchers} class
    *
    * @param matcher decides whether argument matches
    * @return <code>0</code>.
    */
   public static float floatThat(Matcher<Float> matcher) {
      reportMatcher(matcher);
      return 0F;
   }

   /**
    * Allows creating custom <code>Double</code> argument matchers.
    * <p>
    * See examples in javadoc for {@link Matchers} class
    *
    * @param matcher decides whether argument matches
    * @return <code>0</code>.
    */
   public static double doubleThat(Matcher<Double> matcher) {
      reportMatcher(matcher);
      return 0D;
   }




   private static <T> T returnFor(Class<T> clazz)
   {
      if (isPrimitiveOrWrapper(clazz)) {
         return defaultValueForPrimitiveOrWrapper(clazz);
      }
      return null;
   }

   private static <T> T returnFor(T instance)
   {
      return instance == null ? null : (T) returnFor(instance.getClass());
   }



   private static void reportMatcher(Matcher<?> matcher) {
      PROGRESS.reportMatcher(matcher);
   }

}