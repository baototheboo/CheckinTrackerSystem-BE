package com.example.ctsbe.dto.monthlyReport;

import com.example.ctsbe.dto.timesheet.TimesheetDTO;
import com.example.ctsbe.util.DateUtil;
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
    private XSSFSheet sheet2;
    private List<MonthlyReportDTO> list;

    DateUtil dateUtil = new DateUtil();

    private List<TimesheetDTO> listTimesheet;

    public MonthlyReportExport(List<MonthlyReportDTO> list, List<TimesheetDTO> listTimesheet) {
        this.list = list;
        this.listTimesheet = listTimesheet;
        workbook = new XSSFWorkbook();
        sheet = workbook.createSheet("Tổng hợp tháng");
        sheet2 = workbook.createSheet("Chi tiết tháng");
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
    }

    private void writeHeaderRowAtSheet2() {
        Row row = sheet2.createRow(0);

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
        cell1.setCellValue("Id");
        cell1.setCellStyle(style);

        Cell cell2 = row.createCell(1);
        cell2.setCellValue("Tên Nhân Viên");
        cell2.setCellStyle(style);


        for (int i = 2; i <= dateUtil.getLengthOfMonth(listTimesheet.get(1).getMonthYear())+1; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(listTimesheet.get(1).getMonthYear() + "-" + String.valueOf(i-1));
            cell.setCellStyle(style);
        }

    }

    private void writeDataRowsAtSheet2() {
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

        for (TimesheetDTO dto:listTimesheet) {
            Row row = sheet2.createRow(rowCount++);
            Cell cell = row.createCell(0);
            cell.setCellValue(dto.getStaffId());
            sheet2.autoSizeColumn(0);
            cell.setCellStyle(style);

            Cell cell2 = row.createCell(1);
            cell2.setCellValue(dto.getStaffName());
            sheet2.autoSizeColumn(1);
            cell2.setCellStyle(style);
            if(dto.getDayCheck()!=null){
                for (int i= 2;i<=dto.getDayCheck().size()+1;i++){
                    Cell cell1 = row.createCell(i);
                    switch (dto.getDayCheck().get(i-2)){
                        case 1:
                            cell1.setCellValue("OK");
                            break;
                        case 2:
                            cell1.setCellValue("Muộn");
                            //cell1.getCellStyle().setFillForegroundColor(IndexedColors.CORAL.getIndex());
                            break;
                        case 3:
                            cell1.setCellValue("x");
                            //cell1.getCellStyle().setFillForegroundColor(IndexedColors.RED.getIndex());
                            break;
                        case 4:
                            cell1.setCellValue("Ngày nghỉ/Cuối tuần");
                            break;
                        case 5:
                            cell1.setCellValue("Not yet");
                            //cell1.getCellStyle().setFillForegroundColor(IndexedColors.GREY_40_PERCENT.getIndex());
                            break;
                    }
                    sheet2.autoSizeColumn(i);
                    cell1.setCellStyle(style);
                }
            }
        }
    }

    public void export(HttpServletResponse response) throws IOException {
        writeHeaderRow();
        writeDataRows();
        writeHeaderRowAtSheet2();
        writeDataRowsAtSheet2();

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }
}
