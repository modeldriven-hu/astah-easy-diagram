package hu.modeldriven.astah.easydiagram.ui.event;

import hu.modeldriven.core.eventbus.Event;

public record ExceptionOccurredEvent(Exception exception) implements Event {
}
