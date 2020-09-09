//Author Name:Alex Porter
//Date: 8/27/2020
//Program Name: chp3_text_analyzer
//Purpose: Download play, process data, return word frequency

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;

class chp3_text_analyzer {

    public static HashMap<String, Integer> wordCount = new HashMap<String, Integer>();

    public static void main(String[] args) throws IOException {

        //Define the URL and connect to it
        String url = "http://shakespeare.mit.edu/macbeth/full.html";
        Document doc = Jsoup.connect(url).get();

        //Get all BlockQuote Elements and add in the <a> tag (for some reason text is stored in there)
        String text = doc.select("blockquote").text();
        text += doc.select("a").text();

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