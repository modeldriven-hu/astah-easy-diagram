package hu.modeldriven.astah.easydiagram.ui.usecase;

import com.change_vision.jude.api.inf.presentation.ILinkPresentation;
import hu.modeldriven.astah.core.AstahRepresentation;
import hu.modeldriven.astah.core.transaction.AstahTransaction;
import hu.modeldriven.astah.core.transaction.TransactionFailedException;
import hu.modeldriven.astah.easydiagram.ui.event.DiagramSelectionChangedEvent;
import hu.modeldriven.astah.easydiagram.ui.event.ExceptionOccurredEvent;
import hu.modeldriven.astah.easydiagram.ui.event.SnapToPixelRequestedEvent;
import hu.modeldriven.core.eventbus.Event;
import hu.modeldriven.core.eventbus.EventBus;
import hu.modeldriven.core.eventbus.EventHandler;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SnapToPixelUseCase implements EventHandler<SnapToPixelRequestedEvent> {

    private final EventBus eventBus;
    private final AstahRepresentation astah;

    public SnapToPixelUseCase(EventBus eventBus, AstahRepresentation astah) {
        this.eventBus = eventBus;
        this.astah = astah;
    }

    @Override
    public void handleEvent(SnapToPixelRequestedEvent event) {
        if (astah.currentDiagram() == null) {
            return;
        }

        AstahTransaction transaction = new AstahTransaction();

        try {

            transaction.execute(() -> {

                astah.selectedNodes().forEach(node -> {
                    Rectangle2D originalRectangle = node.getRectangle();
                    Rectangle2D newRectangle = pixelSnappedRect(originalRectangle);
                    astah.setBounds(node, newRectangle);
                });

                astah.selectedLinks().forEach(link -> {
                    astah.setPoints(link, pixelSnappedLink(link));
                });

            });

        } catch (TransactionFailedException e) {
            eventBus.publish(new ExceptionOccurredEvent(e));
        }

        eventBus.publish(new DiagramSelectionChangedEvent());
    }

    private Point2D[] pixelSnappedLink(ILinkPresentation link) {

        List<Point2D> points = new ArrayList<>();

        for (Point2D point : link.getAllPoints()) {
            points.add(new Point2D.Double(
                    Math.ceil(point.getX()),
                    Math.ceil(point.getY())
            ));
        }

        return points.toArray(new Point2D[points.size()]);
    }

    private Rectangle2D pixelSnappedRect(Rectangle2D rectangle) {
        return new Rectangle2D.Double(
                Math.ceil(rectangle.getX()),
                Math.ceil(rectangle.getY()),
                Math.ceil(rectangle.getWidth()),
                Math.ceil(rectangle.getHeight())
        );
    }

    @Override
    public List<Class<? extends Event>> subscribedEvents() {
        return Collections.singletonList(SnapToPixelRequestedEvent.class);
    }
}
