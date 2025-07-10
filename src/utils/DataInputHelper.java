package utils;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.text.Normalizer;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import net.serenitybdd.core.pages.WebElementFacade;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

public class DataInputHelper {

  public String getLocalDate(String dateFormat) {
    LocalDate currentDate = LocalDate.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);
    return currentDate.format(formatter);
  }
  public String getFirstDate(String dateFormat) {
    LocalDate currentDate = LocalDate.now();
    LocalDate firstDate = LocalDate.of(currentDate.getYear(),currentDate.getMonth(),1);
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);
    return firstDate.format(formatter);
  }

  public String getDataWith(String typeValue) {
    if (typeValue.contains("DATE_NOW")) {
      String dateFormat = typeValue.replace("DATE_NOW_", "");
      return getLocalDate(dateFormat);
    }
    if (typeValue.contains("FIRST_DATE")) {
      String dateFormat = typeValue.replace("FIRST_DATE_", "");
      return getFirstDate(dateFormat);
    }
    if (typeValue.contains("RANDOM_") && typeValue.contains("_NUMBER")) {
      String stringReplace =
          typeValue
              .subSequence(typeValue.indexOf("RANDOM_"), typeValue.indexOf("_NUMBER") + 7)
              .toString();
      String numberRandom =
          typeValue
              .subSequence(typeValue.indexOf("RANDOM_") + 7, typeValue.indexOf("_NUMBER"))
              .toString();
      String strValueRandom = RandomStringUtils.randomNumeric(Integer.parseInt(numberRandom));
      return typeValue.replace(stringReplace, strValueRandom);
    }
    return typeValue;
  }

  public String getPropertyValue(String propertyName, String filePath) {
    String propertyValue = null;
    try {
      Properties properties = new Properties();
      FileInputStream fileInputStream = new FileInputStream(filePath);
      InputStreamReader isr = new InputStreamReader(fileInputStream, "UTF-8");
      properties.load(isr);
      propertyValue = properties.getProperty(propertyName);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return propertyValue;
  }

  public void writeAppendToFile(String content, String fileName) {
    try {

    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public String replaceFont(String text) {
    String[] chars = {"ậ", "ấ", "ự"};
    String[] replacesBy = {"ậ", "ấ", "ự"};
    String textAfterReplace = text;
    for (int i = 0; i < chars.length; i++) {
      textAfterReplace = text.replaceAll(chars[i], replacesBy[i]);
    }
    return textAfterReplace;
  }

  public static String randomValueByDate(String value) {
    LocalDateTime date = LocalDateTime.now();
    return value + date.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
  }

  public String roundMoney(String money) {
    String availableBalanceInputNoComas = money.replaceAll(",", "");
    double amount = Double.parseDouble(availableBalanceInputNoComas);
    BigDecimal bd = new BigDecimal(amount).setScale(0, RoundingMode.UP);
    DecimalFormat df = new DecimalFormat("###,###");
    money = df.format(bd);
    return money;
  }

  public String plusMoney(String money, double plusMoney) {
    String availableBalanceInputNoComas = money.replaceAll(",", "");
    double amount = Double.parseDouble(availableBalanceInputNoComas);
    amount = amount + plusMoney;
    DecimalFormat df = new DecimalFormat("###,###");
    money = df.format(amount);
    return money;
  }

  public double convertMoneyToDouble(String money) {
    String availableBalanceInputNoComas = money.replaceAll(",", "");
    return Double.parseDouble(availableBalanceInputNoComas);
  }

  public String deAccent(String str) {
    String decompositedForm = Normalizer.normalize(str, Normalizer.Form.NFD);
    String text = decompositedForm.replaceAll("\\p{M}", "").replace('đ', 'd').replace('Đ', 'D');
    return text;
  }

  public String getIDTransactionSuccess(String text) {
    String id = "";
    List<String> arrayText = Arrays.asList(text.split(" "));
    for (String ID : arrayText) {
      if (ID.length() == 16) {
        id = ID;
        break;
      }
    }
    return id;
  }

  public String getIDTransactionSuccess(WebElementFacade webElementFacade) {
    String idTransaction = "";
    try {
      Thread.sleep(2000);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
    String text =
        webElementFacade.withTimeoutOf(Duration.ofSeconds(20)).waitUntilEnabled().getText();
    List<String> arrayText = Arrays.asList(text.split(" "));
    for (String ID : arrayText) {
      if (ID.length() == 16 || ID.length() == 15 || ID.length() == 14 || ID.length() == 13) {
        idTransaction = ID;
        break;
      }
    }
    return idTransaction;
  }

  public String plusDay(String dateFormat, int days){
    LocalDate currentDate = LocalDate.now();
    currentDate = currentDate.plusDays(days);
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);
    return currentDate.format(formatter);
  }

  public Properties getProperties(String filePath) {
    Properties properties = new Properties();
    try {
      FileInputStream fileInputStream = new FileInputStream(filePath);
      InputStreamReader isr = new InputStreamReader(fileInputStream, "UTF-8");
      properties.load(isr);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return properties;
  }

}
