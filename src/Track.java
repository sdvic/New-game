import java.awt.*;

public class Track
{
    public Image trackImage;

    public Track()
    {
        trackImage = Toolkit.getDefaultToolkit().createImage(getClass().getResource("Tracks.png"));
    }
    public Image getTrackImage()
    {
        return trackImage;
    }
}
