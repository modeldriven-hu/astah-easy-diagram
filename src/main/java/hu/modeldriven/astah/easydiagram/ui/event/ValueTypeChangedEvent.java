package hu.modeldriven.astah.easydiagram.ui.event;

import hu.modeldriven.core.eventbus.Event;

public record ValueTypeChangedEvent(String name, String type) implements Event {
}
