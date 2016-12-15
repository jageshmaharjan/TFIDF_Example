package test.tfidf;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by jugs on 12/14/16.
 */
public class j_1
{

    public static Pattern pattern = Pattern.compile("(<.*?>)|\\(\\*?\\)|[^A-Za-z0-9_ \\.,\"-]|-{2,}|\\.{2,}");
    Map<String, Double> idf = new HashMap<>();
    Double N = 0.0;
    public static void main(String[] args) throws Exception
    {
        j_1 j1 = new j_1();
        j1.read();
    }

    private void read() throws Exception
    {
        File file = new File("/home/username/ReviewJSON.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        while ((line = br.readLine()) != null)
        {
            work(line);
            N++;
        }
        finalizeIDF(idf);
        saveIDF();
        System.out.println();
    }

    private void saveIDF()
    {
        System.out.println();
        try
        {
            FileWriter fileWriter = new FileWriter("idf.txt",true);
            fileWriter.write(idf.toString());
            fileWriter.close();

        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private void finalizeIDF(Map<String, Double> idf)
    {
        for (Map.Entry entry : idf.entrySet())
        {
            Double entryValue = (Double) entry.getValue();
            Double logvalue = Math.log10(N/entryValue);
            idf.put((String) entry.getKey(), round(logvalue,8));
        }
    }

    private void work(String line) throws Exception
    {
        String cleantxt = getCleanText(line);
        List<String> tokenList = new ArrayList<>();
        for (String token : cleantxt.split(" "))
        {
            tokenList.add(token);
        }
        Map<String, Double> termcount = getTermCount(tokenList);
        Map<String,Double> termFreq = getTermFreq(termcount, Double.valueOf(tokenList.size()));
        writetoFileTermfreq(termFreq);
        createIDF(termcount);

    }

    private String getCleanText(String line)
    {
        String cleanReview;
        Matcher matcher = pattern.matcher(line );
        if (matcher.find())
        {
            cleanReview = matcher.replaceAll("");
        }
        else
            cleanReview = line;

        return cleanReview;

    }

    private void writetoFileTermfreq(Map<String, Double> termFreq) throws Exception
    {
        FileWriter fileWriter = new FileWriter("termFrequency.txt",true);
        fileWriter.write(termFreq.toString() +'\n');
        fileWriter.close();
    }

    private void createIDF(Map<String, Double> termcount)
    {
        for (Map.Entry entry : termcount.entrySet())
        {
            Double count = idf.get(entry.getKey());
            if (count == null)
                idf.put((String) entry.getKey(), 1.0);
            else
                idf.put((String) entry.getKey(), (count+1));

        }
    }


    private Map<String, Double> getTermFreq(Map<String, Double> termcount, Double size)
    {
        Map<String,Double> tf = new HashMap<>();
        for (Map.Entry entry : termcount.entrySet())
        {
            Double calc = (Double) entry.getValue();
            tf.put((String) entry.getKey(), round(calc / size, 8));
        }
        return tf;
    }

    private Map<String, Double> getTermCount(List<String> tokenList)
    {
        Map<String, Double> termcount = new HashMap<>();

        for (String token : tokenList)
        {
            Double count = termcount.get(token);
            if (count == null)
                termcount.put(token,1.0);
            else
                termcount.put(token,count+1.0);
        }
        return termcount;
    }

    public static double round(double value, int places)
    {
        if (places < 0)
            throw new IllegalArgumentException();
        long factor = (long) Math.pow(10,places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp/factor;
    }
}
