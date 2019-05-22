package uicontrollers;

import dbconnection.PostgresConn;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.Parent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.ChoiceBox;
import model.School;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import static org.postgresql.jdbc.PgResultSet.toInt;


public class SchoolListController implements Initializable {

    private Integer firstSchoolID;
    private Integer lastSchoolID;
    private Stage parentStage;

    public Integer getFirstSchoolID() {
        return firstSchoolID;
    }

    public void setFirstSchoolID(Integer firstSchoolID) {
        this.firstSchoolID = firstSchoolID;
    }

    public Integer getLastSchoolID() {
        return lastSchoolID;
    }

    public void setLastSchoolID(Integer lastSchoolID) {
        this.lastSchoolID = lastSchoolID;
    }

    public Stage getParentStage() {
        return parentStage;
    }

    public void setParentStage(Stage parentStage) {
        this.parentStage = parentStage;
    }

    @FXML
    private Button Pridaj;

    @FXML
    private TableView<School> schtable;

    @FXML
    private TableColumn<School, String> MenoUlica;

    @FXML
    private TableColumn<School, String> Kraj;

    @FXML
    private TableColumn<School, String> Mesto;

    @FXML
    private TableColumn<School, Integer> PocetZiakov;

    @FXML
    private TableColumn<School, Boolean> AktivnyWS;

    @FXML
    private ChoiceBox<String> FilterKraj;

    @FXML
    private ChoiceBox<String> FilterZiaci;

    @FXML
    private ChoiceBox<String> FilterMesto;

    @FXML
    private ChoiceBox<Boolean> FilterAktivny;

    @FXML
    private TextField FIlterMeno;

    @FXML
    private Button Filtruj;

    @FXML
    private Button nextPageButton;

    @FXML
    private Button prevPageButton;

    @FXML
    private Button firstPageButton;

    @FXML
    private Button lastPageButton;

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
    void PridajStudenta(ActionEvent event) throws IOException {
        Parent root1 = FXMLLoader.load(getClass().getResource("/fxml/SchoolCreateUI.fxml"));
        Stage SchoolCreate = (Stage) ((Node) event.getSource()).getScene().getWindow();
        SchoolCreate.getIcons().add(new Image("img/Logo.png"));
        SchoolCreate.setTitle("Pre Stredoškolákov");
        SchoolCreate.setScene(new Scene(root1));
        SchoolCreate.show();
    }

    @FXML
    void Filter(ActionEvent event) {





    }

    @FXML
    void getFirstPage(ActionEvent event) throws IOException {
        Stage SchoolStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        preparePage(SchoolStage, -1, 0);
    }

    @FXML
    void getLastPage(ActionEvent event) throws IOException {
        Stage SchoolStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        preparePage(SchoolStage, Integer.MAX_VALUE, -1);
    }

    @FXML
    void getNextPage(ActionEvent event) throws IOException {
        Stage SchoolStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        preparePage(SchoolStage, 0, getLastSchoolID());
    }

    @FXML
    void getPrevPage(ActionEvent event) throws IOException {
        Stage SchoolStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        preparePage(SchoolStage, getFirstSchoolID(), 0);
    }

    ObservableList<School> schObList = FXCollections.observableArrayList();
    ObservableList<String> krajList = FXCollections.observableArrayList();
    ObservableList<String> mestoList = FXCollections.observableArrayList();
    ObservableList<String> ziaciList = FXCollections.observableArrayList();
    ObservableList<Boolean> aktivnyList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try (Connection conn = new PostgresConn().connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(schoolListQuery(getFirstSchoolID(), getLastSchoolID()))) {

            int i = 0;
            while (rs.next()) {
                if(i == 0)
                    setFirstSchoolID(toInt(rs.getString("school_id")));
                i++;
                schObList.add(new School(toInt(rs.getString("school_id")), rs.getString("name"),
                        rs.getString("region"), rs.getString("city"),
                        rs.getInt("num_of_students"), rs.getBoolean("ws_act")));
                krajList.add(rs.getString("region"));
                mestoList.add(rs.getString("city"));
                ziaciList.add(rs.getString("num_of_students"));
                aktivnyList.add(rs.getBoolean("ws_act"));

                setLastSchoolID(toInt(rs.getString("school_id")));
            }

            stmt.close();
            if (conn != null)
                conn.close();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }


        FilterAktivny.setItems(aktivnyList);
        krajList.add("nezáleží");
        FilterKraj.setItems(krajList);
        mestoList.add("nezáleží");
        FilterMesto.setItems(mestoList);
        ziaciList.add("nazáleží");
        FilterZiaci.setItems(ziaciList);
        MenoUlica.setCellValueFactory(new PropertyValueFactory<>("name"));
        Kraj.setCellValueFactory(new PropertyValueFactory<>("region"));
        Mesto.setCellValueFactory(new PropertyValueFactory<>("city"));
        PocetZiakov.setCellValueFactory(new PropertyValueFactory<>("studentsCount"));
        AktivnyWS.setCellValueFactory(new PropertyValueFactory<>("wsActive"));

        schtable.setItems(schObList);

        schtable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    try {
                        SchoolDetailController controller = new SchoolDetailController();
                        controller.setSchoolID(newValue.getId());

                        FXMLLoader loader = new FXMLLoader();
                        loader.setController(controller);
                        loader.setLocation(getClass().getResource("/fxml/SchoolDetailUI.fxml"));

                        Stage SchoolDetail = getParentStage();

                        SchoolDetail.getIcons().add(new Image("img/Logo.png"));
                        SchoolDetail.setTitle("Pre Stredoškolákov");
                        SchoolDetail.setScene(new Scene(loader.load()));
                        SchoolDetail.show();

                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    }
                });
    }

    private String schoolListQuery(int firstID, int lastID){

        //first page or next page
        if((firstID == -1 && lastID == 0) || (firstID == 0 && lastID > 0))
            return "SELECT s.school_id, s.name, s.region, s.city, s.num_of_students, (not count(w.ws_term_id) = 0) ws_act\n" +
                    "FROM school s LEFT JOIN ws_term w ON s.school_id = w.school_id\n" +
                    "WHERE s.school_id > " + getLastSchoolID() + "\n" +
                    "GROUP BY s.school_id ORDER BY s.school_id\n" +
                    "LIMIT 10;";

        //last page or previous page
        if((firstID == Integer.MAX_VALUE && lastID == -1) || (firstID > 0 && lastID == 0))
            return "SELECT * FROM (\n" +
                    "  SELECT s.school_id, s.name, s.region, s.city, s.num_of_students, (not count(w.ws_term_id) = 0) ws_act\n" +
                    "  FROM school s LEFT JOIN ws_term w ON s.school_id = w.school_id\n" +
                    "  WHERE s.school_id < " + getFirstSchoolID() + "\n" +
                    "  GROUP BY s.school_id\n" +
                    "  ORDER BY s.school_id DESC\n" +
                    "  LIMIT 10\n" +
                    ")result ORDER BY result.school_id ASC;";
        return "";
    }

    public void preparePage(Stage SchoolStage, int first, int last) throws IOException {
        SchoolListController controller = new SchoolListController();
        controller.setFirstSchoolID(first);
        controller.setLastSchoolID(last);
        controller.setParentStage(SchoolStage);

        FXMLLoader loader = new FXMLLoader();
        loader.setController(controller);
        loader.setLocation(getClass().getResource("/fxml/SchoolListUI.fxml"));

        SchoolStage.getIcons().add(new Image("img/Logo.png"));
        SchoolStage.setTitle("Pre Stredoškolákov");
        SchoolStage.setScene(new Scene(loader.load()));
        SchoolStage.show();
    }

}
