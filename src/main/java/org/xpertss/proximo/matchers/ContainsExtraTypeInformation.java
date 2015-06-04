/**
 * Copyright 2015 XpertSoftware
 * <p/>
 * Created By: cfloersch
 * Date: 6/3/2015
 */
package org.xpertss.proximo.matchers;

import java.io.Serializable;

import org.hamcrest.SelfDescribing;

public interface ContainsExtraTypeInformation extends Serializable {
   SelfDescribing withExtraTypeInfo();

   boolean typeMatches(Object object);
}