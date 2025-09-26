package org.example;


import java.util.HashMap;
import java.util.Map;

public class Datastore {
    private static final Map<String, Object> store = new HashMap<>();

    // Prevent instantiation
    private Datastore() {}

    // Save or update a value
    public static void saveValue(String key, Object value) {
        store.put(key, value);
    }

    // Retrieve a value (with cast)
    public static <T> T retrieveValue(String key, Class<T> type) {
        Object value = store.get(key);
        if (value == null) {
            throw new IllegalStateException("No value stored for key: " + key);
        }
        return type.cast(value);
    }

    // Check if a value exists
    public static boolean hasValue(String key) {
        return store.containsKey(key);
    }

    // Remove a value
    public static void removeValue(String key) {
        store.remove(key);
    }

    // Clear everything
    public static void clear() {
        store.clear();
    }
}