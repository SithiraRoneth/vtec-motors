package lk.ijse.Controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import lk.ijse.Model.EmployeeModel;
import lk.ijse.Model.GuardianModel;
import lk.ijse.Model.VehicleModel;
import lk.ijse.dto.EmployeeDto;
import lk.ijse.dto.GuardianDto;
import lk.ijse.dto.VehicleDto;
import org.controlsfx.control.Notifications;

import java.sql.SQLException;
import java.util.List;
import java.util.regex.Pattern;

public class UpdateVehicleController {

    public AnchorPane root;
    public JFXTextField txtVehicleType;
    public JFXComboBox comboVehicle_id;
    public JFXComboBox comboGuardian_id;

    private VehicleModel vehicleModel = new VehicleModel();
    private GuardianModel guardianModel = new GuardianModel();

    public void initialize(){
        setValue();
        setValueGuardian();
    }
    private void setValue(){
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<VehicleDto> idList = vehicleModel.getAllVehicles();

            for ( VehicleDto dto : idList) {
                obList.add(dto.getVehicle_id());
            }
            comboVehicle_id.setItems(obList);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
    public void btnUpdateOnAction(ActionEvent actionEvent) {
        String id = (String) comboVehicle_id.getValue();
        String vehicle_type = txtVehicleType.getText();
        String guardian_id = (String) comboGuardian_id.getValue();



        try{
            if (!validateVehicle()){
                return;
            }
            var dto = new VehicleDto(id,vehicle_type,guardian_id);
            boolean isUpdate = vehicleModel.updateVehicle(dto);
            if (isUpdate){
                new Alert(Alert.AlertType.CONFIRMATION,"Vehicle updated").show();
                clearFields();
            }
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    private boolean validateVehicle() {
        boolean isValidate = true;
        boolean type = Pattern.matches("[A-Za-z]{3,}", txtVehicleType.getText());
        if (!type){
            showErrorNotification("Invalid Vehicle type", "The Vehicle type you entered is invalid");
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
        comboVehicle_id.setValue("");
        txtVehicleType.setText("");
        comboGuardian_id.setValue("");
    }
    private void setValueGuardian(){
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<GuardianDto> idList = guardianModel.GetAllGuardian();

            for ( GuardianDto dto : idList) {
                obList.add(dto.getGuardian_id());
            }
            comboGuardian_id.setItems(obList);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
    public void comboGuardian_idOnAction(ActionEvent actionEvent) {
        String id = (String) comboGuardian_id.getValue();

        try{
            GuardianDto dto = guardianModel.searchGuardian(id);
           // comboGuardian_id.setValue(dto.getGuardian_id());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void comboVehicle_idOnAction(ActionEvent actionEvent) {
        String id = (String) comboVehicle_id.getValue();

        try {
            VehicleDto dto = vehicleModel.searchVehicle(id);
            txtVehicleType.setText(dto.getVehicle_type());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
