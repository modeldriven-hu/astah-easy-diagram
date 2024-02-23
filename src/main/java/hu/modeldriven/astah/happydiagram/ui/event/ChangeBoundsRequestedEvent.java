package hu.modeldriven.astah.happydiagram.ui.event;

import hu.modeldriven.core.eventbus.Event;

import java.awt.geom.Rectangle2D;

public class ChangeBoundsRequestedEvent implements Event {

    private final Rectangle2D bounds;

    public ChangeBoundsRequestedEvent(double left, double top, double width, double height) {
        this.bounds = new Rectangle2D.Double(left, top, width, height);
    }

    public Rectangle2D bounds() {
        return bounds;
    }
}
