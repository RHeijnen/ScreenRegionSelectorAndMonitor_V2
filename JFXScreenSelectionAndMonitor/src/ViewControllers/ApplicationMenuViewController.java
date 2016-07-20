package ViewControllers;

import javafx.event.ActionEvent;
import sample.ScreenSelection.ScreenSelection;
import sample.ScreenSelection.SelectionInformationContainer;
import sample.ScreenSelection.SelectionMonitor;

import java.awt.*;
import java.io.*;

/**
 * Created by root on 19-7-2016.
 */
public class ApplicationMenuViewController extends SuperView {
    ScreenSelection SS;
    SelectionInformationContainer selectionInformationContainer;

    public void selectArea(ActionEvent actionEvent) {
        selectionInformationContainer = new SelectionInformationContainer();
        SS = new ScreenSelection(selectionInformationContainer);
        SS.startSelectionCanvas();

    }



    public void monitorSelectionVisible(ActionEvent actionEvent) throws AWTException {
        SelectionMonitor SM = new SelectionMonitor(selectionInformationContainer);
        SM.createVisibleMonitor();

    }
    public void monitorSelectionInvisible(ActionEvent actionEvent) {
        SelectionMonitor SM = new SelectionMonitor(selectionInformationContainer);
        SM.createMonitor();
    }


    public void saveObject(ActionEvent actionEvent) throws IOException {
        saveToFile("eyo",selectionInformationContainer);
    }

    public void loadObject(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
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

        selectionInformationContainer.findRegionsColorSpectrum();

    }



    public void doComparison(ActionEvent actionEvent) {
        System.out.println(selectionInformationContainer.doColorsMatch(0));
    }

    public void debugObjectData(ActionEvent actionEvent) {
        selectionInformationContainer.debuggObject("Sample Selection");
    }
}
