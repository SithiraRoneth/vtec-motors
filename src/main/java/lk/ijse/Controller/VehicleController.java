package lk.ijse.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import lk.ijse.Model.EmployeeModel;
import lk.ijse.Model.VehicleModel;
import lk.ijse.dto.EmployeeDto;
import lk.ijse.dto.VehicleDto;
import lk.ijse.dto.tm.EmployeeTm;
import lk.ijse.dto.tm.VehicleTm;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class VehicleController {
    public Label lblVehicleId;
    public Label lblGuardian_id;
    public Label lblVehicleType;
    @FXML
    private TableColumn<?, ?> colAction;
    @FXML
    private TableColumn<?, ?> colGuardian_id;

    @FXML
    private TableColumn<?, ?> colVehicle_id;

    @FXML
    private TableColumn<?, ?> colVehicle_type;

    @FXML
    private TableView<VehicleTm> tblVehicle;
    private VehicleModel vehicleModel = new VehicleModel();
    private ObservableList<VehicleTm> obList = FXCollections.observableArrayList();

    public void initialize(){
        setCellValueFactory();
        loadAllVehicles();
        tableListener();
    }


    private void setData(VehicleTm row, String vehicleId) {
        lblVehicleId.setText(row.getVehicle_id());
        lblVehicleType.setText(row.getVehicle_type());
        lblGuardian_id.setText(row.getGuardian_id());
    }

    private void setCellValueFactory(){
        colVehicle_id.setCellValueFactory(new PropertyValueFactory<>("vehicle_id"));
        colVehicle_type.setCellValueFactory(new PropertyValueFactory<>("vehicle_type"));
        colGuardian_id.setCellValueFactory(new PropertyValueFactory<>("guardian_id"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("remove"));
    }
    private void loadAllVehicles(){
        var model = new VehicleModel();

        try {
            List<VehicleDto> dtoList = model.getAllVehicles();

            for (VehicleDto dto : dtoList) {
                Button btn = new Button("Remove");
                setRemoveBtnOnAction(btn,dto.getVehicle_id());
                obList.add(
                        new VehicleTm(
                                dto.getVehicle_id(),
                                dto.getVehicle_type(),
                                dto.getGuardian_id(),
                                btn
                        )
                );
            }
            tblVehicle.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setRemoveBtnOnAction(Button btn, String id) {
        btn.setOnAction((e) -> {
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

            if (type.orElse(no) == yes) {
                obList.removeIf(VehicleTm -> VehicleTm.getVehicle_id().equals(id));
                tblVehicle.refresh();

                // Call the delete method
                DeleteVehicle(id);
            }
        });
    }

    private void DeleteVehicle(String id) {
        try {
            boolean isDeleted = VehicleModel.deleteVehicle(id);
            if(isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Vehicle deleted!").show();
            } else {
                new Alert(Alert.AlertType.CONFIRMATION, "Vehicle not deleted!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        tblVehicle.refresh();
    }

    public void btnAddOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        stage.setScene((new Scene(FXMLLoader.load(this.getClass().getResource("/view/addVehicle_form.fxml")))));
        stage.centerOnScreen();
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                loadAllVehicles();
            }
        });

        stage.show();
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(this.getClass().getResource("/view/updateVehicle_form.fxml"))));
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                loadAllVehicles();
            }
        });
        stage.centerOnScreen();
        stage.show();
    }
    private void tableListener() {
        tblVehicle.getSelectionModel().selectedItemProperty().addListener((observable, oldValued, newValue) -> {
            try {
                VehicleDto dto = vehicleModel.searchVehicle(newValue.getVehicle_id());
                setData(newValue, dto.getVehicle_id());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
