package sample;

import javafx.event.ActionEvent;
import sample.ScreenSelection.*;

import java.awt.*;
import java.io.*;

public class Controller {
    ScreenSelection SS;
    SelectionInformationContainer selectionInformationContainer;

    public void select(ActionEvent actionEvent) {
        selectionInformationContainer = new SelectionInformationContainer();
        SS = new ScreenSelection(selectionInformationContainer);
        SS.startSelectionCanvas();

    }



    public void monitorSelection(ActionEvent actionEvent) throws AWTException {
        SelectionMonitor SM = new SelectionMonitor(selectionInformationContainer);
        SM.createVisibleMonitor();

    }



    public void save(ActionEvent actionEvent) throws IOException {
        saveToFile("eyo",selectionInformationContainer);
    }

    public void load(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        loadFromFile("eyo");
    }

    public void saveToFile(String filename, SelectionInformationContainer data)throws IOException {
        FileOutputStream myFileOutputStream = new FileOutputStream("c://"+filename+".txt");
        ObjectOutputStream myObjectOutputStream = new ObjectOutputStream(myFileOutputStream);
        myObjectOutputStream.writeObject(data);
        myObjectOutputStream.close();
    }
    public void loadFromFile(String filename)throws IOException, ClassNotFoundException {
        FileInputStream myFileInputStream = new FileInputStream("c://"+filename+".txt");
        ObjectInputStream myObjectInputStream = new ObjectInputStream(myFileInputStream);
        selectionInformationContainer = (SelectionInformationContainer) myObjectInputStream.readObject();
        myObjectInputStream.close();
    }

    public void test(ActionEvent actionEvent) {
        System.out.print("pixel count/difference of 60,63,65  with offset 10: ");
        System.out.println(selectionInformationContainer.getColorCountWithOffset(60,63,65,10)); // R G B OFFSET
        System.out.print("pixel count/difference of 60,63,65 with no offset: ");
        System.out.println(selectionInformationContainer.getColorCount(60,63,65)); // R G B
        System.out.print("Start X: ");
        System.out.println(selectionInformationContainer.getStartX());
        System.out.print("End X: ");
        System.out.println(selectionInformationContainer.getEndX());
        System.out.print("Start Y: ");
        System.out.println(selectionInformationContainer.getStartY());
        System.out.print("End Y: ");
        System.out.println(selectionInformationContainer.getEndY());
        System.out.print("Center location of co-ordinates: ");
        int locations[] = selectionInformationContainer.getCenterLocationOfRectangle();
        System.out.print("x: "+locations[0]);
        System.out.println("y: "+locations[1]);
        System.out.print("Center location of co-ordinates with 10 offset");
        int locationsRandom[] = selectionInformationContainer.getCenterLocationOfRectangleWithRandomOffset(10);
        System.out.print("x: "+locationsRandom[0]);
        System.out.println("y: "+locationsRandom[1]);
        System.out.print("rectangle information: ");
        System.out.println(selectionInformationContainer.getSelectionRectangle());
        System.out.print("get number of pixels selected: ");
        System.out.println(selectionInformationContainer.getSelectionPixelAmmount());



    }

    public void monitorSelectionInvis(ActionEvent actionEvent) {
        SelectionMonitor SM = new SelectionMonitor(selectionInformationContainer);
        SM.createMonitor();
    }

    public void doComparison(ActionEvent actionEvent) {
        System.out.println(selectionInformationContainer.doColorsMatch(0));
    }
}
