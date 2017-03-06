package DataProcessing;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import IFPUG.Component.*;

public class readData {
	
	public static double readDataFilefromExcel(String path, List<Component> CList, int[][] table) throws IOException{
		FileInputStream fis = new FileInputStream(path);
		XSSFWorkbook wb = new XSSFWorkbook(fis);
		XSSFSheet ws = wb.getSheetAt(0);
		
		int rowNum = ws.getLastRowNum() + 1;
		int colNum = ws.getRow(0).getLastCellNum();
		int index = path.indexOf(".");
		String prePath = path.substring(0, index-3);
		String ComponentType = path.substring(index-3, index);
		
		for(int i = 1; i <rowNum; i++){
			XSSFRow row = ws.getRow(i);
			XSSFCell cell = row.getCell(1);
			String name = cell.toString();
			
			Map<String, Integer> result = new HashMap<String, Integer>();
			readDFDetailfromExcel(prePath+name+".xlsx", result);
			int curDET = result.get("DET");
			int curRET = result.get("RET");
			
			XSSFCell newcell = row.createCell(2);
			newcell.setCellValue(curDET);
			newcell = row.createCell(3);
			newcell.setCellValue(curRET);
			
			Component curComponent = new ILF();
			if(ComponentType.equals("EIF")){
				curComponent = new EIF();
			}
			curComponent.setDET(curDET);
			curComponent.setRET(curRET);
			curComponent.computeComplexity(table);
			CList.add(curComponent);			
			
			newcell = row.createCell(4);
			newcell.setCellValue(curComponent.getComplexity());
			newcell = row.createCell(5);
			newcell.setCellValue(curComponent.getFunctionPoint());
		}
		
		fis.close();
		
		FileOutputStream fos = new FileOutputStream(path);
		wb.write(fos);
		fos.close();
		
		return 0;
		
	}
	
	public static boolean readDFDetailfromExcel(String path, Map<String, Integer> result) throws IOException{
		File excel = new File(path);
		FileInputStream fis = new FileInputStream(excel);
		XSSFWorkbook wb = new XSSFWorkbook(fis);
		
		int RET = 0;
		int DET = 0;
		
		for(int i=0;i<wb.getNumberOfSheets();i++){
			XSSFSheet ws = wb.getSheetAt(i);
			int tmp = ws.getLastRowNum();
			if(tmp > 0){
				DET = DET + ws.getLastRowNum();
				RET++;
			}else{
				break;
			}
			
		}
		
		result.put("DET", DET);
		result.put("RET", RET);
		return true;
	}
	
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
		List<Integer> colUse = new ArrayList<Integer>();
		colUse.add(0);
		XSSFRow firstrow = ws.getRow(0);
		for(int i=0;i<colNum;i++){
			XSSFCell cell = firstrow.getCell(i);
			String value = cell.toString();
			if(value.equals("FP") || value.equals("Function Points") || value.equals("Effort") || value.equals("PointsAdjust")){
				colUse.add(i);
			}
		}
		
		
		for(int i = 1; i <rowNum; i++){
			double[] data = new double[colUse.size()-1];
			XSSFRow row = ws.getRow(i);
			int index = 0;
			for(int col : colUse){
				XSSFCell cell = row.getCell(col);
				if(col == 0){
					projectname = row.getCell(0).toString();
				}else{
					data[index++] = Double.parseDouble(cell.toString());
				}
			}
			
			if(data[0] != 856 && data[0] > 500 && data[0] < 1000){
				dataMap.put(projectname, data);	
			}
			
		}
		
	}
	
	public static String[][] readStringFromExcel(String path) throws IOException{
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
				//String value = cell.toString();
				data[i][j] = cell.toString();	
			}
		}
		return data;
	}
	
	public static Object[][] readObjectFromExcel(String path) throws IOException{
		File excel = new File(path);
		FileInputStream fis = new FileInputStream(excel);
		XSSFWorkbook wb = new XSSFWorkbook(fis);
		XSSFSheet ws = wb.getSheetAt(0);
		int rowNum = ws.getLastRowNum() + 1;
		int colNum = ws.getRow(0).getLastCellNum();
		Object[][] data = new Object[rowNum][colNum];
		
		for(int i = 0; i <rowNum; i++){
			XSSFRow row = ws.getRow(i);
			for (int j = 0; j < colNum; j++){
				XSSFCell cell = row.getCell(j);
				//String value = cell.toString();
				data[i][j] = cell.toString();	
			}
		}
		return data;
	}
	
	public void readCSV(){
		
	}
	
}
