package hu.modeldriven.astah.easydiagram.ui;

import hu.modeldriven.astah.core.AstahRepresentation;
import hu.modeldriven.astah.easydiagram.ui.event.*;
import hu.modeldriven.astah.easydiagram.ui.usecase.*;
import hu.modeldriven.core.eventbus.EventBus;

import javax.swing.*;

public class EasyDiagramPanel extends AbstractEasyDiagramPanel {

    private final transient EventBus eventBus;
    private final transient AstahRepresentation astah;

    public EasyDiagramPanel(EventBus eventBus) {
        super();
        this.eventBus = eventBus;
        this.astah = new AstahRepresentation();
        initUIComponents();
        initUseCases();
    }

    private void initUIComponents() {

        leftInputField.addActionListener(e -> notifyBoundsChange());
        topInputField.addActionListener(e -> notifyBoundsChange());
        widthInputField.addActionListener(e -> notifyBoundsChange());
        heightInputField.addActionListener(e -> notifyBoundsChange());

        applyButton.addActionListener(e -> notifyBoundsChange());

        valueNameInputField.addActionListener(e -> notifyValueTypeChange());
        valueTypeInputField.addActionListener(e -> notifyValueTypeChange());
        valueConstraintInputField.addActionListener(e -> notifyValueTypeChange());

        createValueButton.addActionListener(e -> {
            notifyValueTypeChange();
            eventBus.publish(new CreateValueTypeRequestedEvent());
        });

        snapSelectedElementsToPixelButton.addActionListener(e -> eventBus.publish(new SnapSelectedElementsToPixelRequestedEvent()));
        snapDiagramToPixelButton.addActionListener(e -> eventBus.publish(new SnapDiagramToPixelRequestedEvent()));
        straightenLineButton.addActionListener(e -> eventBus.publish(new StraightenLineRequestedEvent()));
        resetItemFlowButton.addActionListener(e -> eventBus.publish(new ResetItemFlowRequestedEvent()));
        saveRestoreButton.addActionListener(e -> eventBus.publish(new SaveRestoreRequestedEvent()));
    }

    private void notifyValueTypeChange() {
        eventBus.publish(new ValueTypeChangedEvent(
                valueNameInputField.getText(),
                valueTypeInputField.getText(),
                valueConstraintInputField.getText()
        ));
    }

    private void notifyBoundsChange() {
        try {

            eventBus.publish(new ChangeBoundsRequestedEvent(
                    Double.parseDouble(leftInputField.getText()),
                    Double.parseDouble(topInputField.getText()),
                    Double.parseDouble(widthInputField.getText()),
                    Double.parseDouble(heightInputField.getText())
            ));

            eventBus.publish(new DiagramSelectionChangedEvent());

        } catch (NumberFormatException | NullPointerException e) {
            JOptionPane.showMessageDialog(null,
                    "All field must be a number",
                    "Wrong input",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void initUseCases() {
        this.eventBus.subscribe(new DisplayExceptionUseCase());
        this.eventBus.subscribe(new ChangeBoundsUseCase(eventBus, astah));
        this.eventBus.subscribe(new UpdateBoundariesOnSelectionUseCase(astah, leftInputField, topInputField, widthInputField, heightInputField));
        this.eventBus.subscribe(new SnapSelectedElementsToPixelUseCase(eventBus, astah));
        this.eventBus.subscribe(new SnapEveryDiagramElementToPixelUseCase(eventBus, astah));
        this.eventBus.subscribe(new StraightenLineUseCase(eventBus, astah));
        this.eventBus.subscribe(new ResetItemFlowUseCase(eventBus, astah));
        this.eventBus.subscribe(new SaveRestorePositionUseCase(eventBus, astah, saveRestoreButton));
        this.eventBus.subscribe(new CreateValueTypeUseCase(eventBus, astah));
    }

}
