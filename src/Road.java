import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Road
{
    private Image roadImage;

    public Road()
    {
        try
        {
            roadImage = ImageIO.read(new File("/Users/VicMini/git/New-game/src/ground.png"));
        } catch (IOException e)
        {
            System.out.println("Can't find ground.png");
        }
    }

    public Image getRoadImage()
    {
        return roadImage;
    }
}
