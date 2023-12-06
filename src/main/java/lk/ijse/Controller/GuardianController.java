package lk.ijse.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import lk.ijse.Model.EmployeeModel;
import lk.ijse.Model.GuardianModel;
import lk.ijse.dto.EmployeeDto;
import lk.ijse.dto.GuardianDto;
import lk.ijse.dto.tm.EmployeeTm;
import lk.ijse.dto.tm.GuardianTm;
import javafx.scene.control.Button;

import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class GuardianController {

    public AnchorPane root;
    public Label lblGuardianId;
    public Label lblConNo;
    public Label lblEmployee_id;
    public Label lblGuardianName;
    public TableColumn<?,?> colDelete;
    @FXML
    private TableColumn<?, ?> colGuardian_Id;

    @FXML
    private TableColumn<?, ?> colGuardian_name;

    @FXML
    private TableColumn<?, ?> col_Employee_Id;

    @FXML
    private TableColumn<?, ?> colcontact;

    @FXML
    private TableView<GuardianTm> tblGuardian;
    

    private ObservableList<GuardianTm>obList = FXCollections.observableArrayList();
    private GuardianModel guardianModel = new GuardianModel();

    public void initialize(){
        setCellValueFactory();
        loadAllGuardian();
        tableListener();
    }

    private void tableListener() {
        tblGuardian.getSelectionModel().selectedItemProperty().addListener((observable, oldValued, newValue) -> {
            try {
                GuardianDto dto =guardianModel.searchGuardian(newValue.getGuardian_id());
                setData(newValue, dto.getGuardian_id());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void setData(GuardianTm row, String guardianId) {
        lblGuardianId.setText(row.getGuardian_id());
        lblGuardianName.setText(row.getGuardian_name());
        lblConNo.setText(row.getGuardian_contact());
        lblEmployee_id.setText(row.getEmployee_id());
    }


    private void setCellValueFactory(){
        colGuardian_Id.setCellValueFactory(new PropertyValueFactory<>("Guardian_id"));
        colGuardian_name.setCellValueFactory(new PropertyValueFactory<>("Guardian_name"));
        colcontact.setCellValueFactory(new PropertyValueFactory<>("Guardian_contact"));
        col_Employee_Id.setCellValueFactory(new PropertyValueFactory<>("employee_id"));
        colDelete.setCellValueFactory(new PropertyValueFactory<>("remove"));

    }
    private void loadAllGuardian(){
        var model = new GuardianModel();

        ObservableList<GuardianTm> obList = FXCollections.observableArrayList();
        try {
            List<GuardianDto> dtoList = model.GetAllGuardian();

            for (GuardianDto dto : dtoList){
                Button btn = new Button("remove");
                setDeleteBtnOnAction(btn,dto.getGuardian_id());
                btn.setCursor(Cursor.HAND);
                obList.add(
                        new GuardianTm(
                                dto.getGuardian_id(),
                                dto.getGuardian_name(),
                                dto.getGuardian_contact(),
                                dto.getEmployee_id(),
                                btn

                        )
                );
            }
            tblGuardian.setItems(obList);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    private void setDeleteBtnOnAction(Button btn, String id){
        btn.setOnAction((e) -> {
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

            if (type.orElse(no) == yes) {
                obList.removeIf(guardianTm -> guardianTm.getGuardian_id().equals(id));
                tblGuardian.refresh();
                DeleteGuardian(id);
            }
        });
    }

    private void DeleteGuardian(String id) {
        try {
            boolean isDeleted = GuardianModel.deleteGuardian(id);
            if(isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Guardian deleted!").show();
            } else {
                new Alert(Alert.AlertType.CONFIRMATION, "Guardian not deleted!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        //tblEmployee.refresh();
        loadAllGuardian();
    }

    public void btnAddOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        stage.setScene((new Scene(FXMLLoader.load(this.getClass().getResource("/view/addGuardian_form.fxml")))));
        stage.centerOnScreen();
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                loadAllGuardian();
            }
        });

        stage.show();
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        stage.setScene((new Scene(FXMLLoader.load(this.getClass().getResource("/view/updateGuardian_form.fxml")))));
        stage.centerOnScreen();
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                loadAllGuardian();
            }
        });

        stage.show();
    }
}
