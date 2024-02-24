package hu.modeldriven.astah.happydiagram.ui;

import hu.modeldriven.astah.core.AstahRepresentation;
import hu.modeldriven.astah.happydiagram.ui.event.*;
import hu.modeldriven.astah.happydiagram.ui.usecase.*;
import hu.modeldriven.core.eventbus.EventBus;

public class HappyDiagramPanel extends AbstractHappyDiagramPanel {

    private final EventBus eventBus;
    private final AstahRepresentation astah;

    public HappyDiagramPanel(EventBus eventBus) {
        super();
        this.eventBus = eventBus;
        this.astah = new AstahRepresentation();
        initUIComponents();
        initUseCases();
    }

    private void initUIComponents() {
        applyButton.addActionListener(e -> eventBus.publish(new ChangeBoundsRequestedEvent(
                Double.parseDouble(leftInputField.getText()),
                Double.parseDouble(topInputField.getText()),
                Double.parseDouble(widthInputField.getText()),
                Double.parseDouble(heightInputField.getText())
        )));

        snapToPixelButton.addActionListener(e -> eventBus.publish(new SnapToPixelRequestedEvent()));
        straightenLineButton.addActionListener(e -> eventBus.publish(new StraightenLineRequestedEvent()));
        resetItemFlowButton.addActionListener(e -> eventBus.publish(new ResetItemFlowRequestedEvent()));
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
