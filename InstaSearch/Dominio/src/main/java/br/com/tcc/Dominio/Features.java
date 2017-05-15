package br.com.tcc.Dominio;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.List;

import br.com.tcc.Enums.EficienciadaPostagemEnum;
import br.com.tcc.Enums.PeriodoDoDiaEnum;
import br.com.tcc.VO.FeaturesVO;
import br.com.tcc.VO.HistoricoDePostagemVO;
import br.com.tcc.VO.PostagemVO;

public class Features {

	private FeaturesVO vo;

	public Features() {
		vo = new FeaturesVO();
	}

	public void populeFeatures(PostagemVO postagem) {
		vo.setPostagem(postagem);
		vo.setNumCurtidas(postagem.getNumTotalCurtidas());
		vo.setNumComentarios(postagem.getNumTotalComentarios());
		vo.setHorarioDaPostagem(postagem.getDataCriacao());
		vo.setPeriodoDoDia(obtenhaPeriodoDoDia(postagem.getPeriodoDoDia()));
		vo.setTamanhoDescricao(postagem.getDescricao().length());
		vo.setNumPicoDeCurtida(obtenhaPicoDeCurtida(postagem.getHistorico()));
		vo.setHorarioPicoDeCurtida(obtenhaHorarioPicoDeCurtida(postagem.getHistorico()));
		vo.setVelocidadeDaQueda(obtenhaVelocidadeDeQueda(postagem));
		vo.setTempoAtePicoDeCurtida(obtenhaTempoAtePicoDeCurtida(postagem));

	}

	public void obtenhaEficienciaDaPostagemPorCurtida(List<FeaturesVO> features) {

		double media = 0.0;
		double totalCurtidas = features.stream().mapToDouble(x -> x.getNumCurtidas()).sum();

		media = totalCurtidas / features.size();
		media = new BigDecimal(media).setScale(2, RoundingMode.HALF_EVEN).doubleValue();

		double variancia = (features.stream()
				.mapToDouble(x -> Math.pow((x.getNumCurtidas() - (totalCurtidas / features.size())), 2)).sum())
				/ features.size();
		variancia = new BigDecimal(variancia).setScale(2, RoundingMode.HALF_EVEN).doubleValue();

		double desvio = Math.sqrt(variancia);
		desvio = new BigDecimal(desvio).setScale(2, RoundingMode.HALF_EVEN).doubleValue();

		double equilibrio = 0.0;

		// Porcentagem para equilibrar os dados
		double fatorEquilibrio = 0.8;
		desvio = desvio * fatorEquilibrio;

		for (FeaturesVO featuresVO : features) {
			equilibrio = equilibrio + featuresVO.getNumCurtidas() - media;

			if (featuresVO.getNumCurtidas() < (media - desvio)) {
				featuresVO.setEficienciaDaPostagemCurtida(EficienciadaPostagemEnum.ABAIXO_DA_MEDIA);
			} else if (featuresVO.getNumCurtidas() > (media + desvio)) {
				featuresVO.setEficienciaDaPostagemCurtida(EficienciadaPostagemEnum.ACIMA_DA_MEDIA);
			} else {
				featuresVO.setEficienciaDaPostagemCurtida(EficienciadaPostagemEnum.NA_MEDIA);
			}
		}
	}
	
	public void obtenhaEficienciaDaPostagemPorComentarios(List<FeaturesVO> features) {

		double media = 0.0;
		double totalComentarios = features.stream().mapToDouble(x -> x.getNumComentarios()).sum();

		media = totalComentarios / features.size();
		media = new BigDecimal(media).setScale(2, RoundingMode.HALF_EVEN).doubleValue();

		double variancia = (features.stream()
				.mapToDouble(x -> Math.pow((x.getNumComentarios() - (totalComentarios / features.size())), 2)).sum())
				/ features.size();
		variancia = new BigDecimal(variancia).setScale(2, RoundingMode.HALF_EVEN).doubleValue();

		double desvio = Math.sqrt(variancia);
		desvio = new BigDecimal(desvio).setScale(2, RoundingMode.HALF_EVEN).doubleValue();

		double equilibrio = 0.0;

		// Porcentagem para equilibrar os dados
		double fatorEquilibrio = 0.5;
		desvio = desvio * fatorEquilibrio;

		for (FeaturesVO featuresVO : features) {
			equilibrio = equilibrio + featuresVO.getNumComentarios() - media;

			if (featuresVO.getNumComentarios() < (media - desvio)) {
				featuresVO.setEficienciaDaPostagemComentario(EficienciadaPostagemEnum.ABAIXO_DA_MEDIA);
			} else if (featuresVO.getNumComentarios() > (media + desvio)) {
				featuresVO.setEficienciaDaPostagemComentario(EficienciadaPostagemEnum.ACIMA_DA_MEDIA);
			} else {
				featuresVO.setEficienciaDaPostagemComentario(EficienciadaPostagemEnum.NA_MEDIA);
			}
		}
	}

	private double obtenhaTempoAtePicoDeCurtida(PostagemVO postagem) {

		HistoricoDePostagemVO historicoPicoDeCurtida = obtenhaHistoricoDoPicoDeCurtida(postagem.getHistorico());

		double retorno = (double) (historicoPicoDeCurtida.getDataInicial().getTimeInMillis()
				- postagem.getDataCriacao().getTimeInMillis()) / 60000;
		return Double.valueOf(String.format("%.2f", retorno).replace(",", "."));
	}

	private double obtenhaVelocidadeDeQueda(PostagemVO postagem) {

		// numTotalCurtidas - (PicoDeCurtidas - anteriores) / tempoRestante em
		// min;
		HistoricoDePostagemVO historicoPicoDeCurtida = obtenhaHistoricoDoPicoDeCurtida(postagem.getHistorico());

		int curtidasAposPicoDeCurtida = 0;
		boolean flagPassouDoPico = false;

		for (HistoricoDePostagemVO historico : postagem.getHistorico()) {

			if (flagPassouDoPico) {
				curtidasAposPicoDeCurtida = curtidasAposPicoDeCurtida + historico.getNumCurtidasParcial();
			}

			if (historico.getId().equals(historicoPicoDeCurtida.getId())) {
				flagPassouDoPico = true;
			}
		}

		long tresHoras = 10800000;
		long horarioFinal = (tresHoras + postagem.getDataCriacao().getTimeInMillis());
		double tempoRestanteAposPico = horarioFinal - historicoPicoDeCurtida.getDataInicial().getTimeInMillis();

		if (tempoRestanteAposPico > 0) {
			double retorno = curtidasAposPicoDeCurtida / (tempoRestanteAposPico / 60000);
			return Double.valueOf(String.format("%.2f", retorno).replace(",", "."));

		} else {
			return 0;
		}
	}

	private HistoricoDePostagemVO obtenhaHistoricoDoPicoDeCurtida(List<HistoricoDePostagemVO> historico) {
		int picoDeCurtida = obtenhaPicoDeCurtida(historico);

		for (HistoricoDePostagemVO historicoDePostagemVO : historico) {
			if (historicoDePostagemVO.getNumCurtidasParcial() == picoDeCurtida) {
				return historicoDePostagemVO;
			}
		}

		return null;
	}

	private Calendar obtenhaHorarioPicoDeCurtida(List<HistoricoDePostagemVO> historico) {
		HistoricoDePostagemVO historicoDePostagemVO = obtenhaHistoricoDoPicoDeCurtida(historico);
		return historicoDePostagemVO != null ? historicoDePostagemVO.getDataInicial() : null;
	}

	public FeaturesVO getFeaturesVO() {
		return vo;
	}

	public void setFeaturesVO(FeaturesVO featuresVO) {
		this.vo = featuresVO;
	}

	private int obtenhaPicoDeCurtida(List<HistoricoDePostagemVO> listaHistorico) {
		int picoDeCurtida = 0;

		for (HistoricoDePostagemVO historicoDePostagemVO : listaHistorico) {
			if (historicoDePostagemVO.getNumCurtidasParcial() > picoDeCurtida) {
				picoDeCurtida = historicoDePostagemVO.getNumCurtidasParcial();
			}
		}

		return picoDeCurtida;
	}

	private PeriodoDoDiaEnum obtenhaPeriodoDoDia(String periodo) {
		PeriodoDoDiaEnum retorno = null;
		switch (periodo) {
		case "MANHA 1":
			retorno = PeriodoDoDiaEnum.Manha1;
			break;
		case "MANHA 2":
			retorno = PeriodoDoDiaEnum.Manha2;
			break;
		case "ALMOCO":
			retorno = PeriodoDoDiaEnum.Almoco;
			break;
		case "TARDE 1":
			retorno = PeriodoDoDiaEnum.Tarde1;
			break;
		case "TARDE 2":
			retorno = PeriodoDoDiaEnum.Tarde2;
			break;
		case "NOITE":
			retorno = PeriodoDoDiaEnum.Noite;
			break;
		case "MADRUGADA":
			retorno = PeriodoDoDiaEnum.Madrugada;
			break;
		}

		return retorno;
	}

}
