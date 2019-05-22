package uicontrollers;

import dbconnection.PostgresConn;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import model.Workshop;


import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;


public class WorkshopListController implements Initializable {

    @FXML
    private TableView<Workshop> wstable;

    @FXML
    private TableColumn<Workshop, String> Názov;

    @FXML
    private TableColumn<Workshop, String> Ambasádor;

    @FXML
    private TableColumn<Workshop, String> Škola;

    @FXML
    private TableColumn<Workshop, Integer> PočetŽiakov;

    @FXML
    private TableColumn<Workshop, Integer> Trvanie;

    @FXML
    private TableColumn<Workshop, Double> Hodnotenie;

    @FXML
    private TableColumn<Workshop, String> Report;

    @FXML
    private Button homeButton;

    @FXML
    private ChoiceBox<String> AmbaFilter;

    @FXML
    private ChoiceBox<String> SchoolFilter;

    @FXML
    private TextField NameFilter;


    @FXML
    void Filtrovanie(ActionEvent event) {
        String ambas, school;
        ObservableList<Workshop> FilteredList = FXCollections.observableArrayList();

        if(AmbaFilter.getValue() == null || AmbaFilter.getValue() == "nezáleží"){
            ambas = "amb.name";
        } else ambas = "'" + AmbaFilter.getValue() + "'";
        if(SchoolFilter.getValue() == null || SchoolFilter.getValue() == "nezáleží"){
            school = "s.name";
        } else school = "'" +SchoolFilter.getValue() + "'";

        String SQL1 = "SELECT wt.workshop_id, w.name, amb.name amb, s.name sch, wt.num_of_students num_st, wt.duration_minutes dur, wt.rating, wt.report " +
        "FROM ws_term wt " +
        "JOIN workshop w ON w.workshop_id = wt.workshop_id " +
        "JOIN ambassador amb ON amb.ambassador_id = wt.ambassador_id " +
        "JOIN school s ON s.school_id = wt.school_id " +
        "WHERE amb.name = " + ambas + " AND s.name = " + school +
        " LIMIT 10";


        try (Connection conn = new PostgresConn().connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SQL1)) {

            while (rs.next()) {
                FilteredList.add(new Workshop(rs.getInt("workshop_id"), rs.getString("name"),
                        rs.getString("amb"), rs.getString("sch"),
                        rs.getInt("num_st"), rs.getInt("dur"),
                        rs.getInt("rating"), rs.getString("report")));
            }

            stmt.close();
            if (conn != null)
                conn.close();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        Názov.setCellValueFactory(new PropertyValueFactory<>("name"));
        Ambasádor.setCellValueFactory(new PropertyValueFactory<>("ambass"));
        Škola.setCellValueFactory(new PropertyValueFactory<>("school"));
        PočetŽiakov.setCellValueFactory(new PropertyValueFactory<>("studentCount"));
        Trvanie.setCellValueFactory(new PropertyValueFactory<>("duration"));
        Hodnotenie.setCellValueFactory(new PropertyValueFactory<>("rating"));
        Report.setCellValueFactory(new PropertyValueFactory<>("report"));

        wstable.setItems(FilteredList);
    }

    @FXML
    void FilterMeno(KeyEvent event) {
        ObservableList<Workshop> FilteredList = FXCollections.observableArrayList();
        String text = "'" + NameFilter.getText()+ "%'";
        String SQL2 = "SELECT wt.workshop_id, w.name, amb.name amb, s.name sch, wt.num_of_students num_st, wt.duration_minutes dur, wt.rating, wt.report " +
                "FROM ws_term wt " +
                "JOIN workshop w ON w.workshop_id = wt.workshop_id " +
                "JOIN ambassador amb ON amb.ambassador_id = wt.ambassador_id " +
                "JOIN school s ON s.school_id = wt.school_id " +
                "WHERE w.name LIKE " + text +
                " LIMIT 10";


        try (Connection conn = new PostgresConn().connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SQL2)) {

            while (rs.next()) {
                FilteredList.add(new Workshop(rs.getInt("workshop_id"), rs.getString("name"),
                        rs.getString("amb"), rs.getString("sch"),
                        rs.getInt("num_st"), rs.getInt("dur"),
                        rs.getInt("rating"), rs.getString("report")));
            }

            stmt.close();
            if (conn != null)
                conn.close();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        Názov.setCellValueFactory(new PropertyValueFactory<>("name"));
        Ambasádor.setCellValueFactory(new PropertyValueFactory<>("ambass"));
        Škola.setCellValueFactory(new PropertyValueFactory<>("school"));
        PočetŽiakov.setCellValueFactory(new PropertyValueFactory<>("studentCount"));
        Trvanie.setCellValueFactory(new PropertyValueFactory<>("duration"));
        Hodnotenie.setCellValueFactory(new PropertyValueFactory<>("rating"));
        Report.setCellValueFactory(new PropertyValueFactory<>("report"));

        wstable.setItems(FilteredList);


    }

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
    void PridajWorkshop(ActionEvent event) throws IOException {
        Parent root1 = FXMLLoader.load(getClass().getResource("/fxml/WorkshopCreateUI.fxml"));
        Stage WorkshopCreate = (Stage) ((Node) event.getSource()).getScene().getWindow();
        WorkshopCreate.getIcons().add(new Image("img/Logo.png"));
        WorkshopCreate.setTitle("Pre Stredoškolákov");
        WorkshopCreate.setScene(new Scene(root1));
        WorkshopCreate.show();
    }

    ObservableList<Workshop> wsObList = FXCollections.observableArrayList();
    ObservableList<String> AmbaList = FXCollections.observableArrayList();
    ObservableList<String> SchoolList = FXCollections.observableArrayList();


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        String SQL = "SELECT wt.workshop_id," +
                "(SELECT w.name FROM workshop w WHERE w.workshop_id = wt.workshop_id), " +
                "(SELECT amb.name amb FROM ambassador amb WHERE amb.ambassador_id = wt.ambassador_id), " +
                "(SELECT s.name sch FROM school s WHERE s.school_id = wt.school_id), " +
                "num_of_students num_st, duration_minutes dur, rating, report " +
                "FROM ws_term wt LIMIT 100";


        try (Connection conn = new PostgresConn().connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SQL)) {

            int temp = 0;
            while (rs.next()) {
                wsObList.add(new Workshop(rs.getInt("workshop_id"),rs.getString("name"),
                        rs.getString("amb"), rs.getString("sch"),
                        rs.getInt("num_st"), rs.getInt("dur"),
                        rs.getInt("rating"), rs.getString("report")));
               if(temp < 10){
                AmbaList.add(rs.getString("amb"));
                SchoolList.add(rs.getString("sch"));} temp++;
            }

            stmt.close();
            if (conn != null)
                conn.close();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        AmbaList.add("nezáleží");
        AmbaFilter.setItems(AmbaList);
        SchoolList.add("nezáleží");
        SchoolFilter.setItems(SchoolList);
        Názov.setCellValueFactory(new PropertyValueFactory<>("name"));
        Ambasádor.setCellValueFactory(new PropertyValueFactory<>("ambass"));
        Škola.setCellValueFactory(new PropertyValueFactory<>("school"));
        PočetŽiakov.setCellValueFactory(new PropertyValueFactory<>("studentCount"));
        Trvanie.setCellValueFactory(new PropertyValueFactory<>("duration"));
        Hodnotenie.setCellValueFactory(new PropertyValueFactory<>("rating"));
        Report.setCellValueFactory(new PropertyValueFactory<>("report"));

        wstable.setItems(wsObList);

        wstable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    try {
                        WorkshopDetailController controller = new WorkshopDetailController();
                        controller.setWorkshopID(newValue.getID());

                        FXMLLoader loader = new FXMLLoader();
                        loader.setController(controller);
                        loader.setLocation(getClass().getResource("/fxml/WorkshopDetailUI.fxml"));

                        Stage SchoolDetail = new Stage();
                        SchoolDetail.getIcons().add(new Image("img/Logo.png"));
                        SchoolDetail.setTitle("Pre Stredoškolákov");
                        SchoolDetail.setScene(new Scene(loader.load()));
                        SchoolDetail.show();

                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    }

                });

    }


}
