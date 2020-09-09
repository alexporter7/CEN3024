//Author Name:Alex Porter
//Date: 8/27/2020
//Program Name: chp3_text_analyzer
//Purpose: Download play, process data, return word frequency

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;

class chp3_text_analyzer {

    public static final int DISPLAY_COUNT = 20; //We want to display 20 words
    public static final boolean USE_FILE = true; //Didn't realize it had to be from a file so heres the swap

    public static HashMap<String, Integer> wordCount = new HashMap<String, Integer>();

    public static void main(String[] args) throws IOException {

        String text = "";

        if(!USE_FILE) {
            System.out.println("Downloading text from website");

            //Define the URL and connect to it
            String url = "http://shakespeare.mit.edu/macbeth/full.html";
            Document doc = Jsoup.connect(url).get();

            //Get all BlockQuote Elements and add in the <a> tag (for some reason text is stored in there)
            text = doc.select("blockquote").text();
            text += doc.select("a").text();
            System.out.println("Completed downloading text from [" + url + "]");
        }
        else {

            File currentDirectory = new File("./chapter3");
            File fileToRead = null;

            String files = Arrays.toString(currentDirectory.listFiles(//Filter out the text files
                    new FilenameFilter() {
                        @Override
                        public boolean accept(File dir, String name) {
                            return name.endsWith(".txt");
                        }
                    }
            ));

            if(files.equals("null")) {
                System.exit(1);
            }

            //Create the file
            fileToRead = new File(
                    files
                            .replaceAll("]", "")
                            .replaceAll("\\[", "")
            );

            //Create the Scanner
            Scanner fileReader = new Scanner(fileToRead);
            while(fileReader.hasNextLine()) {
                text += (fileReader.nextLine() + " ");
            }

        }

        //processing text
        System.out.println("Processing text");
        processText(text);
        
    }

    public static void processText(String text) {

        String cleanedText = cleanText(text);

        //Split all of the text into individual words
        String[] words = cleanedText.split(" ");
        for(String s : words) {
            if(wordCount.containsKey(s)) {
                //if it does have the word, add 1 to it's appearance
                wordCount.put(s, wordCount.get(s) + 1);
            }
            else {
                //if it doesn't have the word, it needs to be added with a appearance of 1
                wordCount.put(s, 1);
            }
        }

        //Now it needs to be sorted so we can tell what is actually being found
        //So this is pretty complicated, but essentially what we're doing is taking advantage
        //of the lambda feature in java. We are "streaming" the values through a compare function
        //and then adding them to the sorted word count.

        HashMap<String, Integer> sortedWordCount =
        wordCount.entrySet()
                .stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue(Collections.reverseOrder()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new
                ));

        displayText(sortedWordCount);

    }

    public static void displayText(HashMap<String, Integer> wordCount) {

        for(int i = 0; i < DISPLAY_COUNT; i++) {
            System.out.println("[" + (i + 1) + "] Word: "
                    +  wordCount.keySet().toArray()[i]
                    + " was used "
                    + wordCount.get(wordCount.keySet().toArray()[i])
                    + " times");
        }

    }

    public static String cleanText(String text) {

        //We need to clean the text up, get rid of punctuation etc
        text = text.replaceAll("[.]", "");
        text = text.replaceAll("[?]", "");
        text = text.replaceAll("[!]", "");
        text = text.replaceAll("[,]", "");
        text = text.replaceAll("[:]", "");
        text = text.replaceAll("[;]", "");
        text = text.replaceAll("[{]", "");
        text = text.replaceAll("[-]", " ");

        //This is all case sensitive so we need all lowercase letters
        text = text.toLowerCase();

        return text;

    }

}