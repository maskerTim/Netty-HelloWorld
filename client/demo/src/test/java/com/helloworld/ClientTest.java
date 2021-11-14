package com.helloworld;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit test for client.
 */
public class ClientTest{

    private BufferedReader bufReader = null;

    @BeforeEach
    void setupThis(){
        System.out.println("@BeforeEach executed");

        InputStreamReader reader;
        try {
            reader = new InputStreamReader(new FileInputStream("/tmp/articles/a4"), "UTF-8");
            bufReader = new BufferedReader(reader);
        } catch (UnsupportedEncodingException | FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }

    @Test
    void testRunTestIsWorking(){
        System.out.println("======TEST Running Test is working=======");
        Assertions.assertTrue(true);
    }

    /**
     * Test successfully to read file
     */
    @Test
    void testReadFile(){       
            try {
                String paragraph = "";
                int ch;

                while((ch = bufReader.read()) != -1){
                    paragraph += (char)ch;
                }

                bufReader.close();

                Assertions.assertEquals("Hello, My name is Martai.\nHow are you?\n", paragraph);

            } catch (FileNotFoundException | UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
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
