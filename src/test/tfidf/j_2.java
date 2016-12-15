package test.tfidf;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jugs on 12/14/16.
 */
public class j_2
{
    private Map<String, Double> IDF;

    public static void main(String[] args) throws Exception
    {
        j_2 j2 = new j_2();
        j2.readFile();
    }

    public void readFile() throws Exception
    {
        File file = new File("/home/username/termFrequency.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        Map<String, Double> idf = getIDF();
        while ((line = br.readLine()) != null)
        {
            Map<String, Double> tfidf = new HashMap<>();
            String[] str = line.substring(1, line.length() - 1).split(", ");
            for (int i = 0; i < str.length; i++)
            {
                String[] kvPair = str[i].split("=");
                Double score = Double.valueOf((kvPair[1])) * getTFIDFScore(kvPair[0],idf);
                tfidf.put(kvPair[0], j_1.round(score,8));
            }
            writeToFileTFIDF(tfidf);
        }

    }

    private void writeToFileTFIDF(Map<String, Double> tfidf) throws Exception
    {
        FileWriter fileWriter = new FileWriter("tfidf.txt",true);
        fileWriter.write(tfidf.toString() + '\n');
        fileWriter.close();
    }

    private Double getTFIDFScore(String token, Map<String, Double> idf)
    {
        if (idf.get(token) != null)
            return idf.get(token);
        else
            return 0.0;
    }

    public Map<String,Double> getIDF() throws Exception
    {
        File file = new File("/home/jugs/IdeaProjects/TFIDF_Test/idf.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        Map<String, Double> idf = new HashMap<>();
        while ((line = br.readLine()) != null)
        {
            String[] str = line.substring(1,line.length()-1).split(", ");
            for (int i=0;i<str.length; i++)
            {
                String[] kvPair = str[i].split("=");
                idf.put(kvPair[0], Double.valueOf(kvPair[1]));
            }
        }
        return idf;
    }



}
