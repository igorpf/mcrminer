package com.mcrminer.service.impl.gerrit;

import com.google.gerrit.extensions.restapi.RestApiException;

@FunctionalInterface
public interface ApiSupplier<T> {
    T get() throws RestApiException;
}

