package sarxtest;

public class Utils {

    private static final long MEGABYTE = 1024L * 1024L;
    private static Runtime runtime;

    public Utils(){
        runtime = Runtime.getRuntime();
    }

    /**
     * Utility to easily log memory usage for sketches. Not necessary for deployment,
     * but helpful for optimization.
     * @param frameCount usually "frameCount" if used in a Processing sketch. Whatever frame counter/refresh counter you have
     * @param increment how often you want to update
     */
    public void printMemory(int frameCount, int increment){
        if (frameCount % increment == 0) {
            long memory = runtime.totalMemory() - runtime.freeMemory();
            System.out.println(" Used memory is bytes: " + memory);
            System.out.println("Used memory is megabytes: "
                    + bytesToMegabytes(memory));
        }
    }

    public static long bytesToMegabytes(long bytes) {
        return bytes / MEGABYTE;
    }
}
