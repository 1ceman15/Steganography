import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class DecipherWindow implements ActionListener {

    private JFrame decipherWindow = new JFrame();
    private JTextArea textArea = new JTextArea();
    private JButton select = new JButton("Select");
    private JButton decipherButton = new JButton("Decipher");
    private JPanel panel = new JPanel();
    private JLabel label = new JLabel();
    private BufferedImage image = null;

    public DecipherWindow(){
        decipherWindow.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        decipherWindow.setBounds(35,45,1120,720);
        decipherWindow.setMinimumSize(new Dimension(1120,720));
        decipherWindow.setLayout(null);
        decipherWindow.getContentPane().setBackground(new Color(0x8400FF));

        textArea.setMinimumSize(new Dimension(880,40));
        textArea.setBounds(10,600,880, 40);
        textArea.setFocusable(false);
        textArea.setFont(new Font("Comic Sans", Font.BOLD, 25));

        select.addActionListener(this);
        select.setBounds(920,600,80,40);
        select.setBounds(1005,600,80,40);

        decipherButton.setBounds(900,600,95,40);
        decipherButton.addActionListener(this);

        panel.setBounds(0,0,480,270);
        panel.setBackground(Color.BLACK);
        panel.setOpaque(false);
        panel.setBounds(10,0,1070,590);
        panel.setOpaque(true);
        label.setOpaque(true);
        panel.add(label);

        decipherWindow.add(panel);
        decipherWindow.add(textArea);
        decipherWindow.add(select);
        decipherWindow.add(decipherButton);
        decipherWindow.pack();
        decipherWindow.setVisible(true);



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
                    decipherWindow.repaint();
                }
            } catch (Exception ex) {
                Errors.ErrorsFunction(ex.getMessage());
            }

        }


        if(e.getSource()==decipherButton){
            if(image == null) {
                Errors.ErrorsFunction("Картинка не выбрана");
            }else {
                textArea.setText("");
                textArea.append(Steganography.decipher(image));
                decipherWindow.repaint();
            }


        }


    }



}
