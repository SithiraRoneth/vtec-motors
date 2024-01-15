package lk.ijse.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.BO.BOFactory;
import lk.ijse.BO.Custom.AttendanceBO;
import lk.ijse.BO.Custom.EmployeeBO;
import lk.ijse.BO.Impl.AttendanceBOImpl;
import lk.ijse.DAO.DAOFactory;
import lk.ijse.DAO.Impl.AttendanceDAOImpl;
import lk.ijse.DAO.Custom.EmployeeDAO;
import lk.ijse.dto.AttendanceDto;
import lk.ijse.dto.EmployeeDto;
import lk.ijse.dto.tm.AttendanceTm;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AddAttendanceController {
    @FXML
    private DatePicker dateDate;
    @FXML
    private JFXComboBox<String> cmbEmpId;
    @FXML
    private Label lblDate;

    @FXML
    private Label lblEmployeeName;

    @FXML
    private TableView tblAttendance;

    @FXML
    private TableColumn colEmpId;

    @FXML
    private TableColumn colEmpName;

    @FXML
    private TableColumn colAction;

    @FXML
    private JFXButton BtnMarkAttendance;

    @FXML
    private AnchorPane root;

    @FXML
    private DatePicker txtDate;
    AttendanceBO attendanceBO = (AttendanceBO) BOFactory.getBoFactory().getBO(BOFactory.BOType.ATTENDANCE);
    EmployeeBO employeeBO = (EmployeeBO) BOFactory.getBoFactory().getBO(BOFactory.BOType.EMPLOYEE);

    private ObservableList<AttendanceTm> obList = FXCollections.observableArrayList();

    public void initialize() {
        dateDate.setPromptText(String.valueOf(LocalDate.now()));
        loadAllEmployee();
        setCellValueFactory();
        //setDate();
    }

    private void setCellValueFactory() {
        colEmpId.setCellValueFactory(new PropertyValueFactory<>("empId"));
        colEmpName.setCellValueFactory(new PropertyValueFactory<>("empName"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("remove"));
    }


    public void btnUpdateOnAction(ActionEvent actionEvent) {
        String date =dateDate.getPromptText();

        List<AttendanceDto> attendanceDtoList = new ArrayList<>();
        for (AttendanceTm attendanceTm : obList){
            String id = attendanceTm.getEmpId();
            String name = attendanceTm.getEmpName();
            boolean isPresent = attendanceTm.isPresent();

            AttendanceDto attendanceDto = new AttendanceDto(date,id,name,isPresent);
            attendanceDtoList.add(attendanceDto);
        }

        try{
            boolean isSuccess = attendanceBO.addAttendanceList(attendanceDtoList);
            if(isSuccess) {
                new Alert(Alert.AlertType.CONFIRMATION,"Attendance save success!!!").show();
                obList.clear();
                tblAttendance.setItems(obList);
            }
        } catch (SQLException | ClassNotFoundException e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    public void BtnMarkAttendanceOnAction(ActionEvent actionEvent) {
        String id = cmbEmpId.getValue();
        String name = lblEmployeeName.getText();
        Button btn = new Button("Remove");

        setRemoveBtnAction(btn,id);
        btn.setCursor(Cursor.HAND);


        if (!obList.isEmpty()) {
            for (int i = 0; i < tblAttendance.getItems().size(); i++) {
                if (colEmpId.getCellData(i).equals(id)) {

                    tblAttendance.refresh();
                    return;
                }
            }
        }
        var attendanceTm = new AttendanceTm(id,name,btn);

        obList.add(attendanceTm);

        tblAttendance.setItems(obList);

    }

    private void setRemoveBtnAction(Button btn,String employeeId) {
        btn.setOnAction((e) -> {
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to Delete?", yes, no).showAndWait();

            if (type.orElse(no) == yes) {
                obList.removeIf(attendanceTm -> attendanceTm.getEmpId().equals(employeeId));
                tblAttendance.refresh();
                DeleteEmployee(employeeId);
            }
        });
    }

    private void DeleteEmployee(String employeeId){
        try {
            boolean isDeleted = employeeBO.deleteEmployee(employeeId);
            if(isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Employee remove!").show();
            } else {
                new Alert(Alert.AlertType.CONFIRMATION, "Employee not remove!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        tblAttendance.refresh();
    }



    public void cmbEmpIdOnAction(ActionEvent actionEvent) throws ClassNotFoundException {
        String id = cmbEmpId.getValue();

        try {
            EmployeeDto dto = employeeBO.searchEmployee(id);
            lblEmployeeName.setText(dto.getName());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadAllEmployee() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<EmployeeDto> employeeDtos = employeeBO.getAllEmployee();

            for (EmployeeDto dto : employeeDtos) {
                obList.add(dto.getId());
            }
            cmbEmpId.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        root.getChildren().clear();
        root.getChildren().add(FXMLLoader.load(getClass().getResource("/view/attendance_form.fxml")));
    }
}
