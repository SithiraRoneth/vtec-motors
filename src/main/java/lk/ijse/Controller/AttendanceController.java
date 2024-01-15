package lk.ijse.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.BO.BOFactory;
import lk.ijse.BO.Custom.AttendanceBO;
import lk.ijse.BO.Impl.AttendanceBOImpl;
import lk.ijse.DAO.Impl.AttendanceDAOImpl;
import lk.ijse.Entity.Attendance;
import lk.ijse.dto.AttendanceDto;
import lk.ijse.dto.tm.AttendaceViewTm;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class AttendanceController {
    public DatePicker txtDate;

    public AnchorPane root;
    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colEmployee_id;

    @FXML
    private TableColumn<?, ?> colEmployee_name;

    @FXML
    private TableView<AttendaceViewTm> tblAttendance;

    AttendanceBO attendanceBO = (AttendanceBO) BOFactory.getBoFactory().getBO(BOFactory.BOType.ATTENDANCE);

    private ObservableList<AttendaceViewTm> obList =FXCollections.observableArrayList();

    public void initialize(){
        setCellValueFactory();
      //  loadAllAttendance();
    }


    private void setCellValueFactory(){
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colEmployee_id.setCellValueFactory(new PropertyValueFactory<>("EmpId"));
        colEmployee_name.setCellValueFactory(new PropertyValueFactory<>("EmpName"));
    }


    public void btnUpdateAttendance(ActionEvent actionEvent) throws IOException {
        root.getChildren().clear();
        root.getChildren().add(FXMLLoader.load(getClass().getResource("/view/addattendance_form.fxml")));
    }

    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        root.getChildren().clear();
        root.getChildren().add(FXMLLoader.load(getClass().getResource("/view/employee_form.fxml")));
    }

    public void btnDateOnAction(ActionEvent actionEvent) {
        try {
            String selectedDate = String.valueOf(txtDate.getValue());
            if (selectedDate != null) {
                List<AttendanceDto> attendanceList = attendanceBO.getAttendance(selectedDate);

                for (AttendanceDto dto : attendanceList) {
                    String name = dto.getEmp_name();
                    obList.add(
                            new AttendaceViewTm(
                                    dto.getDate(),
                                    dto.getEmp_id(),
                                    dto.getEmp_name()
                            )
                    );
                }

                tblAttendance.setItems(obList);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
