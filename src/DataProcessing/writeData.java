package DataProcessing;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class writeData {
	
	public static boolean writeExcel(Map<String, double[]> result, List<String> columnNames, String path) throws IOException{
		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet ws = wb.createSheet("result");
		
		int rowCount = 0;
		int cellnum = 0;
		Set<String> newRows = result.keySet();
		
		// Write column names
		Row firstrow = ws.createRow(rowCount++);
		for(String colname : columnNames){
			Cell columnName = firstrow.createCell(cellnum++);
			columnName.setCellValue(colname);
		}
		
		// Write result data
		for(String name : newRows){
			cellnum = 0;
			Row row = ws.createRow(rowCount++);
			double[] resultdata = result.get(name);
			
			Cell cell = row.createCell(cellnum++);
			cell.setCellValue(name);
			for(double data : resultdata){
				cell = row.createCell(cellnum++);
				cell.setCellValue(data);
			}
		}
		
		FileOutputStream os = new FileOutputStream(path);
		wb.write(os);
		System.out.println("Writing to " + path + " finished...");
		
		
		return true;
	}

}
