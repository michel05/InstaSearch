package br.com.tcc.Dominio;

import br.com.tcc.VO.HistoricoDePostagemVO;

public class HistoricoDePostagem{
	
	private HistoricoDePostagemVO vo;
	
	public HistoricoDePostagem() {
		setVo(new HistoricoDePostagemVO());
	}

	public HistoricoDePostagemVO getVo() {
		return vo;
	}

	public void setVo(HistoricoDePostagemVO vo) {
		this.vo = vo;
	}
}
