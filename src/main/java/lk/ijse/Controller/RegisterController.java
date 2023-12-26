package lk.ijse.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.DAO.Custom.UserDAO;
import lk.ijse.DAO.DAOFactory;
import lk.ijse.DAO.Impl.UserDAOImpl;
import lk.ijse.dto.UserDto;
import org.controlsfx.control.Notifications;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.Random;
import java.util.regex.Pattern;

public class RegisterController {
    public JFXTextField txtUsername;
    public JFXTextField txtPassword;
    public JFXButton btnSignup;
    public Label lblTime;

    public AnchorPane root;

    public JFXTextField txtEmail;
    public Hyperlink hlSendOtp;
    public TextField txtOtp;
    public Label lblOtp;
    UserDAO userDAO = (UserDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.USER);

    private void updateTime() {
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        String formattedTime = timeFormat.format(new Date());
        lblTime.setText(formattedTime);

    }

    public void initialize() {
        updateTime();

        Timeline timeline = new Timeline(new KeyFrame(
                Duration.seconds(1),
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        updateTime();
                    }
                }
        ));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        root.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ENTER) {
                btnSignupOnAction(new ActionEvent());
            }
        });
    }
    public void btnSignupOnAction(ActionEvent actionEvent) {
        String username = txtUsername.getText();
        String email = txtEmail.getText();
        String password = txtPassword.getText();
        String otp = txtOtp.getText();

        try {
            if (!validateUsername()){
                return;
            }
            var dto = new UserDto(username,email,password);

            HyperLinkSendOtpOnAction();

            boolean isReg = userDAO.save(dto);
            if (isReg){
                new Alert(Alert.AlertType.CONFIRMATION,"Registered Successfully").show();
                AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/login_form.fxml"));
                Stage stage = (Stage) root.getScene().getWindow();

                stage.setScene(new Scene(anchorPane));
                stage.centerOnScreen();
            }
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    private boolean validateUsername() {

        boolean isValidate = true;
        boolean username = Pattern.matches("[A-Za-z]{1,}", txtUsername.getText());
        if (!username){
            showErrorNotification("Invalid username", "The username you entered is invalid");
            isValidate = false;
        }
        boolean password = Pattern.matches("[0-9]{5,}", txtPassword.getText());
        if (!password){
            showErrorNotification("Invalid password", "The password you entered is invalid");
            isValidate = false;
        }
        boolean email = Pattern.matches("^(.+)@(.+)$",txtEmail.getText());
        if (!email){
            showErrorNotification("Invalid email","The email you entered is invalid");
            isValidate = false;
        }
        return isValidate;
    }

    private void showErrorNotification(String title, String text) {
        Notifications.create()
                .title(title)
                .text(text)
                .showError();
    }

    public void hyperLinkOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/login_form.fxml"));
        Stage stage = (Stage) root.getScene().getWindow();

        stage.setScene(new Scene(anchorPane));
        stage.centerOnScreen();
    }

    public void HyperLinkSendOtpOnAction() {
        email();
    }

    private void  email(){
        System.out.println("Start");

        generateOTP();

        Mail mail = new Mail();
        mail.setMsg("Your OTP is: " + txtOtp);
        mail.setTo(txtEmail.getText());
        mail.setSubject("OTP for V Tec Motors(pvt).Ltd");

        Thread thread = new Thread(mail);
        thread.start();

    }
    private void generateOTP() {
        String generateOtp =  String.format("%06d", new Random().nextInt(1000000));
        System.out.println(generateOtp);
        checkOtp(generateOtp);
    }

    public boolean checkOtp(String generatedOtp) {
        String enteredOtp = txtOtp.getText();

        if (generatedOtp.equals(enteredOtp)) {
            new Alert(Alert.AlertType.CONFIRMATION, "OTP Verified").show();
            return true;
        } else {
            System.out.println("Invalid OTP. Please try again.");
            return false;
        }
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
            String from = "sithiraroneth@gmail.com";
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