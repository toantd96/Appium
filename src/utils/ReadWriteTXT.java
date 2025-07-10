package utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vn.vietinbank.efast.base.BaseScreen;
import vn.vietinbank.efast.screens.api.Login;
import vn.vietinbank.efast.utils.component.DataObject;
import vn.vietinbank.efast.utils.component.DataObject.TRANS;

public class ReadWriteTXT {
  private static final Logger log = LoggerFactory.getLogger(ReadWriteTXT.class);
//  public static void main(String[] args){
//    String path = "D:\\gitlab_project\\AutomationTest\\src\\test\\resources\\data\\PHBLOL.txt";
//    String status = "{";
//    String statusUpdate = "[";
//    updateDataInFile(path,status,statusUpdate);
//  }
  public static String getrefNumber(String filepath, String status) {
    System.out.println(filepath + " > " + status);
    String refNumber = "";
    String line = "";
    BufferedReader reader;
    try {
      reader = new BufferedReader(new FileReader(filepath));
      // Read first line
      while (line != null) {
        line = reader.readLine();
        System.out.println(line);
        if (line == null) {
          refNumber = "";
          break;
        }
        if (!line.equals("")) {
          int indexStartStatus = line.indexOf("\t");
          if (line.substring(indexStartStatus).contains(status)) {
//						System.out.println(line);
            String[] ref = line.split("\t");
            refNumber = ref[0];
            break;
          }

        }
      }
      reader.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
//		System.out.println(refNumber);
    return refNumber;
  }

  public static String getrefNumber2(String filepath, String status) {
    System.out.println(filepath + " > " + status);
    String refNumber = "";
    ObjectNode node = DataObject.findObjectNodeHasFieldEqualValue(filepath, TRANS.status.name(),
        status);
    if (node != null) {
      refNumber = node.get(TRANS.transactioNumber.name()).asText();
    }
    return refNumber;
  }

  public static ObjectNode getDataInput(String filepath, String status, String refNumber) {
    System.out.println(filepath + " > " + status);
    ObjectNode node = DataObject.findObjectNodeHasFieldEqualValue(filepath, TRANS.status.name(),
        status, TRANS.transactioNumber.name(), refNumber);
    return node;
  }

  public static ObjectNode findObjectNodeHasStatus(String filepath, String status) {
    System.out.println(filepath + " > " + status);
    ObjectNode node = DataObject.findObjectNodeHasFieldEqualValue(filepath, TRANS.status.name(),
        status);
    return node;
  }

  static ObjectNode readFromJson(String jsonStr) {
    return (ObjectNode) readJson(jsonStr);
  }

  static JsonNode readJson(String json) {
    ObjectMapper objectMapper = new ObjectMapper();
    JsonNode jsonNode;
    try {
      jsonNode = objectMapper.readTree(json);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
    return jsonNode;
  }

  static List<String> getArrayDataHas(String filepath, String status, String _refNumber) {
    System.out.println(filepath + " > " + status);
    List<String> arrayData = new ArrayList<String>();
    String refNumber = "";
    String line = "";
    String statusDesc = "";
    String dataInput = "";
    BufferedReader reader;
    try {
      reader = new BufferedReader(new FileReader(filepath));
      // Read first line
      while (line != null) {
        line = reader.readLine();
        System.out.println(line);
        if (line == null) {
          refNumber = "";
          break;
        }
        if (!line.equals("")) {
          String[] listData = line.split("\t");
          statusDesc = listData[1];
          if (statusDesc.contains(status)) {
            refNumber = listData[0];
            if (listData.length > 2) {
              if (refNumber.contentEquals(_refNumber)) {
                dataInput = listData[2];
                break;
              }
            } else {
              break;
            }
          }
        }
      }
      reader.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    arrayData.add(refNumber);
    arrayData.add(statusDesc);
    arrayData.add(dataInput);

    return arrayData;
  }

  public static boolean updateDataInFile(String filepath, String status, String statusUpdate) {
    log.info("updateDataInFile:" + status + " > " + statusUpdate);
    boolean result = false;
    String strLine = "";
    String str_data = "";
    BufferedReader reader;
    FileWriter writer = null;
    try {
      reader = new BufferedReader(new FileReader(filepath));
      // Read first line
      while (strLine != null) {
        strLine = reader.readLine();
        if (strLine == null) {
          break;
        }
        if(strLine.contains(status)){
          // neu la ky tu dac biet thi khong dung replace
          if(status.startsWith("{")){
            log.info("updateDataInFile strLine get equal:" + statusUpdate);
            strLine = statusUpdate;
          }
          else {
            strLine = strLine.replace(status, statusUpdate);
            log.info("updateDataInFile strLine replace:" + status + " > " + statusUpdate);
          }
        }

        str_data += strLine + System.lineSeparator();
      }

      writer = new FileWriter(filepath);
      writer.write(str_data);
      reader.close();
      writer.close();
      result = true;
    } catch (Exception e) {
      // TODO: handle exception
      e.printStackTrace();
    }
    return result;
  }

  public static boolean SaveReferenceNumberOrderToFile(String filepath, String text) {
    boolean result = false;
    FileWriter fw = null;
    BufferedWriter bw = null;
    PrintWriter out = null;
    try {
      fw = new FileWriter(filepath, true);
      bw = new BufferedWriter(fw);
      out = new PrintWriter(bw);
      out.println(text);
      out.close();

      result = true;
    } catch (Exception e) {
      // TODO: handle exception
      e.printStackTrace();
    } finally {
      if (out != null) {
        out.close();
      }
      try {
        if (bw != null) {
          bw.close();
        }
      } catch (IOException e) {
        //exception handling left as an exercise for the reader
      }
      try {
        if (fw != null) {
          fw.close();
        }
      } catch (IOException e) {
        //exception handling left as an exercise for the reader
      }
    }
    return result;
  }

  public static boolean saveReferenceNumberOrderToFileOverWrite(String filepath, String text) {
    boolean result = false;
    FileWriter fw = null;
    BufferedWriter bw = null;
    PrintWriter out = null;
    try {
      fw = new FileWriter(filepath, false);
      bw = new BufferedWriter(fw);
      out = new PrintWriter(bw);
      out.println(text);
      out.close();

      result = true;
    } catch (Exception e) {
      // TODO: handle exception
      e.printStackTrace();
    } finally {
      if (out != null) {
        out.close();
      }
      try {
        if (bw != null) {
          bw.close();
        }
      } catch (IOException e) {
        //exception handling left as an exercise for the reader
      }
      try {
        if (fw != null) {
          fw.close();
        }
      } catch (IOException e) {
        //exception handling left as an exercise for the reader
      }
    }
    return result;
  }
}
