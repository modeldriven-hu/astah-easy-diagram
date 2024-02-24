package hu.modeldriven.astah.easydiagram.ui.usecase;

import com.change_vision.jude.api.inf.presentation.ILinkPresentation;
import hu.modeldriven.astah.core.AstahRepresentation;
import hu.modeldriven.astah.core.transaction.AstahTransaction;
import hu.modeldriven.astah.core.transaction.TransactionFailedException;
import hu.modeldriven.astah.easydiagram.ui.event.ExceptionOccurredEvent;
import hu.modeldriven.astah.easydiagram.ui.event.StraightenLineRequestedEvent;
import hu.modeldriven.core.eventbus.Event;
import hu.modeldriven.core.eventbus.EventBus;
import hu.modeldriven.core.eventbus.EventHandler;

import java.awt.geom.Point2D;
import java.util.Collections;
import java.util.List;

public class StraightenLineUseCase implements EventHandler<StraightenLineRequestedEvent> {

    private final EventBus eventBus;
    private final AstahRepresentation astah;

    public StraightenLineUseCase(EventBus eventBus, AstahRepresentation astah) {
        this.eventBus = eventBus;
        this.astah = astah;
    }

    @Override
    public void handleEvent(StraightenLineRequestedEvent event) {
        if (astah.currentDiagram() == null) {
            return;
        }

        try {
            AstahTransaction transaction = new AstahTransaction();

            transaction.execute(() -> {
                astah.selectedLinks().forEach(link -> astah.setPoints(link, endPoints(link)));
            });

        } catch (TransactionFailedException e) {
            eventBus.publish(new ExceptionOccurredEvent(e));
        }
    }

    public Point2D[] endPoints(ILinkPresentation link) {
        Point2D[] points = link.getAllPoints();
        return new Point2D[]{points[0], points[points.length - 1]};
    }

    @Override
    public List<Class<? extends Event>> subscribedEvents() {
        return Collections.singletonList(StraightenLineRequestedEvent.class);
    }
}
