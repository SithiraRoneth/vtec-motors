package lk.ijse.Controller;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.dto.ServiceDto;
import lk.ijse.Model.ServiceModel;
import lk.ijse.dto.tm.ServiceTm;

import java.sql.SQLException;
import java.util.List;

public class ServicePageController {
    public TableColumn colAmountof;
    public Label lblServiceId;
    @FXML
    private JFXTextField txtAmount;
    @FXML
    private JFXTextField txtDescription;

    @FXML
    private JFXTextField txtService_id;

    @FXML
    private JFXTextField txtService_name;
    @FXML
    private TableColumn<?, ?> colService_id;

    @FXML
    private TableColumn<?, ?> colService_name;

    @FXML
    private TableColumn<?, ?> coldesc;

    @FXML
    private TableView<ServiceTm> tblService;

    private ServiceModel serviceModel = new ServiceModel();

    public void initialize(){
        setCellValueFactory();
        loadAllService();
        genarateNextServiceId();
    }

    private void genarateNextServiceId() {
        try {
            String employeeId = serviceModel.generateNextServiceId();
            lblServiceId.setText(employeeId);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void setCellValueFactory(){
        colService_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        colService_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        coldesc.setCellValueFactory(new PropertyValueFactory<>("description"));
        colAmountof.setCellValueFactory(new PropertyValueFactory<>("amount"));
    }
    private void loadAllService(){
        var model = new ServiceModel();

        ObservableList<ServiceTm> obList = FXCollections.observableArrayList();

        try {
            List<ServiceDto> dtoList = model.loadAllService();

            for (ServiceDto dto : dtoList){
                obList.add(
                        new ServiceTm(
                                dto.getId(),
                                dto.getName(),
                                dto.getDescription(),
                                dto.getAmount()
                        )
                );
            }
            tblService.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void btnAddServiceOnAction(ActionEvent actionEvent) {
        String id = lblServiceId.getText();
        String name = txtService_name.getText();
        String description = txtDescription.getText();
        double amount = Double.parseDouble(txtAmount.getText());

        var dto  = new ServiceDto(id,name,description,amount);
        try {
            boolean isSaved = serviceModel.AddService(dto);
            if (isSaved){
                new Alert(Alert.AlertType.CONFIRMATION,"service added successfully !!").show();
                clearField();
            }
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
        clearField();
        loadAllService();
        genarateNextServiceId();
    }

    private void clearField() {
       // txtService_id.setText("");
        txtService_name.setText("");
        txtDescription.setText("");
        txtAmount.setText("");
    }
}
