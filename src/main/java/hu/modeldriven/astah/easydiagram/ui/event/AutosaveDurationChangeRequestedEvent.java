package hu.modeldriven.astah.easydiagram.ui.event;

import hu.modeldriven.core.eventbus.Event;

public record AutosaveDurationChangeRequestedEvent(int durationInSeconds) implements Event {
}
