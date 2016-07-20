package ViewControllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import sample.FXController;

/**
 * Created by Indi on 1/27/2016.
 */
public class SuperView {

    @FXML
    public void CloseFunction() {
        Platform.exit();
    }

    @FXML
    public void HomeFunction() {
        FXController logout = new FXController();
        logout.setMainWindow("Home", "/FXML/App/ApplicationMenuWindow.fxml");
    }

    @FXML
    public void AboutFunction() {
        FXController logout = new FXController();
        logout.setMainWindow("Home", "/FXML/App/AboutViewWindow.fxml");
    }

}
