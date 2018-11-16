import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;

public class Track
{
    private Image trackSectionImage;
    private Point trackPosition;
    private Rectangle2D.Double trackBoundingBox;
    private int numberOfTrackSections;

    public Track(Point trackPosition, int numberOfTrackSections)
    {
        try
        {
            trackSectionImage = ImageIO.read(new File("/Users/VicMini/git/New-game/src/Tracks.png"));

        } catch (IOException e)
        {
            System.out.println("Can't find Tracks.png");
        }
        int trackXposition = trackPosition.x;
        int trackYposition = trackPosition.y;
        int trackSectionWidth = trackSectionImage.getWidth(null);
        int trackSectionHeight = trackSectionImage.getHeight(null);
        trackBoundingBox = new Rectangle2D.Double(trackXposition, trackYposition, trackSectionWidth * numberOfTrackSections, trackSectionHeight);
        this.trackPosition = trackPosition;
        this.numberOfTrackSections = numberOfTrackSections;
    }

    public Image getTrackSectionImage()
    {
        return trackSectionImage;
    }

    public Point getTrackPosition()
    {
        return trackPosition;
    }

    public int getNumberOfTrackSections()
    {
        return numberOfTrackSections;
    }

    public Rectangle2D.Double getTrackBoundingBox()
    {
        return trackBoundingBox;
    }
}


