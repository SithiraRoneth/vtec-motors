package lk.ijse.Controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class DashBoardController {
    public AnchorPane root;
    public JFXButton employee;
    public JFXButton Account;
    public JFXButton vehicle;
    public JFXButton supplier;
    public JFXButton payment;
    public JFXButton attendance;
    public JFXButton home;
    public JFXButton Guardian;
    public JFXButton service;

    public JFXButton order;
    public JFXButton spareParts;

    public void initialize() throws IOException {
        btnHomeOnAction(null);
    }
    void setForms(String forms) throws IOException {
        String [] formArrays = {"/view/employee_form.fxml","/view/account_form.fxml","/view/home_form.fxml",
            "/view/vehicle_form.fxml","/view/supplier_form.fxml","/view/order_form.fxml",
                "/view/guardian_form.fxml","/view/servicepage_form.fxml","/view/spare_parts_form.fxml"
        };

        JFXButton[] btnArray = {employee,Account,home,vehicle,supplier,order,Guardian,service,spareParts};

        AnchorPane load = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(forms)));

        root.getChildren().clear();
        root.getChildren().add(load);
        for (int i = 0; i < formArrays.length; i++) {
            btnArray[i].setStyle("-fx-background-color:  #FFFFFF; -fx-font-size: 12");

            if (forms.equals(formArrays[i])){
                btnArray[i].setStyle("-fx-background-color: #797fff; -fx-text-fill: #000000");
            }
        }
    }

    public void btnEmployeeOnAction(ActionEvent actionEvent) throws IOException {
        setForms("/view/employee_form.fxml");
    }

    public void btnAccountOnAction(ActionEvent actionEvent) throws IOException {
        setForms("/view/account_form.fxml");
    }

    public void btnVehicleOnAction(ActionEvent actionEvent) throws IOException {
        setForms("/view/vehicle_form.fxml");
    }

    public void btnSupplierOnAction(ActionEvent actionEvent) throws IOException {
        setForms("/view/supplier_form.fxml");
    }

    public void btnOrderOnAction(ActionEvent actionEvent) throws IOException {
        setForms("/view/order_form.fxml");
    }

    public void btnHomeOnAction(ActionEvent actionEvent) throws IOException {
        setForms("/view/home_form.fxml");
    }

    public void btnLogoutOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/login_form.fxml"));
        Stage stage = (Stage) root.getScene().getWindow();

        stage.setScene(new Scene(anchorPane));
        stage.centerOnScreen();
    }

    public void btnGuardianOnAction(ActionEvent actionEvent) throws IOException {
        setForms("/view/guardian_form.fxml");
    }

    public void btnServiceOnAction(ActionEvent actionEvent) throws IOException {
        setForms("/view/servicepage_form.fxml");
    }

    public void btnSparePartsOnAction(ActionEvent actionEvent) throws IOException {
        setForms("/view/spare_parts_form.fxml");
    }
}
