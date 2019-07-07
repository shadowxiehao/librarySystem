package com.database.util;

import javax.swing.*;
import java.awt.*;

//±³¾°Í¼Æ¬ÄÚ²¿Àà
public class ImageLabel extends JLabel {
    private Image image;
    private JFrame jFrame;
    public ImageLabel(Image image, JFrame jFrame){
        this.image = image;
        this.jFrame = jFrame;
    }
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        int x = jFrame.getWidth();
        int y = jFrame.getHeight();

        g.drawImage(image, 0, 0, x, y, null);
    }
}