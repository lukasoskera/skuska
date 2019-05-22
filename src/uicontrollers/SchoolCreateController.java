package uicontrollers;

import dbconnection.PostgresConn;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SchoolCreateController {

    @FXML
    private TextField schName;

    @FXML
    private TextField street;

    @FXML
    private TextField address;

    @FXML
    private TextField city;

    @FXML
    private TextField region;

    @FXML
    private TextField studentCount;

    @FXML
    private TextField language;

    @FXML
    private TextField headmName;

    @FXML
    private TextField headmPhone;

    @FXML
    private TextField headmEmail;

    @FXML
    private TextField secPhone;

    @FXML
    private TextField secEmail;

    @FXML
    private TextField teachName;

    @FXML
    private TextField teachEmail;

    @FXML
    private Button backButton;

    @FXML
    private Button addButton ;

    @FXML
    void pridajSkolu(ActionEvent event) throws IOException {

        String SQL = "INSERT INTO public.school" +
                "(name, num_of_students, street, address, city, region, language, " +
                "headmaster_name, headmaster_mail, headmaster_phone, secretary_mail, " +
                "secretary_phone, our_teacher_name, our_teacher_mail) VALUES " +
                "( '" + schName.getText() + "', " + studentCount.getText() + ", '" +
                street.getText() + "', '" + address.getText() + "', '" + city.getText()
                + "', '" + region.getText() + "', '" + language.getText() + "', '" +
                headmName.getText() + "', '" + headmEmail.getText() + "', '" + headmPhone.getText()
                + "', '" + secEmail.getText() + "', '" + secPhone.getText() + "', '" +
                teachName.getText() + "', '" + teachEmail.getText() + "') RETURNING school_id";

        int id = 0;

        try (Connection conn = new PostgresConn().connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SQL)) {

            while (rs.next()) {
                id = rs.getInt("school_id");
            }

            stmt.close();
            if (conn != null)
                conn.close();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        SchoolDetailController controller = new SchoolDetailController();
        controller.setSchoolID(id);

        FXMLLoader loader = new FXMLLoader();
        loader.setController(controller);
        loader.setLocation(getClass().getResource("/fxml/SchoolDetailUI.fxml"));

        Stage SchoolDetail = new Stage();
        SchoolDetail.getIcons().add(new Image("img/Logo.png"));
        SchoolDetail.setTitle("Pre Stredoškolákov");
        SchoolDetail.setScene(new Scene(loader.load()));
        SchoolDetail.show();

        ((Node)(event.getSource())).getScene().getWindow().hide();
    }

    @FXML
    void zrusPridavanie(ActionEvent event) throws IOException{
        Stage SchoolStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        new SchoolListController().preparePage(SchoolStage, -1, 0);
    }

}
