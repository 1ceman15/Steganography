import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;

public class Steganography {
    BufferedImage image;
    StringBuilder text = new StringBuilder("");

    public Steganography(BufferedImage image){
        this.image = image;
    }

    public Steganography(BufferedImage image, String text){
        this.image = image;
        this.text = new StringBuilder(text);
    }

    public BufferedImage cipher(){

        StringBuilder binaryText = new StringBuilder("");

        for (int i = 0; i < text.length(); i++) {
            StringBuilder helper = new StringBuilder(Integer.toBinaryString(text.charAt(i)));
            //Поддерживание длинны равной 7 добавлением несущих нулуй
            if(helper.length()<7){
                for (int j = 1; j <= 7-helper.length(); j++)
                    helper = helper.insert(0,"0");
            }
            binaryText = binaryText.append(helper);
        }

        StringBuilder binaryLength = new StringBuilder(Integer.toBinaryString(binaryText.length()));
        int help = 31-binaryLength.length();
        if(binaryLength.length()<31){
            for (int j = 1; j <= help; j++)
                binaryLength = binaryLength.insert(0,"0");
        }

        //В первых 11 пикселях будет закодированна длинна
        int index = 0;
        for (int i = 0; i < 11; i++) {
            if(index>=31)
                break;
            int pixelColor = image.getRGB(i, 0) & 0x00FFFFFF;




            int red = (pixelColor >> 16) & 0xFF;
            int green = (pixelColor >> 8) & 0xFF;
            int blue = pixelColor & 0xFF;

            char[] pixel = new char[24];

            for (int j = 0; j < 8; j++) {
                pixel[j] = (red & (1 << (7 - j))) == 0 ? '0' : '1';
                pixel[j + 8] = (green & (1 << (7 - j))) == 0 ? '0' : '1';
                pixel[j + 16] = (blue & (1 << (7 - j))) == 0 ? '0' : '1';
            }

            //char[] pixel = Integer.toBinaryString(pixelColor).toCharArray();
            System.out.println(pixel.length);
            System.out.println(pixelColor);
            pixel[7] = binaryLength.charAt(index);
            index++;

            if(index>=31) {
                pixelColor = Integer.parseInt(String.copyValueOf(pixel),2);
                image.setRGB(i,0, pixelColor);
                break;
            }

            pixel[15] = binaryLength.charAt(index);
            index++;

            if(index>=31) {
                pixelColor = Integer.parseInt(String.copyValueOf(pixel),2);
                image.setRGB(i,0, pixelColor);
                break;
            }

            pixel[23] = binaryLength.charAt(index);
            pixelColor = Integer.parseInt(String.copyValueOf(pixel),2);
            index++;
            image.setRGB(i,0, pixelColor);

        }

        index = 0;
        for (int i = 11; i <= round(binaryText.length(),3); i++) {
            if(index>=binaryText.length()-1)
                break;

            int pixelColor = image.getRGB(i, 0) & 0x00FFFFFF;


            int red = (pixelColor >> 16) & 0xFF;
            int green = (pixelColor >> 8) & 0xFF;
            int blue = pixelColor & 0xFF;

            char[] pixel = new char[24];

            for (int j = 0; j < 8; j++) {
                pixel[j] = (red & (1 << (7 - j))) == 0 ? '0' : '1';
                pixel[j + 8] = (green & (1 << (7 - j))) == 0 ? '0' : '1';
                pixel[j + 16] = (blue & (1 << (7 - j))) == 0 ? '0' : '1';
            }


            pixel[7] = binaryText.charAt(index);
            index++;

            if(index>=binaryText.length()-1) {
                pixelColor = Integer.parseInt(String.copyValueOf(pixel),2);
                image.setRGB(i,0, pixelColor);
                break;
            }

            pixel[15] = binaryText.charAt(index);
            index++;

            if(index>=binaryText.length()-1) {
                pixelColor = Integer.parseInt(String.copyValueOf(pixel),2);
                image.setRGB(i,0, pixelColor);
                break;
            }

            pixel[23] = binaryText.charAt(index);
            index++;
            pixelColor = Integer.parseInt(String.copyValueOf(pixel),2);
            image.setRGB(i,0, pixelColor);


        }


        return  image;

    }
    static int round(int a, int b){
        if(a%b==0)
            return a/b;
        else
            return a/b+1;

    }
}
