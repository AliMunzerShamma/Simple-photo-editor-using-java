package javagraphics2d;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Main_Painter extends JFrame implements ChangeListener{

    JButton Eraser_Button, Cropimage_Button, Brushes_Button, Fill_Tool_Button, Color_Picker_Button, Change_image_Button, Save_Button, AddText_Button, Mergimg_Button, ConvertColorsys_Button ,Zoom_Button;
    Edit_AND_Draw ED;
    ActionListener actionListener = new ActionListener() {

        public void actionPerformed(ActionEvent e) {
             if (e.getSource() == Zoom_Button) {
                 ED.zoom();                
              }
             if (e.getSource() == Mergimg_Button) {
                 MergeTowImage Me = new MergeTowImage();
                Me.setVisible(true);                
              }
            if (e.getSource() == Eraser_Button) {
                ED.setEraserCursor(true);
            } else if (e.getSource() == Brushes_Button) {
                ED.setBrushesCursor(true);
                ED.Brushes();
            }else if (e.getSource() == Cropimage_Button) {               
                //ED.setBrushesCursor(true);                    
                ED.Cut();
            } else if (e.getSource() == Fill_Tool_Button) {
                ED.setFillCursor(true);
                ED.activFill_Tool();
            } else if (e.getSource() == ConvertColorsys_Button) {
                ConvertColorSystem ccs = new ConvertColorSystem();
                ccs.setVisible(true);
            } else if (e.getSource() == AddText_Button) {
                MyMouseAdapter myMouseAdapter = new MyMouseAdapter();
                label = new JTextField("Text", SwingConstants.CENTER);
                label.setSize(new Dimension(60, 40));
                label.setOpaque(true);
                label.setLocation(50, 50);
                label.addMouseListener(myMouseAdapter);
                label.addMouseMotionListener(myMouseAdapter);
                ED.add(label);
            } else if (e.getSource() == Color_Picker_Button) {
                Color newColor = JColorChooser.showDialog(null, "Choose a color", Color.RED);
                if (newColor != null) {
                    ED.setColor(newColor);
                }
                System.out.println("Col " + newColor);
            } else if (e.getSource() == Change_image_Button) {
                JFileChooser fc = new JFileChooser();
                int result = fc.showOpenDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File file = fc.getSelectedFile();
                    Image im = null;
                    Image im1 = null;
                    try {
                        im = ImageIO.read(file);
                        im1 = ImageIO.read(file);                       
                        ED.setImage(im);
                        ED.setPenCursor(im1);
                        ED.SetSize(im.getWidth(Save_Button), im.getHeight(Save_Button));
                        int W = im.getWidth(Save_Button);
                        int H = im.getHeight(Save_Button);
                        int HH = H;
                        int WW = W;

                        if (WW < 1000) {
                            WW = 1000;
                        }
                        frame.setSize(WW, HH + 100);
                    } catch (IOException ex) {
                        Logger.getLogger(Main_Painter.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            } else if (e.getSource() == Save_Button) {
                ED.save();
            }
        }
    };

    public static void main(String[] args) {
        new Main_Painter().show(new Main_Painter());
    }
    public void stateChanged(ChangeEvent e) 
    { 
        l.setText("value of Slider is =" + b.getValue()); 
    }
    JFrame frame;
     static JLabel l; 
    static JSlider b; 
    public static JTextField label;

    public void show(Main_Painter s) {
        frame = new JFrame("Swing Paint");
        Container content = frame.getContentPane();
        content.setLayout(new BorderLayout());
        ED = new Edit_AND_Draw();
        Image im = null;
        Image im1 = null;

        try {
            im = ImageIO.read(new File("img.png"/*"grass001.jpg"*/));
            im1 = ImageIO.read(new File("img.png"/*"grass001.jpg"*/));
        } catch (IOException ex) {
            Logger.getLogger(Main_Painter.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        ED.setImage(im);
        ED.setBounds(0, 0, 1000, 1000);
        ED.setPenCursor(im1);

        //content.add(ED, BorderLayout.CENTER);
        JPanel controls = new JPanel();
        JPanel controls1 = new JPanel();

        Eraser_Button = new JButton("Eraser");
        Eraser_Button.addActionListener(actionListener);
        
        Cropimage_Button = new JButton("Crop image");
        Cropimage_Button.addActionListener(actionListener);
        
        Brushes_Button = new JButton("Brushes");
        Brushes_Button.addActionListener(actionListener);
        
        Fill_Tool_Button = new JButton("Fill Tool");
        Fill_Tool_Button.addActionListener(actionListener);
        
        Color_Picker_Button = new JButton("Pick Color");
        Color_Picker_Button.addActionListener(actionListener);
        
        Change_image_Button = new JButton("Change image");
        Change_image_Button.addActionListener(actionListener);
        
        AddText_Button = new JButton("add text");
        AddText_Button.addActionListener(actionListener);
        
        ConvertColorsys_Button = new JButton("Convert Color");
        ConvertColorsys_Button.addActionListener(actionListener);
        
        Mergimg_Button = new JButton("Merge image");
        Mergimg_Button.addActionListener(actionListener);
        
        Save_Button = new JButton("Save Changed Image");
        Save_Button.addActionListener(actionListener);
        
        Zoom_Button = new JButton("Zooming");
        Zoom_Button.addActionListener(actionListener);

        controls.add(Color_Picker_Button);
        controls.add(Brushes_Button);
        controls.add(Fill_Tool_Button);
        controls.add(Eraser_Button);
        controls.add(Cropimage_Button);
        controls.add(Change_image_Button);
        controls1.add(Save_Button);
        controls1.add(AddText_Button);
        controls1.add(ConvertColorsys_Button);
        controls1.add(Mergimg_Button);
        controls1.add(Zoom_Button);

        
        int W = im.getWidth(Save_Button);
        int H = im.getHeight(Save_Button);

        ED.SetSize(W, H);
        int HH = H;
        int WW = W;

        if (WW < 1000) {
            WW = 1000;
        }
        ///////////////////////////////////// update
        l = new JLabel(); 
  
        JPanel p = new JPanel(); 
        b = new JSlider(0, 200, 120); 
        b.setPaintTrack(true); 
        b.setPaintTicks(true); 
        b.setPaintLabels(true); 
        b.setMajorTickSpacing(50); 
        b.setMinorTickSpacing(5); 
        b.addChangeListener(s); 
        p.add(b); 
        p.add(l); 
  
        controls1.add(p);
        content.add(controls, BorderLayout.NORTH);
        content.add(controls1, BorderLayout.AFTER_LAST_LINE);
        content.add(ED, BorderLayout.CENTER);
        // set the text of label 
        l.setText("value of Slider is =" + b.getValue()); 
        
        frame.setSize(WW, HH+100);
        // can close frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // show the swing paint result
        frame.setVisible(true);
    }

    private class MyMouseAdapter extends MouseAdapter {

        private Point initLabelLocation = null;
        private Point initMouseLocationOnScreen = null;

        @Override
        public void mousePressed(MouseEvent e) {
            JTextField label = (JTextField) e.getSource();
            initLabelLocation = label.getLocation();

            initMouseLocationOnScreen = e.getLocationOnScreen();
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            initLabelLocation = null;
            initMouseLocationOnScreen = null;
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            if (initLabelLocation == null || initMouseLocationOnScreen == null) {
                return;
            }
            JTextField label = (JTextField) e.getSource();

            Point mouseLocation = e.getLocationOnScreen();

            int deltaX = mouseLocation.x - initMouseLocationOnScreen.x;
            int deltaY = mouseLocation.y - initMouseLocationOnScreen.y;

            int labelX = initLabelLocation.x + deltaX;
            int labelY = initLabelLocation.y + deltaY;

            label.setLocation(labelX, labelY);
        }
    }
}
