package sample.ScreenSelection;

/**
 * Created by Indi on 7/16/2016.
 */
public class ScreenSelection {
    ScreenSelection screenSelection;
    SelectionInformationContainer selectionInformationContainer;

    public ScreenSelection(SelectionInformationContainer selectionInformationContainer){
        this.screenSelection = this;
        this.selectionInformationContainer = selectionInformationContainer;
        //selectionInformationContainer = new SelectionInformationContainer();

    }

    public void startSelectionCanvas(){
        SelectionCanvas selectionCanvas = new SelectionCanvas(selectionInformationContainer);
        selectionCanvas.DrawCanvas();

    }



}
