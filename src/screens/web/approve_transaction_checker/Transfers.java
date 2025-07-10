package screens.web.approve_transaction_checker;

import java.time.Duration;

import net.serenitybdd.annotations.Step;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;
import org.junit.Assert;
import org.openqa.selenium.By;
import vn.vietinbank.efast.base.BasePage;
import vn.vietinbank.efast.screens.web.newefast.payment.AutoDebit;
import vn.vietinbank.efast.utils.component.ApproveTransaction;
import vn.vietinbank.efast.utils.component.TransferMoney;

public class Transfers extends BasePage {
  @FindBy(xpath = "//div[@class='col-xl-6 col-xxl-6']//div[@class='input-group']//input")
  private WebElementFacade transactionContent_ip;

  @FindBy(xpath = "//a[@class='btn btn-submit']")
  private WebElementFacade submit_btn;

  @FindBy(id = "btnSubmit")
  private WebElementFacade approve_btn;

  @FindBy(xpath = "//a[@class='btn btn-next']")
  private WebElementFacade approve_schedule_btn;

  @FindBy(id = "btnReject")
  private WebElementFacade reject_btn;

  @FindBy(xpath = "//div[@class='select-default']//div")
  private WebElementFacade verificationMethod_dd;

  @FindBy(xpath = "//div[contains(@class,'react-select__menu')]//div[text()='VietinBank OTP']/..")
  private WebElementFacade vietinBankOTP_txt;

  @FindBy(
      xpath = "//div[contains(@class,'react-select__menu')]//div[text()='ByPass OTP Keypass']/..")
  private WebElementFacade byPassOTPKeypass_txt;

  @FindBy(xpath = "//div[@class='btn-right']//a")
  private WebElementFacade accept_btn;

  @FindBy(xpath = "//div[@class='alert__inner']//p")
  private WebElementFacade alert_success_txt;

  @FindBy(xpath = "//div[@id='tbody'] | //div[@id='tblListFile']")
  private WebElementFacade tableTransaction;

  @FindBy(xpath = "//div[@class='modal-countdown__body']")
  private WebElementFacade otpTime;

  private final String transactionNumber_txt = "//a[text()='%s']";
  private final String confirm_xpath =
      "//span[contains(text(),'%s')]/ancestor::li//p[contains(text(),'%s')]";
  private final String confirmSchedule_xpath =
      "//span[contains(text(),'%s')]/ancestor::li//p[text()='%s']";
  private final String verifyTotalAmount = "//span[text()='%s']/following-sibling::b";
  String transactionNumberBulkTransfer_txt = "//input[@id='%s']/..";
  String transactionNumberHugeTransfer_txt =
      "(//div[text()='%s']/parent::td/preceding::td[@padding='checkbox'])[last()]";
  ApproveTransaction approveTransaction = new ApproveTransaction();
  TransferMoney transferMoney = new TransferMoney();
  AutoDebit autoDebit = new AutoDebit();

  @Step("Nội dung giao dịch là: {0}")
  public void inputTransactionContent(String content) {
    setDriver(webDriver);
    transactionContent_ip.waitUntilVisible().sendKeys(content);
  }

  public void clickSearchButton() {
    submit_btn.waitUntilVisible().click();
  }

  @Step("Chọn giao dịch {0}")
  public void clickTransactionNumber(String number) {
    waitLogoLoadingUntilNotVisible();
    WebElementFacade webElementFacade =
        find(By.xpath(String.format(transactionNumber_txt, number)));
    scrollDownWithinWebElementUntilFindElement(tableTransaction, webElementFacade, 200);
  }

  public void clickApproveButton() {
    scrollToWebElement(approve_btn);
    approve_btn.waitUntilVisible().click();
  }

  public void clickApproveButtonInSchedule() {
    scrollToWebElement(approve_schedule_btn);
    approve_schedule_btn.waitUntilVisible().click();
  }

  public void clickRejectButton() {
    reject_btn.waitUntilVisible().click();
  }

  public void clickAcceptButton() {
    click(accept_btn);
    waitLogoLoadingUntilNotVisible();
  }

  public void getInformationConfirmInCheckerScreen(
      String IDNumber,
      String debitAmount,
      String typeTransfer,
      String accountNumber,
      String bankName,
      String transactionAmount,
      String transactionContent) {
    Assert.assertTrue(
        find(By.xpath(
                String.format(confirm_xpath, approveTransaction.transaction_number, IDNumber)))
            .isVisible());
    Assert.assertTrue(
        find(By.xpath(
                String.format(confirm_xpath, approveTransaction.debit_account_number, debitAmount)))
            .isVisible());
    Assert.assertTrue(
        find(By.xpath(
                String.format(
                        confirm_xpath, transferMoney.beneficiary_account_number, accountNumber)
                    + "|"
                    + String.format(confirm_xpath, transferMoney.card_number, accountNumber)))
            .isVisible());
    if (!typeTransfer.equals("within vietinbank") && !typeTransfer.equals("payment order")) {
      //      Assert.assertTrue(
      //          find(By.xpath(String.format(confirm_xpath, transferMoney.bank_name, bankName)))
      //              .isVisible());
    }
    Assert.assertTrue(
        find(By.xpath(
                String.format(
                    confirm_xpath, approveTransaction.total_transaction_amount, transactionAmount)))
            .isVisible());
    Assert.assertTrue(
        find(By.xpath(
                String.format(
                    confirm_xpath, transferMoney.transaction_content, transactionContent)))
            .isVisible());
  }

  public void selectByPassOTP() {
    //    scrollUpUntilFindElement(verificationMethod_dd, 100);
    setDriver(webDriver);
    waitABit(1000);
    scrollUpTopPage();
    click(verificationMethod_dd);
    click(byPassOTPKeypass_txt);
  }

  public void selectVietinbankOTP() {
    setDriver(webDriver);
    waitABit(1000);
    verificationMethod_dd.waitUntilVisible().click();
    vietinBankOTP_txt.waitUntilVisible().click();
  }

  public void verifyTotalTransactionAmount(String amount) {
    Assert.assertEquals(
        amount,
        find(By.xpath(
                String.format(verifyTotalAmount, approveTransaction.total_transaction_amount)))
            .getText());
  }

  public void verifyAlertSuccess(String IDnumber) {
    String alert =
        alert_success_txt.withTimeoutOf(Duration.ofSeconds(30)).waitUntilVisible().getText();

    displayAlert(IDnumber + " " + approveTransaction.mess_approve_success);
    Assert.assertTrue(alert.contains(IDnumber + " " + approveTransaction.mess_approve_success));
  }

  public void verifyAlertApprovedSuccess(String IDnumber) {
    String alert =
        alert_success_txt.withTimeoutOf(Duration.ofSeconds(30)).waitUntilVisible().getText();

    displayAlert(IDnumber + " " + approveTransaction.mess_approved_successfully);
    Assert.assertTrue(
        alert.contains(IDnumber + " " + approveTransaction.mess_approved_successfully));
  }

  public void verifyAlertInsufficientBalance(String IDnumber) {
    String alert = alert_success_txt.waitUntilVisible().getText();
    displayAlert(IDnumber + approveTransaction.mess_approve_insufficient_balance);
    Assert.assertTrue(
        alert.contains(IDnumber + approveTransaction.mess_approve_insufficient_balance));
  }

  public void verifyAlertSuccessWithInternalChanges(String IDnumber) {
    String alert = alert_success_txt.waitUntilVisible().getText();

    displayAlert(IDnumber + " " + approveTransaction.mess_approve_success_with_internal_charges);
    Assert.assertTrue(
        alert.contains(
            IDnumber + " " + approveTransaction.mess_approve_success_with_internal_charges));
  }

  public void clickTransactionNumberBulkTransfer(String number) {
    waitLogoLoadingUntilNotVisible();
    find(By.xpath(String.format(transactionNumberBulkTransfer_txt, number)))
        .waitUntilVisible()
        .click();
  }

  public void clickTransactionNumberHugeTransfer(String number) {
    waitLogoLoadingUntilNotVisible();
    find(By.xpath(String.format(transactionNumberHugeTransfer_txt, number)))
        .waitUntilVisible()
        .click();
  }

  public void verifyInformationConfirmInScheduleCheckerScreen(
      String IDNumber,
      String debitAmount,
      String accountNumber,
      String transactionAmount,
      String startDate,
      String endDate) {
    Assert.assertTrue(
        find(By.xpath(
                String.format(
                    confirmSchedule_xpath, approveTransaction.transaction_number, IDNumber)))
            .isVisible());
    Assert.assertTrue(
        find(By.xpath(
                String.format(
                    confirmSchedule_xpath, approveTransaction.debit_account_number, debitAmount)))
            .isVisible());
    Assert.assertTrue(
        find(By.xpath(
                String.format(
                    confirmSchedule_xpath,
                    approveTransaction.beneficiary_account_number,
                    accountNumber)))
            .isVisible());
    Assert.assertTrue(
        find(By.xpath(
                String.format(
                    confirmSchedule_xpath,
                    approveTransaction.transfer_amount_time,
                    transactionAmount)))
            .isVisible());

    Assert.assertTrue(
        find(By.xpath(
                String.format(
                    confirmSchedule_xpath,
                    approveTransaction.start_date,
                    startDate.replaceAll("/", "-"))))
            .isVisible());

    Assert.assertTrue(
        find(By.xpath(
                String.format(
                    confirmSchedule_xpath,
                    approveTransaction.end_date,
                    endDate.replaceAll("/", "-"))))
            .isVisible());
  }

  public void doubleClickAcceptButton() {
    doubleClick(accept_btn);
    waitLogoLoadingUntilNotVisible();
  }

  @Step("Thông báo hiển thị là: {0}")
  public void displayAlert(String mess) {
    logger.info(mess);
  }

  public void verifyOTPTimeDisplay() {
    waitABit(2000);
    Assert.assertTrue(
        otpTime.withTimeoutOf(Duration.ofSeconds(20)).waitUntilVisible().isDisplayed());
  }

  public void clickAcceptButtonDoubleTimes() {
    accept_btn.waitUntilVisible().doubleClick();
    waitLogoLoadingUntilNotVisible();
  }

  public void verifyAlertSuccessPayment() {
    String alert =
        alert_success_txt.withTimeoutOf(Duration.ofSeconds(30)).waitUntilVisible().getText();
    if (autoDebit.countPayment > 1) {
      displayAlert(autoDebit.countPayment + " " + approveTransaction.mess_approve_success_payment);
      Assert.assertTrue(
          alert.contains(
              autoDebit.countPayment + " " + approveTransaction.mess_approve_success_payment));
    } else {
      displayAlert(approveTransaction.mess_approve_success_payment);
      Assert.assertTrue(alert.contains(approveTransaction.mess_approve_success_payment));
    }
  }

  public void verifyAlertSuccessProd(String IDnumber) {
    String alert =
        alert_success_txt.withTimeoutOf(Duration.ofSeconds(30)).waitUntilVisible().getText();

    displayAlert(alert);
    Assert.assertTrue(
        alert.contains(IDnumber + " " + approveTransaction.mess_approve_success_prod));
  }

  public void rejectTransaction(String IDnumber) {
    String alert =
        alert_success_txt.withTimeoutOf(Duration.ofSeconds(30)).waitUntilVisible().getText();

    displayAlert(IDnumber + " " + approveTransaction.reject_transaction);
    Assert.assertTrue(alert.contains(IDnumber + " " + approveTransaction.reject_transaction));
  }

  public void rejectTransactionPayment() {
    String alert =
        alert_success_txt.withTimeoutOf(Duration.ofSeconds(30)).waitUntilVisible().getText();

    displayAlert(approveTransaction.reject_transaction);
    Assert.assertTrue(alert.contains(approveTransaction.reject_transaction));
  }
}
