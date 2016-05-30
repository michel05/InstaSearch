package br.com.ufg.tcc.model;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import br.com.ufg.tcc.utils.Util;

public class FiltroPost {

	private String idUsuario;
	private int numPosts;
	private String dataInicio;
	private String dataFim;
	private int numPostsFaltantes;
	
	public FiltroPost() {
		// TODO Auto-generated constructor stub
	}
	
	public String getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}
	public int getNumPosts() {
		return numPosts;
	}
	public void setNumPosts(int numPosts) {
		this.numPosts = numPosts;
	}
	public String getDataInicio() {
		return dataInicio;
	}
	public void setDataInicio(String dataInicio) {
		this.dataInicio = dataInicio;
	}
	
	public String monteSufixoUrl() {
		
		Calendar calendar = Calendar.getInstance();
		StringBuilder sb = new StringBuilder("");
		Timestamp tstamp;
		Date data = new Date();
		
		if (dataInicio != null && !dataInicio.equals("")) {
			
			data = Util.stringToDate(dataInicio, "yyyy-MM-dd");
			calendar.setTime(data);
			tstamp = new Timestamp(calendar.getTime().getTime());
			sb.append("&min_timestamp=" + (tstamp.getTime()/1000));
		}
		
		if (dataFim != null && !dataFim.equals("")) {
			
			data = Util.stringToDate(dataFim, "yyyy-MM-dd");
			calendar.setTime(data);
			tstamp = new Timestamp(calendar.getTime().getTime());
			sb.append("&max_timestamp=" + (tstamp.getTime()/1000));
		}
		
		if (this.getNumPostsFaltantes() > 0) {
			sb.append("&count=" + this.getNumPostsFaltantes());
		}
		
		
		return sb.toString();
	}

	public String getDataFim() {
		return dataFim;
	}

	public void setDataFim(String dataFim) {
		this.dataFim = dataFim;
	}

	public int getNumPostsFaltantes() {
		return numPostsFaltantes;
	}

	public void setNumPostsFaltantes(int numPostsFaltantes) {
		this.numPostsFaltantes = numPostsFaltantes;
	}
	
	
}
