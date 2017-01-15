package IFPUG.dataset;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import IFPUG.util.*;

public class readExcel {
	
	public static int read() throws IOException
	{
		int FPbeforeAdjst = 0;
		
		File excel = new File("C:\\Users\\dell1\\Documents\\409\\FPA\\dataset\\myDataset.xlsx");
		FileInputStream fis = new FileInputStream(excel);
		XSSFWorkbook wb = new XSSFWorkbook(fis);
		XSSFSheet ws = wb.getSheetAt(0);

		int rowNum = ws.getLastRowNum() + 1;
		int colNum = ws.getRow(0).getLastCellNum();
		String [][] data = new String [rowNum] [colNum];

		for(int i = 1; i <rowNum; i++){
			XSSFRow row = ws.getRow(i);
			
			for (int j = 0; j < colNum-3; j++){
				XSSFCell cell = row.getCell(j);
				String value = cell.toString();
				data[i][j] = value;
				System.out.println ("["+i+"]["+j+"]" + value);
			}
			
			if(row.getCell(1) != null && colNum>4){
				XSSFCell cell = row.getCell(1);
				String type = cell.toString();
				switch(type){
				case "ILF": 
					ILF ilf = new ILF(data[i][2], (int)Double.parseDouble(data[i][3]), (int)Double.parseDouble(data[i][4]));
					FPbeforeAdjst += ilf.getDataFunction();
					break;
				case "EIF":
					EIF eif = new EIF(data[i][2], (int)Double.parseDouble(data[i][3]), (int)Double.parseDouble(data[i][4]));
					FPbeforeAdjst += eif.getDataFunction();
					break;
				case "EI":
					EI ei = new EI(data[i][2], (int)Double.parseDouble(data[i][3]), (int)Double.parseDouble(data[i][4]));
					FPbeforeAdjst += ei.getTransFunction();
					break;
				case "EO":
					EO eo = new EO(data[i][2], (int)Double.parseDouble(data[i][3]), (int)Double.parseDouble(data[i][4]));
					FPbeforeAdjst += eo.getTransFunction();
					break;
				case "EQ":
					EQ eq = new EQ(data[i][2], (int)Double.parseDouble(data[i][3]), (int)Double.parseDouble(data[i][4]));
					FPbeforeAdjst += eq.getTransFunction();
					break;
				
				}
			}
		}

		return FPbeforeAdjst;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			int FP;
			FP = read();
			System.out.println(FP);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
}
