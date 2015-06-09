package org.xpertss.proximo;

import java.io.IOException;

/**
 * Created by cfloersch on 6/9/2015.
 */
public interface IStream {

   boolean write(byte[] data) throws IOException;

}
