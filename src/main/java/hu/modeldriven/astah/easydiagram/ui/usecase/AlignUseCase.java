package hu.modeldriven.astah.easydiagram.ui.usecase;

import com.change_vision.jude.api.inf.exception.InvalidEditingException;
import com.change_vision.jude.api.inf.presentation.INodePresentation;
import hu.modeldriven.astah.core.AstahRepresentation;
import hu.modeldriven.astah.core.transaction.AstahTransaction;
import hu.modeldriven.astah.core.transaction.TransactionFailedException;
import hu.modeldriven.astah.easydiagram.ui.event.AlignmentRequestedEvent;
import hu.modeldriven.astah.easydiagram.ui.event.ExceptionOccurredEvent;
import hu.modeldriven.core.eventbus.Event;
import hu.modeldriven.core.eventbus.EventBus;
import hu.modeldriven.core.eventbus.EventHandler;

import java.awt.geom.Point2D;
import java.util.List;

public class AlignUseCase implements EventHandler<AlignmentRequestedEvent> {

    private final EventBus eventBus;
    private final AstahRepresentation astah;

    public AlignUseCase(EventBus eventBus, AstahRepresentation astah) {
        this.eventBus = eventBus;
        this.astah = astah;
    }

    @Override
    public void handleEvent(AlignmentRequestedEvent event) {
        switch (event.direction()){
            case HORIZONTAL_CENTER -> alignHorizontalCenter();
            case VERTICAL_CENTER -> alignVerticalCenter();
        }
    }

    private void alignHorizontalCenter() {

        var transaction = new AstahTransaction();

        try {
            transaction.execute(() -> {

                try {

                    var selectedNodes = astah.selectedNodes();

                    if (selectedNodes.isEmpty()) {
                        return;
                    }

                    var minY = selectedNodes.stream()
                            .map(node -> node.getLocation().getY())
                            .min(Double::compareTo)
                            .orElseThrow(() -> new IllegalStateException("No nodes selected"));

                    var maxY = selectedNodes.stream()
                            .map(node -> node.getLocation().getY())
                            .max(Double::compareTo)
                            .orElseThrow(() -> new IllegalStateException("No nodes selected"));

                    var centerY = minY + (maxY - minY) / 2;

                    for (INodePresentation node : selectedNodes) {
                        var location = node.getLocation();
                        node.setLocation(new Point2D.Double(location.getX(), centerY));
                    }

                } catch (InvalidEditingException | IllegalStateException e) {
                    eventBus.publish(new ExceptionOccurredEvent(e));
                }

            });
        } catch (TransactionFailedException e) {
            eventBus.publish(new ExceptionOccurredEvent(e));
        }
    }

    private void alignVerticalCenter() {
        var transaction = new AstahTransaction();

        try {
            transaction.execute(() -> {

                try {

                    var selectedNodes = astah.selectedNodes();

                    if (selectedNodes.isEmpty()) {
                        return;
                    }

                    var minX = selectedNodes.stream()
                            .map(node -> node.getLocation().getX())
                            .min(Double::compareTo)
                            .orElseThrow(() -> new IllegalStateException("No nodes selected"));

                    var maxX = selectedNodes.stream()
                            .map(node -> node.getLocation().getX())
                            .max(Double::compareTo)
                            .orElseThrow(() -> new IllegalStateException("No nodes selected"));

                    var centerX = minX + (maxX - minX) / 2;

                    for (INodePresentation node : selectedNodes) {
                        var location = node.getLocation();
                        node.setLocation(new Point2D.Double(centerX, location.getY()));
                    }

                } catch (InvalidEditingException | IllegalStateException e) {
                    eventBus.publish(new ExceptionOccurredEvent(e));
                }

            });
        } catch (TransactionFailedException e) {
            eventBus.publish(new ExceptionOccurredEvent(e));
        }
    }

    @Override
    public List<Class<? extends Event>> subscribedEvents() {
        return List.of(AlignmentRequestedEvent.class);
    }
}
