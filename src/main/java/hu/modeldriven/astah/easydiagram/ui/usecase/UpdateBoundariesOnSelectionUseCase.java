package hu.modeldriven.astah.easydiagram.ui.usecase;

import hu.modeldriven.astah.core.AstahRepresentation;
import hu.modeldriven.astah.easydiagram.ui.event.DiagramSelectionChangedEvent;
import hu.modeldriven.core.eventbus.Event;
import hu.modeldriven.core.eventbus.EventHandler;

import javax.swing.*;
import java.util.Collections;
import java.util.List;

public class UpdateBoundariesOnSelectionUseCase implements EventHandler<DiagramSelectionChangedEvent> {

    private final AstahRepresentation astah;
    private final JTabbedPane tabbedPanel;
    private final JTextField idInputField;
    private final JTextField leftInputField;
    private final JTextField topInputField;
    private final JTextField widthInputField;
    private final JTextField heightInputField;

    public UpdateBoundariesOnSelectionUseCase(AstahRepresentation astah,
                                              JTabbedPane tabbedPanel,
                                              JTextField idInputField,
                                              JTextField leftInputField,
                                              JTextField topInputField,
                                              JTextField widthInputField,
                                              JTextField heightInputField) {
        this.astah = astah;
        this.tabbedPanel = tabbedPanel;
        this.idInputField = idInputField;
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

        if (tabbedPanel.getSelectedIndex() != 0) {
            return;
        }

        SwingUtilities.invokeLater( () -> {

            if (astah.selectedNodes().isEmpty() && astah.selectedLinks().isEmpty()) {
                this.idInputField.setText("");
                this.leftInputField.setText("");
                this.topInputField.setText("");
                this.widthInputField.setText("");
                this.heightInputField.setText("");
                return;
            }

            if (!astah.selectedNodes().isEmpty()) {
                astah.selectedNodes().stream().findFirst().ifPresent(node -> {
                    var id = node.getModel().getId();
                    this.idInputField.setText(id.substring(id.indexOf("#") + 1));
                    this.leftInputField.setText(String.valueOf(node.getLocation().getX()));
                    this.topInputField.setText(String.valueOf(node.getLocation().getY()));
                    this.widthInputField.setText(String.valueOf(node.getWidth()));
                    this.heightInputField.setText(String.valueOf(node.getHeight()));
                });
            } else if (!astah.selectedLinks().isEmpty()) {
                astah.selectedLinks().stream().findFirst().ifPresent(link -> {
                    var id = link.getModel().getId();
                    this.idInputField.setText(id.substring(id.indexOf("#") + 1));
                });
            }

        });

    }

    @Override
    public List<Class<? extends Event>> subscribedEvents() {
        return Collections.singletonList(DiagramSelectionChangedEvent.class);
    }
}
