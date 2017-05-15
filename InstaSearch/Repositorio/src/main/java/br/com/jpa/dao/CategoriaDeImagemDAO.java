package br.com.jpa.dao;

import br.com.jpa.contratos.BaseDao;
import br.com.tcc.VO.CategoriaImagemVO;

public class CategoriaDeImagemDAO extends BaseDao<CategoriaImagemVO, Integer> {

	public CategoriaImagemVO buscarPorNome(String nome) {
		CategoriaImagemVO categoriaDeImagem = null;
		try {
			categoriaDeImagem = (CategoriaImagemVO) super.getEntityManager().createNamedQuery("categoriaImagem.buscarPorNome")
				.setParameter("nome", nome).getSingleResult();
		} catch (Exception e) {
			System.out.println("categoriaDeImagem não encontrada - " + e.getMessage());
			categoriaDeImagem = null;
		}
		return categoriaDeImagem;
	}
}
