# ScreenRegionSelectorAndMonitor_V2
version 2

! JAVAFX Version !

About : 

Create's a canvas the mouse can create a rectangle on, much like most operating systems do when holding and dragging the mouse.
This selection can then be monitored on changes, and with a simple implementation of a TimerTask can be used to create simplefied and automatic UI tests.

Currently working on:
- A way to interpret the given pixels, image recognition. 
- A way to scan the selection on the entire screen.

How to use :

Create a new "SelectionInformationContainer", this will hold the screen selections data. like so :

    SelectionInformationContainer selectionInformationContainer;
    
Now you've created an empty object reffrence, in order to fill it we call the screen selector like so :
    selectionInformationContainer = new SelectionInformationContainer();
    ScreenSelection SS new ScreenSelection(selectionInformationContainer);
    // Created an container to hold the data, and created a selection object, call the function to start the canvas like so :
    SS.startSelectionCanvas();

You will now see a visual white layer on your screen, click and drag a selection and accept its result, afterwards we will start the
visible monitor to update the selection, and let the user see what is actually happening. 
        SelectionMonitor SM = new SelectionMonitor(selectionInformationContainer);
        SM.createVisibleMonitor();
        //.createMonitor(); for invisible


Functions :

-=-= Selection information -=-=
getScreen_width()                                          - returns screens width
getScreen_height()                                         - returns screen height
getColorCountWithOffset(R,G,B, INT offset)                 - Returns number of pixels that match the RGB values, -/+ offset
getColorCount(R,G,B)                                       - Returns number of pixels that match the RGB values
getStartX()                                                - Returns the starting position (X as) of the selection
getEndX()                                                  - Returns the ending position (X as) of the selection
getStartY()                                                - Returns the starting position (Y as) of the selection
getEndY()                                                  - Returns the ending position (Y as) of the selection
getCenterLocationOfRectangle()                             - Returns array[2] containing X and Y values of selection's center
getCenterLocationOfRectangleWithRandomOffset(INT offset)   - Returns array[2] containing X and Y values of selection's center with offset
getSelectionRectangle()                                    - Returns Java rectangle information of the selection
getSelectionPixelAmmount()                                 - Returns the ammount of pixels selected
isScannerIsLive()                                          - Returns true if the monitor has been started on the selection
-=-= With Monitor on -=-=
getSelectionPixelDifference()                              - Returns the ammount of changed pixels, value dynamicly changes.
