@TestLogin
Feature: login Test

  @M11S03_TC_001
  Scenario: Login new eFast
    Given Chuyển sang "chrome"
    When Open login new eFast
    Then Input username "lethichang1" and password "12121212"
    And Click Login eFast
    When Show Title HomePage

#    Then Quit browser

#  @TC_01  @smoke  @mobile
#  Scenario: login Touch Point
#    And Chuyển sang "ie"
#    And Open login touch point
#    And Input username "wbaolanh03" and password "Aw123456" in touch point
#    And Click Login
#    And switch Window Touch Point
#    And Chọn language "en" and Chọn role "CB_TNTD_SS" and Chọn location "CN TP HA NOI - HOI SO"

