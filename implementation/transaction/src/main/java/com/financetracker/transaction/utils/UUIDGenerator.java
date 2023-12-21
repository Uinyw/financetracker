package com.financetracker.transaction.utils;

import java.util.UUID;

public class UUIDGenerator {

    public static String create() {
        return UUID.randomUUID().toString();
    }
    public static UUID fromString(final String id) {
        return UUID.fromString(id);
    }

    public static String toString(final UUID id) {
        return id.toString();
    }
}
