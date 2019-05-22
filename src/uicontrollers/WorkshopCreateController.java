package uicontrollers;

import dbconnection.PostgresConn;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.event.ActionEvent;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class WorkshopCreateController implements Initializable {

    @FXML
    private ChoiceBox<String> wsName;

    @FXML
    private ChoiceBox<?> schName;

    @FXML
    private ChoiceBox<?> ambName;

    @FXML
    private Button backButton;

    @FXML
    private Button addButton;

    @FXML
    private DatePicker wsDate;

    @FXML
    private Spinner<?> wsTime;

    ObservableList<String> wsObList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Connection conn = null;
        Statement stmt = null;
        String SQL = loadWorkshopQuery();

        try {
            conn = new PostgresConn().connect();
            conn.setAutoCommit(false);
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(SQL);

            wsObList.add("nový..");
            while (rs.next()) {
              wsObList.add(rs.getString("name"));
            }

            stmt.close();
            }

            catch (SQLException e) {
                if (conn != null) {
                    try {
                        System.err.println(e.getMessage());
                        System.err.print("Transaction is being rolled back");
                        conn.rollback();
                    } catch(SQLException excep) {

                    }
                }
            } finally {
                if(stmt != null){
                    try {stmt.close();
                    }
                    catch (SQLException ex) {
                        System.out.println(ex.getMessage());
                    }
                }
            }

        wsName.setItems(wsObList);
    }

    private String loadWorkshopQuery (){
        return "SELECT name FROM workshop; ";
        }


    @FXML
    public void Pridaj(ActionEvent event) {

    }

    @FXML
    void goBack(ActionEvent event) throws IOException {
        Parent root4 = FXMLLoader.load(getClass().getResource("/fxml/WorkshopListUI.fxml"));
        Stage WorkshopStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        WorkshopStage.getIcons().add(new Image("img/Logo.png"));
        WorkshopStage.setTitle("Pre Stredoškolákov");
        WorkshopStage.setScene(new Scene(root4, 828, 556));
        WorkshopStage.show();
    }



}

