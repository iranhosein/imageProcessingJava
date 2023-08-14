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

    public class RetriveImgApp {

        private static int width = 0;
        private static int height = 0;
        private static int newWidth = 0;
        private static int newHeight = 0;
        private static int size = 0;
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
            newWidth = Math.round(width/number);
            newHeight = Math.round(height/number);
            Size scaleSize = new Size(newWidth, newHeight);
            Imgproc.resize(img, img, scaleSize, Imgproc.INTER_AREA);
            return img;
        }

        public static void whatScale(){
            System.out.println(width + " ,,, " + height);
        }

        public static void whatSize(int sizeNumber) throws Exception{
            if (sizeNumber==0)
                sizeNumber = size;
            float copySize = sizeNumber;
            if (copySize>1000){
                copySize /= 1024;
                tag = "KB";
            } else if (copySize>1000){
                copySize /= 1024;
                tag = "MB";
            } else {
                throw new Exception();
            }
            System.out.printf("%.2f", copySize);
            System.out.println(" " + tag);
        }

        public static void readImage(String address){
            File file = new File(address);
            size = (int) file.length();
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
            System.out.println(sizeNumber);
            System.out.println(size);
            if (size<=sizeNumber){
                throw new Exception();
            }
            return downscaleImage((float) size/sizeNumber);
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
            int number = 10;
            readImage(address);
            whatScale();
            try {
                whatSize(0);
            } catch (Exception e){
                System.out.println("Image Not Support ...");
            }
            Mat image = img;
            try {
                image = downSize(number, "KB");
            } catch (Exception e){
                System.out.println("Image Size not valid ...");
            }
            System.out.println(newWidth + " ,,, " + newHeight);
            System.out.println();
            imwrite(dest, image);
            File file = new File(dest);
            int destSize = (int) file.length();
            try {
                whatSize(destSize);
            } catch (Exception e) {
                System.out.println("Not Recognise ...");
            }
        }
    }
