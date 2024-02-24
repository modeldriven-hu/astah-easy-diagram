package hu.modeldriven.astah.happydiagram.ui.usecase;

import com.change_vision.jude.api.inf.presentation.ILinkPresentation;
import hu.modeldriven.astah.core.AstahRepresentation;
import hu.modeldriven.astah.core.transaction.AstahTransaction;
import hu.modeldriven.astah.core.transaction.TransactionFailedException;
import hu.modeldriven.astah.happydiagram.ui.event.ExceptionOccurredEvent;
import hu.modeldriven.astah.happydiagram.ui.event.ResetItemFlowRequestedEvent;
import hu.modeldriven.core.eventbus.Event;
import hu.modeldriven.core.eventbus.EventBus;
import hu.modeldriven.core.eventbus.EventHandler;

import javax.swing.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ResetItemFlowUseCase implements EventHandler<ResetItemFlowRequestedEvent> {

    private final EventBus eventBus;
    private final AstahRepresentation astah;

    public ResetItemFlowUseCase(EventBus eventBus, AstahRepresentation astah) {
        this.eventBus = eventBus;
        this.astah = astah;
    }

    @Override
    public void handleEvent(ResetItemFlowRequestedEvent event) {
        ILinkPresentation connector = null;
        ILinkPresentation itemFlow = null;

        for (ILinkPresentation selectedLinkPresentation : astah.selectedLinks()) {
            if ("ItemFlow".equals(selectedLinkPresentation.getType())) {

                itemFlow = selectedLinkPresentation;
                connector = findConnector(itemFlow);

                break;
            }
        }

        if (connector == null || itemFlow == null) {
            JOptionPane.showMessageDialog(null, "No item flow or connection found!", "Alert", JOptionPane.ERROR_MESSAGE);
            return;
        }

        AstahTransaction transaction = new AstahTransaction();

        try {

            ILinkPresentation finalItemFlow = itemFlow;
            ILinkPresentation finalConnector = connector;

            transaction.execute(() -> {
                astah.setPoints(finalItemFlow, finalConnector.getAllPoints());
            });

        } catch (TransactionFailedException e) {
            eventBus.publish(new ExceptionOccurredEvent(e));
        }
    }

    private ILinkPresentation findConnector(ILinkPresentation itemFlow) {
        return Arrays.stream(itemFlow.getSource().getLinks())
                .filter(link -> "Connector".equals(link.getType()))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Class<? extends Event>> subscribedEvents() {
        return Collections.singletonList(ResetItemFlowRequestedEvent.class);
    }
}
