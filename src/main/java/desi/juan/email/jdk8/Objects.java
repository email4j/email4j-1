/**
 * 58.com Inc.
 * Copyright (c) 2005-2017 All Rights Reserved.
 */
package desi.juan.email.jdk8;

/**
 * @author yangxiang01
 * @version $Id: Objects.java, v 0.1 2017年05月28日 13:01:50 yangxiang01 Exp $
 */
public class Objects {
    public static <T> T requireNonNull(T obj, String message) {
        if (obj == null)
            throw new NullPointerException(message);
        return obj;
    }

    public static <T> T requireNonNull(T obj) {
        if (obj == null)
            throw new NullPointerException();
        return obj;
    }
}