package hu.modeldriven.astah.easydiagram.ui.usecase;

import com.change_vision.jude.api.inf.exception.InvalidEditingException;
import com.change_vision.jude.api.inf.model.IBlock;
import hu.modeldriven.astah.core.AstahRepresentation;
import hu.modeldriven.astah.core.transaction.AstahTransaction;
import hu.modeldriven.astah.easydiagram.ui.event.CreateValueTypeRequestedEvent;
import hu.modeldriven.astah.easydiagram.ui.event.ExceptionOccurredEvent;
import hu.modeldriven.astah.easydiagram.ui.event.ValueTypeChangedEvent;
import hu.modeldriven.core.eventbus.Event;
import hu.modeldriven.core.eventbus.EventBus;
import hu.modeldriven.core.eventbus.EventHandler;

import javax.swing.*;
import java.util.Arrays;
import java.util.List;

public class CreateValueTypeUseCase implements EventHandler<Event> {

    private final AstahRepresentation astah;
    private final EventBus eventBus;
    String name;
    String type;

    public CreateValueTypeUseCase(EventBus eventBus, AstahRepresentation astah) {
        this.eventBus = eventBus;
        this.astah = astah;
    }

    @Override
    public void handleEvent(Event event) {

        if (event instanceof ValueTypeChangedEvent e) {
            this.name = e.name();
            this.type = e.type();
            return;
        }

        if (event instanceof CreateValueTypeRequestedEvent) {
            createValueType();
        }
    }

    private void createValueType() {
        if (this.name != null && this.type != null) {

            var valueType = astah.findValueTypeByName(this.type);

            if (valueType == null) {
                SwingUtilities.invokeLater( () -> {
                    JOptionPane.showMessageDialog(null,
                            "Could not find a valueType with name " + type,
                            "Could not find type",
                            JOptionPane.ERROR_MESSAGE);
                });
                return;
            }

            var editor = astah.modelEditor();
            var transaction = new AstahTransaction();

            try {
                transaction.execute(() -> {
                    for (var node : astah.selectedNodes()) {
                        if (node.getModel() instanceof IBlock block) {
                            try {
                                 editor.createValueAttribute(block, this.name, valueType);
                            } catch (InvalidEditingException e) {
                                SwingUtilities.invokeLater(()->
                                JOptionPane.showMessageDialog(
                                        null,
                                        e.getMessage(),
                                        "Failed to create value type for block " + block.getName(),
                                        JOptionPane.ERROR_MESSAGE
                                ));
                            }
                        }
                    }

                });

            } catch (Exception e) {
                this.eventBus.publish(new ExceptionOccurredEvent(e));
            }

        } else {
            SwingUtilities.invokeLater( () -> {
                        JOptionPane.showMessageDialog(null,
                                "Both name and type has to be entered",
                                "Missing name or type",
                                JOptionPane.ERROR_MESSAGE);
                    }
            );
        }
    }


    @Override
    public List<Class<? extends Event>> subscribedEvents() {
        return Arrays.asList(
                CreateValueTypeRequestedEvent.class,
                ValueTypeChangedEvent.class);
    }
}
