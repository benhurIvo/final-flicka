/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package adapta.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.*;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import org.json.JSONObject;

/**
 *
 * @author benhur
 */
@Stateless // will work only inside a Java EE application
@LocalBean // will work only inside a Java EE application
@Path("/flicka")
public class Flicka {
        @Context
    UriInfo uriInfo;
    @Context
    Request request;
	    
  ObjectMapper mapper = new ObjectMapper();
		JSONObject obj;
	    String arrayToJson = "";
	    static String data = "";
//Getting distance run by the person
//@GET
//@Path("/miles/{mls}")
//@Produces({MediaType.APPLICATION_JSON ,  MediaType.APPLICATION_XML })
//public String distance(@PathParam("mls") String mls){
//    String ds = "";
//  try{
//	   System.out.println("hpty "+mls + " ");
//	   String []dt = mls.split("~~");
//	   
//           String tagt = targetURL+"/gol/"+mls;
//	   String bk1 =  sendurl(tagt, "GET", "");
//	   int ii = 0;
//	   
//	   if(Double.parseDouble(dt[1])<Double.parseDouble(bk1)){	       
//	   ii=randInt(5, 9);
//	       System.out.println("less "+ii);
//	       ds= getImage(ii)+"~~"+"Keep going, only "+(Double.parseDouble(bk1)-Double.parseDouble(dt[1])+"km left.");
//	   }
//	   else{
//	   ii=randInt(0, 4);
//	   System.out.println("more "+ii);
//	      ds= getImage(ii)+"~~"+"BRAVO!!!";
//	   }
//  }catch(Exception ex){
//  ex.printStackTrace();
//  }
//  return ds;
//}



//Get a random number withing a range but include range values
public static int randInt(int min, int max) {

    // Usually this can be a field rather than a method variable
    Random rand = new Random();

    // nextInt is normally exclusive of the top value,
    // so add 1 to make it inclusive
    int randomNum = rand.nextInt((max - min) + 1) + min;

    return randomNum;
}

//Getting an image from flickr
@GET
@Path("/img/{num}")
@Produces({MediaType.APPLICATION_JSON ,  MediaType.APPLICATION_XML })
public String getImage(@PathParam("num") int z){
    String flickrurl="";
	    try {
		URLConnection uc = new URL("https://api.flickr.com/services/rest/?method=flickr.people.getPhotos&user_id=144072969@N04&api_key=9723df63a3417c3cc349482815058ec2").openConnection();
		DataInputStream dis = new DataInputStream(uc.getInputStream());
		// FileWriter fw = new FileWriter(new File("D:\\\\Hello1.xml"));
		String nextline;
		String[] servers = new String[10];
		String[] ids = new String[10];
		String[] secrets = new String[10];
		String dt = "";
		while ((nextline = dis.readLine()) != null) {
		    dt+=nextline;
		}
		dis.close();
		
		byte[] byteArray = dt.getBytes("UTF-8");
		ByteArrayInputStream inputStream = new ByteArrayInputStream(byteArray);
		XMLInputFactory inputFactory = XMLInputFactory.newInstance();
		XMLEventReader r = inputFactory.createXMLEventReader(inputStream);
		
		int i = -1;
		while (r.hasNext()) {
		    
		    XMLEvent event = r.nextEvent();
		    if (event.isStartElement()) {
			StartElement element = (StartElement) event;
			String elementName = element.getName().toString();
			if (elementName.equals("photo")) {
			    i++;
			    Iterator iterator = element.getAttributes();
			    
			    while (iterator.hasNext()) {
				
				Attribute attribute = (Attribute) iterator.next();
				QName name = attribute.getName();
				String value = attribute.getValue();
				System.out.println("Attribute name/value: " + name + "/" + value);
				if ((name.toString()).equals("server")) {
				    servers[i] = value;
				    System.out.println("Server Value" + servers[0]);
				}
				if ((name.toString()).equals("id")) {
				    ids[i] = value;
				}
				if ((name.toString()).equals("secret")) {
				    secrets[i] = value;
				}
			    }
			}
		    }
		}
		 flickrurl =servers[z] + "/" + ids[z] + "_" + secrets[z] + ".jpg";
		System.out.println("flickr "+flickrurl);
	    } catch (Exception ex) {
		Logger.getLogger(Flicka.class.getName()).log(Level.SEVERE, null, ex);
	    }
	    return flickrurl;
}


//Method to send a url to the server
String sendurl(String target, String mtd, String input){
	try {
		    

			URL targetUrl = new URL(target);

			HttpURLConnection httpConnection = (HttpURLConnection) targetUrl.openConnection();
			httpConnection.setDoOutput(true);
			httpConnection.setRequestMethod(mtd);
			
			if(mtd.equals("POST")||mtd.equals("PUT")){
			httpConnection.setRequestProperty("Content-Type", "application/json");
			OutputStream outputStream = httpConnection.getOutputStream();
			outputStream.write(input.getBytes());
			outputStream.flush();
			}
			if (httpConnection.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
					+ httpConnection.getResponseCode());
			}

			BufferedReader responseBuffer = new BufferedReader(new InputStreamReader(
					(httpConnection.getInputStream())));

			String output="";
			System.out.println("Output from Server:\n");
			
			while ((output = responseBuffer.readLine()) != null) {
				data = output;
			}
	    System.out.println("datas "+data);
			httpConnection.disconnect();
			

		  } catch (MalformedURLException e) {

			e.printStackTrace();

		  } catch (IOException e) {

			e.printStackTrace();

		 }
	return data;
	}
}
