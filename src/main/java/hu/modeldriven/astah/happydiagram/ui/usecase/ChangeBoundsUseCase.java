package hu.modeldriven.astah.happydiagram.ui.usecase;

import hu.modeldriven.astah.happydiagram.ui.event.ChangeBoundsRequestedEvent;
import hu.modeldriven.core.eventbus.Event;
import hu.modeldriven.core.eventbus.EventHandler;

import java.util.Collections;
import java.util.List;

public class ChangeBoundsUseCase implements EventHandler<ChangeBoundsRequestedEvent> {

    public ChangeBoundsUseCase(){

    }

    @Override
    public void handleEvent(ChangeBoundsRequestedEvent event) {
        // create transaction
        // get selected elements
        // get the first one that is not
    }

    @Override
    public List<Class<? extends Event>> subscribedEvents() {
        return Collections.singletonList(ChangeBoundsRequestedEvent.class);
    }
}
