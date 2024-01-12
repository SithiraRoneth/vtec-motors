package lk.ijse.Controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import lk.ijse.BO.BOFactory;
import lk.ijse.BO.Custom.HomeBO;
import lk.ijse.DB.DbConnection;
import lk.ijse.dto.IncomeDto;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Optional;
import java.util.ResourceBundle;

public class HomeController implements Initializable {
    public AnchorPane root;
    public BarChart barChart;

    HomeBO homeBO = (HomeBO) BOFactory.getBoFactory().getBO(BOFactory.BOType.HOME);

    public void btnCloseOnAction(ActionEvent actionEvent) {
        ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Are you sure you want to exit ?", yes, no);

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == yes) {
            Platform.exit();
        }
    }

    public void orderChart() {
        barChart.getData().clear();

        XYChart.Series chart = new XYChart.Series();

        try {
            HashSet<IncomeDto> set= homeBO.orderChart();

            for (IncomeDto incomeDto:set) {
                chart.getData().add(new XYChart.Data(incomeDto.getMonth(),incomeDto.getAmount()));
            }
                barChart.getData().add(chart);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnIncomeOnAction(ActionEvent actionEvent) throws IOException {
        root.getChildren().clear();
        root.getChildren().add(FXMLLoader.load(getClass().getResource("/view/income_form.fxml")));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        orderChart();
    }
}
