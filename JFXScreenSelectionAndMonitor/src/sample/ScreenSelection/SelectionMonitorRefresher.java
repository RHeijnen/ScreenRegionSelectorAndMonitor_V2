package sample.ScreenSelection;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.TimerTask;

/**
 * Created by Indi on 7/16/2016.
 */
public class SelectionMonitorRefresher  extends TimerTask {
    private SelectionInformationContainer SIC;
    private Robot robot;
    private boolean runnable = true;
    private BufferedImage bf;


    public SelectionMonitorRefresher(SelectionInformationContainer SIC){
        this.SIC = SIC;
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }
    public void scanScreenData(){
        int temp_screenwidth = (int) SIC.getScreen_width();
        int temp_screenheight = (int) SIC.getScreen_height();
        BufferedImage BI = robot.createScreenCapture( new Rectangle(0,0,temp_screenwidth,temp_screenheight));

        SIC.setScreenXasArray(new ArrayList(temp_screenheight));

        for (int Yas = 0; Yas < temp_screenheight;Yas++){
            // for every line on the screen
            ArrayList temp_colorArray = new ArrayList(temp_screenwidth);
            for(int Xas = 0; Xas < temp_screenwidth;Xas++ ){
                // for every line on the screen, check its Xas co-ords
                temp_colorArray.add(Xas,new Color(BI.getRGB(Xas, Yas)));
            }
            SIC.getScreenXasArray().add(temp_colorArray);
        }
    }

    @Override
    public void run() {
        runnable = false; // guard
        int temp_pixelcount = SIC.getSelectionPixelAmmount();
        ArrayList TEMP_Pixel_Array  = new ArrayList(temp_pixelcount);
        bf = robot.createScreenCapture(SIC.getSelectionRectangle());
        int changecounter = 0;
        int i = 0;
        for (int x = 0; x < bf.getWidth(); x++) {
            for (int y = 0; y < bf.getHeight(); y++) {
                TEMP_Pixel_Array.add(i,new Color(bf.getRGB(x, y)));
                // TODO check double check
                if(!TEMP_Pixel_Array.get(i).equals(SIC.getSelectionPixelList().get(i))){
                    changecounter++;
                }
                i++;
            }
        }
        SIC.setSelectionRefreshedPixelList(TEMP_Pixel_Array);
        //System.out.println(SIC.getSelectionRefreshedPixelList().get(4));
        SIC.setSelectionPixelDifference(changecounter);

        runnable = true; // guard

    }
}
