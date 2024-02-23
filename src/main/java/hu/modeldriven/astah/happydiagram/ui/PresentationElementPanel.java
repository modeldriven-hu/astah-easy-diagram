package hu.modeldriven.astah.happydiagram.ui;

import hu.modeldriven.astah.happydiagram.ui.event.*;
import hu.modeldriven.core.eventbus.EventBus;

public class PresentationElementPanel extends AbstractPresentationElementPanel{

    private final EventBus eventBus;

    public PresentationElementPanel(EventBus eventBus){
        super();
        this.eventBus = eventBus;
        initUIComponents();
        initUseCases();
    }

    private void initUIComponents() {
        applyButton.addActionListener(e -> eventBus.publish(new ChangeBoundsRequestedEvent(
                (Integer)leftInputField.getValue(),
                (Integer)topInputField.getValue(),
                (Integer)widthInputField.getValue(),
                (Integer)heightInputField.getValue()
        )));

        snapToPixelButton.addActionListener(e -> eventBus.publish(new SnapToPixelRequestedEvent()));
        straightenLineButton.addActionListener(e -> eventBus.publish(new StraightenLineRequestedEvent()));
        matchAllPointsButton.addActionListener(e -> eventBus.publish(new MatchAllPointsRequestedEvent()));
        matchAllExceptEndPointsButton.addActionListener(e -> eventBus.publish(new MatchAllExceptEndPointsRequest()));
        resetItemFlowButton.addActionListener(e -> eventBus.publish(new ResetItemFlowRequestedEvent()));
    }

    private void initUseCases() {

    }



}
