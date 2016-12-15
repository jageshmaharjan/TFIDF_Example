package test.tfidf;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * Created by jugs on 12/14/16.
 */
public class j_3
{
    public static void main(String[] args) throws Exception
    {
        File file = new File("/home/username/filename.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        while ((line = br.readLine()) != null)
        {
            System.out.println(line);
        }

    }
}
