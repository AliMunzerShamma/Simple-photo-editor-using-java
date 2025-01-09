/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javagraphics2d;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;

/**
 *
 * @author MABCO
 */
public class JavaGraphics2d extends JFrame implements MouseMotionListener {

    /**
     * @param args the command line arguments
     */
    BufferedImage img = null;
     BufferedImage img2 = null;
    public static void main(String[] args) {
        // TODO code application logic here
        new JavaGraphics2d();
    }

    @Override
    public final void paint(Graphics g) {
        super.paint(g);
        //Graphics2D g2d=(Graphics2D) g;
        //g2d.
        
        g.drawImage(img, 0,0, this);
        
      g.setColor(Color.BLACK);
        g.fillRect(0, 0, 200, 200);
    }

    public JavaGraphics2d() {
        setTitle("painTool");
        try {
            img = ImageIO.read(new File("C:\\Users\\MABCO\\Desktop\\grass001.jpg"));
        } catch (IOException exc) {
    //TODO: Handle exception.
        }
        ActionListener li1=new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Graphics gg=getGraphics();
                    gg.dispose();
                    
                    RenderedImage rendImage = img2;
                    File file = new File("C:\\Users\\MABCO\\Desktop\\newimage.jpg");
                    ImageIO.write(img2, "jpg", file);
                    System.out.println("Ok");
                } catch (IOException ex) {
                    Logger.getLogger(JavaGraphics2d.class.getName()).log(Level.SEVERE, null, ex);
                }
                 
            }
        };
        JButton B=new JButton();
        B.setBounds(0, 0, 20, 20);
        B.addActionListener(li1);
        add(B);
        img2=new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
        paintAll(img2.createGraphics());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(img.getWidth(), img.getHeight());
        addMouseMotionListener(this);
        setLayout(null);
        setVisible(true);
        
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        Graphics g = getGraphics();
        g.setColor(Color.red);
        g.fillRect(e.getX(), e.getY(), 8, 8);
       // paint(g);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

}
