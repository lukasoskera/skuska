package uicontrollers;

import dbconnection.PostgresConn;
import javafx.scene.control.TableView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextArea;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Workshop;
import model.Ambassador;


public class WorkshopDetailController implements Initializable {

    private Integer workshopID;

    public Integer getWorkshopID() {
        return workshopID;
    }

    public void setWorkshopID(Integer workshopID) {
        this.workshopID = workshopID;
    }

    @FXML
    private Label WsName;

    @FXML
    private Label AvgRating;

    @FXML
    private Label AvgTime;

    @FXML
    private Label AvgAttendance;

    @FXML
    private TextArea Description;

    @FXML
    private TableView<Workshop> futureWs;

    @FXML
    private TableColumn<Workshop, String> SchoolCol;

    @FXML
    private TableColumn<Workshop, String> DateCol;

    @FXML
    private TableColumn<Workshop, String> AmbaCol;

    @FXML
    private TableView<Ambassador> Ambas;

    @FXML
    private TableColumn<Ambassador, String> AmbaListCol;

    @FXML
    private TableColumn<Ambassador, Integer> PercCol;

    ObservableList<Workshop> wsObList = FXCollections.observableArrayList();
    ObservableList<Ambassador> ambObList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Connection conn = null;
        Statement stmt = null;
        String SQL = workshopDetailQuery(getWorkshopID());
        String SQL1 = ambasadorsQuery(getWorkshopID());
        String SQL2 = upcomingWorkshopQuery(getWorkshopID());
        String SQL3 = workshopStatsQuery(getWorkshopID());
        Workshop detail = new Workshop();
        Ambassador zaznam = null;


        try {
            conn = new PostgresConn().connect();
            conn.setAutoCommit(false);
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(SQL);

            while (rs.next()) {
                detail.setName(rs.getString("name"));
            }
            stmt.close();
            //----------------------------------------
            stmt = conn.createStatement();
            ResultSet rs1 = stmt.executeQuery(SQL1);

            while (rs1.next()) {
                ambObList.add(new Ambassador(rs1.getString("ambasador"), rs1.getInt("pocet")));
            }
            stmt.close();
            //---------------------------------------
            stmt = conn.createStatement();
            ResultSet rs2 = stmt.executeQuery(SQL2);

            while (rs2.next()) {
                wsObList.add(new Workshop(rs2.getString("school"),rs2.getString("amba"),
                        rs2.getString("date")));
            }

            stmt.close();
            //---------------------------------------
            stmt = conn.createStatement();
            ResultSet rs3 = stmt.executeQuery(SQL3);
            while (rs3.next()) {
                detail.setDuration(rs3.getInt("durat"));
                detail.setStudentCount(rs3.getInt("students"));
                detail.setRating(rs3.getInt("rating"));
            }
            conn.commit();
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
        }


        WsName.setText(detail.getName());
        AvgRating.setText(detail.getRating().toString());
        AvgAttendance.setText(detail.getStudentCount().toString());
        AvgTime.setText(detail.getDuration().toString());

        SchoolCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        AmbaCol.setCellValueFactory(new PropertyValueFactory<>("ambass"));
        DateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        futureWs.setItems(wsObList);

        AmbaListCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        PercCol.setCellValueFactory(new PropertyValueFactory<>("numOfWs"));
        Ambas.setItems(ambObList);

    }

    private String workshopDetailQuery (Integer workshopID){
        return "SELECT name FROM workshop WHERE workshop_id = " + workshopID.toString() + ";";
    }

    private String ambasadorsQuery (Integer workshopID){
       return "SELECT a.name ambasador, count(wt.ws_term_id) pocet " +
            "FROM ambassador a JOIN ws_term wt on a.ambassador_id = wt.ambassador_id " +
            "WHERE wt.workshop_id = " + workshopID.toString() + "GROUP BY a.name;";
    }

    private String upcomingWorkshopQuery (Integer workshopID){
        return "SELECT s.name school,to_char(a.ws_date_time,'DD.MM.YYYY HH24:MI') date,r.name amba " +
                "FROM ws_term a " +
                "JOIN school s ON a.school_id = s.school_id " +
                "JOIN ambassador r ON a.ambassador_id = r.ambassador_id " +
                "WHERE workshop_id = " + workshopID.toString() + ";";
    }

    private String workshopStatsQuery (Integer workshopID){
        return "SELECT avg(duration_minutes) durat, avg(num_of_students) students, avg(rating) rating " +
                "FROM ws_term WHERE workshop_id = " + workshopID.toString() + ";";
    }

}
