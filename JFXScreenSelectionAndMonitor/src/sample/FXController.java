package sample;
/**
 * Created by Indi on 1/26/2016.
 */
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class FXController extends Main {
    //Double for mouse Coords
    double x, y;

    private void addDragListeners(final Node n, Stage primaryStage){

        n.setOnMousePressed((MouseEvent mouseEvent) -> {
            this.x = n.getScene().getWindow().getX() - mouseEvent.getScreenX();
            this.y = n.getScene().getWindow().getY() - mouseEvent.getScreenY();
        });

        n.setOnMouseDragged((MouseEvent mouseEvent) -> {
            Main.fxStage.setX(mouseEvent.getScreenX() + this.x);
            Main.fxStage.setY(mouseEvent.getScreenY() + this.y);
        });
    }

    public void setMainWindow(String Title, String fxmlURL) {
        try {
            Main.fxParent = FXMLLoader.load(getClass().getResource(fxmlURL));
            Main.fxStage.setTitle(Title);
          //App.theStage.setScene(new Scene(App.root, App.GetScreenWorkingWidth() / 1.1, App.GetScreenWorkingHeight() / 1.1));
            Main.fxStage.setScene(new Scene(Main.fxParent,400, 600));
          //App.theStage.centerOnScreen();
            Main.fxStage.show();
            //Listens for 'Dragging'
            addDragListeners(Main.fxParent, Main.fxStage);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

