package hu.modeldriven.astah.easydiagram.ui.usecase;

import java.util.Arrays;
import java.util.List;

import javax.swing.JOptionPane;

import com.change_vision.jude.api.inf.exception.InvalidEditingException;
import com.change_vision.jude.api.inf.model.IAttribute;
import com.change_vision.jude.api.inf.model.IBlock;

import hu.modeldriven.astah.core.AstahRepresentation;
import hu.modeldriven.astah.core.exception.AstahRuntimeException;
import hu.modeldriven.astah.core.transaction.AstahTransaction;
import hu.modeldriven.astah.easydiagram.ui.event.CreateValueTypeRequestedEvent;
import hu.modeldriven.astah.easydiagram.ui.event.ExceptionOccurredEvent;
import hu.modeldriven.astah.easydiagram.ui.event.ValueTypeChangedEvent;
import hu.modeldriven.core.eventbus.Event;
import hu.modeldriven.core.eventbus.EventBus;
import hu.modeldriven.core.eventbus.EventHandler;

public class CreateValueTypeUseCase implements EventHandler<Event> {

    private final AstahRepresentation astah;
    private final EventBus eventBus;
    String name;
    String type;
    String constraint;

    public CreateValueTypeUseCase(EventBus eventBus, AstahRepresentation astah) {
        this.eventBus = eventBus;
        this.astah = astah;
    }

    @Override
    public void handleEvent(Event event) {

        if (event instanceof ValueTypeChangedEvent e) {
            this.name = e.name();
            this.type = e.type();
            this.constraint = e.constraint();
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
                JOptionPane.showMessageDialog(null, "Could not find a valueType with name " + type, "Could not find type", JOptionPane.ERROR_MESSAGE);
                return;
            }

            var editor = astah.modelEditor();
            var transaction = new AstahTransaction();

            try {
                transaction.execute(() -> {
                    for (var node : astah.selectedNodes()) {
                        if (node.getModel() instanceof IBlock block) {

                            try {
                                IAttribute attribute = editor.createValueAttribute(block, this.name, valueType);

                                if (constraint != null && !constraint.trim().isEmpty()) {
                                    //editor.createConstraint(attribute, constraint);
                                    // FIXME use constraint property?
                                }

                            } catch (InvalidEditingException e) {
                                e.printStackTrace();
                                throw new AstahRuntimeException(e);
                            }
                        }
                    }

                });

            } catch (Exception e) {
                e.printStackTrace();
                this.eventBus.publish(new ExceptionOccurredEvent(e));
            }

        } else {
            JOptionPane.showMessageDialog(null, "Both name and type has to be entered", "Missing name or type", JOptionPane.ERROR_MESSAGE);
        }
    }


    @Override
    public List<Class<? extends Event>> subscribedEvents() {
        return Arrays.asList(
                CreateValueTypeRequestedEvent.class,
                ValueTypeChangedEvent.class);
    }
}
