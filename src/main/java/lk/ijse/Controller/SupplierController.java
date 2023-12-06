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
import lk.ijse.Model.SupplierModel;
import lk.ijse.dto.EmployeeDto;
import lk.ijse.dto.SupplierDto;
import lk.ijse.dto.tm.SupplierTm;
import javafx.scene.control.Button;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class SupplierController {
    public Label lblSupId;
    public Label lblContact;
    public Label lblSupName;
    public TableColumn colDelete;
    @FXML
    private TableColumn<?, ?> colContact_No;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colSupplier_name;

    @FXML
    private TableView<SupplierTm> tblSupplier;

    private ObservableList<SupplierTm>obList = FXCollections.observableArrayList();
    private SupplierModel supplierModel = new SupplierModel();

    public void initialize(){
        setCellValueFactory();
        loadAllSupplier();
        tableListener();
    }
    private void setCellValueFactory(){
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colSupplier_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        colContact_No.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colDelete.setCellValueFactory(new PropertyValueFactory<>("btn"));
    }
    private void loadAllSupplier(){
        var model = new SupplierModel();

        ObservableList<SupplierTm> obList = FXCollections.observableArrayList();

        try {
            List<SupplierDto> dtoList = model.getAllSupplier();

            for(SupplierDto dto : dtoList){
                Button btn = new Button("Remove");
                setDeleteBtnOnAction(btn,dto.getId());
                obList.add(
                        new SupplierTm(
                                dto.getId(),
                                dto.getName(),
                                dto.getContact(),
                                btn
                        )
                );
            }
            tblSupplier.setItems(obList);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    private void setDeleteBtnOnAction(Button btn, String id) {
        btn.setOnAction((e) -> {
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

            if (type.orElse(no) == yes) {
                obList.removeIf(supplierTm -> supplierTm.getId().equals(id));
                tblSupplier.refresh();

                // Call the delete method
                DeleteSupplier(id);
            }
        });
    }

    private void DeleteSupplier(String id) {
        try {
            boolean isDeleted = SupplierModel.deleteSupplier(id);
            if(isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Supplier deleted!").show();
            } else {
                new Alert(Alert.AlertType.CONFIRMATION, "Supplier not deleted!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
       // tblSupplier.refresh();
        loadAllSupplier();
    }

    public void btnAddOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(this.getClass().getResource("/view/addSupplier_form.fxml"))));
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                loadAllSupplier();
            }
        });
        stage.centerOnScreen();
        stage.show();
    }
    private void tableListener(){
        tblSupplier.getSelectionModel().selectedItemProperty().addListener((observable, oldValued, newValue) -> {
            try {
                SupplierDto dto = supplierModel.searchSupplier(newValue.getId());
                setData(newValue, dto.getId());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void setData(SupplierTm row, String id) {
        lblSupId.setText(row.getId());
        lblSupName.setText(row.getName());
        lblContact.setText(row.getContact());
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/updateSupplier_form.fxml"))));
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                loadAllSupplier();
            }
        });
        stage.centerOnScreen();
        stage.show();
    }
}
