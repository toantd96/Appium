package steps.mobile.approve_transaction_checker;

import io.cucumber.java.en.And;
import vn.vietinbank.efast.screens.mobile.approve_transaction_checker.Transfers;

import static vn.vietinbank.efast.screens.mobile.transfer_money.InterBankTransfer.idTransaction;
import static vn.vietinbank.efast.steps.BaseSteps.appiumDriver;

public class TransferStep {
  Transfers transfers = new Transfers(appiumDriver);

  @And("Click Điện chuyển tiền button")
  public void clickTransfer() {
//    transfers.clickSkipButton();
    transfers.clickTransferMoney();
  }

  @And("Chọn giao dịch vừa chuyển")
  public void clickJustTransaction() {
    transfers.selectTransaction(idTransaction);
  }

  @And("Chọn duyệt giao dịch")
  public void clickApproveTransaction() {
    transfers.clickApproveButton();
  }

  @And("Click Xác nhận button")
  public void clickConfirmButton() {
    transfers.clickConfirmButton();
  }

  @And("Input PIN {string}")
  public void inputPIN(String otp) {
    transfers.inputPIN(otp);
  }
}
