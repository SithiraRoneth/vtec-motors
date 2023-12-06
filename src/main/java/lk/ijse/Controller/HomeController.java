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
import lk.ijse.DB.DbConnection;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class HomeController implements Initializable {
    public AnchorPane root;
    public BarChart barChart;

    public void btnCloseOnAction(ActionEvent actionEvent) {
        ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Are you sure you want to exit ?", yes, no);

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == yes) {
            // Close the application
            Platform.exit();
        }
    }

    public void orderChart() throws SQLException {
        barChart.getData().clear();
        String sql = "SELECT month, SUM(Amount) FROM income WHERE date >= CURDATE() - INTERVAL 6 MONTH GROUP BY month";

        Connection connection = DbConnection.getInstance().getConnection();

        try {
            XYChart.Series chart = new XYChart.Series();

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                chart.getData().add(new XYChart.Data(resultSet.getString(1), resultSet.getInt(2)));
            }

            barChart.getData().add(chart);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void btnIncomeOnAction(ActionEvent actionEvent) throws IOException {
        root.getChildren().clear();
        root.getChildren().add(FXMLLoader.load(getClass().getResource("/view/income_form.fxml")));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            orderChart();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
