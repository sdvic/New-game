import javax.swing.*;
import java.applet.AudioClip;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.net.URL;

/***********************************************************************************************
 * David Frieder's Thomas Game Copyright 2018 David Frieder 10/16/2018 rev 3.8
 * Thomas Jumping vic 11/2/2018
 ***********************************************************************************************/
public class ThomasShootEmUpController extends JComponent implements ActionListener, Runnable, KeyListener
{
    Graphics g = null;
    private int widthOfScreen = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
    private int heightOfScreen = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;
    private Point thomasPosition = new Point(widthOfScreen / 3, 705 * heightOfScreen / 1000);
    private Point upperTrackPosition = new Point(0, 842 * heightOfScreen / 1000);
    private Point mainTrackPosition = new Point(0, 500 * heightOfScreen / 1000);
    private Thomas thomas = new Thomas(thomasPosition);
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
        thomas.setThomasPosition(thomasPosition);
        repaint();
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
        g2.setTransform(backgroundTx);
        drawThomas();
        drawRoad();
        drawObstacle();
        drawTracks(mainTrack);
        drawTracks(upperTrack);
//        if (testIntersection(thomasBoundingBoxShape, upperTrackShape))
//        {
//            if (jumpingVelocity > 0 && thomasYOffsetFromGround < trackYPos)
//            {
//                jumpingVelocity = initialJumpingVelocity;
//                isJumping = false;
//                isFalling = false;
//                //g2.setTransform(thomasTransform);
//            }
//        } else if (!testIntersection(thomasBoundingBoxShape, upperTrackShape))
//        {
//            isFalling = true;
//            if (thomasYOffsetFromGround > 0)
//            {
//                jumpingVelocity = initialJumpingVelocity;
//                thomasYOffsetFromGround = 0;
//                isJumping = false;
//            }
//            repaint();
//        }
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
        g2.setTransform(backgroundTx);
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
        g2.setTransform(backgroundTx);
        Rectangle2D.Double trackBoundingBox = new Rectangle2D.Double(track.getTrackPosition().x, track.getTrackPosition().y, track.getTrackSectionWidth() * track.getNumberOfTracKSections(), track.getTrackSectionHeight());
        for (int i = 0; i < track.getNumberOfTracKSections(); i++)
        {
            g2.drawImage(track.getTrackSectionImage(), i * track.getTrackSectionWidth(), track.getTrackPosition().y, null);
        }
        g2.setColor(Color.GREEN);
        g2.draw(track.getTrackShape());
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
            g2.draw(thomas.getThomasShape());
            Image thomasSpriteImage = thomas.getForwardThomasSpriteImageArray()[0];
            if (isGoingLeft)// Thomas going left
            {
                thomasSpriteImage = thomas.nextThomasSpriteImage(false);
            }
            if (isGoingRight)// Thomas going right
            {
                thomasSpriteImage = thomas.nextThomasSpriteImage(true);
            }
            if (!isGoingRight && !isGoingLeft) //Shows an image of Thomas when not moving
            {
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
                backgroundTx.setToTranslation(backgroundTx.getTranslateX() + 20, 0);
            }
            if (isGoingRight)
            {
                backgroundTx.setToTranslation(backgroundTx.getTranslateX() - 20, 0);
            }
        }
        if (isJumping)
        {
            thomas.setThomasPosition(new Point(thomas.getThomasPosition().x, thomas.getThomasPosition().y - 20));
        }
        if (!isJumping && thomas.getThomasPosition().y < (heightOfScreen - 480))
        {
            thomas.setThomasPosition(new Point(thomas.getThomasPosition().x, thomas.getThomasPosition().y + 20));
        }
        repaint();
    }
}
