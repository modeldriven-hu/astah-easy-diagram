package hu.modeldriven.astah.easydiagram.ui.usecase;

import com.change_vision.jude.api.inf.model.IActivityDiagram;
import com.change_vision.jude.api.inf.model.IBlock;
import hu.modeldriven.astah.core.AstahRepresentation;
import hu.modeldriven.astah.easydiagram.ui.event.UnmarshallPinsRequestedEvent;
import hu.modeldriven.core.eventbus.Event;
import hu.modeldriven.core.eventbus.EventBus;
import hu.modeldriven.core.eventbus.EventHandler;

import java.util.List;

public class UnmarshallPinsUseCase implements EventHandler<UnmarshallPinsRequestedEvent> {

    private final EventBus eventBus;
    private final AstahRepresentation astah;

    public UnmarshallPinsUseCase(EventBus eventBus, AstahRepresentation astah){
        this.eventBus = eventBus;
        this.astah = astah;
    }

    @Override
    public void handleEvent(UnmarshallPinsRequestedEvent event) {
        var elements = astah.selectedStructureElements();

        if (elements.isEmpty()){
            return;
        }

        var currentDiagram = astah.currentDiagram();

        if (!(currentDiagram instanceof IActivityDiagram)){
            return;
        }

        var element = elements.getFirst();

        if (element instanceof IBlock block){



            for (var property : block.getValueProperties()){

            }
        }
    }

    @Override
    public List<Class<? extends Event>> subscribedEvents() {
        return List.of(UnmarshallPinsRequestedEvent.class);
    }
}
