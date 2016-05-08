package br.com.ufg.tcc.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import br.com.ufg.tcc.model.Post;

public class Utils {

	
	public static void gerarExcelInstagram(List<Post> lista, String nome) {
		
		HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheetAlunos = workbook.createSheet("Posts");
        String months[] = {"Jan","Fev","Mar","Abr","Mai","Jun","Jul","Ago","Set","Out","Nov","Dez"};
        
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
        
        
        for (Post post : lista) {
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
        	
            FileOutputStream out = 
                    new FileOutputStream("F:\\arquivos_coleta\\posts_" + nome + ".xls");
            workbook.write(out);
            out.close();
            System.out.println("Arquivo Excel criado com sucesso!");
             
        } catch (FileNotFoundException e) {
            e.printStackTrace();
               System.out.println("Arquivo não encontrado!");
        } catch (IOException e) {
            e.printStackTrace();
               System.out.println("Erro na edição do arquivo!");
        }

		
	}
}
