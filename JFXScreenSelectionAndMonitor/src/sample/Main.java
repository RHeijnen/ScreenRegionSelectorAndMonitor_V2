package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {
    public static Stage fxStage;
    public static Parent fxParent;
    @Override
    public void start(Stage primaryStage) throws Exception{
        fxStage = primaryStage;
        fxStage.initStyle(StageStyle.UNDECORATED);

        FXController logout = new FXController();
        logout.setMainWindow("Home", "/FXML/App/ApplicationMenuWindow.fxml");
    }


    public static void main(String[] args) {
        launch(args);
    }
}
