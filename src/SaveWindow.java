import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class SaveWindow {
    public static void save(BufferedImage image){
        try {
            JFileChooser chooser = new JFileChooser();
            int choose = chooser.showSaveDialog(null);
            if(choose == JFileChooser.APPROVE_OPTION) {
                File outputFile = chooser.getSelectedFile();
                outputFile = new File(outputFile.getPath() + ".png");
                ImageIO.write(image, "png", outputFile);
            }
        }catch (Exception ex){
            Errors.ErrorsFunction(ex.getMessage());
        }
    }
}
