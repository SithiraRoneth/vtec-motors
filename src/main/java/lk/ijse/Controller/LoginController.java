package lk.ijse.Controller;

import com.jfoenix.controls.JFXButton;

import com.jfoenix.controls.JFXPasswordField;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.DB.DbConnection;
//import lk.ijse.Mail.Mail;
import lk.ijse.Model.UserModel;
import lk.ijse.dto.UserDto;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;


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

    private UserModel userModel = new UserModel();
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
                }
            }
        });
    }

    public void btnSigningOnAction(ActionEvent actionEvent) throws IOException, SQLException {

        String username = txtUsername.getText();
        String email = txtEmail.getText();
        String password = txtPassword.getText();

        List<UserDto> userDtoList = userModel.loginUser();
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

    private void clearFields() {
        txtUsername.setText("");
        txtEmail.setText("");
        txtPassword.setText("");

    }

    public void btnSignupOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/register_form.fxml"));
        Stage stage = (Stage) root.getScene().getWindow();

        stage.setScene(new Scene(anchorPane));
        stage.centerOnScreen();


    }

    /*public void mailSend( String mail) throws MessagingException {
        Properties properties = new Properties();

        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", 587);

        String myEmail = "peirisroneth@gmail.com";
        String password = "mmmw tzmb hkae gakf";

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myEmail,password);
            }
        });

        Message message = prepareMessage(session,myEmail,mail);
        if(message!=null){
            new Alert(Alert.AlertType.CONFIRMATION,"Send Email Successfully").show();
        }else {
            new Alert(Alert.AlertType.WARNING,"Please Enter Recipient's Email Address").show();
        }
        Transport.send(message);
    }

    private static Message prepareMessage(Session session, String myAccountEmail, String recepient) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myAccountEmail));
            message.setRecipients(Message.RecipientType.TO , new InternetAddress[]{
                    new InternetAddress(recepient)
            });

            message.setSubject("Login Successfully Confirm");
            message.setText("Login Successfully to the system!");
            return message;
        }catch (Exception e){
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE,null,e);
        }
        return null;
    }
*/

}

