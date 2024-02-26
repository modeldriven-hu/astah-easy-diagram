package hu.modeldriven.astah.easydiagram.ui;

import hu.modeldriven.astah.core.AstahRepresentation;
import hu.modeldriven.astah.easydiagram.ui.event.*;
import hu.modeldriven.astah.easydiagram.ui.usecase.*;
import hu.modeldriven.core.eventbus.EventBus;

import javax.swing.*;
import java.io.IOException;

public class EasyDiagramPanel extends AbstractEasyDiagramPanel {

    private final EventBus eventBus;
    private final AstahRepresentation astah;

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

        snapToPixelButton.addActionListener(e -> eventBus.publish(new SnapToPixelRequestedEvent()));
        straightenLineButton.addActionListener(e -> eventBus.publish(new StraightenLineRequestedEvent()));
        resetItemFlowButton.addActionListener(e -> eventBus.publish(new ResetItemFlowRequestedEvent()));
    }

    private void notifyBoundsChange(){
        try {
            eventBus.publish(new ChangeBoundsRequestedEvent(
                    Double.parseDouble(leftInputField.getText()),
                    Double.parseDouble(topInputField.getText()),
                    Double.parseDouble(widthInputField.getText()),
                    Double.parseDouble(heightInputField.getText())
            ));

            eventBus.publish(new DiagramSelectionChangedEvent());
        } catch (NumberFormatException | NullPointerException e){
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
        this.eventBus.subscribe(new SnapToPixelUseCase(eventBus, astah));
        this.eventBus.subscribe(new StraightenLineUseCase(eventBus, astah));
        this.eventBus.subscribe(new ResetItemFlowUseCase(eventBus, astah));
    }

}
