package uicontrollers;
import dbconnection.PostgresConn;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.stage.Window;


import java.io.IOException;
import java.sql.*;

public class DeleteController {

    private Integer schoolID;
    private SchoolDetailController deletedDetail;
    private Stage parentStage;

    public Stage getParentStage() {
        return parentStage;
    }

    public void setParentStage(Stage parentStage) {
        this.parentStage = parentStage;
    }

    public Integer getSchoolID() {
        return schoolID;
    }

    public void setSchoolID(Integer schoolID) {
        this.schoolID = schoolID;
    }

    @FXML
    private Button finalDelete;

    @FXML
    private Button exitWindow;

    @FXML
    void closeWindow(ActionEvent event) {
        Stage stage = (Stage) exitWindow.getScene().getWindow();
        stage.close();
    }

    @FXML
    void deleteRecord(ActionEvent event) {
        try {
             Connection conn = new PostgresConn().connect();
             PreparedStatement pstmt = conn.prepareStatement(DeleteQuery());
             pstmt.setInt(1,schoolID);
             pstmt.executeUpdate();
        }
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();

        try {
            Stage SchoolStage = getParentStage();
            new SchoolListController().preparePage(SchoolStage, -1, 0);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

        private String DeleteQuery (){
            return "DELETE FROM school WHERE school_id = ? ";
        }
    }

