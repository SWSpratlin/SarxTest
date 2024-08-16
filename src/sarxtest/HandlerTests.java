package sarxtest;

import processing.core.PApplet;
import processing.core.PImage;
import java.awt.Point;
import java.util.Arrays;
import java.util.HashMap;

public class HandlerTests extends PApplet{

    public static PImage a;
    public static PImage bg;
    public static PImage map;
    public static Utils utils;
    public static CamHandler handler;
    public static HashMap<Integer, Point> coords;
    public static ArrayRot rot;
    public int d = 5;
    public static int max;

    public void settings(){
        size(1280, 720);
        utils = new Utils();
        System.out.println(utils.sysInfo());
        handler = new CamHandler(utils);
        CamHandler.initCam();

        int w = CamHandler.cam.getViewSize().width;
        int h = CamHandler.cam.getViewSize().height;
        a = createImage(w, h, ARGB);
        a.loadPixels();
        max = a .pixels.length;
        Arrays.fill(a.pixels, 0xFF000000);

        bg = loadImage("../data/bg.jpg");
        map = loadImage("../data/map.png");
        map.loadPixels();
        System.out.println("Map Loaded");
        rot = new ArrayRot(this, a, map);
        rot.drawMap();

        coords = new HashMap<Integer, Point>(width*height);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int i = x + y * width;
                coords.put(i, (new Point(x,y)));
            }
        }
        int i = (int)random((width*height) -1);
        //Debug Print Statements
        System.out.println("Cam: " + CamHandler.cam.getName());
        System.out.println("Cam Size: " + CamHandler.cam.getViewSize().width + ", " + CamHandler.cam.getViewSize().height);
        System.out.println("Map Populated: " + coords.size() + ", Sample: " + coords.get(i));
    }

    public void draw(){
        image(bg,0,0);

        if(frameCount % d == 0){
            rot.grow();
        }

        image(a, 0, 0);
        int[] holder = new int[max];
        handler.getMirroredImage(holder);
        for (int i = 0; i < a.pixels.length; i++) {
            if(holder[i] == -1){
                a.pixels[i] = 0x00000000;
            }
        }
        a.updatePixels();

        fill(0x80000000);
        rect(0, 0, 350, 200);
        textSize(30);
        fill(255);
        text("Cam Framerate: " + CamHandler.cam.getFPS(), 40, 40);
        text("Framerate: " + frameRate, 40, 100);
        text("Pixel Value" + a.pixels[mouseX + mouseY * width], 40, 150);

        utils.printMemory(frameCount, 600);
    }

    public static void main(String[] args) {
        String[] processingArgs = {"HandlerTests"};
        HandlerTests handlerTests = new HandlerTests();
        PApplet.runSketch(processingArgs, handlerTests);
    }

}
