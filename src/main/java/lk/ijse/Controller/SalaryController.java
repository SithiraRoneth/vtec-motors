package lk.ijse.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;



import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.Session;
import jakarta.mail.Authenticator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.DAO.Custom.EmployeeDAO;
import lk.ijse.DAO.Custom.ExpenditureDAO;
import lk.ijse.DAO.Custom.SalaryDAO;
import lk.ijse.DAO.DAOFactory;
import lk.ijse.DB.DbConnection;
import lk.ijse.DAO.Impl.EmployeeDAOImpl;
import lk.ijse.DAO.Impl.ExpenditureDAOImpl;
import lk.ijse.DAO.Impl.SalaryDAOImpl;
import lk.ijse.dto.EmployeeDto;
import lk.ijse.dto.ExpenditureDto;
import lk.ijse.dto.SalaryDto;
import lk.ijse.dto.tm.SalaryTm;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;


import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Properties;

public class SalaryController {
    public Label lblFinalSalary;
    public Label lblEmpName;
    public TableColumn<?, ?> colId;
    public TableColumn<?, ?> colName;
    public JFXButton btnAdd;
    public Label lblEmail;

    @FXML
    private JFXComboBox<String> cmbEmpId;

    @FXML
    private JFXComboBox<Month> cmbMonth;

    @FXML
    private TableColumn<?, ?> colDiscount;

    @FXML
    private TableColumn<?, ?> colFinalSalary;

    @FXML
    private TableColumn<?, ?> colSalary;

    @FXML
    private TableColumn<?, ?> colVat;

    @FXML
    private AnchorPane root;

    @FXML
    private TableView<SalaryTm> tblSalary;

    @FXML
    private JFXTextField txtBonus;

    @FXML
    private JFXTextField txtE;

    @FXML
    private JFXTextField txtSalary;
    private String month;
    SalaryDAO salaryDAO = (SalaryDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.SALARY);
    ExpenditureDAO expenditureDAO = (ExpenditureDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.EXPENDITURE);
    EmployeeDAO employeeDAO = (EmployeeDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.EMPLOYEE);
    private ObservableList<SalaryTm> obList = FXCollections.observableArrayList();

    public void initialize(){
        setMonth();
        month = String.valueOf(cmbMonth.getValue());
        setCellValueFactory();
        loadEmployee();
    }

    private void loadEmployee() {
        ObservableList<String>obList = FXCollections.observableArrayList();
        try {
            List<EmployeeDto>dtoList = employeeDAO.getAll();
            for (EmployeeDto dto : dtoList){
                obList.add(dto.getId());
            }
            cmbEmpId.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        colDiscount.setCellValueFactory(new PropertyValueFactory<>("bonus"));
        colVat.setCellValueFactory(new PropertyValueFactory<>("etf"));
        colFinalSalary.setCellValueFactory(new PropertyValueFactory<>("finalSalary"));
    }

    public void setMonth() {
        ObservableList<Month> months = FXCollections.observableArrayList(Month.values());
        cmbMonth.setItems(months);
        cmbMonth.setPromptText("select month");
        tblSalary.refresh();

    }

    public void cmbEmpIdOnAction(ActionEvent actionEvent) {
        String id = cmbEmpId.getValue();

        try {
            EmployeeDto dto = employeeDAO.search(id);
            lblEmpName.setText(dto.getName());
            lblEmail.setText(dto.getEmail());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnAddOnAction(ActionEvent actionEvent) {
        String id = cmbEmpId.getValue();
        String name = lblEmpName.getText();
        double salary = Double.parseDouble(txtSalary.getText());
        double bonus = Double.parseDouble(txtBonus.getText());
        double etf = Double.parseDouble(txtE.getText());
        calFinalSalary();

        double finalSalary = Double.parseDouble(lblFinalSalary.getText());
        String month = String.valueOf(cmbMonth.getValue());

        try {
            var dto = new SalaryDto(id, name, salary, bonus, etf, finalSalary, month);
            boolean isSaved = salaryDAO.save(dto);
            if (isSaved) {
                email();
                new Alert(Alert.AlertType.CONFIRMATION, "Salary pay success").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        goExpenditure();
    }

    private void goExpenditure() {
        LocalDate currentDate = LocalDate.now();
        String dese = "Salary Payment";
        double amount = Double.parseDouble(lblFinalSalary.getText());
        int year = currentDate.getYear();
        String month = String.valueOf(cmbMonth.getValue());
        String localDate = currentDate.toString();

        var exDto = new ExpenditureDto(dese, amount, year, month, localDate);

        try {
            boolean isSuccess = expenditureDAO.save(exDto);
            if (isSuccess) {
                new Alert(Alert.AlertType.CONFIRMATION,"Expenditure added").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

        private void calFinalSalary () {
            try {
                double salary = Double.parseDouble(txtSalary.getText());
                double bonus = Double.parseDouble(txtBonus.getText());
                double etf = Double.parseDouble(txtE.getText());

                double finalSalary = (salary + bonus) - etf;
                lblFinalSalary.setText(String.valueOf(finalSalary));
            } catch (NumberFormatException e) {
                System.err.println("Invalid format salary");
                e.printStackTrace();
            }

        }

        public void cmbMonthOnAction (ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
            Month selectMonth = cmbMonth.getValue();
            obList.clear();
            if (selectMonth != null) {
                month = selectMonth.name();

            }
            List<SalaryTm> dtoList = salaryDAO.searchSalary(month);
            for (SalaryTm dto : dtoList) {
                obList.add(
                        new SalaryTm(
                                dto.getId(),
                                dto.getName(),
                                dto.getSalary(),
                                dto.getBonus(),
                                dto.getEtf(),
                                dto.getFinalSalary()
                        )
                );

            }
            tblSalary.setItems(obList);

        }
    public void btnPrintOnAction(ActionEvent actionEvent) throws JRException, SQLException {
        InputStream resourceAsStream = getClass().getResourceAsStream("/report/salary.jrxml");
        JasperDesign load = JRXmlLoader.load(resourceAsStream);
        JasperReport jasperReport = JasperCompileManager.compileReport(load);
        JasperPrint jasperPrint = JasperFillManager.fillReport(
                jasperReport,
                null,
                DbConnection.getInstance().getConnection()
        );

        JasperViewer.viewReport(jasperPrint, false);

    }

    private void email(){
        System.out.println("Start");
        Mail mail = new Mail();
        mail.setMsg("Your month salary payment successful"+lblFinalSalary.getText());
        mail.setTo(lblEmail.getText());
        mail.setSubject("V Tec Motors(pvt).Ltd");

        Thread thread = new Thread(mail);
        thread.start();
        new Alert(Alert.AlertType.CONFIRMATION,"Email sent");
        System.out.println("end");


    }

    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        root.getChildren().clear();
        root.getChildren().add(FXMLLoader.load(getClass().getResource("/view/employee_form.fxml")));
    }

    public static class Mail implements Runnable{
        private String msg;
        private String to;
        private String subject;
        public void setMsg(String msg) {
            this.msg = msg;
        }
        public void setTo(String to) {
            this.to = to;
        }
        public void setSubject(String subject) {
            this.subject = subject;
        }

        public boolean outMail() throws MessagingException {
            String from = "sithiraroneth@gmail.com"; //sender's email address
            String host = "localhost";

            Properties properties = new Properties();
            properties.put("mail.smtp.auth", "true");
            properties.put("mail.smtp.starttls.enable", "true");
            properties.put("mail.smtp.host", "smtp.gmail.com");
            properties.put("mail.smtp.port", 587);

            Session session = Session.getInstance(properties, new Authenticator() {


                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("sithiraroneth@gmail.com", "gboj ljqu knde oiyc");  // email and password
                }
            });

            MimeMessage mimeMessage = new MimeMessage(session);
            mimeMessage.setFrom(new InternetAddress(from));
            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            mimeMessage.setSubject(this.subject);
            mimeMessage.setText(this.msg);
            Transport.send(mimeMessage);
            return true;
        }
        public void run() {
            if (msg != null) {
                try {
                    outMail();
                } catch (MessagingException e) {
                    throw new RuntimeException(e);
                }
            } else {
                System.out.println("not sent. empty msg!");
            }
        }
    }

}

