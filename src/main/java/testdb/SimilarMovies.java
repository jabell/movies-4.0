package testdb;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SimilarMovies {
	public static void main(String Args[]) throws Exception{
		ArrayList<String> similar = new ArrayList<String>();
                ArrayList<String> actor = new ArrayList<String>();
                String rating;
                String director;
		URL website= new URL("http://www.imdb.com/chart/top");
		URLConnection url = website.openConnection();
		BufferedReader in = new BufferedReader(new InputStreamReader(url.getInputStream()));
		BufferedWriter br = new BufferedWriter(new FileWriter("similarmovies.txt"));
		BufferedWriter br1 = new BufferedWriter(new FileWriter("rating.txt"));
                BufferedWriter br2 = new BufferedWriter(new FileWriter("actors.txt"));
                BufferedWriter br3 = new BufferedWriter(new FileWriter("director.txt"));
                BufferedWriter br4 = new BufferedWriter(new FileWriter("top250.txt"));
		String line;
		String HtmlContent;
		StringBuffer lineBuffer = new StringBuffer();
		while((line=in.readLine())!=null){
			lineBuffer.append(line);			
		}
		HtmlContent = lineBuffer.toString();
		Pattern p1 = Pattern.compile("(<td class=\"titleColumn\">)(.*?)(<a href=\")(.*?)(\" title=\")(.*?)(\" >)(.*?)(</a> )");
		Matcher m1 = p1.matcher(HtmlContent);
		int i=1;
		String name;
		while(m1.find()){
		 name=m1.group(8);
                 br4.write(name+"\n");
		 similar= getSimilar(m1.group(4));
                 actor = getActors(m1.group(4));
                 rating=getRating(m1.group(4));
                 director=getDirector(m1.group(4));
                 
		 System.out.println(i+" "+name);
		 Iterator itr = actor.listIterator();
                 while(itr.hasNext()){
                     Object element = itr.next();
                    br2.write(name+"##"+element+"\n");
		}
                 Iterator itr1 = similar.listIterator();
                 while(itr1.hasNext()){
                     Object element = itr1.next();
                   br.write(name+"##"+element+"\n");
		}
                 br1.write(name+"##"+rating+"\n");
                 br3.write(name+"##"+director+"\n");
                 i++;
                 
               }
		br.close();
		in.close();
		br2.close();
                br1.close();
                br3.close();
                br4.close();
	}
	
	public static ArrayList<String> getSimilar(String link) throws Exception{
		ArrayList<String> similar = new ArrayList<String>();
		URL website= new URL("http://www.imdb.com"+link);
		URLConnection url = website.openConnection();
		BufferedReader in = new BufferedReader(new InputStreamReader(url.getInputStream()));
		String data;
		StringBuffer sb = new StringBuffer();
		while((data=in.readLine())!=null){
			sb.append(data);
		}		
		data=sb.toString();
		Pattern p1 = Pattern.compile("(<img height=\"113\" width=\"76\" alt=\")(.*?)(\" title=)");
		Matcher m1 = p1.matcher(data);
		while(m1.find()){
			similar.add(m1.group(2));
		}
		return similar;	
	}
        
        
        public static ArrayList<String> getActors(String link) throws Exception{
		ArrayList<String> actor = new ArrayList<String>();
		URL website= new URL("http://www.imdb.com"+link);
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
			actor.add(m1.group(2));
		}
		return actor;	
	}
        
        public static String getRating(String link)throws Exception{
            String rating=null;
            URL website= new URL("http://www.imdb.com"+link);
            URLConnection url = website.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(url.getInputStream()));
            String data;
            StringBuffer sb = new StringBuffer();
            while((data=in.readLine())!=null){
                    sb.append(data);
            }		
            data=sb.toString();
            Pattern p1 = Pattern.compile(" (<div class=\"titlePageSprite star-box-giga-star\">)(.*?)(</div>)");
            Matcher m1 = p1.matcher(data);
            while(m1.find()){
                rating=m1.group(2);
                
            }
            return rating;
        }
        
        public static String getDirector(String link) throws Exception{           
            String director=null;
            URL website= new URL("http://www.imdb.com"+link);
            URLConnection url = website.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(url.getInputStream()));
            String data;
            StringBuffer sb = new StringBuffer();
            while((data=in.readLine())!=null){
                    sb.append(data);
            }		
            data=sb.toString();
            Pattern p1 = Pattern.compile(" (<h4 class=\"inline\">Director:</h4>)(.*?)(\">)(.*?)(</span></a>)");
            Matcher m1 = p1.matcher(data);
            while(m1.find()){
                director=m1.group(4);
              }
            
            return director;
            
        }
}
