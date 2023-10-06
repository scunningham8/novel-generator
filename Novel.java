package edu.unl.raikes.novelgenerator;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Class that generates a novel. 
 * 
 * @author sarahcunningham
 *
 */
public class Novel {

    /**
     * Generates a novel with approximately as many tokens as the requested length. The novel may be longer than the
     * requested length (it will finish its last sentence), but it will never be shorter.
     * 
     * @param length a requested minimum number of tokens of the auto-generated novel
     * @return an auto-generated novel
     * @throws FileNotFoundException if the file isn't found
     */
    public static String generateNovel(int length, int k) throws FileNotFoundException {

        Model model = new Model(k);
        model.learnFromFile("./training_texts/AnneOfGreenGables.txt");
        model.learnFromFile("./training_texts/TheWizardOfOz.txt");
        Map<String, List<String>> map = model.getModel();

        Random random = new Random();

        StringBuilder novel = new StringBuilder();

        String previous = map.get(".").get(random.nextInt(map.get(".").size()));
        novel.append(previous);
        novel.append(" ");

        for (int i = 0; i < (length / model.getK()); i++) {
            String temp = map.get(previous).get(random.nextInt(map.get(previous).size()));
            novel.append(temp);
            novel.append(" ");
            previous = temp;
        }
        while (!previous.endsWith(".")) {
            String temp = map.get(previous).get(random.nextInt(map.get(previous).size()));
            novel.append(temp);
            novel.append(" ");
            previous = temp;
        }

        return novel.toString();
    }
}
