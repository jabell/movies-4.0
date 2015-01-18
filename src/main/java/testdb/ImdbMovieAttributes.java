/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package testdb;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Mandar Dalvi
 */
public class ImdbMovieAttributes {
    public static void main(String[] args) throws Exception{
        ArrayList<String> genre = new ArrayList<String>();
        ArrayList<String> actors = new ArrayList<String>();
        BufferedReader bin = new BufferedReader(new FileReader("movies.txt"));
        String data=null;
        String page=null;
        String link=null;
        BufferedWriter br1 = new BufferedWriter(new FileWriter("Genre.txt"));
        BufferedWriter br2 = new BufferedWriter(new FileWriter("movieActors.txt"));
        int i=0;
      String p20="+";
        while((data=bin.readLine())!=null){
            i=0;
            System.out.println(""+data);
            data = data.replaceAll(" ",p20);
            page= getMovie(data);
            Pattern p1 = Pattern.compile("(<h3><a href=\")(.*?)(\" h=\")(.*?)(\">)");
            Matcher m1 = p1.matcher(page);
            while(m1.find()){
                link=m1.group(2);
                i++;
                if(i==1) break;
            }
          
         genre = getGenre(link);
         actors =  getActors(link);
       
         System.out.println(""+genre);  
         System.out.println(""+actors);
         br1.write(data+"##"+genre+"\n");
         Iterator itr = actors.listIterator();
                 while(itr.hasNext()){
                     Object element = itr.next();
                    br2.write(data+"##"+element+"\n");
		}
           
        }
        br1.close();
        br2.close();
      
    }
    
    public static String getMovie(String data) throws Exception{
        String page;
        URL website = new URL("http://www.bing.com/search?q=imdb+"+data);
        URLConnection urlcon = website.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(urlcon.getInputStream()));
        String word;
        StringBuffer line = new StringBuffer();
        while((word=in.readLine())!=null){
         line.append(word);   
        }
        page=line.toString();
        return page;
    }
    
    
    public static ArrayList<String> getGenre(String link)throws Exception {
        ArrayList<String> genre = new ArrayList<String>();
         ArrayList<String> actor = new ArrayList<String>();
		URL website= new URL(link);
		URLConnection url = website.openConnection();
		BufferedReader in = new BufferedReader(new InputStreamReader(url.getInputStream()));
		String data;
		StringBuffer sb = new StringBuffer();
		while((data=in.readLine())!=null){
			sb.append(data);
		}		
		data=sb.toString();
		Pattern p1 = Pattern.compile("(<span class=\"itemprop\" itemprop=\"genre\">)(.*?)(</span>)");
		Matcher m1 = p1.matcher(data);
		while(m1.find()){
                        
			genre.add(m1.group(2));
                 
                }
        return genre;
    }
    
     public static ArrayList<String> getActors(String link) throws Exception{
		int i=0;
                ArrayList<String> actor = new ArrayList<String>();
		URL website= new URL(link);
		URLConnection url = website.openConnection();
		BufferedReader in = new BufferedReader(new InputStreamReader(url.getInputStream()));
		String data;
		StringBuffer sb = new StringBuffer();
		while((data=in.readLine())!=null){
			sb.append(data);
		}		
		data=sb.toString();
		Pattern p1 = Pattern.compile("(<img height=\"44\" width=\"32\" alt=\")(.*?)(\" title=)");
		Matcher m1 = p1.matcher(data);
		while(m1.find()){
                        i++;
			actor.add(m1.group(2));
                        if(i==5) break;
		}
		return actor;	
	}

}

