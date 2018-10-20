import java.awt.*;

public class Road
{
    private Image roadImage;

    public Road()
    {
        roadImage = Toolkit.getDefaultToolkit().createImage(getClass().getResource("ground.png"));
    }

    public Image getRoadImage()
    {
        return roadImage;
    }
}
