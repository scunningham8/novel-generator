package edu.unl.raikes.novelgenerator;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Automatically generates novels based on provided inspiration.
 * 
 * @author Stephanie Valentine and sarahcunningham
 */
public class NovelGenerator {
    
    /**
     * Ensures that user enters a number greater than 0.
     * 
     * @param num the number being checked
     * @param scnr the scanner to find a valid number
     * @return the validated number
     */
    public static int validateNumber(int num, Scanner scnr) {
        while (num < 0) {
            System.out.println("Sorry, that is an invalid number :( Please enter a new one! ");
            num = scnr.nextInt();
        }
        return num;
    }

    /**
     * Main function that reads files, generates models, etc.
     * 
     * @param args does not accept any args.
     * @throws FileNotFoundException  if the file isn't found
     */
    public static void main(String[] args) throws FileNotFoundException {        
        //intitialize scanner
        Scanner scnr = new Scanner(System.in);
        
        // prompts user for k and minimum length
        System.out.println("What do you want the \"K\" to be? ");
        int k = scnr.nextInt();
        k = validateNumber(k, scnr);
        
        System.out.println("What do you want the minimum length of your novel to be? ");
        int min = scnr.nextInt();
        min = validateNumber(min, scnr);
        
        // generate novel
        System.out.println(Novel.generateNovel(min, k));
        
        //closes scanner
        scnr.close();
    }
}
