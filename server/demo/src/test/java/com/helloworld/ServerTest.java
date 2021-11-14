package com.helloworld;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit test for Server.
 */
public class ServerTest 
{
    private BufferedReader bufReader = null;

    @BeforeEach
    void setupThis(){
        System.out.println("@BeforeEach executed");
        // find the file
        try {
            InputStreamReader reader = new InputStreamReader(new FileInputStream("/tmp/articles/a4"));
            bufReader = new BufferedReader(reader);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Calculate the how many words in this article
     */
    @Test
    void testArticleWordCount(){
        
        String article = "";
        String[] words;
        int ch;
        try {
            // read the article
            while((ch = bufReader.read()) != -1){
                article += (char)ch;
            }
            
            // remove all stop words and split articles by space
            words = article.split("\\s|\\n");
            for(int i=0; i<words.length; i++){
                words[i] = words[i].replaceAll("\\W", "");
            }

            Assertions.assertEquals(8, words.length);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @AfterEach
    void tearThis(){
        System.out.println("@AfterEach executed");
        bufReader = null;
    }
}
