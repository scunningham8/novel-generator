package edu.unl.raikes.novelgenerator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Reads a file and finds patterns within it.
 * 
 * @author sarahcunningham
 *
 */
public class FileReader {

    /**
     * Reads and returns all contents of a file.
     * 
     * @param filepath the filepath (including the filename) of the file to read
     * @return a string containing all contents of the file
     */
    public static String readFile(String filepath) {
        // creates a string builder to add all of the strings in the file to
        StringBuilder fileString = new StringBuilder();

        // creates a healthy scanner
        Scanner stringReader = null;
        try {
            stringReader = new Scanner(new File(filepath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(0);
        }

        // adds all lines in the file to the string builder
        while (stringReader.hasNextLine()) {
            fileString.append(stringReader.nextLine());
            fileString.append("\n");
        }

        // closes scanner
        stringReader.close();

        return fileString.toString();
    }

    /**
     * Accepts a string and returns the tokens (words, paragraph breaks, sentence-ending punctuation).
     * 
     * @param input a string that needs to be tokenized
     * @return an ordered List of tokens
     */
    public static List<String> tokenizeString(String input) {
        List<String> results = new ArrayList<String>();

        // fix issues if any input documents use Windows line endings
        input = input.replaceAll("\\r\\n?", "\n");

        // reduce any instance of two or more newlines to only two newlines
        input = input.replaceAll("(\\n\\n+)", "\n\n");

        // use regular expressions to tokenize string
        // this regular expression has 6 options:
        // [a-zA-Z]+'?[a-zA-z]+ : a word of length >=2 or a contraction of length >= 3
        // [a-zA-z] : single-character alphanumeric words
        // [.] : a period
        // [?] : a question mark
        // [!] : an exclamation point
        // [\\n]{2,} : two newlines
        Pattern pattern = Pattern.compile("[a-zA-Z]+'?[a-zA-z]+|[a-zA-z]|[.]|[?]|[!]|[\\n]{2,}");
        Matcher matcher = pattern.matcher(input);

        // collect results
        while (matcher.find()) {
            results.add(matcher.group());
        }

        // return results
        return results;
    }

    /**
     * Creates sequences based on tokens.
     * 
     * @param tokens list of all keys
     * @return an arrayList of all sequences
     */
    public static ArrayList<String> generateSequences(List<String> tokens, int k) {
        // makes array list with all of the sequences
        ArrayList<String> sequences = new ArrayList<String>();

        // for loop to add the next sequence in the file to an arrayList
        for (int i = 0; i < tokens.size(); i++) {
            String single = "";
            for (int j = i; j < i + k; j++) {
                if (j < i + k - 1) {
                    single += tokens.get(j % tokens.size()) + " ";
                } else if (j < i + k) {
                    single += tokens.get(j % tokens.size());
                }
            }

            sequences.add(single);
        }

        // returns array list
        return sequences;
    }

}
