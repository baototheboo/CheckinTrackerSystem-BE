package com.example.ctsbe.dto.monthlyReport;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class MonthlyReportExport {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<MonthlyReportDTO> list;

    public MonthlyReportExport(List<MonthlyReportDTO> list) {
        this.list = list;
        workbook = new XSSFWorkbook();
        sheet = workbook.createSheet("Monthly_Report");
    }

    private void writeHeaderRow() {
        Row row = sheet.createRow(0);

        XSSFFont font = workbook.createFont();
        CellStyle style = workbook.createCellStyle();
        font.setBold(true);
        font.setFontHeight(10);
        style.setFont(font);
        style.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setBorderBottom(BorderStyle.MEDIUM);
        style.setBorderLeft(BorderStyle.MEDIUM);
        style.setBorderRight(BorderStyle.MEDIUM);
        style.setBorderTop(BorderStyle.MEDIUM);

        /*Cell cell = row.createCell(0);
        cell.setCellValue("Staff ID");
        cell.setCellStyle(style);*/

        Cell cell1 = row.createCell(0);
        cell1.setCellValue("Tên Nhân Viên");
        cell1.setCellStyle(style);

        Cell cell2 = row.createCell(1);
        cell2.setCellValue("Số Ngày đi làm");
        cell2.setCellStyle(style);

        Cell cell3 = row.createCell(2);
        cell3.setCellValue("Số Ngày nghỉ phép");
        cell3.setCellStyle(style);

        Cell cell4 = row.createCell(3);
        cell4.setCellValue("Số lần đi muộn");
        cell4.setCellStyle(style);

        Cell cell10 = row.createCell(4);
        cell10.setCellValue("Số giờ làm");
        cell10.setCellStyle(style);


    }

    private void writeDataRows() {
        int rowCount = 1;

        XSSFFont font = workbook.createFont();
        CellStyle style = workbook.createCellStyle();
        font.setFontHeight(10);
        style.setFont(font);
        XSSFFont font1 = workbook.createFont();
        CellStyle style1 = workbook.createCellStyle();
        font1.setBold(true);
        font1.setFontHeight(10);
        style1.setFont(font1);

        for (MonthlyReportDTO t : list) {
            Row row = sheet.createRow(rowCount++);

            Cell cell = row.createCell(0);
            cell.setCellValue(t.getStaffName());
            sheet.autoSizeColumn(0);
            cell.setCellStyle(style);

            Cell cell1 = row.createCell(1);
            cell1.setCellValue(t.getActiveDay());
            sheet.autoSizeColumn(1);
            cell1.setCellStyle(style);

            Cell cell2 = row.createCell(2);
            cell2.setCellValue(t.getOffDay());
            sheet.autoSizeColumn(2);
            cell2.setCellStyle(style);

            Cell cell3 = row.createCell(3);
            cell3.setCellValue(t.getLateDay());
            sheet.autoSizeColumn(3);
            cell3.setCellStyle(style);

            Cell cell4 = row.createCell(4);
            cell4.setCellValue(t.getWorkingHour());
            sheet.autoSizeColumn(4);
            cell4.setCellStyle(style);

        }
        /*Row row1 = sheet.createRow(list.size() + 1);
        Cell cellN = row1.createCell(10);
        cellN.setCellValue("Tổng");
        cellN.setCellStyle(style1);

        Cell cellN1 = row1.createCell(11);
        cellN1.setCellFormula("SUM(L2:L" + (list.size() + 1) + ")");

        cellN1.setCellStyle(style1);*/
    }

    public void export(HttpServletResponse response) throws IOException {
        writeHeaderRow();
        writeDataRows();

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }
}
