package com.test.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import com.test.base.BaseClass;
/**
 * Read Excel data
 * Take screenshot for failed test cases
 * 
 * @author BRONSON
 *
 */
public class TestUtil extends BaseClass {

	public static long PAGE_LOAD_TIMEOUT = 20;
	public static long IMPLICIT_WAIT = 20;

	public static String TESTDATA_SHEET_PATH = System.getProperty("user.dir") + "/src/main/resources/testdata.xlsx";

	static Workbook book;
	static Sheet sheet;
	static JavascriptExecutor js;

	public String getExcelDataFromFile(String filePath, String sheetName, int columnIndex, int rowIndex) {
		String data = "";
		InputStream in = null;
		XSSFWorkbook wb = null;
		try {

			if (filePath == null || filePath.trim().equals(""))
				throw (new Exception());
			if (filePath.endsWith(".xlsx") || filePath.endsWith(".xls")) {

				in = new FileInputStream(filePath);
				wb = new XSSFWorkbook(in);
			} else {
				throw (new Exception());
			}

			if (columnIndex < 1)
				throw (new Exception());
			if (rowIndex < 1)
				throw (new Exception());

			int index = wb.getSheetIndex(sheetName);
			if (index == -1)
				throw (new Exception());

			XSSFSheet mySheet = wb.getSheetAt(index);
			XSSFRow row = mySheet.getRow(rowIndex - 1);
			if (row == null)
				return "";
			Cell cell = row.getCell(columnIndex - 1);
			if (cell != null) {
				if (cell.getCellType() == Cell.CELL_TYPE_STRING)
					data = cell.getStringCellValue();
				else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
					data = cell.getNumericCellValue() + "";
				} else if (cell.getCellType() == Cell.CELL_TYPE_FORMULA)
					data = cell.getNumericCellValue() + "";
				else
					data = cell.toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				wb.close();
				in.close();
			} catch (Exception e) {
			}
		}
		return data;
	}

	public static Object[][] getTestData(String sheetName) {
		FileInputStream file = null;
		try {
			file = new FileInputStream(TESTDATA_SHEET_PATH);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			book = WorkbookFactory.create(file);
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		sheet = book.getSheet(sheetName);
		Object[][] data = new Object[sheet.getLastRowNum()][sheet.getRow(0).getLastCellNum()];

		// sheet.getRow(0).getLastCellNum());
		for (int i = 0; i < sheet.getLastRowNum(); i++) {
			for (int k = 0; k < sheet.getRow(0).getLastCellNum(); k++) {
				data[i][k] = sheet.getRow(i + 1).getCell(k).toString();
				
				data[i][k].toString();

			}
		}

		return data;
	}

	public static void takeScreenshotAtEndOfTest() throws IOException {
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String currentDir = System.getProperty("user.dir");
		FileUtils.copyFile(scrFile, new File(currentDir + "/screenshots/" + System.currentTimeMillis() + ".png"));
	}

	public void runTimeInfo(String messageType, String message) throws InterruptedException {
		js = (JavascriptExecutor) driver;
		// Check for jQuery on the page, add it if need be
		js.executeScript("if (!window.jQuery) {"
				+ "var jquery = document.createElement('script'); jquery.type = 'text/javascript';"
				+ "jquery.src = 'https://ajax.googleapis.com/ajax/libs/jquery/2.0.2/jquery.min.js';"
				+ "document.getElementsByTagName('head')[0].appendChild(jquery);" + "}");
		Thread.sleep(5000);

		// Use jQuery to add jquery-growl to the page
		js.executeScript("$.getScript('https://the-internet.herokuapp.com/js/vendor/jquery.growl.js')");

		// Use jQuery to add jquery-growl styles to the page
		js.executeScript("$('head').append('<link rel=\"stylesheet\" "
				+ "href=\"https://the-internet.herokuapp.com/css/jquery.growl.css\" " + "type=\"text/css\" />');");
		Thread.sleep(5000);

		// jquery-growl w/ no frills
		js.executeScript("$.growl({ title: 'GET', message: '/' });");

		if (messageType.equals("error")) {
			js.executeScript("$.growl.error({ title: 'ERROR', message: '" + message + "' });");
		} else if (messageType.equals("info")) {
			js.executeScript("$.growl.notice({ title: 'Notice', message: 'your notice message goes here' });");
		} else if (messageType.equals("warning")) {
			js.executeScript("$.growl.warning({ title: 'Warning!', message: 'your warning message goes here' });");
		} else
			System.out.println("no error message");
		
		Thread.sleep(5000);
	}

}
