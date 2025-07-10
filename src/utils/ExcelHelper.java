package utils;

import io.cucumber.datatable.DataTable;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Assert;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static vn.vietinbank.efast.utils.driver.DriverHelper.getOperatingSystem;

public class ExcelHelper {
    private static Sheet excelSheet;
    private static Workbook excelWorkbook;
    private static Cell cell;
    private static Cell cellName;
    private static Cell cellNumberAccount;
    private static Row row;
    Map dataMapExcel;
    static Path source;
    static Path target;
    public static String randomValue;

    public static String sheetPath(String pathCustom) {
        return EnvironmentConfig.getProperty("test.data.path") + pathCustom;
    }

    public static String sheetPathDownload(String pathCustom) {
        return EnvironmentConfig.getProperty("test.data.pathDown") + pathCustom;
    }

    private static void setExcelFile(String pathCustom, String nameCustom) throws IOException {
        FileInputStream excelFile = new FileInputStream(new File(sheetPathDownload(pathCustom)).getAbsolutePath());
        if (pathCustom.endsWith(".xls")) {
            excelWorkbook = new HSSFWorkbook(excelFile);
            excelSheet = excelWorkbook.getSheet(nameCustom);
        } else {
            excelWorkbook = new XSSFWorkbook(excelFile);
            excelSheet = excelWorkbook.getSheet(nameCustom);
        }
    }

    private static void setExcelFileUpload(String pathCustom, String nameCustom) throws IOException {
        FileInputStream excelFile = new FileInputStream(new File(sheetPath(pathCustom)).getAbsolutePath());
        if (pathCustom.endsWith(".xls")) {
            excelWorkbook = new HSSFWorkbook(excelFile);
            excelSheet = excelWorkbook.getSheet(nameCustom);
        } else {
            excelWorkbook = new XSSFWorkbook(excelFile);
            excelSheet = excelWorkbook.getSheet(nameCustom);
        }
    }

    private static int getDataRow(String dataKey, int dataColumn) {
        int rowCount = excelSheet.getLastRowNum();
        if (excelSheet == null) {
            throw new IllegalArgumentException("Excel sheet is null");
        }
        for (int row = 0; row <= rowCount; row++) {
            if (ExcelHelper.getCellData(row, dataColumn).equalsIgnoreCase(dataKey)) {
                return row;
            }
        }
        return 0;
    }

    private static String getCellData(int rowNumb, int colNumb) {
        Row row = excelSheet.getRow(rowNumb);
        if (row == null) {
            return "";
        }
        Cell cell = row.getCell(colNumb);
        if (cell == null) {
            return "";
        }
        if (cell.getCellType() == CellType.NUMERIC) {
            cell.setCellType(CellType.STRING);
        }
        return cell.getStringCellValue();
    }


    public Map getData(String pathCustom, String nameCustom, String dataKey, int positionHeader) throws Exception {
        Map dataMap = new HashMap<String, String>();
        setExcelFile(pathCustom, nameCustom);
        int dataRow = getDataRow(dataKey.trim(), 0);
        if (dataRow == 0) {
            throw new Exception("NO DATA FOUND for dataKey: " + dataKey);
        }
        int columnCount = excelSheet.getRow(dataRow).getLastCellNum();
        for (int i = 0; i < columnCount; i++) {
            cell = excelSheet.getRow(dataRow).getCell(i);
            String cellData = null;
            if (cell != null) {
                if (cell.getCellType() == CellType.NUMERIC) {
                    cell.setCellType(CellType.STRING);
                }
                cellData = cell.getStringCellValue();
            }
            dataMap.put(excelSheet.getRow(positionHeader).getCell(i).getStringCellValue(), cellData);
        }
        return dataMap;
    }

    public void verifyDataExcelWithExpectedValue(DataTable dataTable) {
        List<Map<String, String>> data = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> row : data) {
            String valueColumn = row.get("valueColumn");
            String valueRow = row.get("valueRow");
            String excelSheet = row.get("excelSheet");
            String valueCompare = row.get("valueCompare");
            String positionHeader = row.get("positionHeader");
            for (String element : getPathFileList()) {
                String[] name = element.split("download transfer money/");
                try {
                    dataMapExcel = getData(name[1], excelSheet, valueRow, Integer.parseInt(positionHeader));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                Object body = dataMapExcel.get(valueColumn).toString().trim();
                System.out.println("Data is: " + body);
                if (valueCompare != null && !valueCompare.isEmpty()) {
                    Assert.assertEquals(valueCompare, body);
                }
            }
        }
    }

    public static Path renameExcelFile(String fileUpload) {
        randomValue = DataInputHelper.randomValueByDate(" autotest") + ".xls";
        source = Paths.get(fileUpload);
        target = Paths.get(fileUpload.replace(".xls", randomValue));
        try {
            Files.copy(source, target);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return target;
    }

    public static void deleteNameExcelFile() {
        try {
            Files.delete(target);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("deleteNameExcelFile \n" + target);
    }

    public void deleteFileAfterDownload() {
        if (getOperatingSystem().contains("Windows")) {
            String fileDownload = sheetPathDownload("").replace("/", "\\\\");
            File file = new File(fileDownload);
            File[] files = file.listFiles();
            assert files != null;
            for (File f : files) {
                System.out.println(f.getName());
                if (f.isFile() && f.exists() && !f.getName().equals("SAV.txt")) {
                    f.delete();
                    System.out.println("successfully deleted");
                } else {
                    System.out.println("cant delete a file due to open or error");
                }
            }
        } else {
            String fileDownload = sheetPathDownload("");
            File file = new File(fileDownload);
            File[] files = file.listFiles();
            assert files != null;
            for (File f : files) {
                if (f.isFile() && f.exists() && !f.getName().equals("SAV.txt")) {
                    f.delete();
                    System.out.println("successfully deleted");
                } else {
                    System.out.println("cant delete a file due to open or error");
                }
            }
        }
    }

    public List<String> getPathFileList() {
        String fileDownload = sheetPathDownload("").replace("/", "\\\\");
        List<String> listName = new ArrayList<>();
        File file = new File(fileDownload);
        File[] files = file.listFiles();
        assert files != null;
        for (File f : files) {
            if (f.isFile() && f.exists() && !f.getName().equals("SAV.txt")) {
                listName.add(f.getAbsolutePath().replace("\\", "/"));
            } else {
                System.out.println("cant delete a file due to open or error");
            }
        }
        return listName;
    }

    private static String getCellDataAsString(Cell cell) {
        if (cell == null) {
            return "";
        }
        if (cell.getCellType() == CellType.NUMERIC) {
            cell.setCellType(CellType.STRING);
        }
        return cell.getStringCellValue();
    }

    private static int getLastRow(String fileName, String sheetName, String positionHeader) throws IOException {
        setExcelFileUpload(fileName, sheetName);
        int lastRowNum = excelSheet.getLastRowNum();
        int row = Integer.parseInt(positionHeader);
        while (row < lastRowNum) {
            String firstData = ExcelHelper.getCellData(row,0).trim();
            if(firstData.isEmpty()){
                System.out.println("Data is: " + row);
                break;
            }
            row++;
        }
        return row;
    }

    public static List<Map<String, String>> readDataFromExcelAsList(String fileName, String sheetName, String positionHeader) {
        List<Map<String, String>> listOfMap = new ArrayList<>();
        int lastRow = 0;
        try {
            lastRow = getLastRow(fileName, sheetName, positionHeader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        int row = Integer.parseInt(positionHeader);
        while (row < lastRow) {
            Map<String, String> dataMap = new HashMap<>();
            cellName = excelSheet.getRow(row).getCell(3);
            cellNumberAccount = excelSheet.getRow(row).getCell(5);
            String cellNameData = getCellDataAsString(cellName);
            String cellNumberAccountData = getCellDataAsString(cellNumberAccount);
            dataMap.put(cellNameData,cellNumberAccountData);
            listOfMap.add(dataMap);
            row++;
        }
        return listOfMap;
    }


}
