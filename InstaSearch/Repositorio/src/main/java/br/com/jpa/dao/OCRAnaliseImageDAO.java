package br.com.jpa.dao;

import javax.persistence.Query;

import br.com.jpa.contratos.BaseDao;
import br.com.tcc.VO.OCRAnaliseImageVO;

public class OCRAnaliseImageDAO extends BaseDao<OCRAnaliseImageVO, Integer> {
	
	public boolean verifiqueSeNaoExiste(String idPostagem) {
        Query query = super.getEntityManager().createQuery("select count(ocr) from OCRAnaliseImageVO ocr where ocr.postagem.id = :idPostagem");
        query.setParameter("idPostagem", idPostagem);
        return query.getFirstResult() == 0;
    }
}