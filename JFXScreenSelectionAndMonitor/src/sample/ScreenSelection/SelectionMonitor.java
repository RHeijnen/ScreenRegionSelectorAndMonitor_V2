package sample.ScreenSelection;

import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.awt.*;
import java.util.Timer;

/**
 * Created by Indi on 7/16/2016.
 */
public class SelectionMonitor {
    SelectionInformationContainer SIC;
    Stage stage;
    ImageView IV;
    Timer timer;

    public SelectionMonitor(SelectionInformationContainer SIC){
        this.SIC = SIC;
    }
    public void createMonitor(){
        if(!SIC.isScannerIsLive()) {
            attachShutDownHook();
            new Thread() {
                public void run() {
                    timer = new Timer();
                    timer.schedule(new SelectionMonitorRefresher(SIC), 0, 10);

                }
            }.start();
        }else{
            System.out.println("WARNING: Scanner already live!");
        }

    }
    public void createVisibleMonitor() throws AWTException {
        if(!SIC.isScannerIsLive()) {

            stage = new Stage();

            stage.initStyle(StageStyle.DECORATED);
            IV = new ImageView();
            StackPane stackPane = new StackPane();
            stackPane.getChildren().add(IV);
            stackPane.setStyle("-fx-background-color: #c0c9ff");
            Scene scene;
            // if selection is smaller than 150 make custom window
            if (SIC.getSelectionWidth() < 150) {
                scene = new Scene(stackPane, 150, 150);
            } else {
                scene = new Scene(stackPane, SIC.getSelectionWidth() + 25, SIC.getSelectionHeight() + 25);
            }
            IV.setFitHeight(SIC.getSelectionHeight());
            IV.setFitWidth(SIC.getSelectionWidth());

            scene.setFill(null);
            stage.setScene(scene);
            stage.show();
            Draw();
        }else{
            System.out.println("WARNING: Scanner already live!");
        }
    }



    public void Draw() {

        attachShutDownHook();
        new Thread() {
            public void run() {
                timer = new Timer();
                timer.schedule(new SelectionMonitorVisibleRefresher(SIC,
                        SIC.getSelectionPixelList(),
                        IV,
                        SIC.getSelectionPixelAmmount(),
                        SIC.getSelectionRectangle()), 0, 10);
            }
        }.start();

    }

    // shut down hook for the timer works on System.exit(0);
    public void attachShutDownHook() {
        SIC.setScannerIsLive(true);
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                SIC.setScannerIsLive(false);
                timer.cancel();
            }
        });


    }

}
