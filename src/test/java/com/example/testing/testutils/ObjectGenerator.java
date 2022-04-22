package com.example.testing.testutils;

import org.jeasy.random.EasyRandom;

public class ObjectGenerator {

    private static final EasyRandom generator = new EasyRandom();

    private ObjectGenerator() {
        // Intentional blank
    }

    public static <T> T initRandomInstance(Class<T> tClass) {
        return generator.nextObject(tClass);
    }
}
