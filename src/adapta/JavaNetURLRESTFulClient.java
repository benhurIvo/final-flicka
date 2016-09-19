package adapta;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author benhur
 */
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JavaNetURLRESTFulClient {

	private static final String targetURL = "http://127.0.1.1:5700/sdelab/flicka";
	static String data = "";
	public static void main(String[] args) {
	    try {
		
		
		
//		ObjectMapper mapper = new ObjectMapper();
//		JSONObject obj;
//		String st = "{\"pid\":\"4\",\"firstname\":\"ivo\",\"lastname\":\"ivan\",\"birthdate\":\"as\"}~~{\"wt\":\"4\"}~~{\"ht\":\"1\"}";
//	
		String in = "1~~22.712";
		String tagt = targetURL+"/img/2";
	   String bk1 =  sendurl(tagt, "GET", "");
//	   //String bk1 =  sendurl("http://192.168.1.103:5700/sdelab/control/user_hp/1", "GET", "");
//	   System.out.println("tag "+bk1);
//		String tagt = targetURL+"/hp/1";
//	   String bk1 =  sendurl(tagt, "GET", "");
		
//		JSONObject obj;
//		ObjectMapper mapper = new ObjectMapper();
//		String tagt = targetURL;
//		Person p = new Person();
//		p.setBirthdate("18/03/1988");
//		p.setFirstname("Yesu");
//		p.setLastname("Christo");
//		p.setUname("Nse");
//		p.setPassword("Nse");
//		p.setUsertype("Strud");
//		obj= new JSONObject(p);
////        	ObjectNode node = mapper.valueToTree(p);
//		System.out.println("nn "+obj);
//		sendurl(tagt, "POST", obj.toString());
//		sendurl(tagt, "GET", obj.toString());
////		sendurl(tagt, "PUT", obj.toString());
//		TypeReference<List<Person>> mapType = new TypeReference<List<Person>>() {};
//    	List<Person> jsonToPersonList = mapper.readValue(data, mapType);
//		System.out.println("he "+jsonToPersonList);
//	
//	
//	
//Type t = new Type();
//t.setType("weight");
//t.setMeasure("kg");
//obj=new JSONObject(t);
//tagt = targetURL+"/typ";
//	    sendurl(tagt, "POST", obj.toString());
//	    tagt = targetURL+"/typ/weight";
//	    sendurl(tagt, "GET", obj.toString());
//	    TypeReference<List<Type>> mapType1 = new TypeReference<List<Type>>() {};
//    	List<Type> jsonToTypelist = mapper.readValue(data, mapType1);
//		System.out.println("typ "+ jsonToTypelist);
//	    Type tp = jsonToTypelist.get(jsonToTypelist.size()-1); //mapper.readValue(jsonToTypelist.get(0), Type.class);
//	    System.out.println("typ "+tp.getMeasure()+" "+tp.getType()+" "+tp.getTid());
//	    	    Healthprofile hp = new Healthprofile();
//	    hp.setPid(jsonToPersonList.get(jsonToPersonList.size()-1));
//	    Date det = new Date();
//	    hp.setDatecreated(new SimpleDateFormat("yyyy-MM-dd").format(det));
//	    hp.setTid(tp);
//	    obj=new JSONObject(hp);
//	    tagt = targetURL+"/hp";
//	    sendurl(tagt, "POST", obj.toString());
//	      Goal g = new Goal();
//	    g.setPid(jsonToPersonList.get(jsonToPersonList.size()-1));
//	    g.setTid(tp);
//	    g.setGoal("100");
//	    obj=new JSONObject(g);
//	    tagt = targetURL+"/gol";
//	    sendurl(tagt, "POST", obj.toString());
	    } catch (Exception ex) {
		Logger.getLogger(JavaNetURLRESTFulClient.class.getName()).log(Level.SEVERE, null, ex);
	    }
		}	
	
	static String sendurl(String target, String mtd, String input){
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
			

		  } catch (Exception e) {

			e.printStackTrace();

		  } 
	return data;
	}
}