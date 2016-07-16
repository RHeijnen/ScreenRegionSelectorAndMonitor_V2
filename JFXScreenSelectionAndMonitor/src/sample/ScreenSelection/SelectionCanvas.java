package sample.ScreenSelection;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static java.awt.MouseInfo.getPointerInfo;

/**
 * Created by Indi on 7/16/2016.
 */
public class SelectionCanvas {
    private GraphicsContext gc;
    private int offset = 13;
    private Stage stage;
    private Canvas canvas;
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private double Screen_width = screenSize.getWidth();
    private double Screen_height = screenSize.getHeight();
    SelectionInformationContainer selectionInformationContainer;
    Robot robot;

    public SelectionCanvas(SelectionInformationContainer selectionInformationContainer){
        this.selectionInformationContainer = selectionInformationContainer;
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }

    }
    public void DrawCanvas(){
        stage = new Stage();
        stage.initStyle(StageStyle.TRANSPARENT);
        canvas = new Canvas(Screen_width, Screen_height);
        canvas.setStyle("-fx-background-color: rgba(255, 255, 255, 0.1);" +
                "-fx-effect: dropshadow(gaussian, white, 50, 0, 0, 0);" +
                "-fx-background-insets: 0;"
        );

        StackPane stackPane = new StackPane();
        stackPane.setStyle(
                "-fx-background-color: rgba(255, 255, 255, 0.1);" +
                        "-fx-effect: dropshadow(gaussian, white, 50, 0, 0, 0);" +
                        "-fx-background-insets: 0;"
        );

        gc = canvas.getGraphicsContext2D();
        canvas.setOnMousePressed(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                selectionInformationContainer.setStartX((int) getPointerInfo().getLocation().getX());
                selectionInformationContainer.setStartY((int) getPointerInfo().getLocation().getY());
            }
        });
        canvas.setOnMouseReleased(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                selectionInformationContainer.setEndX((int) getPointerInfo().getLocation().getX());
                selectionInformationContainer.setEndY((int) getPointerInfo().getLocation().getY());
                selectionInformationContainer.setSelectionWidth(selectionInformationContainer.getEndX() - selectionInformationContainer.getStartX());
                selectionInformationContainer.setSelectionHeight(selectionInformationContainer.getEndY() - selectionInformationContainer.getStartY());
                selectionInformationContainer.setSelectionPixelAmmount(selectionInformationContainer.getSelectionWidth() * selectionInformationContainer.getSelectionHeight());
                selectionInformationContainer.setSelectionRectangle(new Rectangle(selectionInformationContainer.getStartX(),
                        selectionInformationContainer.getStartY(),
                        selectionInformationContainer.getSelectionWidth(),selectionInformationContainer.getSelectionHeight()));

                //  gc.fillRect(event.getSceneX(),event.getScreenY()+10,4,4);
                javafx.scene.paint.Color c = new javafx.scene.paint.Color(0, 0, 1.0, 0.3);

                gc.setFill(c);
                gc.fillRect(
                        selectionInformationContainer.getStartX(),
                        selectionInformationContainer.getStartY()+offset,
                        selectionInformationContainer.getSelectionWidth(),selectionInformationContainer.getSelectionHeight());

                confirmationPopUp popup = new confirmationPopUp(stage);
                popup.createWindow();
            }
        });


        stackPane.getChildren().add(canvas);
        Scene scene = new Scene(stackPane, Screen_width, Screen_height);
        scene.setFill(null);
        stage.setScene(scene);
        stage.show();

    }

    public void scanInitialPixelData(){
        BufferedImage BI = robot.createScreenCapture(selectionInformationContainer.getSelectionRectangle());
        int temp_pixelcount = selectionInformationContainer.getSelectionPixelAmmount();
        selectionInformationContainer.setSelectionPixelList(new ArrayList(temp_pixelcount));

        int i = 0;
        for (int x = 0; x < BI.getWidth(); x++) {
            for (int y = 0; y < BI.getHeight(); y++) {
                selectionInformationContainer.getSelectionPixelList().add(i,new Color(BI.getRGB(x, y)));
               // getSelectionPixels()[i] = new Color(BI.getRGB(x, y));
                i++;

            }
        }
        selectionInformationContainer.setSelectionRefreshedPixelList(selectionInformationContainer.getSelectionPixelList());
    }


    /////////////////////////////////////



    private class confirmationPopUp {
        private javafx.scene.control.Button but_agree;
        private javafx.scene.control.Button but_disagree;
        Stage canvasStage;

        public confirmationPopUp(Stage stage) {
            this.canvasStage = stage;
        }

        public void createWindow(){
            Stage stage = new Stage();
            stage.initStyle(StageStyle.TRANSPARENT);
            Pane pane = new Pane();
            pane.setStyle(
                    "-fx-background-color: rgba(201, 159, 145, 0.6);" +
                            "-fx-effect: dropshadow(gaussian, white, 50, 0, 0, 0);" +
                            "-fx-background-insets: 0;"
            );


            but_disagree = new javafx.scene.control.Button();
            but_disagree.setLayoutX(25);
            but_disagree.setLayoutY(10);
            but_disagree.setText("Retry");
            but_disagree.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event)  {
                    stage.hide();
                    canvasStage.hide();
                    SelectionCanvas reset = new SelectionCanvas(selectionInformationContainer);
                    reset.DrawCanvas();
                }
            });
            but_agree = new javafx.scene.control.Button();
            but_agree.setLayoutX(75);
            but_agree.setLayoutY(10);
            but_agree.setText("Accept");
            but_agree.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event)  {
                    stage.hide();
                    canvasStage.hide();
                    scanInitialPixelData();

                }
            });

            pane.getChildren().add(but_disagree);
            pane.getChildren().add(but_agree);

            Scene scene = new Scene(pane, 150,50 );
            scene.setFill(null);
            stage.setScene(scene);
            stage.show();
        }
    }

}
