package br.com.relatorios;

import java.io.File;
import java.io.FileOutputStream;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;


public class Relatorio {

	public FileOutputStream crieRelatorio() {
		
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet firstSheet = workbook.createSheet("Aba1");

		FileOutputStream fos = null;

		try {
		fos = new FileOutputStream(new File("teste.xls"));

		// Este trecho obtem uma lista de objetos do tipo CD

		// do banco de dados através de um DAO e itera sobre a lista

		// criando linhas e colunas em um arquivo Excel com o conteúdo

		// dos objetos.



		HSSFRow row = firstSheet.createRow(0);

		row.createCell(0).setCellValue("teste");
		row.createCell(1).setCellValue("teste 2");

		workbook.write(fos);
		
		return fos;

		} catch (Exception e) {
		e.printStackTrace();
		System.out.println("Erro ao exportar arquivo");
		} finally {
		try {
		fos.flush();
		fos.close();
		} catch (Exception e) {
		e.printStackTrace();
		}
	}
		return fos;
	}
}
