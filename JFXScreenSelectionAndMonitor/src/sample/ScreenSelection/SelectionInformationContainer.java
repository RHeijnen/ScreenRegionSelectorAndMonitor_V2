package sample.ScreenSelection;

import java.awt.*;
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
        int width = (int) getSelectionRectangle().getWidth();
        int height =(int) getSelectionRectangle().getHeight();
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
        int width = (int) getSelectionRectangle().getWidth();
        int height =(int) getSelectionRectangle().getHeight();
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
        if(pixelCount == getSelectionPixelAmmount()){
            return true;
        }
        return false;
    }

}
