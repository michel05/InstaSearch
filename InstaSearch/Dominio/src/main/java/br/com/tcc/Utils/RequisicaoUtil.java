package br.com.tcc.Utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class RequisicaoUtil {

	
	public static JSONObject busqueObjetoJson(JSONObject json, String nome) {
		JSONObject jsonObj = null;
		
		try {
			jsonObj = json.getJSONObject(nome);
			
		} catch (JSONException e) {
			e.getMessage();
		} catch (Exception e) {
			e.getMessage();
//			session.setAttribute("error", "Um erro ocorreu, tente novamente");
		}
		return jsonObj;
	}
	
	public static JSONArray busqueArrayObjetoJson(JSONObject json, String nome) throws Exception {
		JSONArray jsonObj = null;
		
		try {
			jsonObj = json.getJSONArray(nome);
			
		} catch (JSONException e) {
			e.getMessage();
		} catch (Exception e) {
			throw new Exception();
		}
		return jsonObj;
	}
	
	public static JSONObject busqueObjetoJsonDeUmArray(JSONArray json, int indice) throws Exception {
		JSONObject jsonObj = null;
		
		try {
			jsonObj = json.getJSONObject(indice);
			
		} catch (JSONException e) {
			e.getMessage();
		} catch (Exception e) {
			throw new Exception();
		}
		return jsonObj;
	}
	
	public static String getResponse(String url) throws Exception {
		
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		con.setRequestMethod("GET");
		con.setRequestProperty("User-Agent", "");
		con.getResponseCode();

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		
		in.close();
		
		return response.toString();
	}
}
