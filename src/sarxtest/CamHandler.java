package sarxtest;

import com.github.eduramiba.webcamcapture.drivers.NativeDriver;
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamResolution;
import java.util.Scanner;

public class CamHandler {

    public String os;
    public String confirm;
    public static boolean confirmed = false;

    public CamHandler() throws IllegalArgumentException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter Operating System. Choose from the following: ");
        System.out.println("Pi, Mac Intel, Mac Silicon, Windows (Case Sensitive)");
        os = scanner.nextLine();

        System.out.println("You've chosen: " + os + ". Is this Correct? (Y/N)");
        confirm = scanner.nextLine();

        if (confirm.equals("Yes") || confirm.equals("Y")) {
            confirmed = true;
        } else throw new IllegalArgumentException("Operating System was not correctly input");
    }


}
