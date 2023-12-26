package lk.ijse.Controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.DAO.AddedSpareModel;
import lk.ijse.DAO.Custom.SparePartsDAO;
import lk.ijse.DAO.Custom.SupplierDAO;
import lk.ijse.DAO.DAOFactory;
import lk.ijse.DAO.Impl.SparePartsDAOImpl;
import lk.ijse.DAO.Impl.SupplierDAOImpl;
import lk.ijse.dto.*;
import lk.ijse.dto.tm.SpareCartTm;
import org.controlsfx.control.Notifications;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

public class AddSupplierController {
    public JFXTextField txtSupplier_id;
    public TableView<SpareCartTm> tblSpareCart;
    @FXML
    private TableColumn<?, ?> colDelete;

    @FXML
    private TableColumn<?, ?> colSpareId;

    @FXML
    private TableColumn<?, ?> colSpareName;

    @FXML
    private TableColumn<?, ?> colprice;


    public JFXTextField txtName;

    public JFXTextField txtContact;

    public AnchorPane root;
    public Label lblSupplierId;
    public JFXComboBox cmbSpareId;
    public Label lblSpareType;
    public Label lblPrice;
    SupplierDAO supplierDAO = (SupplierDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.SUPPLIER);
    SparePartsDAO sparePartsDAO = (SparePartsDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.SPAREPARTS);
    private ObservableList<SpareCartTm> obList = FXCollections.observableArrayList();

    public void initialize(){
        genarateSupplierId();
        loadAllSpareParts();
        setCellValueFactory();

        /*root.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ENTER) {
                btnAddSupOnAction(new ActionEvent());
            }
        });*/
    }

    private void setCellValueFactory() {
        colSpareId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colSpareName.setCellValueFactory(new PropertyValueFactory<>("spare_type"));
        colprice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colDelete.setCellValueFactory(new PropertyValueFactory<>("btn"));
    }

    private void loadAllSpareParts() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<SpareDto> idList = sparePartsDAO.getAll();

            for (SpareDto dto: idList) {
                obList.add(dto.getSpareId());
            }

            cmbSpareId.setItems(obList);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void genarateSupplierId() {
        try {
            String employeeId = supplierDAO.generateNextId();
            lblSupplierId.setText(employeeId);
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
    public void btnAddSupOnAction(ActionEvent actionEvent) {
        String id = lblSupplierId.getText();
        String name = txtName.getText();
        String contact = txtContact.getText();

        List<SpareCartTm>spareCartTmList = new ArrayList<>();
        for (int i = 0 ;i < tblSpareCart.getItems().size(); i ++){
            SpareCartTm spareCartTm = obList.get(i);
            spareCartTmList.add(spareCartTm);
        }
        System.out.println("Spare cart Details" + spareCartTmList);

        try{
            if (!validateSupplier()){
                return;
            }

            var spareOrderDto = new SpareOrderDto(id,name,contact,spareCartTmList);
            boolean isSuccess = AddedSpareModel.addSpare(spareOrderDto);
            if (isSuccess){
                new Alert(Alert.AlertType.CONFIRMATION,"success").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }

        clearField();
        genarateSupplierId();
    }

    private boolean validateSupplier() {
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
        return isValidate;
    }

    private void showErrorNotification(String title, String text) {
        Notifications.create()
                .title(title)
                .text(text)
                .showError();
    }

    private void clearField() {
        //txtSupplier_id.setText("");
        txtName.setText("");
        txtContact.setText("");
        cmbSpareId.setValue("");
      //  lblSpareType.setText("");
      //  lblPrice.setText("");
    }

    public void cmbSpareIdOnAction(ActionEvent actionEvent) {
        String id = (String) cmbSpareId.getValue();
        try {
            SpareDto spareDto = sparePartsDAO.search(id);
            lblSpareType.setText(spareDto.getSpareType());
            lblPrice.setText(String.valueOf(spareDto.getPrice()));

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnSpareCartOnAction(ActionEvent actionEvent) {
        String id = (String) cmbSpareId.getValue();
        String spare_type = lblSpareType.getText();
        double spare_price = Double.parseDouble(lblPrice.getText());

        Button btn = new Button("Delete");
        setDeleteBtnOnAction(btn);
        btn.setCursor(Cursor.HAND);

        if (!obList.isEmpty()){
            for (int i = 0; i<tblSpareCart.getItems().size(); i++){
                if (colSpareId.getCellData(i).equals(id)){
                    //String spareName = (String) colSpareName.getCellData(i);
                    //double price = (double) colprice.getCellData(i);

                    tblSpareCart.refresh();
                    return;
                }
            }
        }
        var spareCartTm = new SpareCartTm(id,spare_type,spare_price,btn);
        obList.add(spareCartTm);
        tblSpareCart.setItems(obList);
        clearField();
    }

    private void setDeleteBtnOnAction(Button btn) {
        btn.setOnAction((e) -> {
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

            if (type.orElse(no) == yes) {
                int focusedIndex = tblSpareCart.getSelectionModel().getSelectedIndex();

                obList.remove(focusedIndex);
                tblSpareCart.refresh();
            }
        });
    }

}
