package com.api.quiz.util;

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


}
