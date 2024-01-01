package lk.ijse.Controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.BO.BOFactory;
import lk.ijse.BO.Custom.ServiceBO;
import lk.ijse.BO.Custom.SparePartsBO;
import lk.ijse.DAO.Custom.ServiceDAO;
import lk.ijse.DAO.Custom.SparePartsDAO;
import lk.ijse.DAO.DAOFactory;
import lk.ijse.DAO.Impl.ServiceDAOImpl;
import lk.ijse.DAO.Impl.SparePartsDAOImpl;
import lk.ijse.dto.ServiceDto;
import lk.ijse.dto.SpareDto;
import lk.ijse.dto.tm.SparePartTm;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class SparePartsController {
    public JFXTextField txtPrice;
    public TableColumn<?,?> colPrice;
    @FXML
    private Label lblSpareId;
    @FXML
    private Label lblServiceName;
    @FXML
    private JFXComboBox<String> cmbService;

    @FXML
    private TableColumn<?, ?> colDelete;

    @FXML
    private TableColumn<?, ?> colDesc;

    @FXML
    private TableColumn<?, ?> colSpareId;

    @FXML
    private TableColumn<?, ?> colSpareType;

    @FXML
    private TableView<SparePartTm> tblSpare;

    @FXML
    private JFXTextField txtDesc;

    @FXML
    private JFXTextField txtSpareType;
    SparePartsBO sparePartsBO = (SparePartsBO) BOFactory.getBoFactory().getBO(BOFactory.BOType.SPARE_PARTS);
    ServiceBO serviceBO = (ServiceBO) BOFactory.getBoFactory().getBO(BOFactory.BOType.SERVICE);
    private ObservableList<SparePartTm>obList = FXCollections.observableArrayList();


    public void initialize(){
        loadAllSpare();
        loadAllService();
        generateNextId();
        setCellFactory();
    }

    private void loadAllSpare() {
        var model = new SparePartsDAOImpl();

        ObservableList<SparePartTm>obList = FXCollections.observableArrayList();

        try{
            List<SpareDto>dtoList = sparePartsBO.getAllSpare();

            for (SpareDto dto : dtoList){
                Button btn = new Button("Delete");
                setDeleteBtnOnAction(btn,dto.getSpareId());
                obList.add(
                        new SparePartTm(
                                dto.getSpareId(),
                                dto.getSpareType(),
                                dto.getDescription(),
                                dto.getPrice(),
                                btn
                        )
                );
            }
            tblSpare.setItems(obList);
        }catch (SQLException | ClassNotFoundException e){
            throw new RuntimeException(e);
        }

    }

    private void setDeleteBtnOnAction(Button btn, String spareId) {
        btn.setOnAction((e) -> {
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

            if (type.orElse(no) == yes) {
                obList.removeIf(sparePartTm -> sparePartTm.equals(spareId));
                tblSpare.refresh();
                DeleteSpare(spareId);
                generateNextId();
            }
        });
    }

    private void DeleteSpare(String spareId) {
        try {
            boolean isDeleted = sparePartsBO.deleteSpare(spareId);
            if(isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "SpareParts deleted!").show();
            } else {
                new Alert(Alert.AlertType.CONFIRMATION, "SpareParts not deleted!").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        //tblEmployee.refresh();
        loadAllSpare();
    }

    private void setCellFactory() {
        colSpareId.setCellValueFactory(new PropertyValueFactory<>("spareId"));
        colSpareType.setCellValueFactory(new PropertyValueFactory<>("spareType"));
        colDesc.setCellValueFactory(new PropertyValueFactory<>("description"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colDelete.setCellValueFactory(new PropertyValueFactory<>("delete"));
    }

    private void generateNextId() {
        try {
            String serviceId = sparePartsBO.generateNextSpareId();
            lblSpareId.setText(serviceId);
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void loadAllService() {
        ObservableList<String>obList = FXCollections.observableArrayList();

        try {
            List<ServiceDto>dtoList = serviceBO.getAllService();

            for (ServiceDto dto : dtoList){
                obList.add(dto.getId());
               // obList.add(dto.getName());
            }
            cmbService.setItems(obList);
           // lblServiceName.setText(obList.toString());
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnAddOnAction(ActionEvent actionEvent) {
        String id = lblSpareId.getText();
        String spare_type = txtSpareType.getText();
        String description = txtDesc.getText();
        double price = Double.parseDouble(txtPrice.getText());
        String service_name = lblServiceName.getText();
        String service_id = (String) cmbService.getValue();

        var dto = new SpareDto(id,spare_type,description,price,service_name,service_id);

        try{
            boolean isAdded = sparePartsBO.saveSpare(dto);
            if (isAdded){
                new Alert(Alert.AlertType.CONFIRMATION,"spare parts added").show();
                clearFiled();
            }
        }catch (SQLException | ClassNotFoundException e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
        loadAllSpare();
        generateNextId();
    }

    private void clearFiled() {
        txtSpareType.setText("");
        txtDesc.setText("");
        txtPrice.setText("");
        lblServiceName.setText("");
        cmbService.setValue("");
    }

    public void cmbServiceOnAction(ActionEvent actionEvent) {
        String id = cmbService.getValue();

        try {
            ServiceDto serviceDto = serviceBO.searchService(id);
            lblServiceName.setText(serviceDto.getName());

        }catch (SQLException | ClassNotFoundException e){
            throw new RuntimeException(e);
        }
    }
}
