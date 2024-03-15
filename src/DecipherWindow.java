import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class DecipherWindow implements ActionListener {

    private JFrame cipherWindow = new JFrame();
    private JTextArea textArea = new JTextArea();
    private JButton select = new JButton("Select");
    private JButton decipherButton = new JButton("Decipher");
    private JPanel panel = new JPanel();
    private JLabel label = new JLabel();
    private BufferedImage image;

    public DecipherWindow(){
        cipherWindow.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        cipherWindow.setBounds(35,45,1120,720);
        cipherWindow.setMinimumSize(new Dimension(1120,720));
        cipherWindow.setLayout(null);

        textArea.setMinimumSize(new Dimension(980,40));
        textArea.setBounds(10,600,900, 40);
        textArea.setFocusable(false);

        select.addActionListener(this);
        select.setBounds(920,600,75,40);
        select.setBounds(1005,600,75,40);

        decipherButton.setBounds(910,600,85,40);

        panel.setBounds(0,0,480,270);
        panel.setBackground(Color.BLACK);
        panel.setOpaque(false);
        panel.setBounds(10,0,1070,590);
        panel.setOpaque(true);
        label.setOpaque(true);
        panel.add(label);

        cipherWindow.add(panel);
        cipherWindow.add(textArea);
        cipherWindow.add(select);
        cipherWindow.add(decipherButton);
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
                ErrorsFunction(ex.getMessage());
            }

        }
    }


    public void ErrorsFunction(String error){
        JOptionPane.showMessageDialog(null,error, "Error",JOptionPane.ERROR_MESSAGE);
    }


}
