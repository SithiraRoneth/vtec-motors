package lk.ijse.Controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import lk.ijse.BO.BOFactory;
import lk.ijse.BO.Custom.EmployeeBO;
import lk.ijse.BO.Custom.GuardianBO;
import lk.ijse.dto.EmployeeDto;
import lk.ijse.dto.GuardianDto;
import org.controlsfx.control.Notifications;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

public class AddGuardianController {
    public AnchorPane root;
    public JFXTextField txtGuar_ID;
    public JFXTextField txtName;
    public JFXTextField txtContact;
    public JFXComboBox comboEmp_Id;
    public Label lblGuardianId;
    GuardianBO guardianBO = (GuardianBO) BOFactory.getBoFactory().getBO(BOFactory.BOType.GUARDIAN);
    EmployeeBO employeeBO = (EmployeeBO) BOFactory.getBoFactory().getBO(BOFactory.BOType.EMPLOYEE);

    public void initialize() {
        loadEmp();
        generateNextGuardianId();
    }

    private void generateNextGuardianId() {
        try {
            String guardianId = guardianBO.generateNextGuardianId();
            lblGuardianId.setText(guardianId);
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void btnAddEmpOnAction(ActionEvent actionEvent) {
        String id = lblGuardianId.getText();
        String name = txtName.getText();
        String contact = txtContact.getText();
        String emp_id = (String) comboEmp_Id.getValue();


        try {
            if (!validateGuardian()){
                return;
            }
            var dto = new GuardianDto(id,name,contact,emp_id);
            boolean isSaved = guardianBO.saveGuardian(dto);

            if (isSaved){
                ButtonType yes = new ButtonType("yes", ButtonBar.ButtonData.OK_DONE);
               // ButtonType no = new ButtonType("no", ButtonBar.ButtonData.CANCEL_CLOSE);

                Optional<ButtonType> type = new Alert(Alert.AlertType.CONFIRMATION,"Guardian added Successfully!! Do you want add another one ", yes).showAndWait();
                clearFields();

              /*  if (type.orElse(yes) == no) {
                    Stage stage = (Stage) this.root.getScene().getWindow();
                    stage.close();
                }*/
            }
        }catch (SQLException | ClassNotFoundException e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
        clearFields();
        generateNextGuardianId();
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


    public void loadEmp() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<EmployeeDto> idList = employeeBO.getAllEmployee();

            for (EmployeeDto dto: idList) {
                obList.add(dto.getId());
            }

            comboEmp_Id.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    private void clearFields() {
        //txtGuar_ID.setText("");
        txtName.setText("");
        txtContact.setText("");
        comboEmp_Id.setValue("");
    }
}
