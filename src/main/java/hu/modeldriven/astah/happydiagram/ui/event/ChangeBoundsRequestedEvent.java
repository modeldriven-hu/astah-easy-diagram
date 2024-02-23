package hu.modeldriven.astah.happydiagram.ui.event;

import hu.modeldriven.core.eventbus.Event;

import java.awt.Dimension;
import java.awt.Point;

public class ChangeBoundsRequestedEvent implements Event {

    private final Point point;
    private final Dimension size;

    public ChangeBoundsRequestedEvent(int left, int  top, int width, int height) {
        this.point = new Point(left, top);
        this.size = new Dimension(width, height);
    }

    public Point point() {
        return point;
    }

    public Dimension size() {
        return size;
    }
}
