import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class WordAnalyzer {

    public static void main(String[] args) throws Exception {

        // Get the path to the file in the current user directory
        Path path = Paths.get(System.getProperty("user.dir")).resolve("txt file to be processed");

        // Use try-with-resources to automatically close the BufferedReader
        try (BufferedReader reader = new BufferedReader(new FileReader(path.toFile()))) {
            // Create a map to store word frequencies
            Map<String, Integer> frequency = new HashMap<>();

            String line;
            // Read each line from the file
            while ((line = reader.readLine()) != null) {
                // Process the line and update word frequencies
                processLine(line, frequency);
            }

            // Print the word frequencies
            System.out.println(frequency);

            // Print the most frequently used word
            printMostFrequentlyUsedWord(frequency);
        }
    }

    // Process a line of text and update word frequencies
    private static void processLine(String line, Map<String, Integer> frequency) {
        if (!line.trim().isEmpty()) {
            // Split the line into words
            String[] words = line.split(" ");

            // Iterate over each word in the line
            for (String word : words) {
                if (word != null && !word.trim().isEmpty()) {
                    // Process the word (convert to lowercase, remove commas)
                    String processed = word.toLowerCase().replace(",", "");

                    // Update word frequencies in the map
                    frequency.put(processed, frequency.getOrDefault(processed, 0) + 1);
                }
            }
        }
    }

    // Print the most frequently used word and its frequency
    private static void printMostFrequentlyUsedWord(Map<String, Integer> frequency) {
        int mostFrequentlyUsed = 0;
        String theWord = null;

        // Iterate over the entries in the frequency map
        for (Map.Entry<String, Integer> entry : frequency.entrySet()) {
            String word = entry.getKey();
            int theVal = entry.getValue();

            // Update the most frequently used word if necessary
            if (theVal > mostFrequentlyUsed) {
                mostFrequentlyUsed = theVal;
                theWord = word;
            }
        }

        // Print the result
        System.out.println();
        System.out.printf("The most frequently used word is '%s', %d times%n", theWord, mostFrequentlyUsed);
    }
}