package br.com.tcc.Utilitarios;

import java.util.List;

import br.com.jpa.dao.PostagemDAO;
import br.com.tcc.VO.HistoricoDePostagemVO;
import br.com.tcc.VO.PostagemVO;

public class ExclusaoPostagensInconsistentes {

	public static void main(String[] args) {

		PostagemDAO postagemDAO = new PostagemDAO();

		List<PostagemVO> listaDePostgens = postagemDAO.getLista();

		listaDePostgens.forEach(x -> {
			HistoricoDePostagemVO primeiroHistorico = x.getHistorico().get(0);

			long diferencaEntreDatas = (primeiroHistorico.getDataInicial().getTimeInMillis()
					- x.getDataCriacao().getTimeInMillis());
			
			long cincoMinutos = 5 * 60 * 1000;
			if(diferencaEntreDatas > cincoMinutos) {
				postagemDAO.delete(x, x.getId());
			}
		});
	}
}
