import java.awt.image.BufferedImage;


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

        StringBuilder binaryText = new StringBuilder("");//Текст в виде двоичного кода

        for (int i = 0; i < text.length(); i++){
            StringBuilder helper = new StringBuilder(Integer.toBinaryString(text.charAt(i)));
            //Поддерживание длинны равной 14 добавлением несущих нулуй
            int helperLen = helper.length();
            if(helper.length()<14){
                for (int j = 1; j <= 14-helperLen; j++)
                    helper = helper.insert(0,"0");
            }
            binaryText = binaryText.append(helper);
        }
        if(binaryText.length()/3>image.getWidth()-10){
            Errors.ErrorsFunction("Слишком большое сообщение");
            return null;
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
            //System.out.println("pa"+ pixelColor);

        }

        index = 0;
        for (int i = 11; i <= 11+ round(binaryText.length(),3); i++) {
            if(index>binaryText.length()-1)
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

            if(index>binaryText.length()-1) {
                pixelColor = Integer.parseInt(String.copyValueOf(pixel),2);
                image.setRGB(i,0, pixelColor);
                break;
            }

            pixel[15] = binaryText.charAt(index);
            index++;

            if(index>binaryText.length()-1) {
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
    public String decipher(){
        StringBuilder cipheredStr = new StringBuilder("");
        StringBuilder str = new StringBuilder("");
        StringBuilder binaryLength = new StringBuilder("");

        //Нахождение длинны зашифрованного сообщения
        for (int i = 0; i < 11; i++) {
            int pixelColor = image.getRGB(i, 0) & 0x00FFFFFF;

            //System.out.println("pX" + pixelColor);

            int red = (pixelColor >> 16) & 0xFF;
            int green = (pixelColor >> 8) & 0xFF;
            int blue = pixelColor & 0xFF;

            char[] pixel = new char[24];

            for (int j = 0; j < 8; j++) {
                pixel[j] = (red & (1 << (7 - j))) == 0 ? '0' : '1';
                pixel[j + 8] = (green & (1 << (7 - j))) == 0 ? '0' : '1';
                pixel[j + 16] = (blue & (1 << (7 - j))) == 0 ? '0' : '1';
            }
            binaryLength.append(pixel[7]);
            binaryLength.append(pixel[15]);
            binaryLength.append(pixel[23]);
        }

        binaryLength = new StringBuilder(binaryLength.substring(0,31));
        int strLength = Integer.parseInt(String.valueOf(binaryLength),2);

        int pixelCount = round(strLength,3);
        int pixelIndex = 11;
        int index = 0;
        while (index<=strLength){

            int pixelColor = image.getRGB(pixelIndex, 0) & 0x00FFFFFF;
            //System.out.println("pixel1 " +  pixelColor);
            int red = (pixelColor >> 16) & 0xFF;
            int green = (pixelColor >> 8) & 0xFF;
            int blue = pixelColor & 0xFF;

            char[] pixel = new char[24];

            for (int j = 0; j < 8; j++) {
                pixel[j] = (red & (1 << (7 - j))) == 0 ? '0' : '1';
                pixel[j + 8] = (green & (1 << (7 - j))) == 0 ? '0' : '1';
                pixel[j + 16] = (blue & (1 << (7 - j))) == 0 ? '0' : '1';
            }
            cipheredStr = cipheredStr.append(pixel[7]);
            index++;
            cipheredStr = cipheredStr.append(pixel[15]);
            index++;
            cipheredStr = cipheredStr.append(pixel[23]);
            index++;
            pixelIndex++;
        }


        cipheredStr = new StringBuilder(cipheredStr.substring(0,strLength));


        for (int i = 0; i < cipheredStr.length(); i+=14) {
            if(i+14>strLength)
                break;
            str = str.append((char) Integer.parseInt(cipheredStr.substring(i,i+14),2));
        }

        return String.valueOf(str);
    }
    static int round(int a, int b){
        if(a%b==0)
            return a/b;
        else
            return a/b+1;

    }

}
