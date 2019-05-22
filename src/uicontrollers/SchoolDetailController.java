package uicontrollers;

import dbconnection.PostgresConn;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.SchoolDetail;
import model.Workshop;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import static org.postgresql.jdbc.PgResultSet.toDouble;
import static org.postgresql.jdbc.PgResultSet.toInt;

public class SchoolDetailController implements Initializable {

    private Integer schoolID;
    private  Stage parentStage;

    public Integer getSchoolID() {
        return schoolID;
    }

    public void setSchoolID(Integer schoolID) {
        this.schoolID = schoolID;
    }


    @FXML
    private Label city;

    @FXML
    private Label region;

    @FXML
    private Label studentCount;

    @FXML
    private Label language;

    @FXML
    private Label headmName;

    @FXML
    private Label headmPhone;

    @FXML
    private Label headmMail;

    @FXML
    private Label secPhone;

    @FXML
    private Label secMail;

    @FXML
    private Label teachName;

    @FXML
    private Label teachMail;

    @FXML
    private TableView<Workshop> wsTable;

    @FXML
    private TableColumn<?, ?> wsCol;

    @FXML
    private TableColumn<?, ?> dateCol;

    @FXML
    private TableColumn<?, ?> ambCol;

    @FXML
    private Button editButton;

    @FXML
    private Button delButton;

    @FXML
    private Label schName;

    @FXML
    private Label wsCount;

    @FXML
    private Label doneWScount;

    @FXML
    private Label plannedWScount;

    @FXML
    private Label studWSavg;

    @FXML
    private Label durWSavg;

    @FXML
    private Label ratWSavg;

    @FXML
    private Label schRat;

    @FXML
    private Label empSCHrat;

    @FXML
    private Label ambSCHrat;


    ObservableList<Workshop> wsObList = FXCollections.observableArrayList();

    @FXML
    private Button backButton;

    @FXML
    void changeRecord(ActionEvent event) throws IOException {
       SchoolDetailChangeController controller = new SchoolDetailChangeController();
        controller.setSchoolID(schoolID);

        Stage   SchoolDetailChange = (Stage) ((Node) event.getSource()).getScene().getWindow();
        controller.setParentStage(  SchoolDetailChange);

        FXMLLoader loader = new FXMLLoader();
        loader.setController(controller);
        loader.setLocation(getClass().getResource("/fxml/SchoolDetailChangeUI.fxml"));


        SchoolDetailChange.getIcons().add(new Image("img/Logo.png"));
        SchoolDetailChange.setTitle("Pre Stredoškolákov");
        SchoolDetailChange.setScene(new Scene(loader.load()));
        SchoolDetailChange.show();

    }

    @FXML
    void goBack(ActionEvent event) throws IOException{
        Stage SchoolStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        new SchoolListController().preparePage(SchoolStage, -1, 0);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Connection conn = null;
        Statement stmt = null;
        String SQL = schoolDetailQuery(getSchoolID());
        String SQL1 = schoolStatisticsQuery(getSchoolID());
        String SQL2 = schoolRatingQuery(getSchoolID());
        String SQL3 = workshopsQuery(getSchoolID());
        SchoolDetail detail = null;

        try {
            conn = new PostgresConn().connect();
            conn.setAutoCommit(false);
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(SQL);

            while (rs.next()) {
                detail = new SchoolDetail(rs.getString("name") + ", " +
                        rs.getString("street"), rs.getString("city"),
                        rs.getString("region"), rs.getInt("num_of_students"),
                        rs.getString("language"), rs.getString("headmaster_name"),
                        rs.getString("headmaster_phone"), rs.getString("headmaster_mail"),
                        rs.getString("secretary_phone"), rs.getString("secretary_mail"),
                        rs.getString("our_teacher_name"), rs.getString("our_teacher_mail"));
            }

            stmt.close();
            stmt = conn.createStatement();
            ResultSet rs1 = stmt.executeQuery(SQL1);

           while (rs1.next()) {
                detail.setWsCount(toInt(rs1.getString("ws")));
                detail.setWsDone(toInt(rs1.getString("pastWS")));
                detail.setWsPlanned(toInt(rs1.getString("planWS")));
                detail.setWsStudAverage(toInt(rs1.getString("stud")));
                detail.setWsDurAverage(toInt(rs1.getString("dur")));
                detail.setWsAmbRatAverage(toDouble(rs1.getString("rat")));
           }

            stmt.close();
            stmt = conn.createStatement();
            ResultSet rs2 = stmt.executeQuery(SQL2);

            while (rs2.next()) {
                detail.setSchRatAverage(toDouble(rs2.getString("rating")));
                detail.setSchRatEmpAverage(toDouble(rs2.getString("empRat")));
                detail.setSchRatAmbAverage(toDouble(rs2.getString("ambRat")));
            }

            stmt.close();
            stmt = conn.createStatement();
            ResultSet rs3 = stmt.executeQuery(SQL3);
            while (rs3.next()) {
                wsObList.add(new Workshop(rs3.getString("ws"),
                        rs3.getString("date"), rs3.getString("amb")));
                }

            conn.commit();
        } catch (SQLException e) {
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

        schName.setText(detail.getName());
        city.setText(detail.getCity());
        region.setText(detail.getRegion());
        studentCount.setText(detail.getStudentsCount().toString());
        language.setText(detail.getLanguage());
        headmName.setText(detail.getHeadmasterName());
        headmPhone.setText(detail.getHeadmasterPhone());
        headmMail.setText(detail.getHeadmasterMail());
        secPhone.setText(detail.getSecretaryPhone());
        secMail.setText(detail.getSecretaryMail());
        teachName.setText(detail.getTeacherName());
        teachMail.setText(detail.getTeacherMail());
        wsCount.setText(detail.getWsCount().toString());
        doneWScount.setText(detail.getWsDone().toString());
        plannedWScount.setText(detail.getWsPlanned().toString());
        studWSavg.setText(detail.getWsStudAverage().toString());
        durWSavg.setText(detail.getWsDurAverage().toString());
        ratWSavg.setText(detail.getWsAmbRatAverage().toString());
        schRat.setText(detail.getSchRatAverage().toString());
        empSCHrat.setText(detail.getSchRatEmpAverage().toString());
        ambSCHrat.setText(detail.getSchRatAmbAverage().toString());

        wsCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("ambass"));
        ambCol.setCellValueFactory(new PropertyValueFactory<>("date"));

        wsTable.setItems(wsObList);
    }

    @FXML
    void deleteRecord(ActionEvent event) throws IOException {
        DeleteController controller = new DeleteController();
        controller.setSchoolID(schoolID);

        Stage SchoolStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        controller.setParentStage(SchoolStage);

        FXMLLoader loader = new FXMLLoader();
        loader.setController(controller);
        loader.setLocation(getClass().getResource("/fxml/DeleteAuth.fxml"));

        Stage DeleteStage = new Stage();
        DeleteStage.getIcons().add(new Image("img/Logo.png"));
        DeleteStage.setTitle("Pre Stredoškolákov");
        DeleteStage.setScene(new Scene(loader.load()));
        DeleteStage.show();
    }


    private String schoolDetailQuery (Integer schoolID){
        return "SELECT name, street, city, region, num_of_students, language, " +
                "headmaster_name, headmaster_phone, headmaster_mail, secretary_phone, " +
                "secretary_mail, our_teacher_name, our_teacher_mail " +
                "FROM school WHERE school_id = " + schoolID.toString() + ";";
    }

    private String schoolStatisticsQuery( Integer schoolID) {
        return "SELECT count(*) ws, " +
                   "count(*) filter (WHERE ws_date_time < current_timestamp) pastWS, " +
                   "count(*) filter (WHERE ws_date_time > current_timestamp) planWS, " +
                   "ceil(avg(num_of_students)) stud, ceil(avg(duration_minutes)) dur, trunc(avg(rating),2) rat " +
                "FROM ws_term wt WHERE school_id = " + schoolID.toString() + ";";
    }

    private String schoolRatingQuery( Integer schoolID) {
        return "SELECT avg(rating) rating, " +
                      "avg(rating) filter (WHERE employee_id NOTNULL) empRat, " +
                      "avg(rating) filter (WHERE ambassador_id NOTNULL) ambRat " +
                "FROM school_rating WHERE school_id = " + schoolID.toString() + ";";
    }

    private String workshopsQuery( Integer schoolID) {
        return "SELECT w.name ws, to_char(wt.ws_date_time, 'DD.MM.YYYY HH24:MI') date, a.name amb " +
               "FROM ws_term wt JOIN ambassador a on wt.ambassador_id = a.ambassador_id " +
                               "JOIN workshop w on wt.workshop_id = w.workshop_id " +
               "WHERE wt.school_id = " + schoolID.toString() + ";";
    }
}

