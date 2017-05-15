package br.com.tcc.Utilitarios;

import java.io.File;
import java.io.FileReader;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import br.com.jpa.dao.OCRAnaliseImageDAO;
import br.com.jpa.dao.PostagemDAO;
import br.com.tcc.VO.OCRAnaliseImageVO;
import br.com.tcc.VO.PostagemVO;

public class AnaliseTextImagemApp {

	private final static String DIRETORIO = "F:/Amazon/InstaSearh/json_text/";

	public static void main(String[] args) throws Exception {

		File diretorio = new File(DIRETORIO);
		File[] arquivos = diretorio.listFiles();

		PostagemDAO daoPostagem = new PostagemDAO();
		OCRAnaliseImageDAO daoOCR = new OCRAnaliseImageDAO();

		for (int i = 0; i < arquivos.length; i++) {
			System.out.println(arquivos[i].getName());

			if (daoOCR.verifiqueSeNaoExiste(arquivos[i].getName().replace(".json", ""))) {

				JSONObject jsonObject;
				// Cria o parse de tratamento
				JSONParser parser = new JSONParser();

				try {
					// Salva no objeto JSONObject o que o parse tratou do
					// arquivo
					jsonObject = (JSONObject) parser.parse(new FileReader(DIRETORIO + arquivos[i].getName()));

					// Salva nas variaveis os dados retirados do arquivo
					JSONArray reponses = (JSONArray) jsonObject.get("responses");
					JSONObject labelAnnotations = (JSONObject) reponses.get(0);

					JSONArray labels = (JSONArray) labelAnnotations.get("textAnnotations");

					// Pega a primeira posicao que contem todo o texto
					// encontrado
					JSONObject label = (JSONObject) labels.get(0);
					String mensagem = (String) label.get("description");

					OCRAnaliseImageVO vo = new OCRAnaliseImageVO();

					PostagemVO voPostagem = daoPostagem.findById(Integer.valueOf(arquivos[i].getName().replace(".json", "")));

					if (voPostagem != null) {
						vo.setPostagem(voPostagem);
						vo.setMensagem(mensagem.replace("\n", " "));
						vo.setQuantidadeDeCaracteres(mensagem.replace("\n", " ").length());

					} else {
						System.out.println("Postagem não encontrada");
					}

					daoOCR.inserir(vo);

				} catch (Exception e) {
					System.out.println("Erro arquivo " + arquivos[i].getName());
					System.out.println(e.getMessage());
				}
			} else {
				System.out.println("Registro já existe: " + arquivos[i].getName());
			}

		}

	}

}
