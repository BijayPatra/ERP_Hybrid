package utilities;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelFileUtil {
	Workbook wb;

// constructor for reading excel path

	public ExcelFileUtil(String Excelpath) throws Throwable {
		FileInputStream fi = new FileInputStream(Excelpath);
		wb = WorkbookFactory.create(fi);
	}

// method for counting no of rows in a sheet

	public int rowCount(String sheetName) 
	{
		return wb.getSheet(sheetName).getLastRowNum();

	}

// method from reading Celldata

	public String getCellData(String sheetName, int row, int column) {
		String data = "";
		if (wb.getSheet(sheetName).getRow(row).getCell(column).getCellType() == CellType.NUMERIC) {
			int celldata = (int) wb.getSheet(sheetName).getRow(row).getCell(column).getNumericCellValue();
			data = String.valueOf(celldata);
		} else {
			data = wb.getSheet(sheetName).getRow(row).getCell(column).getStringCellValue();
		}
		return data;
	}

// method for write status into new workbook
	public void setCellData(String sheetname, int row, int column, String status, String WriteExcelpath)	throws Throwable {
// get sheet from Workbook

		Sheet ws = wb.getSheet(sheetname);

		// getrow from sheet

		Row rowNum = ws.getRow(row);

		// create cell

		Cell cell = rowNum.createCell(column);

		// write status

		cell.setCellValue(status);

		if (status.equalsIgnoreCase("Pass")) 
		{
			CellStyle style = wb.createCellStyle();
			Font font = wb.createFont();
			font.setColor(IndexedColors.GREEN.getIndex());
			font.setBold(true);
			style.setFont(font);
			rowNum.getCell(column).setCellStyle(style);

		} else if (status.equalsIgnoreCase("Fail"))

		{
			CellStyle style = wb.createCellStyle();
			Font font = wb.createFont();
			font.setColor(IndexedColors.RED.getIndex());
			font.setBold(true);
			style.setFont(font);
			rowNum.getCell(column).setCellStyle(style);

		} else if (status.equalsIgnoreCase("Blocked")) {
			CellStyle style = wb.createCellStyle();
			Font font = wb.createFont();
			font.setColor(IndexedColors.BLUE.getIndex());
			font.setBold(true);
			style.setFont(font);
			rowNum.getCell(column).setCellStyle(style);

		}
		FileOutputStream fo = new FileOutputStream(WriteExcelpath);
		wb.write(fo);

	}
	
}
