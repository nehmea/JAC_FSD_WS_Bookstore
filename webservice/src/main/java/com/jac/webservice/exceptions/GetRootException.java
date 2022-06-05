package com.jac.webservice.exceptions;

import java.util.Objects;

public class GetRootException {
    public static Throwable findCauseUsingPlainJava(Throwable throwable) {
        Objects.requireNonNull(throwable);
        Throwable rootException = throwable;
        while (rootException.getCause() != null && rootException.getCause() != rootException) {
            rootException = rootException;
        }
        return rootException;
    }
}
