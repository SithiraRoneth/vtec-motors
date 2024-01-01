package lk.ijse.Controller;

import com.jfoenix.controls.JFXButton;
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
import lk.ijse.BO.BOFactory;
import lk.ijse.BO.Custom.GuardianBO;
import lk.ijse.BO.Custom.IncomeBO;
import lk.ijse.BO.Custom.ServiceBO;
import lk.ijse.BO.Custom.SparePartsBO;
import lk.ijse.DAO.*;
import lk.ijse.DAO.Custom.*;
import lk.ijse.DAO.Impl.*;
import lk.ijse.dto.*;
import lk.ijse.dto.tm.CartTm;
import javafx.scene.control.Button;
import lk.ijse.dto.tm.SpareOrderTm;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderController {
    public Label lblOrderDate;
    public Label lblOrderId;
    public Label lblGuardianName;
    public Label lblFullAmount;
    public JFXButton btnAddToCart;
    public Label lblService_name;
    public Label lblAmount;
    public TableColumn<?, ?> colSer_Id;
    public TableColumn<?, ?> colService_name;
    public TableColumn<?, ?> colAmount;
    public TableView<SpareOrderTm> tblSpare;
    public TableColumn<?,?> colSpareName;
    public TableColumn<?,?> colPrice;
    public TableColumn<?,?> colDelete;
    public Label lblPrice;
    public Label lblSpareName;
    public JFXComboBox <String>cmbSpareId;
    public Label lblSpareTot;
    public Label lblFullAmountOf;
    public TableColumn<?,?> colSpareId;
    public TableColumn<?,?> colServiceid;
    public TableColumn<?, ?> colAction;

    public AnchorPane root;
    public JFXButton btnPlaceOrder;
    @FXML
    private JFXComboBox<String> cmbGuardian_Id;

    @FXML
    private JFXComboBox<String> cmbService_id;

    @FXML
    private TableView<CartTm> tblOrder;

    ServiceBO serviceBO = (ServiceBO) BOFactory.getBoFactory().getBO(BOFactory.BOType.SERVICE);
    OrderDAO orderDAO = (OrderDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDER);
    GuardianBO guardianBO = (GuardianBO) BOFactory.getBoFactory().getBO(BOFactory.BOType.GUARDIAN);
    SparePartsBO sparePartsBO = (SparePartsBO) BOFactory.getBoFactory().getBO(BOFactory.BOType.SPARE_PARTS);
    IncomeBO incomeBO = (IncomeBO) BOFactory.getBoFactory().getBO(BOFactory.BOType.INCOME);
    private PlaceOrderModel placeOrderModel = new PlaceOrderModel();
    private ObservableList<CartTm> obList = FXCollections.observableArrayList();
    private ObservableList<SpareOrderTm>spObList = FXCollections.observableArrayList();

    public void initialize() {
        generateNextOrderId();
        setDate();
        loadGuardianId();
        loadServiceId();
        setCellValueFactory();
        setCellValueFactory_Spare();
    }

    private void setCellValueFactory_Spare() {
        colServiceid.setCellValueFactory(new PropertyValueFactory<>("service_id"));
        colSpareName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colDelete.setCellValueFactory(new PropertyValueFactory<>("btn"));
    }

    private void setCellValueFactory() {
        colSer_Id.setCellValueFactory(new PropertyValueFactory<>("Service_id"));
        colService_name.setCellValueFactory(new PropertyValueFactory<>("Service_name"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("Amount"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("btn"));
    }

    private void loadServiceId() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<ServiceDto> idList = serviceBO.getAllService();

            for (ServiceDto dto : idList) {
                obList.add(dto.getId());
            }

            cmbService_id.setItems(obList);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    private void loadGuardianId() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<GuardianDto> idList = guardianBO.getAllGuardian();
            for (GuardianDto dto : idList) {
                obList.add(dto.getGuardian_id());
            }
            cmbGuardian_Id.setItems(obList);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void generateNextOrderId() {
        try {
            String orderId = orderDAO.generateNextId();
            lblOrderId.setText(orderId);
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void setDate() {
        lblOrderDate.setText(String.valueOf(LocalDate.now()));
    }

    public void cmbGuardianOnAction(ActionEvent actionEvent) {
        String id = cmbGuardian_Id.getValue();

        try {
            GuardianDto guardianDto = guardianBO.searchGuardian(id);
            lblGuardianName.setText(guardianDto.getGuardian_name());

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void cmbServiceOnAction(ActionEvent actionEvent) {
        String id = cmbService_id.getValue();

        try {
            ServiceDto serviceDto = serviceBO.searchService(id);
            lblService_name.setText(serviceDto.getName());
            lblAmount.setText(String.valueOf(serviceDto.getAmount()));
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        loadAllSpare(id);
    }

    public void btnAddToCartOnAction(ActionEvent actionEvent) {

        String service_id = cmbService_id.getValue();
        String service_name = lblService_name.getText();
        double amount = Double.parseDouble(lblAmount.getText());


        Button btn = new Button("remove");
        setRemoveBtnAction(btn);
        btn.setCursor(Cursor.HAND);

        if (!obList.isEmpty()) {
            for (int i = 0; i < tblOrder.getItems().size(); i++) {
                if (colSer_Id.getCellData(i).equals(service_id)) {
                    double S_amount = (double) colAmount.getCellData(i);

                    tblOrder.refresh();
                    return;
                }
            }
        }
        var cartTm = new CartTm(service_id, service_name, amount, btn);
        obList.add(cartTm);
        tblOrder.setItems(obList);
        calcTotal();
    }

    private void calcTotal() {
        double total = 0;
        for (int i = 0; i < tblOrder.getItems().size(); i++) {
            total += (double) colAmount.getCellData(i);
        }
        lblFullAmount.setText(String.valueOf(total));
       calFullAmount();
    }


    private void setRemoveBtnAction(Button btn) {
        btn.setOnAction((e) -> {
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

            if (type.orElse(no) == yes) {
                int focusedIndex = tblOrder.getSelectionModel().getSelectedIndex();

                obList.remove(focusedIndex);
                tblOrder.refresh();

            }
        });
    }

    public void btnPlaceOrderOnAction(ActionEvent actionEvent) {

        String orderId = lblOrderId.getText();
        String date = String.valueOf(LocalDate.parse(lblOrderDate.getText()));
        String guardianId = cmbGuardian_Id.getValue();

        List<CartTm> cartTmList = new ArrayList<>();
        for (int i = 0; i < tblOrder.getItems().size(); i++) {
            CartTm cartTm = obList.get(i);

            cartTmList.add(cartTm);
        }

        System.out.println("Place order form controller : " + cartTmList);
        var placeOrderDto = new PlaceOrderDto(orderId, date, guardianId,cartTmList);
        try {
            boolean isSuccess = placeOrderModel.placeOrder(placeOrderDto);
            if (isSuccess) {
                new Alert(Alert.AlertType.CONFIRMATION, "Order success").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

        GoIncome();
    }
    public void GoIncome() {
        LocalDate currentDate = LocalDate.now();
        String desc = "Confirm Order";
        double amount = Double.parseDouble(lblFullAmountOf.getText());
        int year = currentDate.getYear();
        String month = String.valueOf(currentDate.getMonth());
        String localDate = currentDate.toString();

        var incomeDto = new IncomeDto(desc, amount, year, month, localDate);
        try {
            boolean isSuccess = incomeBO.saveIncome(incomeDto);
            if (isSuccess) {
                new Alert(Alert.AlertType.CONFIRMATION, "Income Payment Save Success!").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnAddOnAction(ActionEvent actionEvent) {
        String service_id = cmbService_id.getValue();
        String name = lblSpareName.getText();
        double price = Double.parseDouble(lblPrice.getText());

        Button btn = new Button("remove");
        setRemoveBtnActionSpare(btn);
        btn.setCursor(Cursor.HAND);

        if (!spObList.isEmpty()) {
            for (int i = 0; i < tblSpare.getItems().size(); i++) {
                if (colSpareName.getCellData(i).equals(name)) {
                    tblSpare.refresh();

                    return;
                }
            }
        }
        var spareOrderTm = new SpareOrderTm(service_id,name, price, btn);
        spObList.add(spareOrderTm);
        tblSpare.setItems(spObList);
        calSpare();
    }

    private void calSpare() {
        double spare = 0;
        for (int i = 0; i < tblSpare.getItems().size(); i++) {
            spare += (double) colPrice.getCellData(i);
        }
        lblSpareTot.setText(String.valueOf(spare));
        calFullAmount();
    }

    private void loadAllSpare(String id) {
        //String id = cmbService_id.getValue();

        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<SpareDto> dtoList = sparePartsBO.getAllSpare();
            for (SpareDto dto : dtoList) {
                obList.add(dto.getSpareId());
            }
            cmbSpareId.setItems(obList);

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void cmbSpareIdOnAction(ActionEvent actionEvent) {
        String id = (String) cmbSpareId.getValue();
        try {
            SpareDto spareDto = sparePartsBO.searchSpare(id);
            lblSpareName.setText(spareDto.getSpareType());
            lblPrice.setText(String.valueOf(spareDto.getPrice()));
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    private void setRemoveBtnActionSpare(Button btn) {
        btn.setOnAction((e) -> {
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

            if (type.orElse(no) == yes) {
                int focusedIndex = tblSpare.getSelectionModel().getSelectedIndex();

                spObList.remove(focusedIndex);
                tblSpare.refresh();

            }
        });
    }
    private void calFullAmount(){
        double ser = Double.parseDouble(lblFullAmount.getText());
        double spare = Double.parseDouble(lblSpareTot.getText());
        double full = ser + spare;

        lblFullAmountOf.setText(String.valueOf(full));
    }
}
