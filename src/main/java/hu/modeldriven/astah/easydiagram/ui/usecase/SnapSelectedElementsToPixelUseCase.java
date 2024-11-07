package hu.modeldriven.astah.easydiagram.ui.usecase;

import hu.modeldriven.astah.core.AstahRepresentation;
import hu.modeldriven.astah.core.transaction.AstahTransaction;
import hu.modeldriven.astah.core.transaction.TransactionFailedException;
import hu.modeldriven.astah.easydiagram.ui.event.DiagramSelectionChangedEvent;
import hu.modeldriven.astah.easydiagram.ui.event.ExceptionOccurredEvent;
import hu.modeldriven.astah.easydiagram.ui.event.SnapSelectedElementsToPixelRequestedEvent;
import hu.modeldriven.astah.easydiagram.ui.usecase.pixelsnap.PixelSnappedLink;
import hu.modeldriven.astah.easydiagram.ui.usecase.pixelsnap.PixelSnappedRectangle;
import hu.modeldriven.core.eventbus.Event;
import hu.modeldriven.core.eventbus.EventBus;
import hu.modeldriven.core.eventbus.EventHandler;

import java.awt.geom.Rectangle2D;
import java.util.Collections;
import java.util.List;

public class SnapSelectedElementsToPixelUseCase implements EventHandler<SnapSelectedElementsToPixelRequestedEvent> {

    private final EventBus eventBus;
    private final AstahRepresentation astah;

    public SnapSelectedElementsToPixelUseCase(EventBus eventBus, AstahRepresentation astah) {
        this.eventBus = eventBus;
        this.astah = astah;
    }

    @Override
    public void handleEvent(SnapSelectedElementsToPixelRequestedEvent event) {
        if (astah.currentDiagram() == null) {
            return;
        }

        var transaction = new AstahTransaction();

        try {

            transaction.execute(() -> {

                astah.selectedNodes().forEach(node -> {
                    Rectangle2D originalRectangle = node.getRectangle();
                    Rectangle2D newRectangle = new PixelSnappedRectangle(originalRectangle).coordinates();
                    astah.setBounds(node, newRectangle);
                });

                astah.selectedLinks().forEach(link -> {
                    astah.setPoints(link, new PixelSnappedLink(link).coordinates());
                });

            });

        } catch (TransactionFailedException e) {
            eventBus.publish(new ExceptionOccurredEvent(e));
        }

        eventBus.publish(new DiagramSelectionChangedEvent());
    }

    @Override
    public List<Class<? extends Event>> subscribedEvents() {
        return Collections.singletonList(SnapSelectedElementsToPixelRequestedEvent.class);
    }
}
