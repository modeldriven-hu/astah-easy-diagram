package hu.modeldriven.astah.easydiagram.ui.event;

import hu.modeldriven.core.eventbus.Event;

public class ValueTypeChangedEvent implements Event {

    private final String name;
    private final String type;
    private final String constraint;

    public ValueTypeChangedEvent(String name, String type, String constraint) {
        this.name = name;
        this.type = type;
        this.constraint = constraint;
    }

    public String name() {
        return name;
    }

    public String type() {
        return type;
    }

    public String constraint() {
        return constraint;
    }
}
