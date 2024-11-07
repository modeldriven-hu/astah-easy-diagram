package hu.modeldriven.astah.easydiagram.ui.usecase.pixelsnap;

import com.change_vision.jude.api.inf.presentation.ILinkPresentation;

import java.awt.geom.Point2D;
import java.util.ArrayList;

public class PixelSnappedLink {

    private final ILinkPresentation link;

    public PixelSnappedLink(ILinkPresentation link){
        this.link = link;
    }

    public Point2D[] coordinates() {

        var points = new ArrayList<Point2D>();

        for (Point2D point : link.getAllPoints()) {
            points.add(new Point2D.Double(
                    Math.ceil(point.getX()),
                    Math.ceil(point.getY())
            ));
        }

        return points.toArray(new Point2D[0]);
    }

}
