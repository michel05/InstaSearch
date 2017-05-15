package br.com.tcc.Utilitarios;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;

import br.com.jpa.dao.FeatureDAO;
import br.com.jpa.dao.FeatureImagemDAO;
import br.com.jpa.dao.PostagemDAO;
import br.com.tcc.Dominio.Features;
import br.com.tcc.Dominio.FeaturesImagem;
import br.com.tcc.Enums.EficienciadaPostagemEnum;
import br.com.tcc.VO.FeaturesVO;
import br.com.tcc.VO.LabelAnaliseImageVO;
import br.com.tcc.VO.PostagemVO;

public class AnaliseFeatures {

	public static void main(String[] args) {

//		List<PostagemVO> listaPostagens = busqueListaPostagensDeComida();

//		inserirFeatures(listaPostagens);
//		inserirFeaturesImagens(listaPostagens);

		balancearPostagens();

	}

	private static void balancearPostagens() {
		FeatureDAO featureDAO = new FeatureDAO();
		List<FeaturesVO> lista = featureDAO.getLista();

		lista.forEach(x -> {
			x.setAtivo(true);
			x.setAtivoComentario(true);
			featureDAO.atualizar(x);
		});

		// Curtidas
		long acimaDaMedia = lista.stream()
				.filter(x -> x.getEficienciaDaPostagemCurtida().equals(EficienciadaPostagemEnum.ACIMA_DA_MEDIA))
				.count();
		long abaixoDaMedia = lista.stream()
				.filter(x -> x.getEficienciaDaPostagemCurtida().equals(EficienciadaPostagemEnum.ABAIXO_DA_MEDIA))
				.count();
		long naMedia = lista.stream()
				.filter(x -> x.getEficienciaDaPostagemCurtida().equals(EficienciadaPostagemEnum.NA_MEDIA)).count();

		long cortar = naMedia -
				(((acimaDaMedia + abaixoDaMedia) / 2)
				+ ((Math.max(acimaDaMedia, abaixoDaMedia) - Math.min(acimaDaMedia, abaixoDaMedia)) / 2));

		List<FeaturesVO> listaNaMedia = lista.stream()
				.filter(x -> x.getEficienciaDaPostagemCurtida().equals(EficienciadaPostagemEnum.NA_MEDIA))
				.collect(Collectors.toList());

		Collections.shuffle(listaNaMedia);

		int i = 0;
		while (i < cortar) {
			listaNaMedia.get(i).setAtivo(false);
			featureDAO.atualizar(listaNaMedia.get(i));
			i++;
		}

		listaNaMedia.forEach(x -> {
			featureDAO.atualizar(x);
		});

		
		// COmentario
		lista = featureDAO.getLista();
		long acimaDaMediaCom = lista.stream()
				.filter(x -> x.getEficienciaDaPostagemComentario().equals(EficienciadaPostagemEnum.ACIMA_DA_MEDIA))
				.count();
		long abaixoDaMediaCom = lista.stream()
				.filter(x -> x.getEficienciaDaPostagemComentario().equals(EficienciadaPostagemEnum.ABAIXO_DA_MEDIA))
				.count();
		long naMediaCom = lista.stream()
				.filter(x -> x.getEficienciaDaPostagemComentario().equals(EficienciadaPostagemEnum.NA_MEDIA)).count();

		long cortarCom = naMediaCom -
				(((acimaDaMediaCom + abaixoDaMediaCom) / 2)
				+ ((Math.max(acimaDaMediaCom, abaixoDaMediaCom) - Math.min(acimaDaMediaCom, abaixoDaMediaCom)) / 2));

		List<FeaturesVO> listaNaMediaCom = lista.stream()
				.filter(x -> x.getEficienciaDaPostagemComentario().equals(EficienciadaPostagemEnum.NA_MEDIA))
				.collect(Collectors.toList());

		Collections.shuffle(listaNaMediaCom);

		int j = 0;
		while (j < cortarCom) {
			listaNaMediaCom.get(j).setAtivoComentario(false);
			featureDAO.atualizar(listaNaMediaCom.get(j));
			j++;
		}

	}

	private static List<PostagemVO> busqueListaPostagensDeComida() {

		PostagemDAO postagemDAO = new PostagemDAO();

		List<PostagemVO> postagens = postagemDAO.getLista();
		List<PostagemVO> postagensAux = new ArrayList<>();

		postagens.forEach(x -> {
			if (x.getListaAnaliseImageVO().stream()
					.anyMatch(img -> img.getCategoriaImagem().getNome().equals("food"))) {
				postagensAux.add(x);
			}
		});

		SortedMap<String, Integer> imagensMap = new TreeMap<String, Integer>();

		postagensAux.forEach(x -> {
			for (LabelAnaliseImageVO img : x.getListaAnaliseImageVO()) {

				Integer valor = imagensMap.get(img.getCategoriaImagem().getNome());
				if (valor == null) {
					imagensMap.put(img.getCategoriaImagem().getNome(), new Integer(1));
				} else {
					imagensMap.put(img.getCategoriaImagem().getNome(), ++valor);
				}
			}
		});

		int total = 0;
		for (Map.Entry<String, Integer> o : imagensMap.entrySet()) {
			total = total + o.getValue();
		}

		return postagensAux;

	}

	private static void inserirFeaturesImagens(List<PostagemVO> listaPostagens) {

		FeatureImagemDAO featureImagemDAO = new FeatureImagemDAO();

		// remove todas
		featureImagemDAO.deleteAll(featureImagemDAO.getLista());

		listaPostagens.forEach(x -> {

			FeaturesImagem featuresImagem = new FeaturesImagem();
			featuresImagem.populeFeaturesImagem(x);

			try {
				featureImagemDAO.inserir(featuresImagem.getVo());
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	private static void inserirFeatures(List<PostagemVO> listaPostagens) {

		FeatureDAO featureDAO = new FeatureDAO();

		List<FeaturesVO> listFeatures = featureDAO.getLista();

		// remove as features
		featureDAO.deleteAll(listFeatures);

		listaPostagens.forEach(x -> {

			Features features = new Features();
			features.populeFeatures(x);

			try {
				featureDAO.inserir(features.getFeaturesVO());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

		listFeatures.clear();
		listFeatures = featureDAO.getLista();
		Features features = new Features();
		features.obtenhaEficienciaDaPostagemPorCurtida(listFeatures);
		features.obtenhaEficienciaDaPostagemPorComentarios(listFeatures);

		listFeatures.forEach(x -> {
			featureDAO.atualizar(x);
		});

	}

}
