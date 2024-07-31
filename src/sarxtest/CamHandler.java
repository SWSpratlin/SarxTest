package sarxtest;

import com.github.eduramiba.webcamcapture.drivers.NativeDriver;
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamException;
import com.github.sarxos.webcam.WebcamResolution;
import com.github.sarxos.webcam.ds.v4l4j.V4l4jDriver;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.geom.AffineTransform;
import java.util.Scanner;

public class CamHandler {

    public static String os;
    public String confirm;
    public static boolean confirmed = false;
    public static Webcam cam;

    public CamHandler() {
        Utils utils = new Utils();
        os = utils.sysInfo();
    }


    /**
     * Private method to Initiate the correct driver depending on the OS
     *
     * @param sys Operating system.
     * @return String of the status of the drivers loading
     * @throws IllegalArgumentException No compatible OS
     */
    private static String initDriver(String sys) throws IllegalArgumentException {
        sys = os.toLowerCase();
        if (sys.contains("silicon")) {
            Webcam.setDriver(new NativeDriver());
            return "Silicon Driver loaded";
        } else if (sys.contains("pi")) {
            Webcam.setDriver(new V4l4jDriver());
            return "Pi Driver loaded";
        } else if (sys.contains("windows")) {
            return "I'm still working on this one, sorry";
        } else throw new IllegalArgumentException("No Compatible OS to load Driver");
    }

    /**
     * public method to Initiate the Webcam. Makes you choose on Apple Silicon, loads default on Pi
     *
     * @throws IllegalArgumentException
     */
    public static void initCam() throws IllegalArgumentException {
        initDriver(os);
        if (os.contains("silicon")) {
            System.out.println(Webcam.getWebcams());
            Scanner scanner = new Scanner(System.in);
            System.out.println("Select Webcam");
            cam = Webcam.getWebcams().get(scanner.nextInt());
            cam.setViewSize(WebcamResolution.HD.getSize());
            cam.open();
        }

        if (os.contains("pi")) {
            if (!Webcam.getWebcams().isEmpty()) {
                cam = Webcam.getDefault();
                cam.setViewSize(WebcamResolution.HD.getSize());
                cam.open();
            } else throw new IllegalArgumentException("No Webcam Connected");
        } else {
            throw new IllegalArgumentException("No Compatible OS to load Webcam");
        }
    }

    public static int[] getImage(Webcam c) throws WebcamException {
        cam.getImage();
        BufferedImage newImage = cam.getImage();
        AffineTransform at = new AffineTransform();
        at.concatenate(AffineTransform.getScaleInstance(-1, 1));
        at.concatenate(AffineTransform.getTranslateInstance(0, -newImage.getHeight()));
        Graphics2D g = newImage.createGraphics();
        g.transform(at);
    }
}
