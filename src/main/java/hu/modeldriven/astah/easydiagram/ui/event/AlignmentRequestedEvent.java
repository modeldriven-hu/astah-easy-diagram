package hu.modeldriven.astah.easydiagram.ui.event;

import hu.modeldriven.core.eventbus.Event;

public record AlignmentRequestedEvent(Direction direction) implements Event {

    public enum Direction {
        HORIZONTAL_CENTER, VERTICAL_CENTER
    }
}
