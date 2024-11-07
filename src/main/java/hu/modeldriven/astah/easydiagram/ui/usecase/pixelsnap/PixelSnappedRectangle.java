package hu.modeldriven.astah.easydiagram.ui.usecase.pixelsnap;

import java.awt.geom.Rectangle2D;

public class PixelSnappedRectangle {

    private final Rectangle2D rectangle;

    public PixelSnappedRectangle(Rectangle2D rectangle) {
        this.rectangle = rectangle;
    }

    public Rectangle2D coordinates() {
        return new Rectangle2D.Double(
                Math.ceil(rectangle.getX()),
                Math.ceil(rectangle.getY()),
                Math.ceil(rectangle.getWidth()),
                Math.ceil(rectangle.getHeight())
        );
    }

}
