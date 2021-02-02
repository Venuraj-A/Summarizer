/*
Allirajah venuraj
2013/sp/041
Summarizer for student feedback project as the final year project
 */

package summarizer;

import java.io.*;
import java.util.Scanner;


/**
 *
 * @author Project Purpose
 */
public class Test 
{
    public static void main(String [] args) throws IOException
    {
        String line;
        String fullText = "";
        Scanner text = new Scanner (System.in);
        System.out.println( "how many sentences want in this summary ? ");
        
        int summaryLength = text.nextInt();
        System.out.println("Enter the text followed bt \"###\" :" );
        InputStreamReader inputStreamReader = new InputStreamReader(System.in);
        BufferedReader bufferedReader = new BufferedReader (inputStreamReader);
        do
        {
            line = bufferedReader.readLine();
            if(line.equals("###"))
                break;
            else
                fullText = fullText + line +" ";
        }
        while (!line.equals("zyxycy"));
        inputStreamReader.close();
        bufferedReader.close();
        System.out.println(".............");
                
        Summarizer summarize = new Summarizer(fullText);
        summarize.summarize(summaryLength);
    }
}
