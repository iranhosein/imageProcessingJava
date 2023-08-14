import nu.pattern.OpenCV;
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import java.io.File;
import static org.opencv.imgcodecs.Imgcodecs.imread;
import static org.opencv.imgcodecs.Imgcodecs.imwrite;

public class RetriveImgApp {

    private static int width = 0;
    private static int height = 0;
    private static int newWidth = 0;
    private static int newHeight = 0;
    private static long size = 0;
    private static String tag = "B";
    private static Mat img;

    public static void resetApp(){
        width = 0;
        height = 0;
        newWidth = 0;
        newHeight = 0;
        size = 0;
        tag = "B";
        img = null;
    }

    static {
        OpenCV.loadLocally();
    }

    public static Mat downscaleImage(float number){
        int new_width = Math.round(width/number);
        int new_height = Math.round(height/number);
        Size scaleSize = new Size(new_width, new_height);
        Imgproc.resize(img, img, scaleSize, Imgproc.INTER_AREA);
        return img;
    }

    public static void whatScale(){
        System.out.println(width + " ,,, " + height);
    }

    public static void whatSize() throws Exception{
        float copySize = size;
        if (copySize>1000){
            copySize /= 1024;
            tag = "KB";
        } else if (copySize>1000){
            copySize /= 1024;
            tag = "MB";
        } else {
            throw new Exception();
        }
        System.out.println(size);
    }

    public static void readImage(String address){
        File file = new File(address);
        size = file.length();
        img = imread(address);
        width = img.width();
        height = img.height();
    }

    public static Mat downSize(int sizeNumber, String tagStr) throws Exception{
        if (tagStr.equals("KB")){
            sizeNumber *= 1024;
        } else if (tagStr.equals("MB")){
            sizeNumber *= 1024*1024;
        }
        if (size<=sizeNumber){
            throw new Exception();
        }
        long pixelNumber = width * height;
        int perPixelSize = Math.round(size/pixelNumber);
        long new_pixelNumber = Math.round(sizeNumber/perPixelSize);
        float pixelRatio = new_pixelNumber/pixelNumber;
        return downscaleImage(pixelRatio);
    }

    public static Mat downScaleWidth(int nWidth) throws Exception{
        if (nWidth>=width)
            throw new Exception();
        newWidth = nWidth;
        float pixelRatio = nWidth/width;
        newHeight = Math.round(pixelRatio*height);
        return downscaleImage(pixelRatio);
    }

    public static Mat downScaleHeight(int nHeight) throws Exception{
        if (nHeight>=height)
            throw new Exception();
        newHeight = nHeight;
        float pixelRatio = nHeight/height;
        newWidth = Math.round(pixelRatio*width);
        return downscaleImage(pixelRatio);
    }

    public static void main(String[] args) {
        String address = "D:\\very test\\aaaa\\kitten.jpg";
        String dest = "D:\\very test\\aaaa\\kittennn.jpg";
        int number = 2;
        readImage(address);
        whatScale();
        try {
            whatSize();
        } catch (Exception e){
            System.out.println("Image Not Support ...");
        }
        Mat image = downscaleImage(number);
        imwrite(dest, image);
    }
}