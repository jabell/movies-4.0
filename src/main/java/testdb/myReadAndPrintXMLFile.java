package testdb;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;


import javax.xml.parsers.ParserConfigurationException;

public class myReadAndPrintXMLFile {

    public static void main (String argv []){
        try {

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc2 = dBuilder.parse("http://www.omdbapi.com/?s=Titanic&r=XML");
            doc2.getDocumentElement().normalize();





            System.out.println ("Root element of the doc is " +
                    doc2.getDocumentElement().getNodeName());



            NodeList nList = doc2.getElementsByTagName("Movie");
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    System.out.println(eElement.getAttribute("Title")+" "+eElement.getAttribute("Year"));
                }
            }




        }catch (SAXParseException err) {
            System.out.println ("** Parsing error" + ", line "
                    + err.getLineNumber () + ", uri " + err.getSystemId ());
            System.out.println(" " + err.getMessage ());

        }catch (SAXException e) {
            Exception x = e.getException ();
            ((x == null) ? e : x).printStackTrace ();

        }catch (Throwable t) {
            t.printStackTrace ();
        }
        //System.exit (0);

    }//end of main


}