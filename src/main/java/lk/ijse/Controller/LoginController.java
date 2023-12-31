package lk.ijse.Controller;

import com.jfoenix.controls.JFXButton;

import com.jfoenix.controls.JFXPasswordField;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
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
import lk.ijse.BO.BOFactory;
import lk.ijse.BO.Custom.UserBO;
import lk.ijse.dto.UserDto;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Random;


public class LoginController {

    public AnchorPane root;
    public TextField txtUsername;
    public TextField txtPassword;
    public JFXButton btnSigning;
    public Label lblTime;

    public JFXPasswordField txtEmail;
    public Label lblEmail;
    public Label lblUserNameInvalied;
    public Label lblPasswordInvalied;
    public Hyperlink hlSendOtp;
    public TextField txtOtp;

    UserBO userBO = (UserBO) BOFactory.getBoFactory().getBO(BOFactory.BOType.USER);
    private void updateTime() {
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        String formattedTime = timeFormat.format(new Date());
        lblTime.setText(formattedTime);

         /*SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
         String formattedDate = dateFormat.format(new Date());
         lblDate.setText(formattedDate);*/
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
                try {
                    btnSigningOnAction(new ActionEvent());
                } catch (IOException | SQLException  e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public void btnSigningOnAction(ActionEvent actionEvent) throws IOException, SQLException, ClassNotFoundException {

        String username = txtUsername.getText();
        String email = txtEmail.getText();
        String otp = txtOtp.getText();
        String password = txtPassword.getText();

        HyperLinkSendOtpOnActionLog();
        List<UserDto> userDtoList = userBO.loginUserDetails();
        for (UserDto userDto:userDtoList) {
            if (userDto.getUser_name().equals(username)) {
                if (userDto.getEmail().equals(email)) {
                    if (userDto.getPassword().equals(password)) {
                        Parent rootNode = FXMLLoader.load(getClass().getResource("/view/dash_board_form.fxml"));
                        Scene scene = new Scene(rootNode);
                        Stage primaryStage = (Stage) root.getScene().getWindow();
                        primaryStage.setScene(scene);
                        primaryStage.centerOnScreen();
                        primaryStage.setTitle("Dashboard");
                    } else {
                        lblPasswordInvalied.setText("Invalid Password");
                    }

                }else {
                    lblEmail.setText("Invalid Email");
                }
            }else {
                lblUserNameInvalied.setText("Invalid Username");
            }
        }
    }
    public void btnSignupOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/register_form.fxml"));
        Stage stage = (Stage) root.getScene().getWindow();

        stage.setScene(new Scene(anchorPane));
        stage.centerOnScreen();


    }
    private void  email(){
        System.out.println("Start");


        generateOTP();

        LoginController.Mail mail = new Mail();
        mail.setMsg("Your OTP is: " + txtOtp);
        mail.setTo(txtEmail.getText());
        mail.setSubject("OTP for V Tec Motors(pvt).Ltd");

        Thread thread = new Thread(mail);
        thread.start();

    }
    private void generateOTP() {
        String generateOtp =  String.format("%06d", new Random().nextInt(1000000));
        checkOtp(generateOtp);
    }

    public boolean checkOtp(String generatedOtp) {
        String enteredOtp = txtOtp.getText();

        //enteredOtp = String.format("%06d", Integer.parseInt(enteredOtp));

        if (generatedOtp.equals(enteredOtp)) {
            new Alert(Alert.AlertType.CONFIRMATION, "OTP Verified").show();
            return true;
        } else {
            System.out.println("Invalid OTP. Please try again.");
            return false;
        }
    }

    public void HyperLinkSendOtpOnActionLog() {
        email();
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

