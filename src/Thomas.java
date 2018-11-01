import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.io.IOException;

import static javax.imageio.ImageIO.read;

public class Thomas
{
    private int thomasBoundingBoxWidth;
    private int thomasBoundingBoxHeight;
    private Image[] forwardThomasSpriteImageArray = new Image[8];
    private Image[] reverseThomasSpriteImageArray = new Image[8];
    private AffineTransform thomasTransform = new AffineTransform();
    private int thomasMaxSpeed = 13;
    private Graphics2D g2;
    private int thomasXpos;
    private int thomasYpos;
    private int thomasSpriteImageCounter;
    private Rectangle2D.Double thomasBoundingBox;
    private Point thomasHomePosition;


    public Thomas(Point thomasHomePosition)
    {
        try
        {
            forwardThomasSpriteImageArray[0] = read(getClass().getResource("Thomas1.png"));
            forwardThomasSpriteImageArray[1] = read(getClass().getResource("Thomas2.png"));
            forwardThomasSpriteImageArray[2] = read(getClass().getResource("Thomas3.png"));
            forwardThomasSpriteImageArray[3] = read(getClass().getResource("Thomas4.png"));
            forwardThomasSpriteImageArray[4] = read(getClass().getResource("Thomas5.png"));
            forwardThomasSpriteImageArray[5] = read(getClass().getResource("Thomas6.png"));
            forwardThomasSpriteImageArray[6] = read(getClass().getResource("Thomas7.png"));
            forwardThomasSpriteImageArray[7] = read(getClass().getResource("Thomas8.png"));
            reverseThomasSpriteImageArray[0] = read(getClass().getResource("Reversed Thomas1.png"));
            reverseThomasSpriteImageArray[1] = read(getClass().getResource("Reversed Thomas2.png"));
            reverseThomasSpriteImageArray[2] = read(getClass().getResource("Reversed Thomas3.png"));
            reverseThomasSpriteImageArray[3] = read(getClass().getResource("Reversed Thomas4.png"));
            reverseThomasSpriteImageArray[4] = read(getClass().getResource("Reversed Thomas5.png"));
            reverseThomasSpriteImageArray[5] = read(getClass().getResource("Reversed Thomas6.png"));
            reverseThomasSpriteImageArray[6] = read(getClass().getResource("Reversed Thomas7.png"));
            reverseThomasSpriteImageArray[7] = read(getClass().getResource("Reversed Thomas8.png"));
            thomasBoundingBoxWidth = forwardThomasSpriteImageArray[0].getWidth(null);
            thomasBoundingBoxHeight = forwardThomasSpriteImageArray[0].getHeight(null);
            this.thomasHomePosition = thomasHomePosition;
            thomasBoundingBox = new Rectangle2D.Double(thomasHomePosition.x, thomasHomePosition.y, thomasBoundingBoxWidth, thomasBoundingBoxHeight);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public Image nextThomasSpriteImage(boolean goingRight)
    {
        if (!goingRight)
        {
            thomasSpriteImageCounter++;
            thomasSpriteImageCounter = thomasSpriteImageCounter % 8;
            return forwardThomasSpriteImageArray[thomasSpriteImageCounter];
        } else
        {
            thomasSpriteImageCounter++;
            thomasSpriteImageCounter = thomasSpriteImageCounter % 8;
            return reverseThomasSpriteImageArray[thomasSpriteImageCounter];
        }
    }

    public Image[] getReverseThomasSpriteImageArray()
    {
        return reverseThomasSpriteImageArray;
    }

    public Image[] getForwardThomasSpriteImageArray()
    {
        return forwardThomasSpriteImageArray;
    }

    public int getThomasXpos()
    {
        return thomasXpos;
    }

    public void setThomasXpos(int thomasXpos)
    {
        this.thomasXpos = thomasXpos;
    }

    public int getThomasYpos()
    {
        return thomasYpos;
    }

    public void setThomasYpos(int thomasYpos)
    {
        this.thomasYpos = thomasYpos;
    }

    public Rectangle2D.Double getThomasBoundingBox()
    {
        return thomasBoundingBox;
    }

    public void setThomasHomePosition(Point thomasHomePosition)
    {
        this.thomasHomePosition = thomasHomePosition;
    }
    
}


