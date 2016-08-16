package sample.ScreenSelection;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Indi on 7/16/2016.
 */
public class SelectionInformationContainer implements Serializable {
    private int startX;
    private int endX;
    private int startY;
    private int endY;
    private int selectionWidth;
    private int selectionHeight;
    private int selectionPixelAmmount;
    private int selectionPixelDifference;
    private boolean scannerIsLive = false;
    private Rectangle selectionRectangle;
    private ArrayList selectionPixelList;
    private ArrayList selectionRefreshedPixelList;
    private ArrayList<ArrayList> screenXasArray;
    private ArrayList<ArrayList> selectionXasArray;

    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private double Screen_width = screenSize.getWidth();
    private double Screen_height = screenSize.getHeight();

    public int getStartX() {
        return startX;
    }

    public void setStartX(int startX) {
        this.startX = startX;
    }

    public int getEndX() {
        return endX;
    }

    public void setEndX(int endX) {
        this.endX = endX;
    }

    public int getStartY() {
        return startY;
    }

    public void setStartY(int startY) {
        this.startY = startY;
    }

    public int getEndY() {
        return endY;
    }

    public void setEndY(int endY) {
        this.endY = endY;
    }

    public int getSelectionWidth() {
        return selectionWidth;
    }

    public void setSelectionWidth(int selectionWidth) {
        this.selectionWidth = selectionWidth;
    }

    public int getSelectionHeight() {
        return selectionHeight;
    }

    public void setSelectionHeight(int selectionHeight) {
        this.selectionHeight = selectionHeight;
    }

    public int getSelectionPixelAmmount() {
        return selectionPixelAmmount;
    }

    public void setSelectionPixelAmmount(int selectionPixelAmmount) {
        this.selectionPixelAmmount = selectionPixelAmmount;
    }

    public int getSelectionPixelDifference() {
        return selectionPixelDifference;
    }

    public void setSelectionPixelDifference(int selectionPixelDifference) {
        this.selectionPixelDifference = selectionPixelDifference;
    }

    public Rectangle getSelectionRectangle() {
        return selectionRectangle;
    }

    public void setSelectionRectangle(Rectangle selectionRectangle) {
        this.selectionRectangle = selectionRectangle;
    }

    public ArrayList getSelectionPixelList() {
        return selectionPixelList;
    }

    public void setSelectionPixelList(ArrayList selectionPixelList) {
        this.selectionPixelList = selectionPixelList;
    }

    public ArrayList getSelectionRefreshedPixelList() {
        return selectionRefreshedPixelList;
    }

    public void setSelectionRefreshedPixelList(ArrayList selectionRefreshedPixelList) {
        this.selectionRefreshedPixelList = selectionRefreshedPixelList;
    }

    public double getScreen_width() {
        return Screen_width;
    }

    public void setScreen_width(double screen_width) {
        Screen_width = screen_width;
    }

    public double getScreen_height() {
        return Screen_height;
    }

    public void setScreen_height(double screen_height) {
        Screen_height = screen_height;
    }

    public void setScannerIsLive(boolean scannerIsLive) {
        this.scannerIsLive = scannerIsLive;
    }

    public boolean isScannerIsLive() {
        return scannerIsLive;
    }
    public int[] getCenterLocationOfRectangle (){

        int locations[] = new int[2];
        int width  = (int) getSelectionRectangle().getWidth();
        int height = (int) getSelectionRectangle().getHeight();
        locations[0] = getSelectionRectangle().x +(width/2);
        locations[1] = getSelectionRectangle().y +(height/2);
        return locations;
    }
    private int randomOffset(int offset){
        Random rand = new Random();
        int  random = rand.nextInt(offset) + 1;
        return random;
    }
    public int[] getCenterLocationOfRectangleWithRandomOffset (int offset){
        int locations[] = new int[2];
        int width  = (int) getSelectionRectangle().getWidth();
        int height = (int) getSelectionRectangle().getHeight();
        locations[0] = getSelectionRectangle().x +(width/2)  + randomOffset(offset);
        locations[1] = getSelectionRectangle().y +(height/2) + randomOffset(offset);
        return locations;
    }
    public int getColorCount(int Rcolor,int Gcolor, int Bcolor) {
        int ammountOfColorPixels = 0;
        for(int i = 0; i< getSelectionRefreshedPixelList().size();i++){
            Color temp = (Color) getSelectionRefreshedPixelList().get(i);
            int R = temp.getRed();
            int G = temp.getGreen();
            int B = temp.getBlue();

            if(R == Rcolor && G == Gcolor && B == Bcolor){
                ammountOfColorPixels++;
            }
        }
        return ammountOfColorPixels;
    }
    public int getColorCountWithOffset(int Rcolor,int Gcolor, int Bcolor,int offset) {
        int ammountOfColorPixels = 0;
        for(int i = 0; i< getSelectionRefreshedPixelList().size();i++){
            Color temp = (Color) getSelectionRefreshedPixelList().get(i);
            int R = temp.getRed();
            int G = temp.getGreen();
            int B = temp.getBlue();

            if(Rcolor >= R-offset && Rcolor <= R+offset &&
                    Gcolor >= G-offset && Gcolor <= G+offset &&
                    Bcolor >= B-offset && Bcolor <= B+offset){
                ammountOfColorPixels++;
            }

        }
        return ammountOfColorPixels;
    }

    public boolean doColorsMatch(int offset){
        int pixelCount = 0;
        for(int i = 0; i < getSelectionPixelList().size();i++){
            if(getSelectionPixelList().get(i).equals(getSelectionRefreshedPixelList().get(i))){
                pixelCount++;
            }
        }
        if(pixelCount+offset >= getSelectionPixelAmmount()){
            return true;
        }
        return false;
    }

    public void debuggObject(String name){
        System.out.println("=-=-=-=-"+name +" Data: "+"=-=-=-=-");
        System.out.println("Screen width: "+getScreen_width());
        System.out.println("Screen height: "+getScreen_height());
        System.out.print("Start X: ");
        System.out.println(getStartX());
        System.out.print("End X: ");
        System.out.println(getEndX());
        System.out.print("Start Y: ");
        System.out.println(getStartY());
        System.out.print("End Y: ");
        System.out.println(getEndY());
        System.out.print("Center location of co-ordinates: ");
        int locations[] = getCenterLocationOfRectangle();
        System.out.print("X: "+locations[0]);
        System.out.println(" Y: "+locations[1]);
        System.out.print("Center location of co-ordinates with random(10) offset");
        int locationsRandom[] = getCenterLocationOfRectangleWithRandomOffset(10);
        System.out.print("X: "+locationsRandom[0]);
        System.out.println(" Y: "+locationsRandom[1]);
        System.out.print("Rectangle information:");
        System.out.println(getSelectionRectangle());
        System.out.print("Number of pixels selected: ");
        System.out.println(getSelectionPixelAmmount());
        System.out.println("Scanner is alive - "+isScannerIsLive());
        System.out.println("=-=-=-=- Monitoring difference: =-=-=-=-");
        System.out.print("Number of pixel that changed: ");
        System.out.println(getSelectionPixelDifference());
        System.out.println("=-=-=-=- end of: "+name+" =-=-=-=-");
        System.out.println("...");
        System.out.println("Screen canvas size");
        System.out.println(screenXasArray.size());
        System.out.println("Screen canvas Colors on Y255 X500");
        System.out.println(screenXasArray.get(500).get(255));
        System.out.println("...");
        System.out.println("Selection canvas size");
        System.out.println(selectionXasArray.size());
        System.out.println(selectionXasArray.get(1).size());
        System.out.println("Selection canvas");
        System.out.println(selectionXasArray.get(5).get(5));



    }
    public void debuggObjectWithColorValues(String name,int R,int G, int B, int offset){
        System.out.println("=-=-=-=-"+name +" Data: "+"=-=-=-=-");
        System.out.println("Screen width: "+getScreen_width());
        System.out.println("Screen height: "+getScreen_height());
        System.out.print("pixel count/difference of 60,63,65  with offset 10: ");
        System.out.println(getColorCountWithOffset(R,G,B,offset)); // R G B OFFSET
        System.out.print("pixel count/difference of 60,63,65 with no offset: ");
        System.out.println(getColorCount(R,G,B)); // R G B
        System.out.print("Start X: ");
        System.out.println(getStartX());
        System.out.print("End X: ");
        System.out.println(getEndX());
        System.out.print("Start Y: ");
        System.out.println(getStartY());
        System.out.print("End Y: ");
        System.out.println(getEndY());
        System.out.print("Center location of co-ordinates:");
        int locations[] = getCenterLocationOfRectangle();
        System.out.print(" X: "+locations[0]);
        System.out.println(" Y: "+locations[1]);
        System.out.print("Center location of co-ordinates with random(10) offset");
        int locationsRandom[] = getCenterLocationOfRectangleWithRandomOffset(10);
        System.out.print(" X: "+locationsRandom[0]);
        System.out.println(" Y: "+locationsRandom[1]);
        System.out.print("Rectangle information: ");
        System.out.println(getSelectionRectangle());
        System.out.print("Number of pixels selected: ");
        System.out.println(getSelectionPixelAmmount());
        System.out.println("Scanner is alive - "+isScannerIsLive());
        System.out.println("=-=-=-=- Monitoring difference: =-=-=-=-");
        System.out.print("Number of pixel that changed: ");
        System.out.println(getSelectionPixelDifference());
        System.out.println("=-=-=-=- end of: "+name+" =-=-=-=-");

    }
    // TODO WIP
    public void findRegionsColorSpectrum(){
        int  missingPixels =0,greyPixels = 0,blackPixels =0, whitePixels =0, greenPixels =0, bluePixels =0, redPixels =0,yellowPixels =0, lightBluePixels =0, purplePixels =0;
        int pixelsize = getSelectionPixelAmmount();
        for(int i = 0; i < pixelsize; i++){
            Color temp = (Color) getSelectionPixelList().get(i);
            int Red = temp.getRed();
            int Green = temp.getGreen();
            int Blue = temp.getBlue();
            //temp.
            if(Red == Green && Red == Blue){
                if(Red <= 40){
                    blackPixels++;
                }else if(Red < 220){
                    greyPixels++;
                }else{
                    whitePixels++;
                }
            }else if (Blue > Red && Blue > Green){
                // blue highest value
                // sort color in a blue subcatagory
                if(Green >= 125) {
                    lightBluePixels++;
                }else if(Green >= 200) {
                   // verylightBluePixels++;
                }else{
                    bluePixels++;
                }
            }else if(Red > Blue && Red > Green){
                // red highest value
                // sort color in a red subcatagory
                if(Green < Red ){
                    purplePixels++;
                }else{
                    redPixels++;
                }
            }else if(Green > Red && Green > Blue){
                // green highest value
                // sort color in a green subcatagory
                if( Green >= Blue*2 && Red >= Blue*2){
                    yellowPixels++;
                }else {
                    greenPixels++;
                }
            }else{
                missingPixels++;
            }

        }
        System.out.println("grey: "+greyPixels);
        System.out.println("white: "+whitePixels);
        System.out.println("black: "+blackPixels);
        System.out.println("green: "+greenPixels);
        System.out.println("blue: "+bluePixels);
        System.out.println("red: "+redPixels);
        System.out.println("light blue: "+lightBluePixels);
        System.out.println("yellow: "+yellowPixels);
        System.out.println("purple: "+purplePixels);
        System.out.println("missing pixeldata: "+missingPixels);
    }
/*
    public void scanScreenData(){
        int temp_screenwidth = (int) getScreen_width();
        int temp_screenheight = (int) getScreen_height();
        BufferedImage BI = robot.createScreenCapture( new Rectangle(0,0,temp_screenwidth,temp_screenheight));

        setScreenXasArray(new ArrayList(temp_screenheight));

        for (int Yas = 0; Yas < temp_screenheight;Yas++){
            // for every line on the screen
            ArrayList temp_colorArray = new ArrayList(temp_screenwidth);
            for(int Xas = 0; Xas < temp_screenwidth;Xas++ ){
                // for every line on the screen, check its Xas co-ords
                temp_colorArray.add(Xas,new Color(BI.getRGB(Xas, Yas)));
            }
            getScreenXasArray().add(temp_colorArray);
        }
    } */

    public ArrayList getScreenXasArray() {
        return screenXasArray;
    }

    public void setScreenXasArray(ArrayList screenXasArray) {
        this.screenXasArray = screenXasArray;
    }

    public ArrayList<ArrayList> getSelectionXasArray() {
        return selectionXasArray;
    }

    public void setSelectionXasArray(ArrayList<ArrayList> selectionXasArray) {
        this.selectionXasArray = selectionXasArray;
    }
}

/*
            if(Red >= Blue*2 && Green >= Blue*2){
                yellowPixels++;
            }else if(Red >= Green*2 && Blue >= Green*2){
                purplePixels++;
            } else if(Green >= Red*2 && Blue >= Red*2) {
                turquoisePixels++;
            } else


            // try 2

            if(Red < 40 && Green < 40 && Blue < 40){
                 blackPixels++;
            }else if(Red > 240 && Green > 240 && Blue > 240){
                 whitePixels++;
            }else if (Red > 40 && Red < 100 && Blue > 40 && Blue < 100 && Red > 40 && Red < 100 ){
                greyPixels++;
            }else if (Blue > Red && Blue > Green){
                 // blue highest value
                 // sort color in a blue subcatagory
                 if(Blue >= Red*2 && Green >= Red*2) {
                     turquoisePixels++;
                 }else{
                     bluePixels++;
                 }
            }else if(Red > Blue && Red > Green){
                 // red highest value
                 // sort color in a red subcatagory
                 if(Red >= Green*2 && Blue >= Green*2){
                     purplePixels++;
                 }else{
                     redPixels++;
                 }
            }else if(Green > Red && Green > Blue){
                 // green highest value
                 // sort color in a green subcatagory
                 if( Green >= Blue*2 && Red >= Blue*2){
                     yellowPixels++;
                 }else {
                     greenPixels++;
                 }
            }else{
                System.out.println(temp);
                missingPixels++;
            }


 */