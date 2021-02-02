/*
Allirajah Venuraj
2013/sp/041
Summarizer for student feedback project as the final year project
 */

package summarizer;

import java.util.*;
/**
 *
 * @author Project Purpose
 */
public class Summarizer 
{
    /**
     * Pre_processing steps
     *  1-get the text.
     *  2-need to remove , ? ! like this symbols.
     *  3-need to find stop words and eliminate it.
     */
    private String fullText; // for the inputting textparagraph.
    private String [] words;
    private String [] stopWords = {"i","me","my","myself","we", "us", "our", "ours", "ourselves",
            "you", "your", "yours", "yourself", "yourselves", "he", "him", "his",
            "himself", "she", "her", "hers", "herself", "it", "its", "itself",
            "they", "them", "their", "theirs", "themselves", "what", "which",
            "who", "whom", "this", "that", "these", "those", "am", "is", "are",
            "was", "were", "be", "been", "being", "have", "has", "had", "having",
            "do", "does", "did", "doing", "would", "should", "could", "ought",
            "i'm", "you're", "he's", "she's", "it's", "we're", "they've", "i've",
            "you've", "we've", "they've", "i'd", "you'd", "he'd", "she'd", "we'd",
            "they'd", "i'll", "you'll", "he'll", "she'll", "we'll", "they'll",
            "isn't", "aren't", "wasn't", "weren't", "hasn't", "haven't", "hadn't",
            "doesn't", "don't", "didn't", "won't", "wouldn't", "can't", "cannot",
            "couldn't", "mustn't", "let's", "that's", "who's", "what's", "here's",
            "there's", "when's", "where's", "why's", "how's", "a", "an", "the",
            "and", "but", "if", "or", "because", "as", "until", "while", "of", "at",
            "by", "for", "with", "about", "against", "between", "into", "through",
            "during", "before", "after", "above", "below", "to", "from", "up", "down",
            "in", "out", "on", "off", "over", "under", "again", "further", "then",
            "once", "here", "there", "when", "where", "why", "how", "all", "any",
            "both", "each", "few", "more", "most", "other", "some", "such", "no",
            "nor", "not", "only", "own", "same", "so", "than", "too", "very"};
    
    /**
     * for order the words, add items and repeat back the items need to use HASHMAP
     * linked list and array list are same but these are in order.
     * In HASHMAP we can get in any order.
     */
    private HashMap <String, Integer> wordFrequency = new HashMap <> ();
    private HashMap <Integer, Integer> sentenceScores = new HashMap <> ();
    
    private ArrayList <String> sentences = new ArrayList <> ();
    
public Summarizer (String fullText)
{
    this.fullText=fullText;
}

public void summarize (int n)
{
    getWords();// split the paragraph to sentences
    parseSentences();
    evaluateSentences();
    showSummary(n);
}

private void getWords()
{
     words = fullText.split("\\W+");//"\\,|\\:|\\;|\\!;|\\?"
     
     for (int i=0; i<words.length; i++)
     {
         words[i] = words[i].toLowerCase();
     }
     
     for (int i=0; i<words.length; i++)
     {
         String currentWords = words[i];
         
         if(Arrays.asList(stopWords).contains(currentWords))
         {
             i++;
         }
         else
         {
             int count = wordFrequency.containsKey(currentWords) ? wordFrequency.get(currentWords): 0 ;
             wordFrequency.put(currentWords, count + 1);
         }
     }
}

private void parseSentences()
{
    String[] ignoreWords = {"Miss", "Mr", "Mrs"};
    String currentSentence;
    int currentChar = 0;
    int previousStop = 0;
    while(currentChar < fullText.length()-1)
    {
        if(fullText.charAt(currentChar)== '?' || fullText.charAt(currentChar)== '!')//to end the sentence
        {
            currentSentence =fullText.substring(previousStop,currentChar +1);
            sentences.add(currentSentence);
            currentChar++;
            previousStop= currentChar;
        }
        else if(fullText.charAt(currentChar)== '.')
        {
            if (currentChar - previousStop <= 2)
            {
                currentChar++;
            }
            else if (currentChar >2)
            {
                String twoLetter = fullText.substring(currentChar - 2, currentChar);
                String threeLetter = fullText.substring(currentChar - 3, currentChar);
                
                //ignoring the ignorewoords.
                if(Arrays.asList(ignoreWords).contains(twoLetter)|| Arrays.asList(ignoreWords).contains(threeLetter))
                {
                    currentChar++;
                }
                //end the sentence
                else
                {
                    currentSentence = fullText.substring(previousStop, currentChar +1);
                    sentences.add(currentSentence);
                    currentChar++;
                    previousStop = currentChar;
                }
            }
        }
        currentChar++;
    }
    System.out.println();
    System.out.println("Number of Sentences - " + sentences.size());
}

private void evaluateSentences()
{
    //its for for a sentence calculate the score for each sentences and save the score.
    //to get the sentences
    int sentenceCount = 0;
    for(String s : sentences)
    {
        int sentenceScore = 0; // as initial
        
        //for each sentence calculating the score
        String[] wordsInSentence = s.split(" ");
        for(String word : wordsInSentence)
        {
            if(wordFrequency.get(word)!= null)
            {
                int value = wordFrequency.get(word);
                value += word.length();
                sentenceScore += value;
            }
        }
        //sentences are looped now save the score
        sentenceScores.put(sentenceCount, sentenceScore);
        sentenceCount ++;
    }
}

private void showSummary(int n)
{
    //get the highest values in sentences
    int[] highest = findhighest(n);
    //print them in order
    for( int x: highest)
    {
        System.out.println(sentences.get(x));
    }
}

private int[] findhighest(int n)
{
    int[] topKeys = new int[n];
    List<Integer> values = new ArrayList<> (sentenceScores.values());
    Collections.sort(values, Collections.reverseOrder());
    List<Integer> topN = values.subList(0, n);
    List<Integer> keys = new ArrayList <> (sentenceScores.keySet());
    
    for(int key: keys)
    {
        int currentScore = sentenceScores.get(key);
        if (topN.contains(currentScore))
        {
            //finding the index
            for(int i=0; i<n; i++)
            {
                if(topN.get(i) == currentScore)
                {
                    topKeys[i] = key;
                }
            }
        }
    }
    return topKeys;
}
}
