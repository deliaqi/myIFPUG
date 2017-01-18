package DataProcessing;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import IFPUG.Component.*;

public class readData {
	
	public static boolean readComponentfromExcel(String path, List<Component> CList) throws IOException
	{
		File excel = new File(path);
		FileInputStream fis = new FileInputStream(excel);
		XSSFWorkbook wb = new XSSFWorkbook(fis);
		XSSFSheet ws = wb.getSheetAt(0);

		int rowNum = ws.getLastRowNum() + 1;
		int colNum = ws.getRow(0).getLastCellNum();

		for(int i = 1; i <rowNum; i++){
			XSSFRow row = ws.getRow(i);
			Component component = null;
			if(row.getCell(1) != null && colNum>4){
				XSSFCell cell = row.getCell(1);
				String type = cell.toString();
				switch(type){
					case "ILF": component = new ILF();break;
					case "EIF": component = new EIF();break;
					case "EI": component = new EI();break;
					case "EO": component = new EO();break;
					case "EQ": component = new EQ();break;
				}
			}
			
			for (int j = 0; j < colNum; j++){
				XSSFCell cell = row.getCell(j);
				String value = cell.toString();
				System.out.println ("["+i+"]["+j+"]" + value);
				if(j == 2) component.setComplexity(value);
				else if(j == 3) component.setWeight((int)cell.getNumericCellValue());
				else if(j == 4){
					component.setNumber((int)cell.getNumericCellValue());break;
				}
			}
			CList.add(component);			
		}

		return true;
	}
	
	public static void readDoubleFromExcel(String path, Map<String, double[]> dataMap) throws IOException{
		File excel = new File(path);
		FileInputStream fis = new FileInputStream(excel);
		XSSFWorkbook wb = new XSSFWorkbook(fis);
		XSSFSheet ws = wb.getSheetAt(0);
		
		int rowNum = ws.getLastRowNum() + 1;
		int colNum = ws.getRow(0).getLastCellNum();
		
		String projectname = "";
		
		for(int i = 1; i <rowNum; i++){
			double[] data = new double[colNum-1];
			XSSFRow row = ws.getRow(i);
			for (int j = 0; j < colNum; j++){
				XSSFCell cell = row.getCell(j);
				if(j == 0){
					projectname = row.getCell(0).toString();
				}else{
					data[j-1] = cell.getNumericCellValue();
				}
				
			}
			dataMap.put(projectname, data);	
		}
		
	}
	
	public static String[][] readDataFromExcel(String path) throws IOException{
		File excel = new File(path);
		FileInputStream fis = new FileInputStream(excel);
		XSSFWorkbook wb = new XSSFWorkbook(fis);
		XSSFSheet ws = wb.getSheetAt(0);
		int rowNum = ws.getLastRowNum() + 1;
		int colNum = ws.getRow(0).getLastCellNum();
		String[][] data = new String[rowNum][colNum];
		
		for(int i = 0; i <rowNum; i++){
			XSSFRow row = ws.getRow(i);
			for (int j = 0; j < colNum; j++){
				XSSFCell cell = row.getCell(j);
				String value = cell.toString();
				data[i][j] = cell.toString();	
			}
		}
		return data;
	}
	
	public void readCSV(){
		
	}
	
}
