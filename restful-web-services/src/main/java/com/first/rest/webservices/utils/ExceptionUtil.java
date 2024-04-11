package com.first.rest.webservices.utils;

public final class ExceptionUtil {
    private ExceptionUtil() {
    }

    public static RuntimeException wrapCheckedException(Throwable t) {
        if (t instanceof Error) {
            throw (Error) t;
        }
        return wrapException(t, RuntimeException.class);
    }

    private static <T extends Exception> T wrapException(Throwable t, Class<T> keep) {
        if (keep.isAssignableFrom(t.getClass())) {
            return keep.cast(t);
        }
        try {
            return keep.getConstructor(new Class<?>[] {Throwable.class}).newInstance(t);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
}
