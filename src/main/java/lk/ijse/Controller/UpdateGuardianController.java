package lk.ijse.Controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import lk.ijse.BO.BOFactory;
import lk.ijse.BO.Custom.EmployeeBO;
import lk.ijse.BO.Custom.GuardianBO;
import lk.ijse.dto.EmployeeDto;
import lk.ijse.dto.GuardianDto;
import org.controlsfx.control.Notifications;

import java.sql.SQLException;
import java.util.List;
import java.util.regex.Pattern;

public class UpdateGuardianController {
    @FXML
    private JFXComboBox<String> comboEmployee_id;

    @FXML
    private JFXComboBox<String> comboGuardian_id;

    @FXML
    private AnchorPane root;

    @FXML
    private JFXTextField txtContact;

    @FXML
    private JFXTextField txtName;

    GuardianBO guardianBO = (GuardianBO) BOFactory.getBoFactory().getBO(BOFactory.BOType.GUARDIAN);
    EmployeeBO employeeBO = (EmployeeBO) BOFactory.getBoFactory().getBO(BOFactory.BOType.EMPLOYEE);

    public void initialize(){
        setValue();
        setValueEmp();
    }
    private void setValue(){
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<GuardianDto> idList = guardianBO.getAllGuardian();

            for ( GuardianDto dto : idList) {
                obList.add(dto.getGuardian_id());
            }
            comboGuardian_id.setItems(obList);
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
    public void btnUpdateOnAction(ActionEvent actionEvent) {
        String id = (String) comboGuardian_id.getValue();
        String name = txtName.getText();
        String contact = txtContact.getText();
        String emp_id = (String) comboEmployee_id.getValue();

        try {
            if (!validateGuardian()){
                return;
            }
            var dto = new GuardianDto(id,name,contact,emp_id);
            boolean isUpdate = guardianBO.updateGuardian(dto);
            if (isUpdate){
                new Alert(Alert.AlertType.CONFIRMATION,"Guardian updated").show();
            }
        }catch (SQLException | ClassNotFoundException e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    private boolean validateGuardian() {
        boolean isValidate = true;
        boolean name = Pattern.matches("[A-Za-z]{5,}", txtName.getText());
        if (!name){
            showErrorNotification("Invalid Guardian Name", "The Guardian name you entered is invalid");
            isValidate = false;
        }
        boolean con = Pattern.matches("[0-9]{10,}",txtContact.getText());
        if (!con){
            showErrorNotification("Invalid Contact Number", "The contact number you entered is invalid");
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

    public void comboGuardian_idOnAction(ActionEvent actionEvent) {
        String id = (String) comboGuardian_id.getValue();
        try {
            GuardianDto dto = guardianBO.searchGuardian(id);
            txtName.setText(dto.getGuardian_name());
            txtContact.setText(dto.getGuardian_contact());
           // comboEmployee_id.setItems(dto.getEmployee_id());
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    private void setValueEmp(){
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
    public void comboEmployee_idOnAction(ActionEvent actionEvent) {
        String id =(String) comboEmployee_id.getValue();
        try {
            EmployeeDto dto = employeeBO.searchEmployee(id);
            //comboEmployee_id.setItems(dto.getId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
