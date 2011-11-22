/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package labfiles;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author lucian
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

       final String datePatternSlashString = "((((([^0-9]?[1-9])|([1-2][0-9]))/([13-9]|(1[012]))/|(30/(([13-9])|(1[012]))/)|((31/(([13578])|(1[02]))))))([0-9]{4}))|((([^0-9][1-9])|(1[0-9])|(2[0-8]))/2/[0-9]{4})|(29/2/[0-9]{2}(([02468][048])|([13579][62])))";
       

       Pattern datePattern = Pattern.compile(datePatternSlashString);
       Matcher matcher = null;
       String inFileName = args[0];
       String outFileName = args[1];
       File inFile = null;
       File outFile = null;

       if(outFileName != null)
            {
                outFile = new File (outFileName);
                if (!outFile.exists())
                {
                    System.out.println ("WAR:FILE "+"\'"+outFileName+"\' is not exists.It will be created");
                }
            }
             else
           {
               System.out.println("ERR:null  output filname");
               return;
           }
       try
       {
           if (inFileName!=null)
           {
               inFile = new File (inFileName);
               if (!inFile.exists())
               {
                   System.out.println ("ERR:FILE "+"\'"+inFileName+"\' is not exists");
                   return;
               }
               BufferedReader br = new BufferedReader(new FileReader(inFileName));
               BufferedWriter bw = new BufferedWriter (new FileWriter(outFile));
               while (br.ready())
               {
                   String tmp = br.readLine();                  
                   matcher = datePattern.matcher(tmp);
                   while (matcher.find())
                   {
                       String match = matcher.group();                       
                       tmp=matcher.replaceFirst(format(match));
                       matcher = datePattern.matcher(tmp);                       
                   }
                   bw.write(tmp);
                   bw.newLine();
               }
               bw.close();
               br.close();
           }
           else
           {
               System.out.println("ERR:null  input filname");
               return ;
           }
       }
       catch (IOException e)
       {
           System.out.println(e);
           return;
       }
    }

    public static String format (String date)
    {
        date = date.replaceAll("/", ".");
        if(date.indexOf(".")==1)
        {
            date = (new StringBuffer(date)).insert(0,"0").toString();
        }
        if (date.indexOf(".", 4)==4)
        {
               date = (new StringBuffer(date)).insert(3,"0").toString();
        }
        return date;

    }

}
