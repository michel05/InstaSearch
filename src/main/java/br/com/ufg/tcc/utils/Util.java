package br.com.ufg.tcc.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {

	public static Date stringToDate(String data, String formato) {
		
		DateFormat format = new SimpleDateFormat(formato);
		try {
			return format.parse(data);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
}
