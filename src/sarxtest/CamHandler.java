package sarxtest;

import com.github.eduramiba.webcamcapture.drivers.NativeDriver;
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamException;
import com.github.sarxos.webcam.WebcamResolution;
import com.github.sarxos.webcam.ds.javacv.JavaCvDriver;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.geom.AffineTransform;
import java.util.Scanner;

public class CamHandler {

    public static String os;
    public static Webcam cam;

    public CamHandler(Utils u) {
        os = u.sysInfo();
        initDriver();
    }


    /**
     * Private method to Initiate the correct driver depending on the OS
     * @throws IllegalArgumentException No compatible OS
     */
    private static void initDriver() throws IllegalArgumentException {
        String sys = os.toLowerCase();
        if (sys.contains("silicon")) {
            Webcam.setDriver(new NativeDriver());
            System.out.println("Silicon Driver loaded");
        } else if (sys.contains("pi")) {
            Webcam.setDriver(new JavaCvDriver());
            System.out.println("Pi Driver loaded");
        } else if (sys.contains("windows")) {
            System.out.println("I'm still working on this one, sorry");
        } else throw new IllegalArgumentException("No Compatible OS to load Driver");
    }

    /**
     * public method to Initiate the Webcam. Makes you choose on Apple Silicon, loads default on Pi
     *
     * @throws IllegalArgumentException Either there is no webcam, or no Compatible OS (Apple or Pi)
     */
    public static void initCam() throws IllegalArgumentException {
        if (os.toLowerCase().contains("silicon")) {
            for (int i = 0; i < Webcam.getWebcams().size(); i++) {
                System.out.println(i + ": " + Webcam.getWebcams().get(i).getName());
            }
            Scanner scanner = new Scanner(System.in);
            System.out.println("Select Webcam");
            cam = Webcam.getWebcams().get(scanner.nextInt());
            cam.setViewSize(WebcamResolution.HD.getSize());
            cam.open();
        } else if (os.toLowerCase().contains("pi")) {
            if (!Webcam.getWebcams().isEmpty()) {
                for (int i = 0; i < Webcam.getWebcams().size(); i++) {
                    System.out.println(i + ": " + Webcam.getWebcams().get(i).getName());
                }
                cam = Webcam.getDefault();
                cam.setViewSize(WebcamResolution.HD.getSize());
                cam.open();
            } else throw new IllegalArgumentException("No Webcam Connected");
        } else {
            throw new IllegalArgumentException("No Compatible OS to load Webcam");
        }
    }

    /**
     * Reads the loaded Webcam and flips the image using AffineTransform.
     * Still in testing
     * @return int array holding the flipped Image data. Draw to PImage.
     * @throws WebcamException If the webcam is null, this will not fire.
     */
    public int[] getMirroredImage(int[] output) throws WebcamException {
        //Initialize the original image and output image
        if(cam != null){
            BufferedImage image = cam.getImage();
            int w = image.getWidth();
            int h = image.getHeight();

            BufferedImage flipped = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

            AffineTransform at = new AffineTransform();
            at.setToScale(-1, 1);
            at.translate(-image.getWidth(), 0);

            Graphics2D g = flipped.createGraphics();
            g.drawImage(image, at, null);
            g.dispose();

            int rX = (int)(Math.random() * image.getWidth());
            int rY = (int)(Math.random() * image.getHeight());
//            System.out.print("flipped" + flipped.getRGB(rX, rY) + ", ");
//            System.out.println("Regular" + image.getRGB(rX, rY));
            flipped.getRGB(0,0,image.getWidth(), image.getHeight(), output, 0, image.getWidth());
            return output;
        } else throw new WebcamException("Webcam Cannot be Null");
    }
}
