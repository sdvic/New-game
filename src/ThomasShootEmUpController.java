import javax.swing.*;
import java.applet.AudioClip;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.net.URL;

/***********************************************************************************************
 * David Frieder's Thomas Game Copyright 2018 David Frieder 10/16/2018
 * Feeling better about clean up vic 11/12/2018  rev 3.9
 ***********************************************************************************************/
public class ThomasShootEmUpController extends JComponent implements ActionListener, Runnable, KeyListener
{
    Graphics g = null;
    private int widthOfScreen = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
    private int heightOfScreen = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;
    private Point thomasHomePosition = new Point(widthOfScreen / 3, 705 * heightOfScreen / 1000);
    private Point thomasPosition;
    private Point mainTrackPosition = new Point(0, 842 * heightOfScreen / 1000);
    private Point upperTrackPosition = new Point(0, 500 * heightOfScreen / 1000);
    private Thomas thomas = new Thomas(thomasHomePosition);
    private Track upperTrack = new Track(upperTrackPosition, 1);
    private Track mainTrack = new Track(mainTrackPosition, 3);
    private Road road = new Road();
    private boolean isGoingRight;
    private JFrame mainGameWindow = new JFrame("NewGame");// Makes window with
    private AffineTransform identityTx = new AffineTransform();
    private AffineTransform backgroundTx = new AffineTransform();
    private Timer animationTicker = new Timer(40, this);
    private Timer jumpingTicker = new Timer(800 / 60, this);
    private boolean isGoingLeft;
    private boolean isJumping;
    private Graphics2D g2;
    private URL thomasThemeAddress = getClass().getResource("ThomasThemeSong.wav");
    private AudioClip thomasThemeSong = JApplet.newAudioClip(thomasThemeAddress);

    /***********************************************************************************************
     * Main
     ***********************************************************************************************/
    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(new ThomasShootEmUpController());
    }

    /***********************************************************************************************
     * Run
     ***********************************************************************************************/
    @Override
    public void run()
    {
        setUpMainGameWindow();
//        thomasThemeSong.loop();
        animationTicker.start();
        jumpingTicker.start();
    }

    /***********************************************************************************************
     * Set up main JFrame
     ***********************************************************************************************/
    private void setUpMainGameWindow()
    {
        mainGameWindow.setTitle("Thomas the tank");
        mainGameWindow.setSize(widthOfScreen, heightOfScreen);
        mainGameWindow.add(this);// Adds the paint method to the JFrame
        mainGameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainGameWindow.getContentPane().setBackground(new Color(200, 235, 255));
        mainGameWindow.setVisible(true);
        mainGameWindow.addKeyListener(this);
    }

    /***********************************************************************************************
     * Paint
     ***********************************************************************************************/
    public void paint(Graphics g)
    {
        g2 = (Graphics2D) g;
        drawThomas();
        g2.setTransform(backgroundTx);
        drawRoad();
        drawObstacle();
        drawTracks(mainTrack);
        drawTracks(upperTrack);
        if (testIntersection(thomas.getThomasBoundingBoxShape(), upperTrack.getTrackBoundingBoxShape())) ;
        {
            System.out.println("Bump upper track");
            g2.setColor(Color.RED);
            g2.fillOval(500, 500, 10, 10);
        }
    }

    private void drawObstacle()
    {
        g2.setTransform(backgroundTx);
        g2.translate(-widthOfScreen, heightOfScreen - 400);
        g2.fillRect(0, 0, 500, 300);
    }

    /***********************************************************************************************
     * Draw road
     ***********************************************************************************************/
    private void drawRoad()
    {
        Image roadImage = road.getRoadImage();
        int roadImageWidth = roadImage.getWidth(null);
        for (int i = 0; i < 1 + widthOfScreen / roadImageWidth; i++)
        {
            g2.drawImage(roadImage, i * roadImageWidth, 85 * heightOfScreen / 100, null);
        }
    }

    /***********************************************************************************************
     * Draw tracks
     ***********************************************************************************************/
    private void drawTracks(Track track)
    {
        Image trackSectionImage = track.getTrackSectionImage();
        int trackSectionWidth = trackSectionImage.getWidth(null);
        Point trackPosition = track.getTrackPosition();
        int trackYposition = trackPosition.y;
        g2.setTransform(backgroundTx);
        for (int i = 0; i < widthOfScreen / trackSectionWidth; i++)
        {
            g2.drawImage(trackSectionImage, i * trackSectionWidth, trackYposition, null);
        }
        g2.setColor(Color.GREEN);
        g2.draw(track.getTrackBoundingBoxShape());
    }

    /***********************************************************************************************
     * Draw Thomas
     ***********************************************************************************************/
    public void drawThomas()
    {
        g2.setTransform(identityTx);
        try
        {
            g2.setColor(Color.GREEN);
            g2.draw(thomas.getThomasBoundingBoxShape());
            Image thomasSpriteImage = thomas.getForwardThomasSpriteImageArray()[0];
            if (isGoingLeft)// Thomas going left
            {
                thomasSpriteImage = thomas.nextThomasSpriteImage(false);
            }
            if (isGoingRight)// Thomas going right
            {
                thomasSpriteImage = thomas.nextThomasSpriteImage(true);
            }
            g2.drawImage(thomasSpriteImage, thomas.getThomasPosition().x, thomas.getThomasPosition().y, null);
        } catch (Exception ex)
        {
            System.out.println("error reading thomas thomasSpriteImage from thomas sprite thomasSpriteImage array");
        }
    }

    /***********************************************************************************************
     * Check for intersections
     ***********************************************************************************************/
    public boolean testIntersection(Shape shapeA, Shape shapeB)
    {
        if (shapeA.intersects(shapeB.getBounds2D()))
        {
            return true;
        }
        return false;
    }

    /***********************************************************************************************
     * Respond to key typed...Not being used
     ***********************************************************************************************/
    @Override
    public void keyTyped(KeyEvent e)
    {
    }

    /***********************************************************************************************
     * Respond to key pressed
     ***********************************************************************************************/
    @Override
    public void keyPressed(KeyEvent e)
    {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) // going right
        {
            isGoingRight = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) // going left
        {
            isGoingLeft = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_UP)
        {
            isJumping = true;
        }
    }

    /***********************************************************************************************
     * Respond to key released
     ***********************************************************************************************/
    @Override
    public void keyReleased(KeyEvent e)
    {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) // going right
        {
            isGoingRight = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) // going left
        {
            isGoingLeft = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_UP)
        {
            isJumping = false;
        }
    }

    /***********************************************************************************************
     * Action Performed.....Respond to animation ticker and paint ticker
     ***********************************************************************************************/
    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == animationTicker)
        {
            if (isGoingLeft)
            {
                backgroundTx.setToTranslation(backgroundTx.getTranslateX() + 10, 0);
            }
            if (isGoingRight)
            {
                backgroundTx.setToTranslation(backgroundTx.getTranslateX() - 10, 0);
            }
        }
        if (isJumping)
        {
            thomas.setThomasPosition(new Point(thomas.getThomasPosition().x, thomas.getThomasPosition().y - 5));
        }
        if (!isJumping && thomas.getThomasPosition().y < (heightOfScreen - 475))
        {
            thomas.setThomasPosition(new Point(thomas.getThomasPosition().x, thomas.getThomasPosition().y + 5));
        }
        repaint();
    }
}
