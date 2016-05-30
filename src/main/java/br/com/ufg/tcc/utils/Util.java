package br.com.ufg.tcc.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

public class Util {

	private static final Logger logger =  Logger.getLogger(Util.class);
	
	public static Date stringToDate(String data, String formato) {
		
		DateFormat format = new SimpleDateFormat(formato);
		try {
			return format.parse(data);
		} catch (ParseException e) {
			logger.error("Não foi possível formatar a data. Erro: " + e.getMessage());
		}
		return null;
	}
}
