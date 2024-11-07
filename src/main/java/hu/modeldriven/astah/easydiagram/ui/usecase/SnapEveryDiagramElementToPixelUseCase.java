package hu.modeldriven.astah.easydiagram.ui.usecase;

import hu.modeldriven.astah.core.AstahRepresentation;
import hu.modeldriven.astah.easydiagram.ui.event.SnapDiagramToPixelRequestedEvent;
import hu.modeldriven.astah.easydiagram.ui.event.SnapSelectedElementsToPixelRequestedEvent;
import hu.modeldriven.core.eventbus.Event;
import hu.modeldriven.core.eventbus.EventBus;
import hu.modeldriven.core.eventbus.EventHandler;

import java.util.List;

public class SnapEveryDiagramElementToPixelUseCase implements EventHandler<SnapDiagramToPixelRequestedEvent> {

    private final EventBus eventBus;
    private final AstahRepresentation astah;

    public SnapEveryDiagramElementToPixelUseCase(EventBus eventBus, AstahRepresentation astah) {
        this.eventBus = eventBus;
        this.astah = astah;
    }

    @Override
    public void handleEvent(SnapDiagramToPixelRequestedEvent event) {

        if (astah.currentDiagram() == null) {
            return;
        }

        astah.selectCurrentDiagramElements();
        eventBus.publish(new SnapSelectedElementsToPixelRequestedEvent());
    }

    @Override
    public List<Class<? extends Event>> subscribedEvents() {
        return List.of(SnapDiagramToPixelRequestedEvent.class);
    }
}
