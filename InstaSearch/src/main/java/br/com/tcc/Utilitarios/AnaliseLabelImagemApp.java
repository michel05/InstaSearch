package br.com.tcc.Utilitarios;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import br.com.jpa.dao.CategoriaDeImagemDAO;
import br.com.jpa.dao.LabelAnaliseImageDAO;
import br.com.jpa.dao.PostagemDAO;
import br.com.tcc.Enums.TipoDeAnaliseDeImagem;
import br.com.tcc.VO.CategoriaImagemVO;
import br.com.tcc.VO.LabelAnaliseImageVO;
import br.com.tcc.VO.PostagemVO;

public class AnaliseLabelImagemApp {

	private final static String DIRETORIO = "F:/Amazon/InstaSearh/json_landmark_postagens/novos/";
	
	public static void main(String[] args) throws Exception {

		File diretorio = new File(DIRETORIO);
		File[] arquivos = diretorio.listFiles();
		
		PostagemDAO daoPostagem = new PostagemDAO();
		CategoriaDeImagemDAO daoCategoriaImg = new CategoriaDeImagemDAO();
		LabelAnaliseImageDAO daoLabelAnaliseImage = new LabelAnaliseImageDAO();
			
		for (int i = 0; i < arquivos.length; i++) {
			
			
				JSONObject jsonObject;
				//Cria o parse de tratamento
				JSONParser parser = new JSONParser();
				
				try {
					//Salva no objeto JSONObject o que o parse tratou do arquivo
					jsonObject = (JSONObject) parser.parse(new FileReader(DIRETORIO + arquivos[i].getName()));
					
					//Salva nas variaveis os dados retirados do arquivo
					JSONArray reponses = (JSONArray) jsonObject.get("responses");
					JSONObject labelAnnotations = (JSONObject) reponses.get(0);
					
					JSONArray labels = (JSONArray) labelAnnotations.get("labelAnnotations");
					
					for (int j = 0; j < labels.size(); j++) {
						JSONObject label = (JSONObject) labels.get(j);
						String descricao = (String) label.get("description");
						double nota = (double) label.get("score");
						
						LabelAnaliseImageVO vo = new LabelAnaliseImageVO();
						
						CategoriaImagemVO voCategoriaImg = daoCategoriaImg.buscarPorNome(descricao);
						
						if(voCategoriaImg == null) {
							voCategoriaImg = new CategoriaImagemVO();
							voCategoriaImg.setTipoDeAnaliseDeImagem(TipoDeAnaliseDeImagem.LABEL_DETECTION);
							voCategoriaImg.setNome(descricao);
							daoCategoriaImg.inserir(voCategoriaImg);
							vo.setCategoriaImagem(daoCategoriaImg.buscarPorNome(descricao));
						} else {
							vo.setCategoriaImagem(voCategoriaImg);
						}
						
						
						PostagemVO voPostagem = daoPostagem.findById(Integer.valueOf(arquivos[i].getName().replace(".json", "")));
						
						if(voPostagem != null) {
							
							vo.setPostagem(voPostagem);
							
							vo.setNota(nota);
							
							daoLabelAnaliseImage.inserir(vo);
						}
						
					}
					
				} 
				catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (ParseException e) {
					e.printStackTrace();
				} 
			}
		
	}

}
