package br.com.ufg.tcc.model;

import java.util.Calendar;
import java.util.Date;

import br.com.ufg.tcc.utils.Util;

public class FiltroPost {

	private String idUsuario;
	private int numPosts;
	private Date dataInicio;
	
	public FiltroPost() {
		// TODO Auto-generated constructor stub
	}
	
	public FiltroPost(String idUsuario, int numPosts, String dataInicio) {
		this.idUsuario = idUsuario;
		this.numPosts = numPosts;
		this.dataInicio = Util.stringToDate(dataInicio, "yyyy-MM");
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
	public Date getDataInicio() {
		return dataInicio;
	}
	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}
	
	public String monteSufixoUrl() {
		
		Calendar calendar = Calendar.getInstance();
		StringBuilder sb = new StringBuilder("");
		
		if (this.getDataInicio() != null) {
			calendar.setTime(this.getDataInicio());
			sb.append("&min_timestamp=" + calendar.getTimeInMillis());
		}
		if (this.getNumPosts() > 0) {
			sb.append("&count=" + this.getNumPosts());
		}
		
		return sb.toString();
	}
	
	
}
