package lk.ijse.Controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import lk.ijse.BO.BOFactory;
import lk.ijse.BO.Custom.SupplierBO;
import lk.ijse.DAO.Custom.SupplierDAO;
import lk.ijse.DAO.DAOFactory;
import lk.ijse.DAO.Impl.SupplierDAOImpl;
import lk.ijse.dto.SupplierDto;
import org.controlsfx.control.Notifications;

import java.sql.SQLException;
import java.util.List;
import java.util.regex.Pattern;

public class UpdateSupplierController {
    @FXML
    private JFXComboBox<String> comboSupplier_id;

    @FXML
    private AnchorPane root;

    @FXML
    private JFXTextField txtContact;

    @FXML
    private JFXTextField txtName;
    SupplierBO supplierBO = (SupplierBO) BOFactory.getBoFactory().getBO(BOFactory.BOType.SUPPLIER);

    public void initialize(){
        setValue();
    }
    private void setValue(){
        ObservableList<String>obList = FXCollections.observableArrayList();

        try{
            List<SupplierDto> dtoList = supplierBO.getAllSupplier();

            for (SupplierDto dto : dtoList){
                obList.add(dto.getId());
            }
            comboSupplier_id.setItems(obList);
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
    public void btnUpdateOnAction(javafx.event.ActionEvent actionEvent) {
        String id = (String) comboSupplier_id.getValue();
        String name = txtName.getText();
        String contact = txtContact.getText();



        try {
            if (!validateSupplier()){
                return;
            }
            var dto = new SupplierDto(id,name,contact);
            boolean isUpdate = supplierBO.updateSupplier(dto);
            if (isUpdate){
                new Alert(Alert.AlertType.CONFIRMATION,"Supplier updated").show();
            }
        }catch (SQLException | ClassNotFoundException e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    private boolean validateSupplier() {
        boolean isValidate = true;
        boolean name = Pattern.matches("[A-Za-z]{5,}", txtName.getText());
        if (!name){
            showErrorNotification("Invalid Supplier Name", "The Supplier name you entered is invalid");
            isValidate = false;
        }
        boolean con = Pattern.matches("[0-9]{10,}",txtContact.getText());
        if (!con){
            showErrorNotification("Invalid Contact Number", "The Contact Number you entered is invalid");
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

    public void comboSupplier_idOnAction(javafx.event.ActionEvent actionEvent) {
        String id = (String) comboSupplier_id.getValue();

        try {
            SupplierDto dto = supplierBO.searchSupplier(id);
            txtName.setText(dto.getName());
            txtContact.setText(dto.getContact());
        }catch (SQLException | ClassNotFoundException e){
            throw new RuntimeException(e);
        }
    }
}
