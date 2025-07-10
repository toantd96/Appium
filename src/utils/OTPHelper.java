package utils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import java.time.Duration;
import java.time.Instant;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OTPHelper {
  public String getRegexOTPOf(String address){
    if(address.contentEquals("FPT-CA")){
      return "body=\\[FPT-CA]\\ Ma OTP cua ban: (.*?),";
    }

    return null;
  }
  public String waitAndGetOTP(AppiumDriver appiumDriver,String address,int timeoutSecond) {
    try{
      Instant startTime = Instant.now();
      while (Duration.between(startTime,Instant.now()).compareTo(Duration.ofSeconds(timeoutSecond))<=0){
        String smsContentNew = "";
        if(appiumDriver instanceof AndroidDriver){
          smsContentNew = String.valueOf(appiumDriver.executeScript("mobile: listSms")).substring(0,200);
        }
        else if(appiumDriver instanceof IOSDriver){
          smsContentNew = "ios khong cho lay truc tiep tin nhan tu sms ma can phai thong qua notification center, ban nao dung iphone can viet code them de lay";
        }
        System.out.println(smsContentNew);
        Matcher matcher = Pattern.compile(getRegexOTPOf(address)).matcher(smsContentNew);
        if(matcher.find()){
          return matcher.group(1);
        }
        else{
          System.out.println("ko thay otp");
          Thread.sleep(3000);
        }
      }
    }catch (Exception e){
      e.printStackTrace();
    }
    return null;
  }
}
