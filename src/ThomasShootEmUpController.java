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
 * David Frieder's Thomas Game Copyright 2018 David Frieder 10/16/2018 rev 3.5
 * Refactoring vic 10/22/2018
 ***********************************************************************************************/
public class ThomasShootEmUpController extends JComponent implements ActionListener, Runnable, KeyListener
{
    private Thomas thomas = new Thomas();
    private Track track = new Track();
    private Road road = new Road();
    private boolean isGoingRight;
    private Shape upperTrackShape;
    private int thomasBoxWidth;
    private int thomasBoxHeight;
    private Shape thomasShape;
    private int widthOfScreen = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
    private int heightOfScreen = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;
    private JFrame mainGameWindow = new JFrame("NewGame");// Makes window with
    private AffineTransform identityTx = new AffineTransform();
    private AffineTransform backgroundTx = new AffineTransform();
    private Timer animationTicker = new Timer(40, this);
    private Timer jumpingTicker = new Timer(800 / 60, this);
    private Image thomasSpriteImage = thomas.getThomasSpriteImageArray()[0];
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
    private Point thomasHomePosition = new Point(widthOfScreen / 3, 705*heightOfScreen/1000);
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
        //jumpingTicker.start();
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
//        if (testIntersection(thomasShape, upperTrackShape))
//        {
//            if (jumpingVelocity > 0 && thomasYOffsetFromGround < trackYPos)
//            {
//                jumpingVelocity = initialJumpingVelocity;
//                isJumping = false;
//                isFalling = false;
//                //g2.setTransform(thomasTransform);
//            }
//        } else if (!testIntersection(thomasShape, upperTrackShape))
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
        for (int i = 0; i < 1 + widthOfScreen/roadImageWidth; i++)
        {
            g2.drawImage(roadImage, i * roadImageWidth, 85 * heightOfScreen / 100, null);
        }
    }

    /***********************************************************************************************
     * Draw any tracks
     ***********************************************************************************************/
    private void drawTracks(int trackXPos, int trackYPos, int trackSections)
    {
        g2.setTransform(backgroundTx);
        Image trackImage = track.getTrackImage();
        int trackImageWidth = trackImage.getWidth(null);
        Rectangle2D.Double trackBoundingBox = new Rectangle2D.Double(0, 0, track.getTrackImage().getWidth(null), track.trackImage.getHeight(null));
        g2.setColor(Color.GREEN);
        g2.draw(trackBoundingBox);
        for (int i = 0; i < trackSections; i++)
        {
            g2.drawImage(trackImage, i * trackImageWidth, trackYPos, null);
        }
    }

    /***********************************************************************************************
     * Draw Thomas
     ***********************************************************************************************/
    public void drawThomas()
    {
        g2.setTransform(identityTx);
        try
        {
            Rectangle2D.Double thomasBoundingBox = new Rectangle2D.Double(0, 0, thomasBoxWidth, thomasBoxHeight);
            thomasShape = thomasBoundingBox.getBounds();
            g2.setColor(Color.GREEN);
            thomasBoundingBox.x = thomasHomePosition.x;
            thomasBoundingBox.y = thomasHomePosition.y;
            thomas.setThomasXpos(thomasHomePosition.x);
            thomas.setThomasYpos(thomasHomePosition.y);
            g2.draw(thomasBoundingBox);
            if (!isGoingLeft && !isGoingRight) // To show initial standing still Thomas
            {
                g2.drawImage(thomasSpriteImage, thomasHomePosition.x, thomasHomePosition.y, null);
            }
            if (isGoingLeft)// Thomas going left
            {
                thomasSpriteImage = thomas.nextThomasSpriteImage(false);
                g2.drawImage(thomasSpriteImage, thomasHomePosition.x, thomasHomePosition.y, null);
                lastWayFacing = true;
                thomasBoxWidth = thomasSpriteImage.getWidth(null);
                thomasBoxHeight = thomasSpriteImage.getHeight(null);
            }
            if (isGoingRight)// Thomas going right
            {
                thomasSpriteImage = thomas.nextThomasSpriteImage(true);
                g2.drawImage(thomasSpriteImage, thomasHomePosition.x, thomasHomePosition.y, null);
                lastWayFacing = false;
                thomasBoxWidth = thomasSpriteImage.getWidth(null);
                thomasBoxHeight = thomasSpriteImage.getHeight(null);
            }

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
            if (g2 != null)
            {
                thomasYOffsetFromGround += jumpingVelocity;
                jumpingVelocity += gravityAcceleration;
            }
            if (thomasYOffsetFromGround > 0)
            {
                jumpingVelocity = initialJumpingVelocity;
                thomasYOffsetFromGround = 0;
                isJumping = false;
                isFalling = false;
            }
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
            if ((thomasYOffsetFromGround > 0 || testIntersection(thomasShape, upperTrackShape)))
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
            if (isGoingLeft == true)
            {
                thomasSpriteImage = thomas.nextThomasSpriteImage(!isGoingRight);
                backgroundTx.setToTranslation(backgroundTx.getTranslateX() + 20, 0);
            }
            if (isGoingRight == true)
            {
                thomasSpriteImage = thomas.nextThomasSpriteImage(isGoingRight);
                backgroundTx.setToTranslation(backgroundTx.getTranslateX() - 20, 0);

            }
        }
        if (isJumping == true)
        {
            jump(e);
        }
        if (isFalling == true)
        {
            fall(e);
        }
        repaint();
    }
}
