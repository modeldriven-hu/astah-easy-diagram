package hu.modeldriven.astah.happydiagram.ui.usecase;

import com.change_vision.jude.api.inf.model.IDiagram;
import hu.modeldriven.astah.core.AstahRepresentation;
import hu.modeldriven.astah.core.transaction.AstahTransaction;
import hu.modeldriven.astah.core.transaction.TransactionFailedException;
import hu.modeldriven.astah.happydiagram.ui.event.ChangeBoundsRequestedEvent;
import hu.modeldriven.core.eventbus.Event;
import hu.modeldriven.core.eventbus.EventBus;
import hu.modeldriven.core.eventbus.EventHandler;

import javax.swing.*;
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
                JOptionPane.showMessageDialog(null,
                        "The width or height you set is smaller than the minimum size, use a larger number!",
                        "Wrong width or height",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

    }

    @Override
    public List<Class<? extends Event>> subscribedEvents() {
        return Collections.singletonList(ChangeBoundsRequestedEvent.class);
    }
}
