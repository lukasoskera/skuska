import dbconnection.PostgresConn;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;

public class Main extends Application {

   // Statement stat = null;
   // ResultSet rset = null;

    @Override
    public void start(Stage MenuStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/MenuUI.fxml"));
        MenuStage.getIcons().add(new Image("img/Logo.png"));
        MenuStage.setTitle("Pre Stredoškolákov");
        MenuStage.setScene(new Scene(root, 600, 400));
        MenuStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
