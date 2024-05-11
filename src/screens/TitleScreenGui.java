package screens;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class TitleScreenGui extends JFrame {
    public TitleScreenGui() {
        super("Title Screen");

        setSize(425, 575);
//        to allow manual positioning of components
        setLayout(null);
//        to center frame when displayed
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}
