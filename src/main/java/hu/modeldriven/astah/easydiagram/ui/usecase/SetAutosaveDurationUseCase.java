package hu.modeldriven.astah.easydiagram.ui.usecase;

import hu.modeldriven.astah.core.AstahRepresentation;
import hu.modeldriven.astah.core.transaction.AstahTransaction;
import hu.modeldriven.astah.easydiagram.ui.event.AutosaveDurationChangeRequestedEvent;
import hu.modeldriven.astah.easydiagram.ui.event.ExceptionOccurredEvent;
import hu.modeldriven.core.eventbus.Event;
import hu.modeldriven.core.eventbus.EventBus;
import hu.modeldriven.core.eventbus.EventHandler;

import java.util.List;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SetAutosaveDurationUseCase implements EventHandler<AutosaveDurationChangeRequestedEvent> {

    private final EventBus eventBus;
    private final AstahRepresentation astah;
    private ScheduledExecutorService service;

    public SetAutosaveDurationUseCase(EventBus eventBus, AstahRepresentation astah){
        this.eventBus = eventBus;
        this.astah = astah;
    }

    @Override
    public void handleEvent(AutosaveDurationChangeRequestedEvent event) {

        if (this.service != null){
            this.service.shutdown();
        }

        if (event.durationInSeconds() == 0){
            return;
        }

        this.service = Executors.newSingleThreadScheduledExecutor();

        var timerTask = new TimerTask(){

            @Override
            public void run() {
                try {
                    var transaction = new AstahTransaction();
                    transaction.execute(astah::saveProject);
                } catch (Exception e){
                    eventBus.publish(new ExceptionOccurredEvent(e));
                }
            }
        };

        this.service.scheduleAtFixedRate(timerTask, 0, event.durationInSeconds(), TimeUnit.SECONDS);
    }

    @Override
    public List<Class<? extends Event>> subscribedEvents() {
        return List.of(AutosaveDurationChangeRequestedEvent.class);
    }
}
