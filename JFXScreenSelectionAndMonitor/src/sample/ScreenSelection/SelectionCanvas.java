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

    private int selectionSelector = 0; // will be 1/2/3/4 and used in a switch to determine what the orientation is of the selection
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
        canvas.setOnMouseDragged(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                gc.clearRect(0 ,
                        0,
                        Screen_width,
                        Screen_height);
                javafx.scene.paint.Color c = new javafx.scene.paint.Color(0, 0, 1.0, 0.3);
                gc.setFill(c);
                // if start selection values are HIGHER than drag values on Xas [DRAG RIGHT]
                if ((int) getPointerInfo().getLocation().getX() > selectionInformationContainer.getStartX()){
                    if (getPointerInfo().getLocation().getY() < selectionInformationContainer.getStartY()){
                        //if start selection values are HIGHER than drag values on Yas [DRAG UP]
                        gc.fillRect(
                                selectionInformationContainer.getStartX(),
                                (int) getPointerInfo().getLocation().getY()+offset,
                                (int) getPointerInfo().getLocation().getX() - selectionInformationContainer.getStartX() ,
                                selectionInformationContainer.getStartY() - (int) getPointerInfo().getLocation().getY()
                        );
                        selectionSelector = 1;
                    }else if((int) getPointerInfo().getLocation().getY() > selectionInformationContainer.getStartY()) {
                        //if start selection values are LOWER than drag values on Yas [DRAG DOWN]
                        gc.fillRect( // DONE
                                selectionInformationContainer.getStartX(),
                                selectionInformationContainer.getStartY()+offset,
                                (int) getPointerInfo().getLocation().getX() - selectionInformationContainer.getStartX() ,
                                (int) getPointerInfo().getLocation().getY() - selectionInformationContainer.getStartY()
                        );
                        selectionSelector = 2;
                    }

                    // if start selection values are LOWER than drag values on Xas [DRAG LEFT]
                }else if ((int) getPointerInfo().getLocation().getX() < selectionInformationContainer.getStartX()) {
                    if (getPointerInfo().getLocation().getY() < selectionInformationContainer.getStartY()){
                        //if start selection values are HIGHER than drag values on Yas [DRAG UP]
                        gc.fillRect( // DONE
                                (int) getPointerInfo().getLocation().getX(),
                                (int) getPointerInfo().getLocation().getY() + offset,
                                selectionInformationContainer.getStartX() - (int) getPointerInfo().getLocation().getX(),
                                selectionInformationContainer.getStartY() - (int) getPointerInfo().getLocation().getY()
                        );
                        selectionSelector = 3;
                    // end if top right bot left
                    }else if ((int) getPointerInfo().getLocation().getY() > selectionInformationContainer.getStartY()) {
                        //if start selection values are LOWER than drag values on Yas [DRAG DOWN]
                        gc.fillRect( // DONE
                                (int) getPointerInfo().getLocation().getX(),
                                selectionInformationContainer.getStartY() + offset,
                                selectionInformationContainer.getStartX() - (int) getPointerInfo().getLocation().getX(),
                                (int) getPointerInfo().getLocation().getY() - selectionInformationContainer.getStartY()
                        );
                        selectionSelector = 4;

                    }
                }


            }
        });
        canvas.setOnMouseReleased(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                setData(selectionSelector);
                // cut
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

    public void setData(int selection){
        switch (selection) {
            case 1:
                selectionInformationContainer.setEndY(selectionInformationContainer.getStartY());
                selectionInformationContainer.setStartY((int) getPointerInfo().getLocation().getY());
                selectionInformationContainer.setEndX((int) getPointerInfo().getLocation().getX());
                selectionInformationContainer.setSelectionWidth(selectionInformationContainer.getEndX() - selectionInformationContainer.getStartX());
                selectionInformationContainer.setSelectionHeight(selectionInformationContainer.getEndY() - selectionInformationContainer.getStartY());
                selectionInformationContainer.setSelectionPixelAmmount(selectionInformationContainer.getSelectionWidth() * selectionInformationContainer.getSelectionHeight());
                selectionInformationContainer.setSelectionRectangle(new Rectangle(
                        selectionInformationContainer.getStartX(),
                        selectionInformationContainer.getStartY(),
                        selectionInformationContainer.getSelectionWidth(),
                        selectionInformationContainer.getSelectionHeight()));
                break;
            case 2:
                selectionInformationContainer.setEndX((int) getPointerInfo().getLocation().getX());
                selectionInformationContainer.setEndY((int) getPointerInfo().getLocation().getY());
                selectionInformationContainer.setSelectionWidth(selectionInformationContainer.getEndX() - selectionInformationContainer.getStartX());
                selectionInformationContainer.setSelectionHeight(selectionInformationContainer.getEndY() - selectionInformationContainer.getStartY());
                selectionInformationContainer.setSelectionPixelAmmount(selectionInformationContainer.getSelectionWidth() * selectionInformationContainer.getSelectionHeight());
                selectionInformationContainer.setSelectionRectangle(new Rectangle(selectionInformationContainer.getStartX(),
                        selectionInformationContainer.getStartY(),
                        selectionInformationContainer.getSelectionWidth(),selectionInformationContainer.getSelectionHeight()));
                break;
            case 3:
                selectionInformationContainer.setEndY(selectionInformationContainer.getStartY());
                selectionInformationContainer.setEndX(selectionInformationContainer.getStartX());
                selectionInformationContainer.setStartY((int) getPointerInfo().getLocation().getY());
                selectionInformationContainer.setStartX((int) getPointerInfo().getLocation().getX());
                selectionInformationContainer.setSelectionWidth(selectionInformationContainer.getEndX() - selectionInformationContainer.getStartX());
                selectionInformationContainer.setSelectionHeight(selectionInformationContainer.getEndY() - selectionInformationContainer.getStartY());
                selectionInformationContainer.setSelectionPixelAmmount(selectionInformationContainer.getSelectionWidth() * selectionInformationContainer.getSelectionHeight());
                selectionInformationContainer.setSelectionRectangle(new Rectangle(
                        selectionInformationContainer.getStartX(),
                        selectionInformationContainer.getStartY(),
                        selectionInformationContainer.getSelectionWidth(),
                        selectionInformationContainer.getSelectionHeight()));

                break;
            case 4:
                selectionInformationContainer.setEndY((int) getPointerInfo().getLocation().getY());
                selectionInformationContainer.setEndX(selectionInformationContainer.getStartX());
                selectionInformationContainer.setStartX((int) getPointerInfo().getLocation().getX());
                selectionInformationContainer.setSelectionWidth(selectionInformationContainer.getEndX() - selectionInformationContainer.getStartX());
                selectionInformationContainer.setSelectionHeight(selectionInformationContainer.getEndY() - selectionInformationContainer.getStartY());
                selectionInformationContainer.setSelectionPixelAmmount(selectionInformationContainer.getSelectionWidth() * selectionInformationContainer.getSelectionHeight());
                selectionInformationContainer.setSelectionRectangle(new Rectangle(
                        selectionInformationContainer.getStartX(),
                        selectionInformationContainer.getStartY(),
                        selectionInformationContainer.getSelectionWidth(),
                        selectionInformationContainer.getSelectionHeight()));
                break;

        }
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
    public void scanInitialSelectionPixelData(){
        BufferedImage BI = robot.createScreenCapture(selectionInformationContainer.getSelectionRectangle());
        int temp_selectionWidth = BI.getWidth();
        int temp_selectionHeight = BI.getHeight();
        selectionInformationContainer.setSelectionXasArray(new ArrayList(temp_selectionHeight));

        for(int Yas = 0; Yas< temp_selectionHeight;Yas++){
            ArrayList temp_sublist = new ArrayList(temp_selectionWidth);
            for(int Xas = 0; Xas < temp_selectionWidth;Xas++){
                temp_sublist.add(Xas,new Color(BI.getRGB(Xas, Yas)));
                System.out.println(new Color(BI.getRGB(Xas, Yas)));
            }
            selectionInformationContainer.getSelectionXasArray().add(temp_sublist);
        }
        System.out.println("X5 Y5");
        System.out.println(new Color((BI.getRGB(5, 5))));
    }

    public void setOffset(int offset) {
        this.offset = offset;
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
                    scanInitialSelectionPixelData();

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
