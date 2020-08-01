package com.api.quiz;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestUtility {

   static Pattern pattern =
            Pattern.compile("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[^a-zA-Z0-9])(?!.*\\s).{8,20}$");

   public static boolean validateRegex( String value ) {
       Matcher matcher = pattern.matcher(value);
       return matcher.matches();
   }

    public static void main ( String args [] ) {
            List<String> values= Arrays.asList(
                    "VishwajitShinde@89",
                    "Vishwajit__w81",
                    "Fusfus_Sap_143_",
                    "Dhamma4u!",
                    "Dhamma4uE%",
                    "Dhamma4u@",
                    "Dhamma4u#",
                    "Pds1123S@",
                    "11Shruna*wx",
                    "DSTest##$Ion"
            );
            for ( String value : values )
            System.out.println(  value  + " -> " + validateRegex(value) );

    }
}
