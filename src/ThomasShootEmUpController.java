import javax.imageio.ImageIO;
import javax.swing.*;

import java.applet.AudioClip;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class ThomasShootEmUpController extends JComponent implements ActionListener, Runnable
{
    public ThomasUtilites util = new ThomasUtilites(1);
    private Rectangle2D.Double upperTrackDetectionZone = new Rectangle2D.Double(0, 0, 200, 49);
    private AffineTransform tx;
    private URL thomasThemeAddress = getClass().getResource("Thomas The Tank Engine Theme Song.wav");
    private AudioClip thomasThemeSong = JApplet.newAudioClip(thomasThemeAddress);
    private BufferedImage img;
    private Image[] thomasSpriteImageArray = new Image[8];
    private Image gun = Toolkit.getDefaultToolkit().createImage(getClass().getResource("Minigun_SU.png"));
    private int widthOfScreen = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
    private int heightOfScreen = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;
    private JFrame mainGameWindow = new JFrame("NewGame");// Makes window with title "NewGame"
    private AffineTransform identityTx = new AffineTransform();
    private Timer paintTicker = new Timer(20, this);
    private Timer animationTicker = new Timer(45, this);
    private ImageIcon thomasImageIcon = new ImageIcon();
    private Image thomasSpriteImage;
    private int pictureCounter;
    private int thomasSpriteImageCounter;
    private int thomasXPos = widthOfScreen / 2;
    private int thomasYPos = (int)(heightOfScreen * 0.69);
    private Image roadImage = Toolkit.getDefaultToolkit().createImage(getClass().getResource("ground.png"));
    private Image tracksImage = Toolkit.getDefaultToolkit().createImage(getClass().getResource("Standard Gauge Train Track Sprite.png"));
    int trackWidth = tracksImage.getWidth(mainGameWindow);
    int roadWidth = roadImage.getWidth(mainGameWindow);
    private int roadXPos = 0;
    private int groundLevelTrackYPos = (int)(heightOfScreen * 0.809);
    private int level2TrackYPos = (int)(heightOfScreen * 0.2);
    private double trackScale = 1.7;
    private boolean isGoingRight;
    private boolean isGoingLeft = true;
    private boolean isNotMoving;
    private boolean isJumping;
    private boolean isFalling;
    private int position;
    private int thomasMaxSpeed = 22;
    private int initialJumpingVelocity = -37;
    public int jumpingVelocity = initialJumpingVelocity;
    private int movingVelocity;
    private int gravityAcceleration = 1;
    private Graphics2D g2;

    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(new ThomasShootEmUpController());
    }

    public void run()
    {
        paintTicker.start();
        animationTicker.start();
        loadThomasSpriteImages();
        makeMainGameWindow();
        thomasThemeSong.play();
        isGoingLeft = true;
        isGoingRight = false;
    }

	



    public void paint(Graphics g)
    {
        g2 = (Graphics2D)g;
        g2.setTransform(identityTx);


        if (!util.isJumping && jumpingVelocity < 0)
        {
            gravityAcceleration = 3;
        }
        if (util.isJumping || thomasYPos < (int)(heightOfScreen * 0.69))
        {
            thomasSpriteImage = thomasSpriteImageArray[thomasSpriteImageCounter];
            thomasYPos += jumpingVelocity;
            jumpingVelocity += gravityAcceleration;
            gravityAcceleration = 1;
        }

        for (int i = -5; i < 5; i++) //for loop that condenses the drawing of the roads
        {
            g2.setTransform(identityTx);
            g2.translate(roadXPos + roadWidth * i, groundLevelTrackYPos);
            g2.scale(trackScale, trackScale);
            g2.drawImage(roadImage, 0, 0, this);
        }
        for (int i = -4; i < 4; i++) //for loop that condenses the drawing of the tracks
        {
            g2.setTransform(identityTx);
            g2.translate(roadXPos + trackWidth * trackScale * i, groundLevelTrackYPos);
            g2.scale(trackScale, trackScale);
            g2.drawImage(tracksImage, 0, 0, this);
        }
        g2.setColor(Color.green);
        g2.setTransform(identityTx);
        g2.scale(trackScale, trackScale);
        g2.translate(roadXPos / trackScale, 0);
        g2.drawImage(tracksImage, 0, level2TrackYPos, this); //draws an elevated set of tracks
        g2.setTransform(identityTx);
        g2.translate(roadXPos, level2TrackYPos);
        g2.draw(upperTrackDetectionZone); // upper tracks detection zone rectangle
        if (roadXPos > mainGameWindow.getWidth())
        {
            roadXPos = -mainGameWindow.getWidth() / 4;
        }
        if (roadXPos < -mainGameWindow.getWidth() / 4)
        {
            roadXPos = mainGameWindow.getWidth();
        }
        if (thomasYPos > (int)(heightOfScreen * 0.69))
        {
            thomasYPos = (int)(heightOfScreen * 0.69); //Added this to compensate for Thomas falling through the tracks
        }
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == paintTicker)
        {
            repaint();
            if (thomasYPos >= (int)(heightOfScreen * 0.69))
            {
                if (util.isJumping)
                {
                    isJumping = true;
                }
            }
        }
        if (e.getSource() == animationTicker)
        {

            movingVelocity = movingVelocity + 1;
            thomasSpriteImage = thomasSpriteImageArray[thomasSpriteImageCounter];
            thomasSpriteImageCounter = (thomasSpriteImageCounter + 1) % 8;
            if(g2 != null)
            {
                drawNextThomasSpriteImage(g2, thomasSpriteImageCounter);
            }
            roadXPos = roadXPos + movingVelocity;
            if (movingVelocity > thomasMaxSpeed || (movingVelocity > 0 && !(util.moveLeft || util.moveRight)) || movingVelocity > 0 && isNotMoving)
            { //allows Thomas to decelerate going right
                movingVelocity--;
            }
            if (movingVelocity < -thomasMaxSpeed || (movingVelocity < 0 && !(util.moveLeft || util.moveRight)) || movingVelocity < 0 && isNotMoving)
            { //allows Thomas to decelerate going left
                movingVelocity++;
            }
            if (util.moveLeft)
            {
                isGoingLeft = true;
                isGoingRight = false;
                isNotMoving = false;
                drawNextThomasSpriteImage(g2, thomasSpriteImageCounter);
                movingVelocity = movingVelocity + 1;
                repaint();
            }


            if (util.moveRight)
            {
                isGoingRight = true;
                isGoingLeft = false;
                isNotMoving = false;
                try
                {
                    thomasSpriteImage = thomasSpriteImageArray[thomasSpriteImageCounter];
                } catch (Exception ex)
                {
                    System.out.println("error reading thomas thomasSpriteImage from thomas sprite thomasSpriteImage array");
                }
                AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
                movingVelocity = movingVelocity - 1;
                repaint();
            }
            if (util.moveLeft && util.moveRight)
            {
                isNotMoving = true;
            }

            if (thomasYPos >= (int)(heightOfScreen * 0.69))
            {
                jumpingVelocity = initialJumpingVelocity;
            }
        }
    }
    /***********************************************************************************************
    * Create the main JFrame
    ************************************************************************************************/
    public void makeMainGameWindow()
	{
		mainGameWindow.setTitle("Thomas the tank");
        mainGameWindow.setSize(widthOfScreen, heightOfScreen);
        mainGameWindow.add(this);// Adds the paint method to the JFrame
        mainGameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainGameWindow.getContentPane().setBackground(new Color(200, 235, 255));
        mainGameWindow.setVisible(true);
        mainGameWindow.addKeyListener(util);
	}
    /***********************************************************************************************
     * Get sprite image and draw Thomas
     ***********************************************************************************************/
    private void drawNextThomasSpriteImage(Graphics g2, int thomasSpriteImageCounter)
    {
        //g2.setTransform(identityTx);
        try
        {
            thomasSpriteImage = thomasSpriteImageArray[thomasSpriteImageCounter];
            System.out.println(thomasSpriteImageCounter);
        } catch (Exception ex)
        {
            System.out.println("error reading thomas thomasSpriteImage from thomas sprite thomasSpriteImage array");
        }
        g2.drawImage(thomasSpriteImage, 0, 0, null);
        repaint();
    }

    /***********************************************************************************************
     * Get Thomas sprite .png files, convert to Image and load sprite array...thomasSpriteImageArray
     ***********************************************************************************************/
    private void loadThomasSpriteImages()
    {
        try
        {
            thomasSpriteImageArray[0] = ImageIO.read(getClass().getResource("Thomas1.png"));
            thomasSpriteImageArray[1] = ImageIO.read(getClass().getResource("Thomas2.png"));
            thomasSpriteImageArray[2] = ImageIO.read(getClass().getResource("Thomas3.png"));
            thomasSpriteImageArray[3] = ImageIO.read(getClass().getResource("Thomas4.png"));
            thomasSpriteImageArray[4] = ImageIO.read(getClass().getResource("Thomas5.png"));
            thomasSpriteImageArray[5] = ImageIO.read(getClass().getResource("Thomas6.png"));
            thomasSpriteImageArray[6] = ImageIO.read(getClass().getResource("Thomas7.png"));
            thomasSpriteImageArray[7] = ImageIO.read(getClass().getResource("Thomas8.png"));
        } catch (IOException e)
        {
            System.out.println("error reading from thomas sprite array");
        }
    }
}


  