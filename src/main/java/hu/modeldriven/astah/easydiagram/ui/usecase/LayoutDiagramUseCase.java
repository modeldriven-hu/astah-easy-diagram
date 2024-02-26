package hu.modeldriven.astah.easydiagram.ui.usecase;

import hu.modeldriven.astah.core.AstahRepresentation;
import hu.modeldriven.astah.easydiagram.ui.event.LayoutRequestedEvent;
import hu.modeldriven.core.eventbus.Event;
import hu.modeldriven.core.eventbus.EventBus;
import hu.modeldriven.core.eventbus.EventHandler;

import java.util.Collections;
import java.util.List;

public class LayoutDiagramUseCase implements EventHandler<LayoutRequestedEvent> {

    private final EventBus eventBus;
    private final AstahRepresentation astah;

    public LayoutDiagramUseCase(EventBus eventBus, AstahRepresentation astah) {
        this.eventBus = eventBus;
        this.astah = astah;
    }

    @Override
    public void handleEvent(LayoutRequestedEvent event) {

    }

    @Override
    public List<Class<? extends Event>> subscribedEvents() {
        return Collections.singletonList(LayoutRequestedEvent.class);
    }
}
