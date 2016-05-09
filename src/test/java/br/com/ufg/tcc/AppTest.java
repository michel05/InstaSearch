//package br.com.ufg.tcc;
//
//import java.io.BufferedReader;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.util.ArrayList;
//import java.util.List;
//
//import com.google.gson.Gson;
//import com.google.gson.JsonArray;
//import com.google.gson.JsonParser;
//
///**
// * Unit test for simple App.
// */
//public class AppTest
//{
//	final static String token = "1678850039.1fb234f.bdc0d1c346fe4b7b91d98b3b765add07 ";
//	
//	public static void main(String[] args) throws Exception {
//		sendGet();
//	}
//	
//	private static void sendGet() throws Exception {
//
//		String usuario = "globo_esporte";
////		String url = "https://api.instagram.com/v1/users/self/feed?access_token=" + token;
//		
//		String url = "https://api.instagram.com/v1/users/search?q=" + usuario + "&access_token=" + token;
////		String url = "https://api.instagram.com/v1/users/search?q=jack&access_token=1678850039.d3b7ec6.01919588baf947abba4e937b80df939d";
//		
//		URL obj = new URL(url);
//		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
//
//		// optional default is GET
//		con.setRequestMethod("GET");
//
//		//add request header
//		con.setRequestProperty("User-Agent", "");
//
//		int responseCode = con.getResponseCode();
//		System.out.println("\nSending 'GET' request to URL : " + url);
//		System.out.println("Response Code : " + responseCode);
//
//		BufferedReader in = new BufferedReader(
//		        new InputStreamReader(con.getInputStream()));
//		String inputLine;
//		StringBuffer response = new StringBuffer();
//
//		while ((inputLine = in.readLine()) != null) {
//			response.append(inputLine);
//			System.out.println(inputLine + "\n");
//		}
//		
//		Gson gson = new Gson();
//		
//		UserFeed user;
//		List<UserFeed> users = new ArrayList<UserFeed>();
//		
//		 JsonParser parser = new JsonParser();
//		 JsonArray array = parser.parse(response.toString()).getAsJsonArray();
//		 
//		 for (int i = 0; i < array.size(); i++) {
//             // Adicionando na lista a posicao atual do JsonArray
//			 users.add(gson.fromJson(array.get(i), UserFeed.class));
//         }
//		 
//		in.close();
//
//		System.out.println(users);
//		
//		gson.toJson(response);
//		
//		System.out.println(gson.toString());
//		
//		
//		//print result
////		System.out.println(response.toString());
//
//	}
//	
//}
