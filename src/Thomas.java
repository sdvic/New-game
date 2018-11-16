import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.IOException;

import static javax.imageio.ImageIO.read;

public class Thomas
{

    private Image[] forwardThomasSpriteImageArray = new Image[8];
    private Image[] reverseThomasSpriteImageArray = new Image[8];
    private int thomasSpriteImageCounter;
    private Rectangle2D.Double thomasBoundingBox;
    private Point thomasPosition;
    private Shape thomasBoundingBoxShape;


    public Thomas(Point thomasPosition)
    {
        int thomasBoundingBoxWidth;
        int thomasBoundingBoxHeight;
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
            this.thomasPosition = thomasPosition;
            thomasBoundingBox = new Rectangle2D.Double(thomasPosition.x, thomasPosition.y, thomasBoundingBoxWidth, thomasBoundingBoxHeight);
            // thomasBoundingBoxShape = thomasBoundingBox.getBounds();

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

    public Image[] getForwardThomasSpriteImageArray()
    {
        return forwardThomasSpriteImageArray;
    }


    public void setThomasPosition(Point thomasPosition)
    {
        this.thomasPosition = thomasPosition;
    }

    public Point getThomasPosition()
    {
        return thomasPosition;
    }

//    public Shape getThomasBoundingBoxShape()
//    {
//        return thomasBoundingBoxShape;
//    }

    public Rectangle2D.Double getThomasBoundingBox()
    {
        return thomasBoundingBox;
    }

    public void setThomasBoundingBox(Rectangle2D.Double thomasBoundingBox)
    {
        this.thomasBoundingBox = thomasBoundingBox;
        thomasBoundingBoxShape = thomasBoundingBox.getBounds();
    }
}


