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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import lk.ijse.BO.BOFactory;
import lk.ijse.BO.Custom.EmployeeBO;
import lk.ijse.DAO.Impl.EmployeeDAOImpl;
import lk.ijse.dto.EmployeeDto;
import lk.ijse.dto.tm.EmployeeTm;


import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class EmployeeController {

    public TextField txtEmp_id;
    public TextField txtEmp_name;
    public TextField txtNic;
    public TextField txtJob;
    public TextField txtContact_no;

    public AnchorPane root;
    public Label lblEmail;
    public TableColumn<?,?> colEmail;
    @FXML
    private TableColumn<?, ?> colDelete;
    @FXML
    private TableColumn<?, ?> colUpdate;
    @FXML
    private TableColumn<?, ?> colAction;

    @FXML
    private TableColumn<?, ?> colContact;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colJobType;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colNic;

    @FXML
    private TableView<EmployeeTm>  tblEmployee;
    @FXML
    private Label lblConNo;

    @FXML
    private Label lblEmpId;

    @FXML
    private Label lblEmpName;

    @FXML
    private Label lblJobType;

    @FXML
    private Label lblNIC;
    EmployeeBO employeeBO = (EmployeeBO) BOFactory.getBoFactory().getBO(BOFactory.BOType.EMPLOYEE);

    private ObservableList<EmployeeTm>obList = FXCollections.observableArrayList();

    public void initialize() {
        setCellValueFactory();
        loadAllEmployee();
        tableListener();
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colNic.setCellValueFactory(new PropertyValueFactory<>("nic"));
        colJobType.setCellValueFactory(new PropertyValueFactory<>("job"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colDelete.setCellValueFactory(new PropertyValueFactory<>("remove"));

    }

    public void loadAllEmployee() {
        ObservableList<EmployeeTm> obList = FXCollections.observableArrayList();


        try {
            List<EmployeeDto> dtoList = employeeBO.getAllEmployee();

            for (EmployeeDto dto : dtoList) {
                Button btn = new Button("Remove");
                setDeleteBtnOnAction(btn,dto.getId());
                btn.setCursor(Cursor.HAND);
                obList.add(
                        new EmployeeTm(
                                dto.getId(),
                                dto.getName(),
                                dto.getContact(),
                                dto.getNic(),
                                dto.getJob(),
                                dto.getEmail(),
                                btn
                        )
                );
            }
            tblEmployee.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    private void setDeleteBtnOnAction(Button btn,String id){
        btn.setOnAction((e) -> {
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

            if (type.orElse(no) == yes) {
                obList.removeIf(employeeTm -> employeeTm.getId().equals(id));
                tblEmployee.refresh();
                DeleteEmployee(id);
            }
        });
    }

    private void DeleteEmployee(String id) {
        try {
            boolean isDeleted = employeeBO.deleteEmployee(id);
            if(isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Employee deleted!").show();
            } else {
                new Alert(Alert.AlertType.CONFIRMATION, "Employee not deleted!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        loadAllEmployee();
    }

    public void btnAddOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage1=new Stage();
        stage1.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/addEmployee_form.fxml"))));
        stage1.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                loadAllEmployee();
            }
        });
        stage1.show();
    }

    private void tableListener() {

        tblEmployee.getSelectionModel().selectedItemProperty().addListener((observable, oldValued, newValue) -> {
            try {
                EmployeeDto dto =employeeBO.searchEmployee(newValue.getId());
                setData(newValue, dto.getId());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
    }
    private void setData(EmployeeTm row, String id) {
        lblEmpId.setText(row.getId());
        lblEmpName.setText(row.getName());
        lblNIC.setText(row.getNic());
        lblConNo.setText(row.getContact());
        lblJobType.setText(row.getJob());
        lblEmail.setText(row.getEmail());
    }
    public void btnManageAttendanceOnAction(ActionEvent actionEvent) throws IOException {

        root.getChildren().clear();
        root.getChildren().add(FXMLLoader.load(getClass().getResource("/view/attendance_form.fxml")));
    }
    public void btnUpdateOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(this.getClass().getResource("/view/updateEmployee_form.fxml"))));
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                loadAllEmployee();
            }
        });
        stage.centerOnScreen();
        stage.show();
    }

    public void btnSalaryOnAction(ActionEvent actionEvent) throws IOException {
        root.getChildren().clear();
        root.getChildren().add(FXMLLoader.load(getClass().getResource("/view/salary_form.fxml")));
    }
}
