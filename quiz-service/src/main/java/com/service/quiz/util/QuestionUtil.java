package com.service.quiz.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.*;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class QuestionUtil {

    private static  Logger logger = LoggerFactory.getLogger( QuestionUtil.class);

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

    public static File getFileFromClassPath(String filePath,  ResourceLoader resourceLoader ) throws IOException {
        try
        {
            return new ClassPathResource(filePath).getFile();
        } catch ( Exception sd ) {
            logger.error(" Exception Message : {} | cause : {} ", sd.getMessage(), sd.getCause() );
            Resource resource = resourceLoader.getResource("classpath:" + filePath );
            return resource.getFile();
        }
    }

    /**
     *
     * @param filePath
     * @return
     * @throws IOException
     */
    public static String getFileContent(String filePath, ResourceLoader resourceLoader ) throws IOException {
        String content = null;
        try
        {
            File resource =  new ClassPathResource(filePath).getFile();
            content = new String(Files.readAllBytes(resource.toPath()));
        } catch ( Exception sd ) {
            logger.error(" Exception Message : {} | cause : {} ", sd.getMessage(), sd.getCause() );
            Resource resource = resourceLoader.getResource("classpath:" + filePath );
            InputStream stram = resource.getInputStream();
            StringBuffer sb = new StringBuffer();
            BufferedReader br = new BufferedReader(new InputStreamReader(stram));
            br.lines().forEach( line -> { sb.append(line );});
            br.close();
            content = sb.toString();
        }
        return  content;
    }



}
