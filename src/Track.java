import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;

public class Track
{
    private Image trackSectionImage;
    private Rectangle2D.Double trackBoundingBox;
    private int numberOfTrackSections;
    private Point trackPosition;
    private int trackSectionWidth;
    private int trackSectionHeight;
    private Shape trackShape;

    public Track(Point trackPosition, int numberOfTrackSections)
    {
        try
        {
            trackSectionImage = ImageIO.read(new File("/Users/VicMini/git/New-game/src/Tracks.png"));
        } catch (IOException e)
        {
            System.out.println("Can't find Tracks.png");
        }
        trackBoundingBox = new Rectangle2D.Double(trackPosition.x, trackPosition.y, trackSectionImage.getWidth(null) * numberOfTrackSections, trackSectionImage.getHeight(null));
        trackShape = new Rectangle2D.Double(trackPosition.x, trackPosition.y, trackSectionImage.getWidth(null) * numberOfTrackSections, trackSectionImage.getHeight(null));
        this.numberOfTrackSections = numberOfTrackSections;
        this.trackPosition = trackPosition;
        trackSectionWidth = trackSectionImage.getWidth(null);
        trackSectionHeight = trackSectionImage.getHeight(null);
    }

    public Image getTrackSectionImage()
    {
        return trackSectionImage;
    }

    public Rectangle2D.Double getTrackBoundingBox()
    {
        return trackBoundingBox;
    }

    public int getNumberOfTracKSections()
    {
        return numberOfTrackSections;
    }

    public int getTrackSectionHeight()
    {
        return trackSectionHeight;
    }

    public int getTrackSectionWidth()
    {
        return trackSectionWidth;
    }

    public Point getTrackPosition()
    {
        return trackPosition;
    }

    public Shape getTrackShape()
    {
        return trackShape;
    }
}