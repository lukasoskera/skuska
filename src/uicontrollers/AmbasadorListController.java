package uicontrollers;

import dbconnection.PostgresConn;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import model.Ambassador;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class AmbasadorListController implements Initializable {

    @FXML
    private TableView<Ambassador> ambtable;

    @FXML
    private TableColumn<Ambassador, String> MenoAPriezvisko;

    @FXML
    private TableColumn<Ambassador, String> Adresa;

    @FXML
    private TableColumn<Ambassador, String> Telefón;

    @FXML
    private TableColumn<Ambassador, String> Email;

    @FXML
    private TableColumn<Ambassador, Boolean> Zaškolený;

    @FXML
    private Button homeButton;

    @FXML
    void goHome(ActionEvent event) throws IOException {
        Stage MenuStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/MenuUI.fxml"));
        MenuStage.getIcons().add(new Image("img/Logo.png"));
        MenuStage.setTitle("Pre Stredoškolákov");
        MenuStage.setScene(new Scene(root, 600, 400));
        MenuStage.show();
    }

    @FXML
    void PridajAmbasadora(ActionEvent event) {

    }

    ObservableList<Ambassador> ambObList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        String SQL = "SELECT name, city, city2, phone, mail, trained FROM ambassador";


        try (Connection conn = new PostgresConn().connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SQL)) {

            while (rs.next()) {
                ambObList.add(new Ambassador(rs.getString("name"),
                        rs.getString("city")+", "+rs.getString("city2"),
                        rs.getString("mail"), rs.getString("phone"),
                        rs.getBoolean("trained")));
            }

            stmt.close();
            if (conn != null)
                conn.close();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        MenoAPriezvisko.setCellValueFactory(new PropertyValueFactory<>("name"));
        Adresa.setCellValueFactory(new PropertyValueFactory<>("address"));
        Telefón.setCellValueFactory(new PropertyValueFactory<>("phone"));
        Email.setCellValueFactory(new PropertyValueFactory<>("mail"));
        Zaškolený.setCellValueFactory(new PropertyValueFactory<>("trained"));

        ambtable.setItems(ambObList);
    }
}
