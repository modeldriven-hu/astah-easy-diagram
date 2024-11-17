package hu.modeldriven.astah.easydiagram.ui.usecase;

import hu.modeldriven.astah.core.AstahRepresentation;
import hu.modeldriven.astah.easydiagram.ui.event.DisplayStatisticsRequestedEvent;
import hu.modeldriven.core.eventbus.Event;
import hu.modeldriven.core.eventbus.EventBus;
import hu.modeldriven.core.eventbus.EventHandler;

import javax.swing.*;
import java.util.List;

public class DisplayStatisticsUseCase implements EventHandler<DisplayStatisticsRequestedEvent> {

    private final EventBus eventBus;
    private final AstahRepresentation astah;

    public DisplayStatisticsUseCase(EventBus eventBus, AstahRepresentation astahRepresentation){
        this.eventBus = eventBus;
        this.astah = astahRepresentation;
    }

    @Override
    public void handleEvent(DisplayStatisticsRequestedEvent event) {
        SwingUtilities.invokeLater(() -> {

            var elementCount = astah.allElements().size();
            var diagramCount = astah.allDiagrams().size();

            JOptionPane.showMessageDialog(null,
                    "Element count: " + elementCount + "\nDiagram count: " + diagramCount,
                    "Statistics",
                    JOptionPane.INFORMATION_MESSAGE);
        });
    }

    @Override
    public List<Class<? extends Event>> subscribedEvents() {
        return List.of(DisplayStatisticsRequestedEvent.class);
    }
}
