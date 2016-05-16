package br.com.ufg.tcc.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Calendar;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import br.com.ufg.tcc.constants.Constants;
import br.com.ufg.tcc.model.InformacoesUsuario;
import br.com.ufg.tcc.model.Post;

public class ExcelUtil {

	
	public static String gerarExcelInstagram(InformacoesUsuario infoUser) throws Exception {
		
		FileOutputStream file = null;
		HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheetAlunos = workbook.createSheet("Posts");
        String months[] = {"Jan","Fev","Mar","Abr","Mai","Jun","Jul","Ago","Set","Out","Nov","Dez"};
        File arquivo = crieArquivo(Constants.LOCAL_ARQUIVO + infoUser.getNome() + ".xls");
        
        int rownum = 0;
        
        Row row1 = sheetAlunos.createRow(rownum++);
        row1.createCell(0).setCellValue("Id");
        row1.createCell(1).setCellValue("Descricao");
        row1.createCell(2).setCellValue("Curtidas");
        row1.createCell(3).setCellValue("Boca a boca");
        row1.createCell(4).setCellValue("N Comentarios");
        row1.createCell(5).setCellValue("Mes");
        row1.createCell(6).setCellValue("Nº Caracteres");
        row1.createCell(7).setCellValue("Nº HashTags");
        row1.createCell(8).setCellValue("Link");
        
        
        for (Post post : infoUser.getListaPosts()) {
            Row row = sheetAlunos.createRow(rownum++);
            int cellnum = 0;
            
            Cell cellId = row.createCell(cellnum++);
            cellId.setCellValue(post.getId());
            
            Cell cellDesc = row.createCell(cellnum++);
            cellDesc.setCellValue(post.getDescricao());
            
            Cell cellCurtidas = row.createCell(cellnum++);
            cellCurtidas.setCellValue(post.getNumCurtidas());
            
            Cell cellMarcacoes = row.createCell(cellnum++);
            cellMarcacoes.setCellValue(post.getNumMarcacoes());
            
            Cell cellComentarios = row.createCell(cellnum++);
            cellComentarios.setCellValue(post.getNumComentarios());
            
            Cell cellData = row.createCell(cellnum++);
            Calendar cal = Calendar.getInstance();
    		cal.setTime(post.getData());
            cellData.setCellValue(months[post.getData().getMonth()] + "/" + cal.get(Calendar.YEAR));
            
            Cell cellCaracteres = row.createCell(cellnum++);
            cellCaracteres.setCellValue(post.getNumCaracteres());
            
            Cell cellHashtags =row.createCell(cellnum++);
            cellHashtags.setCellValue(post.getNumHashtags());
            
            Cell cellLink =row.createCell(cellnum++);
            cellLink.setCellValue(post.getLink());
        }
         
        try {
        	
        	file = new FileOutputStream(arquivo);
            workbook.write(file);
            System.out.println("Arquivo Excel criado com sucesso!");
            file.close();
             
        } catch (Exception e) {
        	System.out.println(e.getMessage());
        	throw new Exception();
        } 
        
        return arquivo.getAbsolutePath();
	}
	
	
	private static File crieArquivo(String arquivo) {
		
		File file = new File(arquivo);
		if (file.exists()) {
			file.delete();
			return new File(arquivo); 
		}
		
		return file;
	}
	
	public static byte[] fileToArrayByte(File arq) {
		
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		
		try {
			FileInputStream in = new FileInputStream(arq);
			int b;
			while((b = in.read())>-1){
			   out.write(b);
			}
			out.close();
			in.close();
		} catch (Exception e) {
			e.getMessage();
		}
		
		return out.toByteArray();
	}
}
