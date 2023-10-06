package edu.unl.raikes.novelgenerator.test;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import edu.unl.raikes.novelgenerator.FileReader;
import edu.unl.raikes.novelgenerator.Model;
import edu.unl.raikes.novelgenerator.Novel;
import edu.unl.raikes.novelgenerator.NovelGenerator;

public class NovelGeneratorTest {

    @Test
    public void testIfReadFileCreatesSpacesInBetweenWords() {
        // setup
        String expected = "This \n" + "is my\n" + "testing \n" + "file.\n";

        // execute
        String actual = FileReader.readFile("./src/testFile.txt");

        // test
        assertTrue("readFile did not add the words to a string correctly", expected.equals(actual));
    }

    @Test
    public void testIfGenerateSequencesWorksWithKof2() {
        // setup
        List<String> tokens = new ArrayList<String>();
        tokens.add("The");
        tokens.add("dog");
        tokens.add("is");
        tokens.add("crusty");
        tokens.add("!");
        int k = 2;

        ArrayList<String> expected = new ArrayList<String>();
        expected.add("The dog");
        expected.add("dog is");
        expected.add("is crusty");
        expected.add("crusty !");
        expected.add("! The");

        // execute
        ArrayList<String> actual = FileReader.generateSequences(tokens, k);

        // test
        for (int i = 0; i < expected.size(); i++) {
            assertEquals("This sequence isn't right", expected.get(i), actual.get(i));
        }
    }

    @Test
    public void testIfGenerateNovelGeneratesALargeEnoughNovelwithLowNumber() {
        // setup
        int length = 10;
        int k = 3;
        String novel = "";

        // execute
        try {
            novel = Novel.generateNovel(length, k);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // test
        assertTrue("The length isn't long enough", novel.length() > length);

    }

    @Test
    public void testIfGenerateNovelGeneratesALargeEnoughNovelwithHighNumber() {
        // setup
        int length = 100;
        int k = 3;
        String novel = "";

        // execute
        try {
            novel = Novel.generateNovel(length, k);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // test
        assertTrue("The length isn't long enough", novel.length() > length);

    }
    
    @Test
    public void testIfGenerateNovelEndsWithPeriod() {
        // setup
        int length = 10;
        int k = 3;
        String novel = "";
        char expected = '.';

        // execute
        try {
            novel = Novel.generateNovel(length, k);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        char actual = novel.charAt(novel.length() - 2);

        // test
        assertSame("doesn't end in period", expected, actual);

    }
    
    @Test
    public void testIfGenerateNovelStartsWithCapital() {
        // setup
        int length = 10;
        int k = 3;
        String novel = "";

        // execute
        try {
            novel = Novel.generateNovel(length, k);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // test
        assertTrue("start with a capital letter", Character.isUpperCase(novel.charAt(0)));

    }

    @Test
    public void testIfGenerateSequencesWorksWithKof3() {
        // setup
        List<String> tokens = new ArrayList<String>();
        tokens.add("The");
        tokens.add("dog");
        tokens.add("is");
        tokens.add("crusty");
        tokens.add("!");
        int k = 3;

        ArrayList<String> expected = new ArrayList<String>();
        expected.add("The dog is");
        expected.add("dog is crusty");
        expected.add("is crusty !");
        expected.add("crusty ! The");
        expected.add("! The dog");

        // execute
        ArrayList<String> actual = FileReader.generateSequences(tokens, k);

        // test
        for (int i = 0; i < expected.size(); i++) {
            assertEquals("This sequence isn't right", expected.get(i), actual.get(i));
        }
    }

    @Test
    public void testIfTrainModelAddsMapItemsIfEverykeyHasOneValuek2() {
        // setup
        List<String> sequences = new ArrayList<String>();
        sequences.add("The dog");
        sequences.add("dog is");
        sequences.add("is crusty");
        sequences.add("crusty !");
        sequences.add("! The");

        Map<String, List<String>> expected = new HashMap<String, List<String>>();
        List<String> first = new ArrayList<String>();
        first.add("is crusty");
        expected.put("The dog", first);
        List<String> second = new ArrayList<String>();
        second.add("crusty !");
        expected.put("dog is", second);
        List<String> third = new ArrayList<String>();
        third.add("! The");
        expected.put("is crusty", third);
        List<String> period = new ArrayList<String>();
        expected.put(".", period);
        List<String> fourth = new ArrayList<String>();
        fourth.add("The dog");
        expected.put("crusty !", fourth);
        List<String> fifth = new ArrayList<String>();
        fifth.add("dog is");
        expected.put("! The", fifth);

        int k = 2;
        Model test = new Model(k);

        // execute
        test.trainModel(sequences);
        Map<String, List<String>> actual = test.getModel();

        // test
        assertEquals("trainModel is wrong", expected, actual);

    }
    
    

    @Test
    public void testIfTrainModelAddsMapItemsIfEverykeyHasMoreThanOneValuek2() {
        // setup
        List<String> sequences = new ArrayList<String>();
        sequences.add("The dog");
        sequences.add("dog is");
        sequences.add("is crusty");
        sequences.add("crusty dog");
        sequences.add("dog is");
        sequences.add("is musty");
        sequences.add("musty !");
        sequences.add("! The");

        Map<String, List<String>> expected = new HashMap<String, List<String>>();
        List<String> first = new ArrayList<String>();
        first.add("! The");
        expected.put("is musty", first);
        List<String> seventh = new ArrayList<String>();
        seventh.add("dog is");
        expected.put("! The", seventh);
        List<String> sixth = new ArrayList<String>();
        sixth.add("dog is");
        expected.put("is crusty", sixth);
        List<String> second = new ArrayList<String>();
        second.add("is musty");
        expected.put("crusty dog", second);
        List<String> fourth = new ArrayList<String>();
        fourth.add("crusty dog");
        fourth.add("musty !");
        expected.put("dog is", fourth);
        List<String> third = new ArrayList<String>();
        third.add("is crusty");
        expected.put("The dog", third);
        List<String> period = new ArrayList<String>();
        expected.put(".", period);
        List<String> eigth = new ArrayList<String>();
        eigth.add("is musty");
        expected.put("crusty dog", eigth);
        List<String> fifth = new ArrayList<String>();
        fifth.add("The dog");
        expected.put("musty !", fifth);

        int k = 2;
        Model test = new Model(k);

        // execute
        test.trainModel(sequences);
        Map<String, List<String>> actual = test.getModel();

        // test
        assertEquals("trainModel is wrong", expected, actual);

    }

    @Test
    public void testIfTrainModelAddsMapItemsIfValuek3() {
        // setup
        List<String> sequences = new ArrayList<String>();
        sequences.add("The dog is");
        sequences.add("dog is crusty");
        sequences.add("is crusty !");
        sequences.add("crusty ! The");
        sequences.add("! The dog");

        Map<String, List<String>> expected = new HashMap<String, List<String>>();
        List<String> first = new ArrayList<String>();
        first.add("crusty ! The");
        expected.put("The dog is", first);
        List<String> second = new ArrayList<String>();
        second.add("! The dog");
        expected.put("dog is crusty", second);
        List<String> third = new ArrayList<String>();
        third.add("The dog is");
        expected.put("is crusty !", third);
        List<String> period = new ArrayList<String>();
        expected.put(".", period);
        List<String> fourth = new ArrayList<String>();
        fourth.add("dog is crusty");
        expected.put("crusty ! The", fourth);
        List<String> fifth = new ArrayList<String>();
        fifth.add("is crusty !");
        expected.put("! The dog", fifth);

        int k = 3;
        Model test = new Model(k);

        // execute
        test.trainModel(sequences);
        Map<String, List<String>> actual = test.getModel();

        // test
        assertEquals("trainModel is wrong", expected, actual);

    }
}
