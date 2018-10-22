import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.io.IOException;

import static javax.imageio.ImageIO.read;

public class Thomas
{
    int thomasBoxWidth;
    int thomasBoxHeight;
    private Image[] thomasSpriteImageArray = new Image[8];
    private Image[] reverseThomasImageArray = new Image[8];
    private AffineTransform thomasTransform = new AffineTransform();
    private int thomasMaxSpeed = 13;
    private Graphics2D g2;
    private int thomasXpos;
    private int thomasYpos;
    private int thomasSpriteImageCounter;
    private Image thomasSpriteImage;
    private Image reverseThomasImage;

    public Thomas()
    {
        Rectangle2D.Double thomasBoundingBox = new Rectangle2D.Double(0, 0, thomasBoxWidth, thomasBoxHeight);
        try
        {
            thomasSpriteImageArray[0] = read(getClass().getResource("Thomas1.png"));
            thomasSpriteImageArray[1] = read(getClass().getResource("Thomas2.png"));
            thomasSpriteImageArray[2] = read(getClass().getResource("Thomas3.png"));
            thomasSpriteImageArray[3] = read(getClass().getResource("Thomas4.png"));
            thomasSpriteImageArray[4] = read(getClass().getResource("Thomas5.png"));
            thomasSpriteImageArray[5] = read(getClass().getResource("Thomas6.png"));
            thomasSpriteImageArray[6] = read(getClass().getResource("Thomas7.png"));
            thomasSpriteImageArray[7] = read(getClass().getResource("Thomas8.png"));
            reverseThomasImageArray[0] = read(getClass().getResource("Reversed Thomas1.png"));
            reverseThomasImageArray[1] = read(getClass().getResource("Reversed Thomas2.png"));
            reverseThomasImageArray[2] = read(getClass().getResource("Reversed Thomas3.png"));
            reverseThomasImageArray[3] = read(getClass().getResource("Reversed Thomas4.png"));
            reverseThomasImageArray[4] = read(getClass().getResource("Reversed Thomas5.png"));
            reverseThomasImageArray[5] = read(getClass().getResource("Reversed Thomas6.png"));
            reverseThomasImageArray[6] = read(getClass().getResource("Reversed Thomas7.png"));
            reverseThomasImageArray[7] = read(getClass().getResource("Reversed Thomas8.png"));
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public Image nextThomasSpriteImage(boolean goingRight)
    {
        if (goingRight)
        {
            thomasSpriteImageCounter++;
            thomasSpriteImageCounter = thomasSpriteImageCounter % 8;
            return thomasSpriteImageArray[thomasSpriteImageCounter];
        } else
        {
            thomasSpriteImageCounter++;
            thomasSpriteImageCounter = thomasSpriteImageCounter % 8;
            return reverseThomasImageArray[thomasSpriteImageCounter];
        }
    }

    public Image[] getReverseThomasImageArray()
    {
        return reverseThomasImageArray;
    }

    public Image[] getThomasSpriteImageArray()
    {
        return thomasSpriteImageArray;
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
}
