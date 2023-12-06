package lk.ijse.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.Model.EmployeeModel;
import lk.ijse.dto.EmployeeDto;
import org.controlsfx.control.Notifications;


import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;
import java.util.regex.Pattern;

public class AddEmployeeController {
   // public JFXTextField txtEmployee_id;
    public JFXTextField txtName;
    public JFXTextField txtContact;
    public JFXTextField txtNic;
    public JFXTextField txtJob;
    public AnchorPane root;
    public Label lblEmployeeId;
    public JFXButton add;
    public JFXTextField txtEmail;

    EmployeeController employeeController=null;

    private EmployeeModel employeeModel = new EmployeeModel();

    public void initialize(){
        generateNextEmployeeId();

        root.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ENTER) {
                btnAddEmpOnAction(new ActionEvent());
            }
        });
    }

    private void generateNextEmployeeId() {
        try {
            String employeeId = employeeModel.generateNextEmployeeId();
            lblEmployeeId.setText(employeeId);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void btnAddEmpOnAction(ActionEvent actionEvent) {
        String id = lblEmployeeId.getText();
        String name = txtName.getText();
        String contact = txtContact.getText();
        String nic = txtNic.getText();
        String job = txtJob.getText();
        String email = txtEmail.getText();

        try {
            if (!validateEmployee()){
                return;
            }

            clearFields();
            var dto = new EmployeeDto(id,name,contact,nic,job,email);
            boolean isSaved = employeeModel.saveEmployee(dto);


            if (isSaved){
                ButtonType yes = new ButtonType("yes", ButtonBar.ButtonData.OK_DONE);

                Optional<ButtonType> type = new Alert(Alert.AlertType.CONFIRMATION,"Employee added Successfully!! Do you want add another one ", yes).showAndWait();
                clearFields();

            }
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
        clearFields();
        generateNextEmployeeId();
    }
    private boolean validateEmployee() {
        boolean isValidate = true;
        boolean name = Pattern.matches("[A-Za-z]{5,}", txtName.getText());
        if (!name){
            showErrorNotification("Invalid Employee Name", "The Employee name you entered is invalid");
            isValidate = false;
        }
        boolean con = Pattern.matches("[0-9]{10,}",txtContact.getText());
        if (!con){
            showErrorNotification("Invalid Contact Number", "The contact number you entered is invalid");
            isValidate = false;
        }
        boolean NIC = Pattern.matches("^([0-9]{9}|[0-9]{12})$",txtNic.getText());
        if (!NIC){
            showErrorNotification("Invalid NIC", "The NIC Number you entered is invalid");
            isValidate = false;

        }
        boolean Job = Pattern.matches("[A-Za-z]{5,}",txtJob.getText());
        if (!Job){
            showErrorNotification("Invalid job type", "The job type you entered is invalid");
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

    private void clearFields() {
        txtName.setText("");
        txtContact.setText("");
        txtNic.setText("");
        txtJob.setText("");
        txtEmail.setText("");
    }

    public void setController(EmployeeController employeeController) {
        this.employeeController=employeeController;
    }
}
