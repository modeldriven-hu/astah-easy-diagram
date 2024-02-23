package hu.modeldriven.astah.happydiagram.ui.usecase;

import com.change_vision.jude.api.inf.model.IDiagram;
import hu.modeldriven.astah.core.AstahRepresentation;
import hu.modeldriven.astah.core.transaction.AstahTransaction;
import hu.modeldriven.astah.core.transaction.TransactionFailedException;
import hu.modeldriven.astah.happydiagram.ui.event.ChangeBoundsRequestedEvent;
import hu.modeldriven.astah.happydiagram.ui.event.ExceptionOccurredEvent;
import hu.modeldriven.core.eventbus.Event;
import hu.modeldriven.core.eventbus.EventBus;
import hu.modeldriven.core.eventbus.EventHandler;

import java.util.Collections;
import java.util.List;

public class ChangeBoundsUseCase implements EventHandler<ChangeBoundsRequestedEvent> {
    private final EventBus eventBus;
    private final AstahRepresentation astah;

    public ChangeBoundsUseCase(EventBus eventBus, AstahRepresentation astah) {
        this.eventBus = eventBus;
        this.astah = astah;
    }

    @Override
    public void handleEvent(ChangeBoundsRequestedEvent event) {

        IDiagram diagram = astah.currentDiagram();

        if (diagram == null) {
            return;
        }

        astah.selectedNodes().stream().findFirst().ifPresent(node -> {
            AstahTransaction transaction = new AstahTransaction();

            try {
                transaction.execute(() -> {
                    astah.setBounds(node, event.bounds());
                });
            } catch (TransactionFailedException e) {
                eventBus.publish(new ExceptionOccurredEvent(new IllegalArgumentException("Minimum size reached, use bigger value!")));
            }
        });

    }

    @Override
    public List<Class<? extends Event>> subscribedEvents() {
        return Collections.singletonList(ChangeBoundsRequestedEvent.class);
    }
}
