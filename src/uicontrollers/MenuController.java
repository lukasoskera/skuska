package uicontrollers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;

public class MenuController {

    @FXML
    void ShowAmbasadorList(ActionEvent event) throws IOException {
        Parent root2 = FXMLLoader.load(getClass().getResource("/fxml/AmbasadorListUI.fxml"));
        Stage AmbasadorStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        AmbasadorStage.getIcons().add(new Image("img/Logo.png"));
        AmbasadorStage.setTitle("Pre Stredoškolákov");
        AmbasadorStage.setScene(new Scene(root2, 828, 556));
        AmbasadorStage.show();
    }

    @FXML
    void ShowSchoolList(ActionEvent event) throws IOException {
        Stage SchoolStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        new SchoolListController().preparePage(SchoolStage, -1, 0);
    }

    @FXML
    void ShowWorkshopList(ActionEvent event) throws IOException {
        Parent root4 = FXMLLoader.load(getClass().getResource("/fxml/WorkshopListUII.fxml"));
        Stage WorkshopStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        WorkshopStage.getIcons().add(new Image("img/Logo.png"));
        WorkshopStage.setTitle("Pre Stredoškolákov");
        WorkshopStage.setScene(new Scene(root4, 828, 556));
        WorkshopStage.show();
    }

}
