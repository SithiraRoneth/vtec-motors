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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.Model.GuardianModel;
import lk.ijse.Model.VehicleModel;
import lk.ijse.dto.EmployeeDto;
import lk.ijse.dto.GuardianDto;
import lk.ijse.dto.VehicleDto;
import org.controlsfx.control.Notifications;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

public class AddvehicleController {
    public JFXTextField txtVehicle_Id;
    public JFXTextField txtVehicle_type;

    public AnchorPane root;
    public JFXComboBox comboGuardian_id;
    public Label lblGuardian_name;
    public Label lblVehicleId;

    private VehicleModel vehicleModel = new VehicleModel();
    private GuardianModel guardianModel = new GuardianModel();

    public void initialize(){
        loadGuardian();
        generateVehicleId();

        root.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ENTER) {
                btnAddVehicleOnAction(new ActionEvent());
            }
        });
    }

    private void generateVehicleId() {
        try {
            String employeeId = vehicleModel.generateNextVehicleId();
            lblVehicleId.setText(employeeId);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void btnAddVehicleOnAction(ActionEvent actionEvent) {
        String id = lblVehicleId.getText();
        String types = txtVehicle_type.getText();
        String guardian_id = (String) comboGuardian_id.getValue();

        try {
            if (!validateVehicle()){
                return;
            }
            var dto = new VehicleDto(id, types, guardian_id);
            boolean isAdded = vehicleModel.addVehicle(dto);
            if(isAdded){
                ButtonType yes = new ButtonType("yes", ButtonBar.ButtonData.OK_DONE);
               // ButtonType no = new ButtonType("no", ButtonBar.ButtonData.CANCEL_CLOSE);

                Optional<ButtonType> type = new Alert(Alert.AlertType.CONFIRMATION,"Vehicle added Successfully!! Do you want add another one ", yes).showAndWait();
                clearFields();

               /* if (type.orElse(yes) == no) {
                    Stage stage = (Stage) this.root.getScene().getWindow();
                    stage.close();
                }*/
            }
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
        clearFields();
        generateVehicleId();
    }

    private boolean validateVehicle() {
        boolean isValidate = true;
        boolean type = Pattern.matches("[A-Za-z]{3,}", txtVehicle_type.getText());
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

    public void loadGuardian(){
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<GuardianDto> idList = guardianModel.GetAllGuardian();

            for (GuardianDto dto: idList) {
                obList.add(dto.getGuardian_id());
            }

            comboGuardian_id.setItems(obList);
           // lblGuardian_name.setText(obList.toString());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void clearFields() {
       //txtVehicle_Id.setText("");
        txtVehicle_type.setText("");
        comboGuardian_id.setValue("");
      // lblGuardian_name.setText("");
    }
}
