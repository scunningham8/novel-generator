package edu.unl.raikes.novelgenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Object that creates a model to base a novel off of.
 * 
 * @author sarahcunningham
 *
 */
public class Model {
    private Map<String, List<String>> model;
    private int k;

    /**
     * Constructor for Model Class.
     * 
     * @param model the map of the model
     * @param k the length of the sequences
     */
    public Model(int k) {
        this.model = new HashMap<String, List<String>>();
        this.k = k;
    }

    /**
     * Gets the K value.
     * 
     * @return the k value
     */
    public int getK() {
        return k;
    }

    /**
     * Gets the training model (convenience method for use by unit testing functions).
     * 
     * @return the model
     */
    public Map<String, List<String>> getModel() {
        return this.model;
    }

    /**
     * Reads a file, tokenizes it, and uses the tokens to train the NovelGenerator model.
     * 
     * @param filepath the filepath (including the filename) of the file from which to learn
     */
    public void learnFromFile(String filepath) {
        // TODO: COMBINE THE FILE-READING, TOKENIZING, AND MODEL-TRAINING FUNCTIONS

        String file = FileReader.readFile(filepath);

        // call tokenize string on the return of previous comment
        List<String> tokens = FileReader.tokenizeString(file);

        // generate sequences based off of tokens
        ArrayList<String> sequences = FileReader.generateSequences(tokens, this.k);

        // train the novel generator model
        this.trainModel(sequences);
    }

    /**
     * Learns an author's style. Returns a map with each unique word in the input as a key. The map values are Lists of
     * the tokens that follow the key in the text.
     * 
     * @param input an ordered list of tokens
     */
    public void trainModel(List<String> input) {
        // TODO: FINISH THIS FUNCTION
        ArrayList<String> starts = new ArrayList<String>();

        for (int i = 0; i < input.size(); i++) {
            List<String> values = new ArrayList<String>();

            if (this.model.get(input.get(i)) != null) {
                values = this.model.get(input.get(i));
            }
            if (input.get(i).endsWith(".")) {
                starts.add(input.get((i + this.k) % input.size()));
            }

            values.add(input.get((i + this.k) % input.size()));

            this.model.put(input.get(i), values);
        }

        this.model.put(".", starts);
    }
}
