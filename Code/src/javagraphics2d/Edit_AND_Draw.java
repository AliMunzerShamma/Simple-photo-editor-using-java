package javagraphics2d;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.util.Pair;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;

public class Edit_AND_Draw extends JComponent  {

    private Image image ;
    private BufferedImage image2;
    private BufferedImage image3;
    private boolean Fill = false;
    Color FillColor = Color.YELLOW;
    int Height, Width;
    
    public void setColor(Color color)
    {
        FillColor=color;
        g2.setPaint(FillColor);
    }
    
    public void SetSize(int w, int h) {
        System.out.println("W : "+ w+" H : "+h);
        this.Width = w;
        this.Height = h;
    }

    public void setPenCursor(Image image2) {
        setCursor(Toolkit.getDefaultToolkit().createCustomCursor(new ImageIcon("pen.png").getImage(), new Point(0, 30), "custom cursor"));
        this.image2 = (BufferedImage) image2;
    }
    boolean isBrushes = false;
    public void setBrushesCursor(boolean isBrushes) {
        setCursor(Toolkit.getDefaultToolkit().createCustomCursor(new ImageIcon("Brushes.png").getImage(), new Point(0, 30), "custom cursor"));
        isBrushes = isBrushes;
    }
    boolean isFill = false;
    public void setFillCursor(boolean isFill) {
        setCursor(Toolkit.getDefaultToolkit().createCustomCursor(new ImageIcon("Fill.png").getImage(), new Point(0, 30), "custom cursor"));
        isFill = isFill;
    }
    
    private Graphics2D g2;
    private int CurX, CurY, PrevX, PrevY , PrevX1, PrevY1 , PrevX2, PrevY2 , count =0;
    boolean eraser = false , boo = false ,boo1 = false;

    public void setEraserCursor(boolean eraser) {
        setCursor(Toolkit.getDefaultToolkit().createCustomCursor(new ImageIcon("eraser.png").getImage(), new Point(0, 25), "custom cursor"));
        this.eraser = eraser;
        Fill=false;
    }

    public Edit_AND_Draw() {
        setDoubleBuffered(false);
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                
                PrevX = e.getX();
                PrevY = e.getY();
                if(boo1 == true){
                    int size=0;
                        image3 = new BufferedImage(image2.getWidth()+size, image2.getHeight()+size, BufferedImage.TYPE_INT_RGB);
                        
                        float zoom=2;
                        int d=image2.getWidth()/2-1,d1=image2.getHeight()/2-1;
                        
                        for( int i=(image2.getWidth()+size)/2 ; i<image2.getWidth()+size;i++){      
                            if(i%zoom == 0)
                                d++;
                            d1=image2.getHeight()/2-1;
                            for( int j=(image2.getHeight()+size)/2 ; j<image2.getHeight()+size ;j++){
                                if(j%zoom == 0){                                
                                d1++;      
                                }
                                image3.setRGB(i , j, image2.getRGB(d, d1));                                    
                            }                        
                        }
                        d=image2.getWidth()/2-1;d1=image2.getHeight()/2-1;
                        for( int i=(image2.getWidth()+size)/2 ; i>=0;i--){      
                            if(i%zoom == 0)
                                d--;
                            d1=image2.getHeight()/2-1;
                            for( int j=(image2.getHeight()+size)/2 ; j>=0 ;j--){
                                if(j%zoom == 0){                                
                                d1--;      
                                }
                                image3.setRGB(i , j, image2.getRGB(d, d1));                                    
                            }                        
                        }
                        d=image2.getWidth()/2-1;d1=image2.getHeight()/2-1;
                        for( int i=(image2.getWidth()+size)/2 ; i<image2.getWidth()+size;i++){      
                            if(i%zoom == 0)
                                d++;
                            d1=image2.getHeight()/2-1;
                            for( int j=(image2.getHeight()+size)/2 ; j>=0 ;j--){
                                if(j%zoom == 0){                                
                                d1--;      
                                }
                                image3.setRGB(i , j, image2.getRGB(d, d1));                                    
                            }                        
                        }
                        d=image2.getWidth()/2-1;d1=image2.getHeight()/2-1;
                        for( int i=(image2.getWidth()+size)/2 ; i>=0;i--){      
                            if(i%zoom == 0)
                                d--;
                            d1=image2.getHeight()/2-1;
                            for( int j=(image2.getHeight()+size)/2 ; j<image2.getHeight()+size ;j++){
                                if(j%zoom == 0){                                
                                d1++;      
                                }
                                image3.setRGB(i , j, image2.getRGB(d, d1));                                    
                            }                        
                        }
                        image2 = image3;
                        image = image2;
                        repaint();
                }
                else if (boo == true){
                    g2.setPaint(Color.black);   
                    count ++;
                    if(count == 1){
                       PrevX1 = PrevX; 
                       PrevY1 = PrevY;
                    }
                    else if(count == 2){
                        int tempX , tempY;
                        if(PrevX > PrevX1){
                            tempX = PrevX1;
                            PrevX1 = PrevX;
                            PrevX = tempX;
                        }
                        if(PrevY > PrevY1){
                            tempY = PrevY1;
                            PrevY1 = PrevY;
                            PrevY = tempY;
                        }
                        g2.drawLine(PrevX, PrevY,PrevX, PrevY1);
                        g2.drawLine(PrevX, PrevY,PrevX1, PrevY);
                        g2.drawLine(PrevX, PrevY1,PrevX1, PrevY1);
                        g2.drawLine(PrevX1, PrevY,PrevX1, PrevY1);
                        repaint();
                        PrevX2 = PrevX ;
                        PrevY2 = PrevY;
                    }
                    else if(count == 3){
                        count = 0;
                        for( int i=PrevX2 ; i<PrevX1 ;i++){
                            Color Orginal_Color = new Color(image2.getRGB(i, PrevY1));
                            g2.setPaint(Orginal_Color);
                            g2.drawRect(i, PrevY1, 1, 1);
                            Orginal_Color = new Color(image2.getRGB(i, PrevY2));
                            g2.setPaint(Orginal_Color);
                            g2.drawRect(i, PrevY2, 1, 1);
                            repaint();
                        }
                        for( int j=PrevY2 ; j<PrevY1 ;j++){
                        Color Orginal_Color = new Color(image2.getRGB(PrevX1, j));
                        g2.setPaint(Orginal_Color);
                        g2.drawRect(PrevX1, j, 1, 1);
                        Orginal_Color = new Color(image2.getRGB(PrevX2, j));
                        g2.setPaint(Orginal_Color);
                        g2.drawRect(PrevX2, j, 1, 1);
                        repaint();
                        }  
                        image3 = new BufferedImage(PrevX1-PrevX2+1, PrevY1-PrevY2+1, BufferedImage.TYPE_INT_RGB);
                        for( int i=PrevX2 ; i<PrevX1 ;i++)
                            for( int j=PrevY2 ; j<PrevY1 ;j++){
                                image3.setRGB(i-PrevX2, j-PrevY2, image2.getRGB(i, j));                                
                            }                        
                    }
                }
                System.out.println("X = "+PrevX + " Y "+PrevY);
                 if(Main_Painter.label!=null)
                {
                    String s = Main_Painter.label.getText();
                    Point p  = Main_Painter.label.getLocation();
                    System.out.println("Poi "+p);
                    g2.drawString(s, p.x, p.y);
                    Main_Painter.label.setVisible(false);
                    Main_Painter.label=null;
                    repaint();
                }
                else if (eraser) {
                    MyClear(PrevX, PrevY, 20, 20);
                    repaint();
                } else if (Fill) {
                    FillArea(PrevX, PrevY); 
                    repaint();
                }
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                if(boo == true){
                    
                }
                else if (eraser) {
                    CurX = e.getX();
                    CurY = e.getY();
                    MyClear(CurX, CurY, 20, 20);
                    repaint();

                } else if (Fill) {

                } else {
                    CurX = e.getX();
                    CurY = e.getY();

                    if (g2 != null) {
                        g2.drawLine(PrevX, PrevY, CurX, CurY);
                        repaint();
                        PrevX = CurX;
                        PrevY = CurY;
                    }

                }

            }
        });
    }

    public void MyClear(int x, int y, int H, int W) {
        boo = false;
        boo1 = false;
        g2.setStroke(new BasicStroke(1));
        Color Curent_Color = g2.getColor();
        for (int i = x; i < x + H; i++) {
            for (int j = y; j < y + W; j++) {
                try {
                    Color Orginal_Color = new Color(image2.getRGB(i, j));
                    g2.setPaint(Orginal_Color);
                    g2.drawRect(i, j, 1, 1);
                } catch (Exception e) {
                }
            }
        }
        g2.setPaint(Curent_Color);
    }

    public void FillArea(int x, int y) {
        boo = false;
        boo1 = false;
        Pair<Integer, Integer> n = new Pair(x, y);
        Color OrginalColor = new Color(((BufferedImage) image).getRGB(x, y));
        if(OrginalColor.equals(FillColor))
            return;
        HashSet<Pair<Integer, Integer>> hs = new HashSet<>();
        Stack<Pair<Integer, Integer>> sta = new Stack<>();
        hs.add(n);
        sta.push(n);
        g2.setPaint(FillColor);
        int dx[] = new int[]{0, 0, 1, -1, 1, 1, -1, -1};
        int dy[] = new int[]{1, -1, 0, 0, -1, 1, -1, 1};
        while (!sta.empty()) {

            n = sta.pop();
            Integer xx = n.getKey();
            Integer yy = n.getValue();

            ((BufferedImage) image).setRGB(xx, yy, FillColor.getRGB());
            
            for (int i = 0; i < 8; i++) {
                int xxx = xx + dx[i];
                int yyy = yy + dy[i];
                if (xxx < 0 || xxx >= Width || yyy < 0 || yyy >= Height ) {
                    System.out.println("55");
                    continue;
                }
                Color CurrentColor=null;
                try {
                     CurrentColor = new Color(((BufferedImage) image).getRGB(xxx, yyy));
                } catch (Exception e) {
                    System.out.println("Xx " +xxx+" Yy"+yyy);
                }
                

                n = new Pair(xxx, yyy);
                if ( !CurrentColor.equals(OrginalColor) ) {
                    continue;
                }
                sta.add(n);
                hs.add(n);
            }

        }
    }

    @Override
    protected void paintComponent(Graphics g) {        
        if (image == null) {
            image = createImage(getSize().width, getSize().height);
            g2 = (Graphics2D) image.getGraphics();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        }
        g.drawImage(image, 0, 0, null);
    }

    public void setImage(Image image) {
        this.image = image;
        g2 = (Graphics2D) image.getGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
         g2.drawImage(image, 0, 0, null);
         repaint();
    }

    public void Brushes() {
        boo = false;
        boo1 = false;
        eraser = false;
        g2.setStroke(new BasicStroke(Main_Painter.b.getValue()));
        g2.setPaint(FillColor);
    }

    public void save() {        
        if(boo == true)
        try {
            File file = new File("new_image.jpg");            
            ImageIO.write((RenderedImage) image3, "jpg", file);
        } catch (IOException ex) {
            Logger.getLogger(Edit_AND_Draw.class.getName()).log(Level.SEVERE, null, ex);
        }
        else
            try {
            File file = new File("new_image.jpg");
            ImageIO.write((RenderedImage) image, "jpg", file);
        } catch (IOException ex) {
            Logger.getLogger(Edit_AND_Draw.class.getName()).log(Level.SEVERE, null, ex);
        }
        boo = false;
        boo1 = false;
    }

    public void activFill_Tool() {
        boo = false;
        Fill = true;
        eraser = false;
        boo1 = false;
    }
    public void Cut() {
        boo = true;
        setCursor(Toolkit.getDefaultToolkit().createCustomCursor(new ImageIcon("crop.png").getImage(), new Point(0, 30), "custom cursor"));
        eraser = false;
        g2.setPaint(Color.black);
        boo1 = false;
    }
    public void zoom(){
     boo1=true;   
     eraser = false;
     boo = false;
     setCursor(Toolkit.getDefaultToolkit().createCustomCursor(new ImageIcon("zoom-in.png").getImage(), new Point(0, 30), "custom cursor"));
    }
    
}

