package com.service.quiz.util;

import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;


public class QuestionUtil {

    public static Set<Integer> generateQuestions(int totalQuestions, int selectionQuestionCount ) {
        Set<Integer> selectedQuestionNumbers = new HashSet<>();
        int max = totalQuestions;
        int min = 1;

        do {
            int questionNumber = getRandomNumberUsingNextInt( min, max);
            selectedQuestionNumbers.add(questionNumber);
        } while ( selectedQuestionNumbers.size() < selectionQuestionCount );

       return selectedQuestionNumbers;
    }

    private static int getRandomNumberUsingNextInt(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }

    public static File getFileFromClassPath(String filePath ) throws IOException {
       return new ClassPathResource(filePath).getFile();
    }

    /**
     *
     * @param filePath
     * @return
     * @throws IOException
     */
    public static String getFileContent(String filePath ) throws IOException {
        File resource =  new ClassPathResource(filePath).getFile();
        String content = new String(Files.readAllBytes(resource.toPath()));
        return  content;
    }



}
