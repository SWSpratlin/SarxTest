package sarxtest;

import com.github.eduramiba.webcamcapture.drivers.NativeDriver;
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamResolution;
import processing.core.PApplet;
import processing.core.PImage;
import java.awt.image.BufferedImage;
import java.awt.Point;
import java.util.Arrays;
import java.util.HashMap;

public class Main extends PApplet {

    //Global scope Objects
    public static Webcam cam;
    public static PImage a;
    public static PImage bg;
    public static PImage map;
    public static BufferedImage buff;
    public static HashMap<Integer, Point> coords;
    public Utils utils;
    public ArrayRot rot;
    public int d = 5;

    public void settings() {
        size(1280, 720);
        utils = new Utils();
        //Necessary to make this work on macOS
        Webcam.setDriver(new NativeDriver());

        //Initialize the Camera by name. If you don't know the name, call "getWebcams()" first
        cam = Webcam.getWebcamByName("Logitech StreamCam");

        //Set the view size for performance
        cam.setViewSize(WebcamResolution.HD.getSize());

        cam.open();

        //Creating the PImage graphic to analyze and process
        a = createImage(cam.getViewSize().width, cam.getViewSize().height, ARGB);
        a.loadPixels();
        Arrays.fill(a.pixels, 0xFF000000);

        //load the PImage for the background
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
        System.out.println("Cam: " + cam.getName());
        System.out.println("Cam Size: " + cam.getViewSize().width + ", " + cam.getViewSize().height);
        System.out.println("Image: " + cam.getImage());
        System.out.println("Map Populated: " + coords.size() + ", Sample: " + coords.get(i));
    }

    public void draw() {
        //Holder for camera pixel values
        int[] holder = new int[a.pixels.length];

        //use the GetRGB method to pull every pixel from the Buffered Image and write them to the pixel array.
        //Note: The images need to be the same size for this to work properly. Working on getting size flexibility
        cam.getImage().getRGB(0,0,cam.getViewSize().width, cam.getViewSize().height,
                holder, 0, cam.getViewSize().width);

        //draw the BG Image
        image(bg, 0, 0);

        //analysis for camera pixel values
        //Camera cannot write directly to image pixels, needs an intermediary.
        for (int i = 0; i < a.pixels.length; i++) {
            if(holder[i] == -1){
                a.pixels[i] = 0x00000000;
            }
        }

        if(frameCount % d == 0){
            rot.grow();
        }

        //Update and draw the Image
        image(a, 0, 0);
        a.updatePixels();


        //Debug Text
        fill(0x80000000);
        rect(0, 0, 350, 200);
        textSize(30);
        fill(255);
        text("Cam Framerate: " + cam.getFPS(), 40, 40);
        text("Framerate: " + frameRate, 40, 100);
        text("Pixel Value" + a.pixels[mouseX + mouseY * width], 40, 150);

        utils.printMemory(frameCount, 600);
    }

    //Boilerplate for processing sketch
    public static void main(String[] args) {
        String[] processingArgs = {"Main"};
        Main main = new Main();
        PApplet.runSketch(processingArgs, main);
    }
}