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
 * David Frieder's Thomas Game Copyright 2018 David Frieder 10/16/2018 rev 3.6
 * Refactoring vic 10/23/2018
 ***********************************************************************************************/
public class ThomasShootEmUpController extends JComponent implements ActionListener, Runnable, KeyListener
{
    Graphics g = null;
    private int widthOfScreen = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
    private int heightOfScreen = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;
    private Point upperTrackPosition = new Point(0, 500 * heightOfScreen / 1000);
    private Point mainTrackPosition = new Point(0, 842 * heightOfScreen / 1000);
    private Point thomasHomePosition = new Point(widthOfScreen / 3, 705 * heightOfScreen / 1000);
    private int thomasDeltaY;
    private Thomas thomas = new Thomas(thomasHomePosition);
    private Track upperTrack = new Track(upperTrackPosition, 1);
    private Track mainTrack = new Track(mainTrackPosition, 5);
    private Road road = new Road();
    private boolean isGoingRight;
    private Shape upperTrackShape;
    private Shape thomasBoundingBoxShape;
    private JFrame mainGameWindow = new JFrame("NewGame");// Makes window with
    private AffineTransform identityTx = new AffineTransform();
    private AffineTransform backgroundTx = new AffineTransform();
    private Timer animationTicker = new Timer(40, this);
    private Timer jumpingTicker = new Timer(800 / 60, this);
    private boolean isGoingLeft;
    private boolean isJumping;
    private boolean isFalling;
    private int initialJumpingVelocity = -31;
    private int jumpingVelocity = initialJumpingVelocity;
    private int initialFallingVelocity = 0;
    private int fallingVelocity = initialFallingVelocity;
    private int movingVelocity;
    private int gravityAcceleration = 1;
    private Graphics2D g2;
    private int thomasYOffsetFromGround = 0;
    private boolean lastWayFacing = true;
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
        thomas.setThomasHomePosition(thomasHomePosition);
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
        drawTracks(0, 842 * heightOfScreen / 1000, 6);//Lower track
        drawTracks(0, 500 * heightOfScreen / 1000, 2);//Upper track
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
    private void drawTracks(int trackXPos, int trackYPos, int trackSections)
    {
        g2.setTransform(backgroundTx);
        Image trackSectionImage = upperTrack.getTrackSectionImage();
        int trackSectionWidth = trackSectionImage.getWidth(null);
        int trackSectionHeight = trackSectionImage.getHeight(null);
        Rectangle2D.Double trackBoundingBox = new Rectangle2D.Double(trackXPos, trackYPos, trackSectionWidth * trackSections, trackSectionHeight);
        for (int i = 0; i < trackSections; i++)
        {
            g2.drawImage(trackSectionImage, i * trackSectionWidth, trackYPos, null);
        }
        g2.setColor(Color.GREEN);
        g2.draw(trackBoundingBox);
    }

    /***********************************************************************************************
     * Draw Thomas
     ***********************************************************************************************/
    public void drawThomas()
    {
        g2.setTransform(identityTx);
        try
        {
            Rectangle2D.Double thomasBoundingBox = thomas.getThomasBoundingBox();
            thomasBoundingBoxShape = thomasBoundingBox.getBounds();
            g2.setColor(Color.GREEN);
            g2.draw(thomasBoundingBoxShape);
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
            g2.drawImage(thomasSpriteImage, thomas.getThomasHomePosition().x, thomas.getThomasHomePosition().y, null);
        } catch (Exception ex)
        {
            System.out.println("error reading thomas thomasSpriteImage from thomas sprite thomasSpriteImage array");
        }
    }

    /***********************************************************************************************
     * Thomas Jumper
     ***********************************************************************************************/
    public void jump(ActionEvent e)
    {
        if (e.getSource() == jumpingTicker)
        {
            thomasYOffsetFromGround += jumpingVelocity;
            jumpingVelocity += gravityAcceleration;
            repaint();
        }
    }

    /***********************************************************************************************
     * Thomas Falling
     ***********************************************************************************************/
    public void fall(ActionEvent e)
    {
        if (e.getSource() == jumpingTicker && !isJumping)
        {
            if (g2 != null)
            {
                thomasYOffsetFromGround += fallingVelocity;
                fallingVelocity += gravityAcceleration;
            }
            if ((thomasYOffsetFromGround > 0 || testIntersection(thomasBoundingBoxShape, upperTrackShape)))
            {
                fallingVelocity = initialFallingVelocity;
                thomasYOffsetFromGround = 0;
                isFalling = false;
                isJumping = false;
            }
            isFalling = false;
            repaint();
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
            thomas.setThomasHomePosition(new Point(thomas.getThomasHomePosition().x, thomas.getThomasHomePosition().y - 20));
        }
        if (!isJumping && thomas.getThomasHomePosition().y < (heightOfScreen - 480))
        {
            thomas.setThomasHomePosition(new Point(thomas.getThomasHomePosition().x, thomas.getThomasHomePosition().y + 20));
        }
        repaint();
    }
}
