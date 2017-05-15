package br.com.tcc.Utilitarios;

import java.util.List;

import br.com.jpa.dao.PostagemDAO;
import br.com.mongoDB.MonitoramentoRepository;
import br.com.tcc.VO.MonitoramentoVO;

public class IntegradorMongoToPostgres {

	public static void main(String[] args) {

		MonitoramentoRepository repoMonitoramento = new MonitoramentoRepository();
		PostagemDAO dao = new PostagemDAO();

		List<MonitoramentoVO> monitoramentos = repoMonitoramento.listarTodos();
		System.out.println("Lista recebida... ");

		monitoramentos.forEach(x -> {
			x.getPostagens().removeIf(p -> (dao.findById(p.getId()) != null));

			x.getPostagens().forEach(postagem -> {

				postagem.setMonitoramento(x);
				postagem.getHistorico().forEach(historico -> {
					int contador = 0;
					historico.setPostagem(postagem);
					historico.setId(Integer.parseInt(
							postagem.getId() + "" + historico.getDataInicial().getTimeInMillis() + "" + contador++));
				});

				try {
					dao.inserir(postagem);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
		});

	}

}
