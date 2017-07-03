package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
 

public class ExcelReader {

	public static String FILEPATH  = null;
	final static Logger log = Logger.getLogger(ExcelReader.class.getName());
	public ExcelReader(String FILEPATH){
		this.FILEPATH = FILEPATH;
		log.info("Test data folder path:"+FILEPATH);
	}
     public HashMap ReadExcell(String fileName,int row) throws IOException{
      String excelFILEPATH = FILEPATH+fileName;
         FileInputStream inputStream = new FileInputStream(new File(excelFILEPATH));
         // Create a hash map
         HashMap data = new HashMap();
         ArrayList elements= new ArrayList();
        
         Workbook workbook = new XSSFWorkbook(inputStream);
         Sheet firstSheet = workbook.getSheetAt(0);
         Iterator<Row> iterator = firstSheet.iterator();
        
         
         for(int count=1;count<=(row+1);count++){
             Row nextRow = iterator.next();
             Iterator<Cell> cellIterator = nextRow.cellIterator();
            if((row+1)==count||count==1){  
             while (cellIterator.hasNext()) {
                 Cell cell = cellIterator.next();
                  
                 switch (cell.getCellType()) {
                     case Cell.CELL_TYPE_STRING:
                        // System.out.print(cell.getStringCellValue());
                         elements.add(cell.getStringCellValue());
                         break;
                     case Cell.CELL_TYPE_BOOLEAN:
                        // System.out.print(cell.getBooleanCellValue());
                         elements.add(cell.getBooleanCellValue());
                         break;
                     case Cell.CELL_TYPE_NUMERIC:
                        // System.out.print(cell.getNumericCellValue());
                         elements.add(cell.getNumericCellValue());
                         break;
                 }
                 
                // hm.put("Zara", new Double(3434.34));
                // System.out.print(" - ");
             }
             //System.out.println();
            }
         }
          
         workbook.close();
         inputStream.close();
         
         //adding data to hashmap
         for(int i=0;i<elements.size()/2;i++){
          data.put(elements.get(i), elements.get(i+(elements.size()/2)));
			// System.out.println(elements.get(i)+"---"+ elements.get(i+(elements.size()/2)));
         }
         return data;
     }
     
     public String getStrValue(String fileName,int row,String colum){
    	HashMap<String, String>data =  new HashMap<String,String>();
    	try {
			data =ReadExcell(fileName,row);
	    	 return data.get(colum); 

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "No Value";
		}
     }
     
  public int getNoOfData(String str,String delimiter){
	  List<String> valueList = Arrays.asList(str.split(delimiter));
	  return valueList.size();
  }
 
}