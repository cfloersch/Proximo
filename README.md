# Proximo

Proximo is a rip off of the mockito mocking system but applied to java.lang.reflect.Proxy
instances. It provides a simple declarative means for creating proxies and stubbing method
behavior overrides.


Example 1:
````
  HttpServletRequest proxy = Proximo.proxy(HttpServletRequest.class, request);
  doReturn("joeshmoe").when(proxy).getRemoteUser();
  filterChain.doFilter(proxy, response);
````

The above example overrides the getRemoteUser() method from HttpServletRequest to return a
specific user name. This would be useful for implementing a ServletFilter which authenticates
a user and wishes to propagate the username through the standard interface method.


Example 2:
````
  Iterator<String> keys = myCacheMap.keys().iterator();
  Iterator<String> proxy = Proximo.proxy(Iterator.class, keys);
  doThrow(new UnsupportedOperationException()).when(proxy).remove();
````

The above is an example of a simple immutable Iterator wrapper that prevents cache keys from
being removed via the iterator.


Example 3:
````
  List<String> proxy = Proximo.proxy(List.class, myList);
  doNothing().when(proxy).clear();
````

The above example proxies a List implementation and ensures that calls to clear are ignored.


Example 4:
````
  Map<String,String> cache = Proximo.proxy(Map.class, myBackingMap);
  doAnswer(new Answer() {
    public void answer(Invocation invocation) throws Throwable {
      String key = invocation.getArgumentAt(0, String.class).toUpperCase();
      String value = invocation.getArgumentAt(1, String.class);
      return invocation.getMethod().invoke(invocation.getProxied(), new Object[] { key, value });
   }
  }).when(cache).put(anyString(),anyString());

````

The above example wraps Map instance and forces all of the key's to be upper case using a custom
behavior.


Argument Matchers
-----------------

Argument matchers allow specific method calls (with arguments) to be overridden.

Example:
````
  List<String> list = Proximo.proxy(List.class, myBackingList);
  doReturn("john?").when(list).get(eq(0));
````

In the above example the proxied instance's get method will NOT be called if and only if the
supplied index is 0. In that case "john?" will be returned. Use a different index value and
the actual list will be queried.


Example 2:
````
   public interface Joiner {
      public String join(char c, Object... objects);
   }
   Joiner proxy = Proximo.proxy(Joiner.class, myJoinerImpl);
   doReturn("Ha Ha").when(proxy).join(anyChar(), anyVarargs());
````

The above example we return "Ha Ha" for any call made to the vararg method join irrespective to
the type or number of variable arguments.

Example 3:
````
   public interface Joiner {
      public String join(char c, Object... objects);
   }
   Joiner proxy = Proximo.proxy(Joiner.class, myJoinerImpl);
   doNothing().when(proxy).join(anyChar(), eq("chris"), anyString());

   // prints nothing
   System.out.println(proxy.join(' ', "chris", "singer"));

   // prints chris 1973
   System.out.println(proxy.join(' ', "chris", 1973));

   // prints chris john frank
   System.out.println(proxy.join(' ', "chris", "john", "frank"));
````

In the above example we override the join method if any only if there are exactly two string
arguments (varargs) passed to the join method. The first one equaling "chris" and the second one
being of any string value. The first call matches the specified argument matchers. The second call
does not match because the second vararg is an Integer rather than a string. The third call does
not match because there are three strings supplied rather than the stubbed requirement of two.



Precedence
----------

When a stubbed method is executed a matching answer is found using a precedence from most specific
to least specific. That means matchers that are more specific will be evaluated before those that
are less specific.

Example:
````
  List<String> proxy = Proximo.proxy(List.class, myList);
  doReturn("no").when(proxy).get(eq(3));
  doReturn("yes").when(proxy).get(anyInt());
````

In the above example the stubbing which returns "no" is more specific than the stubbing that returns
"yes" and as such will be evaluated first for a match. If the supplied argument is three it will of
course match and be executed. For all other values we fall through to the more generic stubbing which
accepts any integer value.

Example 2:
````
   public interface Joiner {
      public String join(char c, Object... objects);
   }
   Joiner proxy = Proximo.proxy(Joiner.class, myJoinerImpl);
   doReturn("yes").when(proxy).join(anyChar(), eq("fred"));
   doReturn("no").when(proxy).join(eq(' '), anyString());

   // prints "no"
   System.out.println(proxy.join(' ', "fred");
````

In the above example it is ambiguous which is more specific as they both have a single highly specific
matcher and a secondary generic matcher. In this case the one stubbed last will be evaluated first. It
will in effect override the first stubbing in all cases where the separator char is a space.


Chaining
--------

In the rare event that you wish subsequent calls to behave differently you may use chaining to
accomplish that goal.

Example:
````
   List<String> items = Proximo.proxy(List.class, myList);
   doNothing()
     .doForwardCall()
     .when(items)
     .clear();
````

In the above example the first call to clear will do nothing. All subsequent calls to clear will
actually forward the call to the proxied list instance resulting in its content's being cleared.

The important thing to note is that the last DO operation will be executed on all subsequent calls
after the initial set of DO operations have been executed.
