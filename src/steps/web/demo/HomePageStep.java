package steps.web.demo;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import screens.web.HomePage;
import vn.vietinbank.efast.utils.Constant;
import vn.vietinbank.efast.utils.component.*;

public class HomePageStep {

  HomePage homePage = new HomePage();
  TransferMoney transferMoney = new TransferMoney();
  Deposit deposit = new Deposit();
  CollectPayOnBehalf collectPayOnBehalf = new CollectPayOnBehalf();
  NationalBudgetPaymentService nationalBudgetPaymentService = new NationalBudgetPaymentService();
  Home homeLanguage = new Home();
  Payment payment = new Payment();
  Account account = new Account();
  MoreServices moreServices = new MoreServices();
  Bonds bonds = new Bonds();
  TransactionManagement transactionManagement = new TransactionManagement();
  Utilities utilities = new Utilities();
  Report report = new Report();
  UserAdmin userAdmin = new UserAdmin();

  @Then("^Trang chủ hiển thị$")
  @Then("^Show Title HomePage$")
  public void showTitleHomePage() {
    homePage.showTitleBigPage(homeLanguage.dashboard);
    homePage.closePopupMarketingAds();
  }

  @And("^Click left menu \"([^\"]*)\"$")
  public void ClickMenu(String menuPath) {
    System.out.println(menuPath);
    homePage.ChooseLeftMenu(menuPath);
  }

  @And("^Chọn dịch vụ chuyển tiền trong menu trái$")
  @And("^Select Transfer money in Left Menu$")
  public void selectTransferMoney() {
    homePage.getNameCompany();
    homePage.selectMenu(transferMoney.transfer_money);
  }

  @And("^Chọn Chuyển tiền liên ngân hàng trong menu trái$")
  @And("^Select Interbank transfer in Left Menu$")
  public void selectInterbankTranser() {
    homePage.selectMenu(transferMoney.interbank_transfer);
  }

  @And("^Chọn Duyệt giao dịch trong menu trái$")
  @And("^Select Approve transaction in Left Menu$")
  public void selectApproveTransaction() {
    homePage.selectMenu(homeLanguage.approve_transaction);
  }

  @And("^Chọn Chuyển tiền trong menu trái$")
  @And("^Select Transfer in Left Menu$")
  public void selectTransfer() {
    homePage.selectMenu(homeLanguage.transfers);
  }

  @And("Select Bulk transfer in Left Menu")
  public void selectBulkTransferInLeftMenu() {
    homePage.selectMenu(transferMoney.bulk_transfer);
  }

  @And("^Select left menu Term Deposit$")
  public void selectTermdeposit() {
    homePage.ChooseLeftMenu(deposit.Term_deposit);
  }

  @When("^Nhấn vào thu chi hộ tại menu trái màn hình$")
  public void selectCollectPayOnBehalf() {
    homePage.selectMenu(collectPayOnBehalf.collect_pay_on_behalf);
  }

  @When("^Chọn nộp ngân sách nhà nước ở checker$")
  @When("^Select left menu Tax Payment$")
  public void selectTaxPayment() {
    homePage.selectMenu(nationalBudgetPaymentService.tax_payment);
  }

  @When("^Chọn nộp ngân sách nhà nước ở menu trái$")
  @When("^Select left menu national budget payment service$")
  public void selectNationalBudgetPaymentService() {
    homePage.selectMenu(nationalBudgetPaymentService.national_budget_payment_service);
  }

  @When("^Chọn nộp thuế nội địa$")
  @And("^Select child menu domestic tax payment$")
  public void selectDomesticTaxPayment() {
    homePage.selectMenu(nationalBudgetPaymentService.domestic_tax_payment);
  }

  @And("^Chọn nộp thuế hạ tầng ở menu trái$")
  @And("^Select child menu infrastructure fee payment$")
  public void selectInfrastructureFeePayment() {
    homePage.selectMenu(nationalBudgetPaymentService.infrastructure_fee_payment);
  }

  @And("^Chọn nộp thuế hải quan ở menu trái$")
  @And("^Select child menu customs duties payment$")
  public void selectCustomsDutiesPayment() {
    homePage.selectMenu(nationalBudgetPaymentService.customs_duties_payment);
  }

  @And("^Chọn chi lương tại menu trái$")
  public void selectPaymentSalary() {
    homePage.selectMenu(collectPayOnBehalf.salary_payment);
  }

  @And("^Chọn Chuyển tiền trong Vietinbank trong menu trái$")
  @And("^Select Transfer within Vietinbank in Left Menu$")
  public void selectTransferWithinVietinbank() {
    homePage.selectMenu(transferMoney.transfer_within_VietinBank);
  }

  @And("^Chọn Lệnh chi trong menu trái$")
  @And("^Select Payment order in Left Menu$")
  public void selectPaymentOrder() {
    homePage.selectMenu(transferMoney.payment_order);
  }

  @And("^Chọn Dịch vụ thanh toán -> Đăng ký trích nợ tự động trong menu trái$")
  @And("^Select Payment -> Auto debit in Left Menu$")
  public void selectAutoDebit() {
    homePage.selectMenu(payment.payment);
    homePage.selectMenu(payment.auto_debit);
  }

  @And("Chọn Dịch vụ thanh toán trong menu trái")
  @And("Select Payment in Left Menu")
  public void selectPaymentInLeftMenu() {
    homePage.selectMenu(homeLanguage.payment);
  }

  @And("Chọn Đăng ký trích nợ tự động trong menu trái")
  @And("Select AutoDebit in Left Menu")
  public void selectAutoDebitInLeftMenu() {
    homePage.selectTab(homeLanguage.auto_debit);
  }

  @And("^Chọn Thanh toán -> Thanh toán hóa đơn trong menu trái$")
  @And("^Select Payment -> Pay Bills in Left Menu$")
  public void selectPayBills() {
    homePage.selectMenu(payment.payment);
    homePage.selectMenu(payment.pay_bills);
  }

  @And("^Chọn Thanh toán -> Nộp BHXH trong menu trái$")
  @And("^Select Payment -> Social Insurance in Left Menu$")
  public void selectSocialInsurance() {
    homePage.selectMenu(payment.payment);
    homePage.selectMenu(payment.social_insurance);
  }

  @And("^Chọn Thanh toán -> Nộp phí công đoàn trong menu trái$")
  @And("^Select Payment -> Trade Union Fee in Left Menu$")
  public void selectTradeUnionFee() {
    homePage.selectMenu(payment.payment);
    homePage.selectMenu(payment.trade_union_fee);
  }

  @And("Chọn Nộp phí công đoàn trong menu trái")
  @And("Select Trade Union Fee in Left Menu")
  public void selectTradeUnionFeeInLeftMenu() {
    homePage.selectTab(payment.trade_union_fee);
  }

  @And("Chọn Thanh toán hóa đơn")
  @And("Select PayBills in Left Menu")
  public void selectPayBillInLeftMenu() {
    homePage.selectTab(homeLanguage.pay_bills);
  }

  @And("Select Social Insurance in Left Menu")
  public void selectSocialInsuranceInLeftMenu() {
    homePage.selectTab(homeLanguage.social_insurance);
  }

  @And("^Chọn Đặt lịch chuyển tiền định kỳ trong menu trái$")
  @And("^Select Schedule transfer in Left Menu$")
  public void selectScheduletransfer() {
    homePage.selectMenu(transferMoney.schedule_transfer);
  }

  @And("^Chọn tab Đặt lịch chuyển tiền trong menu trái$")
  @And("^Select Schedule transfer tab in Left Menu$")
  public void selectScheduletransferTab() {
    homePage.selectTab(transferMoney.schedule_transfer);
  }

  @And("^Chọn account ở menu trái$")
  @And("^Select Account in Left Menu$")
  public void selectAccount() {
    homePage.selectMenu(account.account);
  }

  @And("^Select Bonds in Left Menu$")
  public void selectBonds() {
    homePage.selectMenu(bonds.bonds);
  }

  @And("^Select Bond List in Left Menu$")
  public void selectBondList() {
    homePage.selectMenu(bonds.bond_list);
  }

  @And("^Select Account List in Left Menu$")
  public void selectAccountList() {
    homePage.selectMenu(account.account_list);
  }

  @And("^Select create QR in Left Menu$")
  public void selectCreateQRCode() {
    homePage.selectMenu(account.qr_code);
  }

  @And("^Select Payment Tab$")
  public void selectPaymentTab() {
    homePage.selectTabScrollUp(account.payment);
  }

  @And("^Select Deposit Tab$")
  public void selectDepositTab() {
    homePage.selectTabScrollUp(account.deposits);
  }

  @And("^Select Loan Tab$")
  public void selectLoanTab() {
    homePage.selectTabScrollUp(account.loan);
  }

  @And("^Chọn Chuyển tiền chứng khoán trong menu trái$")
  @And("^Select Securities Transfer in Left Menu$")
  public void selectSecuritiesTransfer() {
    homePage.selectMenu(transferMoney.securities_transfer);
  }

  @And("^Chọn Nộp thuế theo file trong menu trái$")
  @And("Select child menu bulk tax payment")
  public void selectChildMenuBulkTaxPayment() {
    homePage.selectMenu(nationalBudgetPaymentService.bulk_tax_payment);
  }

  @And("^Select Account outsite VietinBank Tab$")
  public void selectAccountOutsiteVietinBank() {
    homePage.selectTab(account.account_outsite_vietinbank);
  }

  @And("^Chọn Thanh toán -> Nộp phí không dừng bên menu trái$")
  @And("^Select Payment -> Nop Phi Khong Dung in Left Menu$")
  public void selectVETC() {
    homePage.selectMenu(payment.payment);
    homePage.selectMenu(payment.pay_toll_fees);
  }

  @And("Chọn tab Thu phí không dừng")
  @And("Select Pay toll fees Tab")
  public void selectPayTollFeesInLeftMenu() {
    homePage.selectTab(homeLanguage.pay_toll_fees);
  }

  @And("Chọn tab Chuyển tiền chứng khoán")
  @And("Select Securities transfer Tab")
  public void selectSecuritiesTransferTab() {
    homePage.selectTab(homeLanguage.securities_transfer);
  }

  @And("^Chọn tạo tên riêng tài khoản bên menu trái$")
  public void selectCreateYourAccount() {
    homePage.selectMenu(account.create_your_own_account_name);
  }

  @When("Select left menu transaction management")
  public void selectLeftMenuTransactionManagement() {
    homePage.selectMenu(transactionManagement.transactionManagementMenu);
  }

  @And("Select child menu transaction statistics")
  public void selectChildMenuTransactionStatistics() {
    homePage.selectMenu(transactionManagement.transactionStatistics);
  }

  @And("^Chọn Tiện tích bên menu trái$")
  public void selectUtilities() {
    homePage.selectMenu(utilities.utilities);
  }

  @And("^Chọn Đăng ký nhận chứng từ có ký số của Ngân hàng bên menu trái$")
  public void selectRegisterDigital() {
    homePage.selectMenu(utilities.register_for_digitally_signed_documents);
  }

  @And("^Trang chủ hiển thị đầy đủ thông tin$")
  public void TheDashBoardDisplaySuccess() {
    homePage.verifyDashBoardDisplaySuccess();
  }

  @And("^Chọn Tab tài khoản thành viên$")
  public void selectMemberCompanyTab() {
    homePage.selectTab(account.member_account);
  }

  @And("^Chọn Sao kê tài khoản trong menu trái$")
  @And("^Select Account Statement in Left Menu$")
  public void selectAccountStatement() {
    homePage.selectMenu(account.account_statement);
  }

  @And("^Chọn Dịch vụ khác -> Tra soát trong menu trái$")
  @And("^Select More Services -> Tracing in Left Menu$")
  public void selectMoreServices() {
    homePage.selectMenu(moreServices.more_services);
    homePage.selectMenu(moreServices.tracing);
  }

  @And("^Chọn Dịch vụ khác trong menu trái$")
  @And("^Select More services in Left Menu$")
  public void selectMoreSerice() {
    homePage.selectMenu(moreServices.more_services);
  }

  @And("Chọn tab Tra soát")
  @And("Select Tracing Tab")
  public void selectTracingTab() {
    homePage.selectTab(moreServices.tracing);
  }

  @And("Select Send Document Fast Tab")
  public void selectSendDocumentFastTab() {
    homePage.selectTab(moreServices.send_document_fast);
  }

  @And("^Chọn Dịch vụ khác -> Gửi chứng từ nhanh trong menu trái$")
  @And("^Select More Services -> Send Document Fast in Left Menu$")
  public void selectSendDocumentFast() {
    homePage.selectMenu(moreServices.more_services);
    homePage.selectMenu(moreServices.send_document_fast);
  }

  @And("^Chọn Báo cáo -> Tra cứu chứng từ ký số trong menu trái$")
  @And("^Select Report -> Inquire Digitally Signed Documents in Left Menu$")
  public void selectInquireDigitallySignedDocuments() {
    homePage.selectMenu(report.report);
    homePage.selectMenu(report.inquire_digitally_signed_documents);
  }

  @And("^Chọn Quản trị người dùng bên menu trái -> Chọn Đăng ký chữ ký số$")
  public void selectUserAdmin() {
    homePage.selectMenu(userAdmin.user_admin);
    homePage.selectMenu(userAdmin.digital_signature_registration);
  }

  @And("Lấy data từ bảng {string} và testcase ID là {string}")
  public void selectDataFromTable(String table_name, String testcase_id) {
    Constant.QUERY_GET_DATABASE =
        String.format(Constant.QUERY_GET_DATABASE, table_name, testcase_id);
  }
}
