import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

public class CipherWindow implements ActionListener {


    private JFrame cipherWindow = new JFrame();
    private JTextField textField = new JTextField();
    private JButton select = new JButton("Select");
    private JButton cipherButton = new JButton("Cipher");
    private JPanel panel = new JPanel();
    private JLabel label = new JLabel();
    private BufferedImage image = null;

    public CipherWindow(){
        cipherWindow.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        cipherWindow.setBounds(35,45,1120,720);
        cipherWindow.setMinimumSize(new Dimension(1120,720));
        cipherWindow.setLayout(null);
        cipherWindow.getContentPane().setBackground(new Color(0x8400FF));

        textField.setMinimumSize(new Dimension(900,40));
        textField.setBounds(10,600,900, 40);

        select.addActionListener(this);
        select.setBounds(920,600,75,40);
        select.setBounds(1005,600,75,40);

        cipherButton.setBounds(920,600,75,40);
        cipherButton.addActionListener(this);


        panel.setBounds(0,0,480,270);
        panel.setBackground(Color.BLACK);
        panel.setBounds(10,0,1070,590);
        panel.setOpaque(true);
        panel.add(label);

        cipherWindow.add(panel);
        cipherWindow.add(textField);
        cipherWindow.add(select);
        cipherWindow.add(cipherButton);
        cipherWindow.pack();
        cipherWindow.setVisible(true);



    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==select){
            JFileChooser chooser = new JFileChooser();
            int choose = chooser.showOpenDialog(null);
            try {
                if (choose == JFileChooser.APPROVE_OPTION) {
                    image = ImageIO.read(chooser.getSelectedFile());
                    label.removeAll();
                    label.setIcon(new ImageIcon(image));
                    cipherWindow.repaint();

                }
            } catch (Exception ex) {
                Errors.ErrorsFunction(ex.getMessage());
            }
        }
        if(e.getSource()==cipherButton){
            if(image == null){
                Errors.ErrorsFunction("Картинка не выбрана");
            }else {
                Steganography st = new Steganography(image,textField.getText());
                image = st.cipher();

                SaveWindow.save(image);

                label.removeAll();
                label.setIcon(new ImageIcon(image));
                cipherWindow.repaint();

            }


        }
    }



}
