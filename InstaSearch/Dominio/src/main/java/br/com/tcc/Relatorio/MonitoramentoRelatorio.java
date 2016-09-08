package br.com.tcc.Relatorio;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;

import br.com.tcc.VO.HistoricoDePostagemVO;
import br.com.tcc.VO.MonitoramentoVO;
import br.com.tcc.VO.PostagemVO;

public class MonitoramentoRelatorio {

	//private static final String LOCAL_ARQUIVO = "../relatorios/LayuotPlanilha.xls";
	
	private MonitoramentoVO vo;
	
	public MonitoramentoRelatorio(MonitoramentoVO vo) {
		this.vo = vo;
	}
	
	public ByteArrayOutputStream gerarExcelInstagram() throws Exception {
		
		//String months[] = {"Jan","Fev","Mar","Abr","Mai","Jun","Jul","Ago","Set","Out","Nov","Dez"};
		SimpleDateFormat sdfHoras = new SimpleDateFormat("HH:mm:ss");
		sdfHoras.setTimeZone(TimeZone.getTimeZone("GMT-03:00"));
		SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		fmt.setTimeZone(TimeZone.getTimeZone("GMT-03:00"));
		
		HSSFWorkbook workbook = new HSSFWorkbook();
		
		HSSFCellStyle styleTitulo = criaStiloTitulo(workbook);
		HSSFCellStyle styleTituloTabela = criaStiloTituloTabela(workbook);
		
		int contador = 1;
		for (PostagemVO postagem : vo.getPostagens()) {
			HSSFSheet sheet = 
					workbook.createSheet("P" + contador++ + postagem.getDescricao().substring(0, 5) + "...");
			sheet.setDefaultColumnWidth(15);
			
			//Titulo Monitoramento
			Row rowMonitoramento = sheet.createRow(0);
			Cell celTituloMonitoramento = rowMonitoramento.createCell(1);
			celTituloMonitoramento.setCellValue("Monitoramento:");
			celTituloMonitoramento.setCellStyle(styleTitulo);
			rowMonitoramento.createCell(2).setCellValue(vo.getTitulo());
			
			//Titulo Postagem
			Row rowPostagem = sheet.createRow(1);
			Cell celTituloPostagem = rowPostagem.createCell(1);
			celTituloPostagem.setCellValue("Postagem:");
			celTituloPostagem.setCellStyle(styleTitulo);
			rowPostagem.createCell(2).setCellValue(postagem.getDescricao());
			
			//Data Início
			Row rowDataInicio = sheet.createRow(2);
			Cell celTituloDataInicio = rowDataInicio.createCell(1);
			celTituloDataInicio.setCellValue("Data início:");
			celTituloDataInicio.setCellStyle(styleTitulo);
			rowDataInicio.createCell(2).setCellValue(fmt.format(new Date(vo.getDataDeInicio())));
			
			//Data Fim
			Row rowDataFim = sheet.createRow(3);
			Cell celTituloDataFim = rowDataFim.createCell(1);
			celTituloDataFim.setCellValue("Data fim:");
			celTituloDataFim.setCellStyle(styleTitulo);
			rowDataFim.createCell(2).setCellValue(
												vo.getDataDeTermino() != 0 
												? fmt.format(new Date(vo.getDataDeTermino()))
												: "");

			int rowDados = 6;
	        Row row1 = sheet.createRow(rowDados++);
	        Cell cellTabelaData = row1.createCell(0);
	        cellTabelaData.setCellValue("Horário");
	        cellTabelaData.setCellStyle(styleTituloTabela);
	        
	        Cell cellTabelaCurtidas = row1.createCell(1);
	        cellTabelaCurtidas.setCellValue("Nº Curtidas");
	        cellTabelaCurtidas.setCellStyle(styleTituloTabela);
	        
	        Cell cellTabelaCurtidasParcial = row1.createCell(2);
	        cellTabelaCurtidasParcial.setCellValue("Nº Curtidas Parciais");
	        cellTabelaCurtidasParcial.setCellStyle(styleTituloTabela);
	        
	        Cell cellTabelaComentariosParcial = row1.createCell(3);
	        cellTabelaComentariosParcial.setCellValue("Nº Comentários");
	        cellTabelaComentariosParcial.setCellStyle(styleTituloTabela);
	        
	        Cell cellTabelaComentarios = row1.createCell(4);
	        cellTabelaComentarios.setCellValue("Nº Comentários Parciais");
	        cellTabelaComentarios.setCellStyle(styleTituloTabela);
	        
	        for (HistoricoDePostagemVO historio : postagem.getHistorico()) {
	            Row row = sheet.createRow(rowDados++);
	            int cellnum = 0;
	            
	            row.createCell(cellnum++).setCellValue(sdfHoras.format(historio.getDataInicial().getTime()));
	            row.createCell(cellnum++).setCellValue(historio.getNumCurtidas());
	            row.createCell(cellnum++).setCellValue(historio.getNumCurtidasParcial());
	            row.createCell(cellnum++).setCellValue(historio.getNumComentarios());
	            row.createCell(cellnum++).setCellValue(historio.getNumComentariosParcial());
	        }
			
		}
         
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        workbook.write(baos);
        
        return baos;
	}
	
	private HSSFCellStyle criaStiloTituloTabela(HSSFWorkbook workbook) {
		HSSFCellStyle style = workbook.createCellStyle();
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(CellStyle.ALIGN_CENTER);
		style.setWrapText(true);
		HSSFPalette palette = workbook.getCustomPalette();
		// get the color which most closely matches the color you want to use
		HSSFColor myColor = palette.findSimilarColor(192,192,192);
		style.setFillForegroundColor(myColor.getIndex());
		style.setFillBackgroundColor(myColor.getIndex());
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		HSSFFont font = workbook.createFont();
		font.setFontName(HSSFFont.FONT_ARIAL);
		font.setFontHeightInPoints((short)10);
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		style.setFont(font);
		return style;
	}

	private HSSFCellStyle criaStiloTitulo(HSSFWorkbook workbook) {
		HSSFCellStyle style = workbook.createCellStyle();
		style.setAlignment(CellStyle.ALIGN_RIGHT);
		HSSFFont font = workbook.createFont();
		font.setFontName(HSSFFont.FONT_ARIAL);
		font.setFontHeightInPoints((short)10);
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		style.setFont(font);
		return style;
	}
}
