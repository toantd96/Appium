package utils;

import static utils.EnvironmentConfig.env_var;

import java.util.List;

public class Constant {

  String passOTP = "123";
  public static String transactionStatus;
  public static String transactionNumber;
  public static List<String> listAllInfoInput;

  public static String data_ekyc_api_path = "src/test/resources/data/api/ekyc/";
  ;
  public static String data_ekyc_image_path = "src/test/resources/data/api/ekyc_image/";
  ;
  public static String resource_path = "src/test/resources/";
  public static String data_report_portal_api_path = "src/test/resources/data/api/report_portal/";

  public static String accountnumber;
  public static String OTP = "";
  public static String WINDOW = "win";
  public static String MAC = "mac";
  public static String OS =
      System.getProperty("os.name").toLowerCase().contains(WINDOW) ? WINDOW : MAC;
  public static String QUERY_GET_DATABASE =
      "SELECT * FROM autotest.autotest.%s" + " WHERE testcase_id = '%s'";
  public static String onboardingId;
}
