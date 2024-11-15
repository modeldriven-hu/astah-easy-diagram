package hu.modeldriven.astah.easydiagram.ui.usecase;

import com.change_vision.jude.api.inf.exception.InvalidEditingException;
import com.change_vision.jude.api.inf.model.IActivityDiagram;
import com.change_vision.jude.api.inf.presentation.ILinkPresentation;
import hu.modeldriven.astah.core.AstahRepresentation;
import hu.modeldriven.astah.core.transaction.AstahTransaction;
import hu.modeldriven.astah.core.transaction.TransactionFailedException;
import hu.modeldriven.astah.easydiagram.ui.event.ExceptionOccurredEvent;
import hu.modeldriven.astah.easydiagram.ui.event.SplitObjectFlowRequestedEvent;
import hu.modeldriven.core.eventbus.Event;
import hu.modeldriven.core.eventbus.EventBus;
import hu.modeldriven.core.eventbus.EventHandler;

import java.awt.geom.Point2D;
import java.util.List;

public class SplitObjectFlowUseCase implements EventHandler<SplitObjectFlowRequestedEvent> {

    private final EventBus eventBus;
    private final AstahRepresentation astah;

    public SplitObjectFlowUseCase(EventBus eventBus, AstahRepresentation astahRepresentation){
        this.eventBus = eventBus;
        this.astah = astahRepresentation;
    }

    @Override
    public void handleEvent(SplitObjectFlowRequestedEvent event) {
        if (astah.currentDiagram() instanceof IActivityDiagram activityDiagram
        && astah.selectedLinks() != null
        && astah.selectedLinks().size() == 1
        && "ControlFlow/ObjectFlow".equals(astah.selectedLinks().getFirst().getType())) {

            try {
                var transaction = new AstahTransaction();
     
                transaction.execute(() -> {
                    try {
                        splitFlow(activityDiagram, astah.selectedLinks().getFirst());
                    } catch (InvalidEditingException e) {
                        eventBus.publish(new ExceptionOccurredEvent(e));
                    }
                });
     
            } catch (TransactionFailedException e){
                eventBus.publish(new ExceptionOccurredEvent(e));
            }
        }
    }

    private void splitFlow(IActivityDiagram activityDiagram, ILinkPresentation linkPresentation) throws InvalidEditingException {
        var source = linkPresentation.getSource();
        var target = linkPresentation.getTarget();

        var firstObjectNode = astah.createObjectNode(activityDiagram, "a", null, new Point2D.Double(0,0));
        var secondObjectNode = astah.createObjectNode(activityDiagram, "a", null, new Point2D.Double(50,0));

        firstObjectNode.setProperty("constraint_visibility", "false");
        secondObjectNode.setProperty("constraint_visibility", "false");

        astah.createObjectFlow(activityDiagram, source, firstObjectNode);
        astah.createObjectFlow(activityDiagram, secondObjectNode, target);
    }

    @Override
    public List<Class<? extends Event>> subscribedEvents() {
        return List.of(SplitObjectFlowRequestedEvent.class);
    }
}
