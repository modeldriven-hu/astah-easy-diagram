package hu.modeldriven.astah.easydiagram.ui.usecase;

import com.change_vision.jude.api.inf.exception.InvalidEditingException;
import com.change_vision.jude.api.inf.model.IActivityDiagram;
import com.change_vision.jude.api.inf.model.IAttribute;
import com.change_vision.jude.api.inf.model.IBlock;
import com.change_vision.jude.api.inf.model.IValueType;
import com.change_vision.jude.api.inf.presentation.INodePresentation;
import hu.modeldriven.astah.core.AstahRepresentation;
import hu.modeldriven.astah.core.transaction.AstahTransaction;
import hu.modeldriven.astah.core.transaction.TransactionFailedException;
import hu.modeldriven.astah.easydiagram.ui.event.ExceptionOccurredEvent;
import hu.modeldriven.astah.easydiagram.ui.event.UnmarshallPinsRequestedEvent;
import hu.modeldriven.core.eventbus.Event;
import hu.modeldriven.core.eventbus.EventBus;
import hu.modeldriven.core.eventbus.EventHandler;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UnmarshallPinsUseCase implements EventHandler<UnmarshallPinsRequestedEvent> {

    private final int ACTION_WIDTH = 120;
    private final int PIN_WIDTH = 14;
    private final int PIN_HEIGHT = 14;
    private final int PIN_GAP = 20;

    private final EventBus eventBus;
    private final AstahRepresentation astah;

    public UnmarshallPinsUseCase(EventBus eventBus, AstahRepresentation astah) {
        this.eventBus = eventBus;
        this.astah = astah;
    }

    @Override
    public void handleEvent(UnmarshallPinsRequestedEvent event) {
        var elements = astah.selectedStructureElements();

        if (elements.isEmpty()) {
            return;
        }

        if (!(astah.currentDiagram() instanceof IActivityDiagram activityDiagram)) {
            return;
        }

        SupportedElement supportedElement;

        if (elements.getFirst() instanceof IBlock block) {
            supportedElement = new SupportedElement(block);
        } else if (elements.getFirst() instanceof IValueType valueType) {
            supportedElement = new SupportedElement(valueType);
        } else {
            return;
        }

        var transaction = new AstahTransaction();

        try {
            transaction.execute(() -> {

                try {
                    var action = astah.createAction(activityDiagram,
                            "Unmarshall data",
                            new Point2D.Double(0, 0));

                    resizeAction(supportedElement.attributes().size(), action);

                    var location = action.getLocation();

                    var x = event.direction() == UnmarshallPinsRequestedEvent.PinDirection.IN ?
                            location.getX() + (double) PIN_WIDTH / 2 :
                            location.getX() + action.getWidth() - (double) PIN_WIDTH / 2;

                    var y = location.getY() + PIN_GAP;

                    for (var attribute : supportedElement.attributes()) {

                        if (attribute.getName() == null || attribute.getName().isBlank()) {
                            continue;
                        }

                        var pin = astah.createPin(
                                activityDiagram,
                                action,
                                attribute.getName(),
                                attribute.getType(),
                                event.direction() == UnmarshallPinsRequestedEvent.PinDirection.IN,
                                new Point2D.Double(x, y)
                        );

                        pin.setProperty("constraint_visibility", "false");

                        y += PIN_HEIGHT + PIN_GAP;
                    }

                } catch (InvalidEditingException e) {
                    eventBus.publish(new ExceptionOccurredEvent(e));
                }
            });
        } catch (TransactionFailedException e) {
            eventBus.publish(new ExceptionOccurredEvent(e));
        }

    }

    private void resizeAction(int count, INodePresentation action) throws InvalidEditingException {
        action.setWidth(ACTION_WIDTH);
        action.setHeight(count * (PIN_HEIGHT + PIN_GAP) + PIN_GAP);
    }

    @Override
    public List<Class<? extends Event>> subscribedEvents() {
        return List.of(UnmarshallPinsRequestedEvent.class);
    }

    static class SupportedElement {

        private final List<IAttribute> attributes = new ArrayList<>();

        public SupportedElement(IBlock block) {
            Collections.addAll(attributes, block.getValueAttributes());
        }

        public SupportedElement(IValueType valueType) {
            Collections.addAll(attributes, valueType.getAttributes());
        }

        public List<IAttribute> attributes() {
            return attributes;
        }

    }

}
