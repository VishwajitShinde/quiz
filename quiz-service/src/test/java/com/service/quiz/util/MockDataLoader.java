package com.service.quiz.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class MockDataLoader {
    static ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_NUMBERS_FOR_ENUMS, false);
    }

    /**
     * @param fileName
     * @return
     */
    public static String loadDataFromFile(String fileName) {
        try {
            return objectMapper.readValue(new File(fileName), String.class);
        } catch (IOException e) {
            System.out.println(" Exception While Parsing Data from File : " + fileName);
        }
        return null;
    }

    /**
     * @param data
     * @param valueTypeRef
     * @param <T>
     * @return
     */
    public static <T> T loadJsonDataFromString(String data, Class<T> valueTypeRef) {
        try {
            return objectMapper.readValue(data, valueTypeRef);
        } catch (IOException e) {
            System.out.println(" Exception While Parsing Data from File : " + data);
        }
        return null;
    }

    /**
     *
     * @param data
     * @param <T>
     * @return
     */
    public static <T> String loadDataInJsonString(T data) {
        try {
            return objectMapper.writeValueAsString(data);
        } catch (IOException e) {
            System.out.println(" Exception While Parsing Data from File : " + data);
        }
        return null;
    }


    /**
     *
     * @param sourceData
     * @param destValueTypeRef
     * @param <T>
     * @return
     */
    public static <T, X> T convertData(X sourceData, Class<T> destValueTypeRef) {
        try {
             String fromSource = objectMapper.writeValueAsString(sourceData);
             return objectMapper.readValue(fromSource, destValueTypeRef);
        } catch (IOException e) {
            System.out.println(" Exception While Parsing Data from File : " + sourceData.getClass());
        }
        return null;
    }

    /**
     * @param fileName
     * @param valueTypeRef
     * @param <T>
     * @return
     */
    public static <T> T loadDataFromFile(String fileName, Class<T> valueTypeRef) {
        try {
            return objectMapper.readValue(new File(fileName), valueTypeRef);
        } catch (IOException e) {
            System.out.println(" Exception While Parsing Data from File : " + fileName);
        }
        return null;
    }

}
