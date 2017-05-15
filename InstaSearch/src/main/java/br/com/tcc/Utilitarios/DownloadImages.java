package br.com.tcc.Utilitarios;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

import br.com.mongoDB.MonitoramentoRepository;
import br.com.mongoDB.PostagemRepository;
import br.com.tcc.VO.MonitoramentoVO;
import br.com.tcc.VO.PostagemVO;

public class DownloadImages {

	final static String DIRETORIO_DOWNLOAD = "F://Amazon/InstaSearh/visionImagens/";
	
	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {

		DownloadImages downloadImages = new DownloadImages();
		
		downloadImages.listaMonitoramentos().forEach(x -> {
			System.out.println("-- Verificando " + x.getTitulo() + " --");
			x.getPostagens().forEach(postagem -> {
				try {
					System.out.println("Downloading image " + postagem.getId() + "...");
					downloadImages.downloadImage(postagem);
				} catch (Exception e) {
					System.out.println("Erro ao tentar download de: " + postagem.getId());
				}
			});
		});
	}
	
	public List<MonitoramentoVO> listaMonitoramentos() {
		MonitoramentoRepository repositorio = new MonitoramentoRepository();
		return repositorio.listarTodos();
	}
	
	public List<PostagemVO> listaPostagens() throws Exception {
		PostagemRepository repositorio = new PostagemRepository();
		return repositorio.listarTodos();
	}
	
	public void downloadImage(PostagemVO postagem) throws Exception {
		
		URL url = new URL(postagem.getUrlImagem());
		InputStream in = new BufferedInputStream(url.openStream());
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] buf = new byte[1024];
		int n = 0;
		while (-1!=(n=in.read(buf)))
		{
		   out.write(buf, 0, n);
		}
		out.close();
		in.close();
		byte[] response = out.toByteArray();
		
		File file = new File(DIRETORIO_DOWNLOAD + postagem.getId() + ".jpg");
		if(file.exists()) {
			System.out.println(postagem.getId() + " já existe.");
			return;
		}
		
		FileOutputStream fos = new FileOutputStream(file);
		
		fos.write(response);
		fos.close();
	}

}
