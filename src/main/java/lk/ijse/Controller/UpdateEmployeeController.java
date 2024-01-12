package lk.ijse.Controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import lk.ijse.BO.BOFactory;
import lk.ijse.BO.Custom.EmployeeBO;
import lk.ijse.dto.EmployeeDto;
import org.controlsfx.control.Notifications;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.Pattern;

public class UpdateEmployeeController {

    public AnchorPane root;

    public JFXTextField txtName;

    public JFXTextField txtContact;
    public JFXTextField txtNic;
    public JFXTextField txtJob;
    public JFXComboBox<String> comboEmployee_id;

    public JFXTextField txtEmail;
    EmployeeBO employeeBO = (EmployeeBO) BOFactory.getBoFactory().getBO(BOFactory.BOType.EMPLOYEE);

    public void initialize() {
        setValue();
    }

    private void setValue() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<EmployeeDto> idList = employeeBO.getAllEmployee();

            for ( EmployeeDto dto : idList) {
                obList.add(dto.getId());
            }
            comboEmployee_id.setItems(obList);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        String id = comboEmployee_id.getValue();
        String name = txtName.getText();
        String contact = txtContact.getText();
        String nic = txtNic.getText();
        String job = txtJob.getText();
        String email = txtEmail.getText();


        try{
            if (!validateEmployee()){
                return;
            }
            var dto = new EmployeeDto(id,name,contact,nic,job,email);
            boolean isUpdate = employeeBO.updateEmployee(dto);

            if (isUpdate){
                new Alert(Alert.AlertType.CONFIRMATION,"Employee is updated").show();
                clearFields();
            }

        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean validateEmployee() {
        boolean isValidate = true;
        boolean name = Pattern.matches("[A-Za-z]{5,}", txtName.getText());
        if (!name){
            showErrorNotification("Invalid Customer Name", "The customer name you entered is invalid");
            isValidate = false;
        }
        boolean con = Pattern.matches("[0-9]{10,}",txtContact.getText());
        if (!con){
            showErrorNotification("Invalid Contact Number", "The contact number you entered is invalid");
            isValidate = false;
        }
        boolean NIC = Pattern.matches("^([0-9]{9}|[0-9]{12})$",txtNic.getText());
        if (!NIC){
            showErrorNotification("Invalid NIC", "The NUC Number you entered is invalid");
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
        comboEmployee_id.setValue("");
        txtName.setText("");
        txtContact.setText("");
        txtNic.setText("");
        txtJob.setText("");
        txtEmail.setText("");
    }
    public void comboEmployee_idOnAction(ActionEvent actionEvent) {
        String id = comboEmployee_id.getValue();
        try {
            EmployeeDto dto = employeeBO.searchEmployee(id);
            txtName.setText(dto.getName());
            txtContact.setText(dto.getContact());
            txtNic.setText(dto.getNic());
            txtJob.setText(dto.getJob());
            txtEmail.setText(dto.getEmail());
            System.out.println("Employee Search");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
