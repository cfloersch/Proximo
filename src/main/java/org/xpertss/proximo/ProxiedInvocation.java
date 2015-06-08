/**
 * Copyright 2015 XpertSoftware
 * <p/>
 * Created By: cfloersch
 * Date: 6/7/2015
 */
package org.xpertss.proximo;

import org.xpertss.proximo.util.Utils;
import xpertss.proximo.Invocation;

import java.lang.reflect.Method;

public class ProxiedInvocation implements Invocation {

   private Object proxied;
   private Object proxy;
   private Method method;
   private Object[] args;

   ProxiedInvocation(Object proxied, Object proxy, Method method, Object[] args)
   {
      this.proxied = proxied;
      this.proxy = proxy;
      this.method = method;
      this.args = Utils.clone(args);
   }


   @Override
   public Object getProxy()
   {
      return proxy;
   }

   @Override
   public Object getProxied()
   {
      return proxied;
   }

   @Override
   public Method getMethod()
   {
      return method;
   }

   @Override
   public <T> T getArgumentAt(int index, Class<T> clazz)
   {
      return clazz.cast(args[index]);
   }

   @Override
   public Object[] getArguments()
   {
      return args.clone();
   }

   @Override
   public Object forwardCall()
      throws Throwable
   {
      return method.invoke(proxied, args);
   }
}
