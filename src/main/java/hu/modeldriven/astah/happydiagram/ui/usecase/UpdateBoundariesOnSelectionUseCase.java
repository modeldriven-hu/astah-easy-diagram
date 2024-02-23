package hu.modeldriven.astah.happydiagram.ui.usecase;

import hu.modeldriven.astah.core.AstahRepresentation;
import hu.modeldriven.astah.happydiagram.ui.event.DiagramSelectionChangedEvent;
import hu.modeldriven.core.eventbus.Event;
import hu.modeldriven.core.eventbus.EventHandler;

import javax.swing.*;
import java.util.Collections;
import java.util.List;

public class UpdateBoundariesOnSelectionUseCase implements EventHandler<DiagramSelectionChangedEvent> {

    private final AstahRepresentation astah;
    private final JTextField leftInputField;
    private final JTextField topInputField;
    private final JTextField widthInputField;
    private final JTextField heightInputField;

    public UpdateBoundariesOnSelectionUseCase(AstahRepresentation astah,
                                              JTextField leftInputField,
                                              JTextField topInputField,
                                              JTextField widthInputField,
                                              JTextField heightInputField) {
        this.astah = astah;
        this.leftInputField = leftInputField;
        this.topInputField = topInputField;
        this.widthInputField = widthInputField;
        this.heightInputField = heightInputField;
    }

    @Override
    public void handleEvent(DiagramSelectionChangedEvent event) {
        if (astah.currentDiagram() == null) {
            return;
        }

        if (astah.selectedNodes().isEmpty()) {
            this.leftInputField.setText("");
            this.topInputField.setText("");
            this.widthInputField.setText("");
            this.heightInputField.setText("");
            return;
        }

        astah.selectedNodes().stream().findFirst().ifPresent(node -> {
            this.leftInputField.setText(String.valueOf(node.getLocation().getX()));
            this.topInputField.setText(String.valueOf(node.getLocation().getY()));
            this.widthInputField.setText(String.valueOf(node.getWidth()));
            this.heightInputField.setText(String.valueOf(node.getHeight()));
        });

    }

    @Override
    public List<Class<? extends Event>> subscribedEvents() {
        return Collections.singletonList(DiagramSelectionChangedEvent.class);
    }
}
