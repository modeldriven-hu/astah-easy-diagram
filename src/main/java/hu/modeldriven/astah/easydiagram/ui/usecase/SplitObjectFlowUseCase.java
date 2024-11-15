package hu.modeldriven.astah.easydiagram.ui.usecase;

import com.change_vision.jude.api.inf.exception.InvalidEditingException;
import com.change_vision.jude.api.inf.exception.InvalidUsingException;
import com.change_vision.jude.api.inf.model.IActivityDiagram;
import com.change_vision.jude.api.inf.model.IObjectNode;
import com.change_vision.jude.api.inf.model.IPin;
import com.change_vision.jude.api.inf.presentation.ILinkPresentation;
import com.change_vision.jude.api.inf.presentation.INodePresentation;
import hu.modeldriven.astah.core.AstahRepresentation;
import hu.modeldriven.astah.core.transaction.AstahTransaction;
import hu.modeldriven.astah.core.transaction.TransactionFailedException;
import hu.modeldriven.astah.easydiagram.ui.event.ExceptionOccurredEvent;
import hu.modeldriven.astah.easydiagram.ui.event.SplitObjectFlowRequestedEvent;
import hu.modeldriven.core.eventbus.Event;
import hu.modeldriven.core.eventbus.EventBus;
import hu.modeldriven.core.eventbus.EventHandler;

import java.awt.geom.Point2D;
import java.util.HashSet;
import java.util.List;

public class SplitObjectFlowUseCase implements EventHandler<SplitObjectFlowRequestedEvent> {

    private static final int DISTANCE_X = 200;
    private static final int DISTANCE_Y = 14;
    private static final String FALSE = "false";

    private final EventBus eventBus;
    private final AstahRepresentation astah;

    public SplitObjectFlowUseCase(EventBus eventBus, AstahRepresentation astahRepresentation) {
        this.eventBus = eventBus;
        this.astah = astahRepresentation;
    }

    @Override
    public void handleEvent(SplitObjectFlowRequestedEvent event) {
        if (astah.currentDiagram() instanceof IActivityDiagram activityDiagram
                && astah.selectedLinks() != null
                && astah.selectedLinks().size() == 1
                && "ControlFlow/ObjectFlow".equals(astah.selectedLinks().getFirst().getType())) {

            var link = astah.selectedLinks().getFirst();

            if (link.getSource().getModel() instanceof IPin && link.getTarget().getModel() instanceof IPin) {

                try {
                    var transaction = new AstahTransaction();

                    transaction.execute(() -> {
                        try {
                            splitFlow(activityDiagram, link);
                        } catch (InvalidEditingException | InvalidUsingException e) {
                            eventBus.publish(new ExceptionOccurredEvent(e));
                        }
                    });

                } catch (TransactionFailedException e) {
                    eventBus.publish(new ExceptionOccurredEvent(e));
                }
            }
        }
    }

    private void splitFlow(IActivityDiagram activityDiagram, ILinkPresentation linkPresentation) throws InvalidEditingException, InvalidUsingException {

        var name = generateName(activityDiagram);

        var source = linkPresentation.getSource();
        var target = linkPresentation.getTarget();

        var firstObjectNode = astah.createObjectNode(activityDiagram,
                name,
                null,
                new Point2D.Double(
                        source.getLocation().getX() + DISTANCE_X,
                        source.getLocation().getY() - DISTANCE_Y)
        );

        var secondObjectNode = astah.createObjectNode(activityDiagram,
                name,
                null,
                new Point2D.Double(
                        target.getLocation().getX() - DISTANCE_X,
                        target.getLocation().getY() - DISTANCE_Y
                )
        );

        firstObjectNode.setProperty("stereotype_visibility", FALSE);
        firstObjectNode.setProperty("constraint_visibility", FALSE);

        secondObjectNode.setProperty("constraint_visibility", FALSE);
        secondObjectNode.setProperty("stereotype_visibility", FALSE);

        astah.createObjectFlow(activityDiagram, source, firstObjectNode);
        astah.createObjectFlow(activityDiagram, secondObjectNode, target);

        astah.deleteLink(activityDiagram, linkPresentation);
    }

    private String generateName(IActivityDiagram activityDiagram) throws InvalidUsingException {

        var usedAlphabets = new HashSet<>();

        for (var presentation : activityDiagram.getPresentations()){
            if (presentation instanceof INodePresentation nodePresentation
                    && nodePresentation.getModel() instanceof IObjectNode objectNode
                    && objectNode.getName().length() == 1) {
                        usedAlphabets.add(objectNode.getName().toLowerCase());
                }
        }

        for (char c = 'a'; c <= 'z'; c++) {
            if (!usedAlphabets.contains(String.valueOf(c))) {
                return String.valueOf(c);
            }
        }

        return "a1";
    }

    @Override
    public List<Class<? extends Event>> subscribedEvents() {
        return List.of(SplitObjectFlowRequestedEvent.class);
    }
}
