package vn.vietinbank.efast.steps.web.newefast.approve_transaction_checker;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import net.serenitybdd.annotations.Steps;
import org.junit.Assert;
import screens.web.approve_transaction_checker.Transfers;
import vn.vietinbank.efast.screens.web.newefast.transfer_money.interbank_transfer.BulkTransfer;
import vn.vietinbank.efast.screens.web.newefast.transfer_money.interbank_transfer.InterbankTransfer;
import vn.vietinbank.efast.steps.web.newefast.transfer_money.TransferMoneyStep;

public class TransfersStep {
  @Steps Transfers transfers;

  InterbankTransfer interbankTransfer = new InterbankTransfer();

  @And("^Nhập nội dung chuyển tiền của giao dịch vừa tạo$")
  @And("^Input transaction content just transacted$")
  public void selectNormalTransfer() {
    transfers.inputTransactionContent(TransferMoneyStep.content_maker);
    transfers.clickSearchButton();
  }

  @And("^Click mã giao dịch vừa tạo$")
  @And("^Click transaction number just transacted$")
  public void clickTransactionNumber() {
    transfers.clickTransactionNumber(InterbankTransfer.idTransaction);
  }

  @And("^Click Phê duyệt button$")
  @And("^Click Approve button$")
  public void clickApproveButton() {
    transfers.clickApproveButton();
  }

  @And("^Click Phê duyệt button trong Đặt lịch chuyển tiền$")
  @And("^Click Approve button in Schedule Transfer$")
  public void clickApproveButtonInScheduleTransfer() {
    transfers.clickApproveButtonInSchedule();
  }

  @And("^Click Reject button$")
  public void clickRejectButton() {
    transfers.clickRejectButton();
  }

  @And("^Click Chấp nhận button$")
  @And("^Click Accept button$")
  public void clickAcceptButton() {
    transfers.clickAcceptButton();
  }

  @And("^Chọn phương thức xác thực là \"([^\"]*)\"$")
  @And("^Select Verification method is \"([^\"]*)\"$")
  public void clickRejectButton(String method) {
    if (method.equals("VietinBank OTP")) {
      transfers.selectVietinbankOTP();
    } else {
      transfers.selectByPassOTP();
    }
  }

  @And("^Xác nhận Tổng số tiền giao dịch \"([^\"]*)\"$")
  @And("^Verify Total transaction amount \"([^\"]*)\"$")
  public void verifyTotalTransactionAmount(String amount) {
    transfers.verifyTotalTransactionAmount(amount);
  }

  @Then(
      "^Xác nhận thông tin trong màn hình checker hiển thị chính xác với tài khoản chuyển \"([^\"]*)\", \"([^\"]*)\" \"([^\"]*)\", số tiền \"([^\"]*)\" và nội dung \"([^\"]*)\"$")
  @Then(
      "^Verify information in checker screen display correct is debit account \"([^\"]*)\", \"([^\"]*)\" \"([^\"]*)\", amount \"([^\"]*)\" and content \"([^\"]*)\"$")
  public void verifyInformationInCheckerDisplayCorrectIsDebitAccountAmountAndContent(
      String debitAccount,
      String typeTransfer,
      String accountNumber,
      String amount,
      String content) {
    if (amount == null) {
      transfers.getInformationConfirmInCheckerScreen(
          InterbankTransfer.idTransaction,
          debitAccount,
          typeTransfer,
          accountNumber,
          InterbankTransfer.bankNameInput,
          TransferMoneyStep.amount,
          content);
    } else {
      transfers.getInformationConfirmInCheckerScreen(
          InterbankTransfer.idTransaction,
          debitAccount,
          typeTransfer,
          accountNumber,
          InterbankTransfer.bankNameInput,
          amount,
          content);
    }
  }

  @And("^Xác nhận thông báo \"([^\"]*)\" hiển thị")
  @And("^Verify alert \"([^\"]*)\" display$")
  public void verifyAlertSuccess(String alert) {
    if (alert.equals("approve transaction successfully")) {
      transfers.verifyAlertSuccess(InterbankTransfer.idTransaction);
    } else if (alert.equals("transaction insufficient_balance")) {
      transfers.verifyAlertInsufficientBalance(TransferMoneyStep.debitAccount);
    } else if (alert.equals("exceeding maximum transfer limit")) {
      interbankTransfer.verifyAlertMaximumTransferLimit();
    } else if (alert.equals("exceeding maximum transfer limit normal")) {
      interbankTransfer.verifyAlertMaximumTransferLimitNormal();
    } else if (alert.equals("amount less than fee amount")) {
      interbankTransfer.verifyAlertMessAmountLessThanFeeAmount();
    } else if (alert.equals("approve transaction successfully with internal changes")) {
      transfers.verifyAlertSuccessWithInternalChanges(InterbankTransfer.idTransaction);
    } else if (alert.equals("transfer amount is lower than the minimum transaction limit")) {
      interbankTransfer.verifyAlertMinLimit();
    } else if (alert.equals("approved successfully")) {
      transfers.verifyAlertApprovedSuccess(InterbankTransfer.idTransaction);
    } else if (alert.equals("exceeding maximum transfer limit by account"))
      interbankTransfer.verifyAlertMaximumTransferLimitByAccount();
    else if (alert.equals("approve transaction successfully prod")) {
      transfers.verifyAlertSuccessProd(InterbankTransfer.idTransaction);
    } else if (alert.equals("approve payment success")) {
      transfers.verifyAlertSuccessPayment();
    } else if (alert.equals("reject transaction")) {
      transfers.rejectTransaction(InterbankTransfer.idTransaction);
    }else if (alert.equals("reject transaction payment")) {
      transfers.rejectTransactionPayment();
    }else if (alert.equals("approve transaction VETC successfully")) {
      transfers.rejectTransactionPayment();
    }
  }

  @And("^Verify Total transaction no input amount$")
  public void verifyTotalTransactionNoInputAmount() {
    transfers.verifyTotalTransactionAmount(TransferMoneyStep.amount);
  }

  @Then(
      "Xác nhận thông tin trong Đặt lịch chuyển tiền checker hiển thị chính xác với số tài khoản chuyển {string}, {string}, số tiền {string}")
  @Then(
      "Verify information in schedule transfer checker screen display correct is debit account {string}, {string}, amount {string}")
  public void verifyInformationInScheduleTransferCheckerScreenDisplayCorrectIsDebitAccountAmount(
      String debitAccount, String numberAccount, String amount) {
    System.out.println(InterbankTransfer.idTransaction);
    transfers.verifyInformationConfirmInScheduleCheckerScreen(
        InterbankTransfer.idTransaction,
        debitAccount,
        numberAccount,
        amount,
        InterbankTransfer.startDate,
        InterbankTransfer.endDate);
  }

  @And("^Double click Chấp nhận button$")
  public void doubleClickAcceptButton() {
    transfers.doubleClickAcceptButton();
  }

  @And("^Xác nhận màn hình OTP đếm thời gian hiển thị$")
  public void verifyOTPTimeDisplay() {
    transfers.verifyOTPTimeDisplay();
  }

  @And("^Click Accept button double times$")
  public void clickAcceptButtonDoubleTimes() {
    transfers.clickAcceptButtonDoubleTimes();
  }
}
