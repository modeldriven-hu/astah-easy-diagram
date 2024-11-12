package hu.modeldriven.astah.easydiagram.ui.event;

import hu.modeldriven.core.eventbus.Event;

public record UnmarshallPinsRequestedEvent(PinDirection direction) implements Event {

    public enum PinDirection{
        IN,
        OUT
    }

}
