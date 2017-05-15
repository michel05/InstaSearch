package br.com.jpa.dao;

import javax.persistence.Query;

import br.com.jpa.contratos.BaseDao;
import br.com.tcc.VO.LabelAnaliseImageVO;

public class LabelAnaliseImageDAO extends BaseDao<LabelAnaliseImageVO, Integer> {

	public boolean verifiqueSeNaoExiste(String idPostagem) {
        Query query = super.getEntityManager().createQuery("select count(id) from LabelAnaliseImageVO label where label.postagem.id = :idPostagem");
        query.setParameter("idPostagem", idPostagem);
        return query.getFirstResult() == 0;
    }
}
