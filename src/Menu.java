import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu implements ActionListener {
    private JFrame menu = new JFrame("Steganography");
    private JButton cipher = new JButton("Cipher");
    private JButton decipher = new JButton("Decipher");


    public Menu(){
        menu.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        menu.setBounds(25,25,480,320);
        menu.setLayout(new GridLayout(2,1));

        cipher.addActionListener(this);
        cipher.setFont(new Font("Comic Sans", Font.BOLD, 50));
        cipher.setFocusable(false);
        cipher.setBackground(new Color(0x8400FF));

        decipher.addActionListener(this);
        decipher.setFont(new Font("Comic Sans", Font.BOLD, 50));
        decipher.setFocusable(false);
        decipher.setBackground(new Color(0x8400FF));


        menu.add(cipher);
        menu.add(decipher);
        menu.setVisible(true);

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==cipher){
            CipherWindow window = new CipherWindow();
        }

        if(e.getSource()==decipher){
            DecipherWindow window = new DecipherWindow();
        }

    }
}
