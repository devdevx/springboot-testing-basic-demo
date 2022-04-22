package com.example.testing.testutils;

import lombok.extern.slf4j.Slf4j;

import static org.junit.jupiter.api.Assertions.fail;

@Slf4j
public class ResponseReader {

    private static final String TEST_JSON_DIR = "testjson/";

    private ResponseReader() {
        // Intentional blank
    }

    public static String readResponse(String path) {
        path = TEST_JSON_DIR + path;
        try {
            return new String(ResponseReader.class.getClassLoader().getResourceAsStream(path).readAllBytes());
        } catch (Exception e) {
            // Intentional blank
        }
        throw new RuntimeException("Resource not found: " + path);
    }
}
