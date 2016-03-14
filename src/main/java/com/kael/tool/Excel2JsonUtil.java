package com.kael.tool;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class Excel2JsonUtil {

	private static int make0(final File srcFile, final String dstDirPath,boolean isXls) throws Exception{
		Workbook workbook = null;
		try {
			workbook  =  isXls ? new HSSFWorkbook(new FileInputStream(srcFile)): new XSSFWorkbook(srcFile);
			readWorkBook(workbook, dstDirPath);
			return 1;
		} catch(Exception e){
			if(e instanceof ReadExcelException){
				throw ((ReadExcelException) e).append(srcFile.getName());
			}else{
				throw e;
			}
		}finally{
			if(workbook != null){
				try {
					workbook.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static int make(final File srcFile, final String dstDirPath)throws Exception{
		if(srcFile.isFile()){
			return make0(srcFile, dstDirPath == null ? srcFile.getAbsolutePath(): dstDirPath,"xls".equalsIgnoreCase(getPrefix(srcFile.getAbsolutePath())));
		}else{
			int successCount = 0 ;
			for (File f : srcFile.listFiles()) {
				if(f.isDirectory()) continue;
				String prefix =getPrefix(f.getAbsolutePath());
				if("xls".equalsIgnoreCase(prefix)){
					successCount+=make0(f, dstDirPath == null ? srcFile.getAbsolutePath(): dstDirPath,true);
				}else if("xlsx".equalsIgnoreCase(prefix)){
					successCount+=make0(f, dstDirPath == null ? srcFile.getAbsolutePath(): dstDirPath,false);
				}
			}
			return successCount;
		}
	}
	
	private static String getPrefix(String absolutePath){
		return absolutePath.substring(absolutePath.lastIndexOf('.')+1);
	}
	
	private static void readWorkBook(Workbook workbook, String dstDirPath)  throws Exception{
//		List<String> list = new ArrayList<String>();
		for (int sheetIndex = 0; sheetIndex < workbook.getNumberOfSheets(); sheetIndex++) {
			Sheet sheet = workbook.getSheetAt(sheetIndex);
			if(sheet == null || sheet.getSheetName().startsWith("sheet")|| sheet.getSheetName().startsWith("Sheet"))continue;
//			workBooks.put(sheet.getSheetName(), readSheet(sheet));
			FileWriter fileWriter = null;
			try {
				fileWriter= new FileWriter(dstDirPath+"/"+sheet.getSheetName()+".json");
				JSON.writeJSONStringTo(readSheet(sheet), fileWriter);
//				list.add(dstDirPath+"/"+sheet.getSheetName()+"-"+toDateStr()+".json");
			} finally{
				if(fileWriter!=null){
					fileWriter.close();
				}
			}
		}
//		return list;
	}

	private static JSONArray readSheet(Sheet sheet) {
		List<String> titles = new ArrayList<String>();
		List<String> dataTypes = new ArrayList<String>();
		JSONArray array = new JSONArray();
		for (int rowIndex = 0; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
			Row row = sheet.getRow(rowIndex);
			if(row == null || rowIndex == sheet.getFirstRowNum()){
				continue;
			}
			if(rowIndex == sheet.getFirstRowNum() + 1){
				Row nextRow = sheet.getRow(rowIndex+1);
				for (int i = row.getFirstCellNum(); i < row.getLastCellNum(); i++) {
					String stringCellValue = row.getCell(i).getStringCellValue();
					if("#".equals(stringCellValue))break;
					titles.add(stringCellValue);
					if(nextRow == null){
						return array;
					}
					Cell cell = nextRow.getCell(i);
					if(cell == null){
						throw new ReadExcelException("列["+stringCellValue+"],下边没有类型!");
					}
					dataTypes.add(cell.getStringCellValue());
				}
				rowIndex++;
				continue;
			}
			JSONObject rowData = new JSONObject();
//			HashMap<String, Object> rowDatas = new HashMap<String, Object>(row.getLastCellNum()+1);
			for (int i = 0; i < titles.size(); i++) {
				Cell cell = row.getCell(i);
				if(cell == null){
					throw new ReadExcelException("["+titles.get(i)+"]列第["+(rowIndex + 1)  +"]行没数据");
				}
				switch (cell.getCellType()) {
				case Cell.CELL_TYPE_BLANK:
					throw new ReadExcelException("["+titles.get(i)+"]列第["+(rowIndex + 1) +"]行没数据");
				case Cell.CELL_TYPE_BOOLEAN:
//					rowDatas.put(titles[i], cell.getBooleanCellValue());
					rowData.put(titles.get(i), cell.getBooleanCellValue());
					break;
				case Cell.CELL_TYPE_NUMERIC:
					if("int".equals(dataTypes.get(i))){
						rowData.put(titles.get(i), new Double(cell.getNumericCellValue()).intValue());
					}else if("float".equals(dataTypes.get(i))){
						rowData.put(titles.get(i), new Double(cell.getNumericCellValue()).floatValue());
					}
					break;
				case Cell.CELL_TYPE_STRING:
					if("dict".equals(dataTypes.get(i)) || "list".equals(dataTypes.get(i))){
						rowData.put(titles.get(i),JSON.parse(cell.getStringCellValue()));
					}else{
						rowData.put(titles.get(i),cell.getStringCellValue());
					}
					break;
				default:
					break;
				}
			}
			array.add(rowData);
		}
		return array;
	}
}
