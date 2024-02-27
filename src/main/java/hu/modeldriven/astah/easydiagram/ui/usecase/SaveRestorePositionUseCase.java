package hu.modeldriven.astah.easydiagram.ui.usecase;

import com.change_vision.jude.api.inf.exception.InvalidEditingException;
import com.change_vision.jude.api.inf.presentation.ILinkPresentation;
import com.change_vision.jude.api.inf.presentation.INodePresentation;
import hu.modeldriven.astah.core.AstahRepresentation;
import hu.modeldriven.astah.core.exception.AstahRuntimeException;
import hu.modeldriven.astah.core.transaction.AstahTransaction;
import hu.modeldriven.astah.easydiagram.ui.event.ExceptionOccurredEvent;
import hu.modeldriven.astah.easydiagram.ui.event.SaveRestoreRequestedEvent;
import hu.modeldriven.core.eventbus.Event;
import hu.modeldriven.core.eventbus.EventBus;
import hu.modeldriven.core.eventbus.EventHandler;

import javax.swing.*;
import java.awt.geom.Point2D;
import java.util.*;
import java.util.stream.Collectors;

public class SaveRestorePositionUseCase implements EventHandler<SaveRestoreRequestedEvent> {
    private final EventBus eventBus;
    private final AstahRepresentation astah;

    private final JButton button;

    private INodePresentation selectedNode;

    private Status status = Status.EMPTY;

    private Map<String, Point2D> childNodesCoordinates = new HashMap<>();

    private Map<String, Point2D[]> childLinkCoordinates = new HashMap<>();


    public SaveRestorePositionUseCase(EventBus eventBus, AstahRepresentation astah, JButton button) {
        this.eventBus = eventBus;
        this.astah = astah;
        this.button = button;
    }

    @Override
    public void handleEvent(SaveRestoreRequestedEvent event) {
        if (status == Status.EMPTY) {
            savePositions();
        } else {
            restorePositions();
        }
    }

    void savePositions() {
        if (astah.selectedNodes().size() != 1) {
            return;
        }

        this.selectedNode = astah.selectedNodes().get(0);

        for (INodePresentation child : selectedNode.getChildren()) {

            Point2D location = child.getLocation();

            childNodesCoordinates.put(child.getModel().getId(),
                    new Point2D.Double(
                            location.getX(),
                            location.getY()
                    )
            );
        }

        for (ILinkPresentation linkPresentation : selectedNode.getLinks()){
            childLinkCoordinates.put(
              linkPresentation.getID(),
              clone(linkPresentation.getAllPoints())
            );
        }

        this.button.setText("Restore position");

        this.status = Status.SAVED;
    }

    private Point2D[] clone(Point2D[] source){
        return Arrays.stream(source)
                .map(p -> new Point2D.Double(p.getX(), p.getY()))
                .toArray(Point2D.Double[]::new);
    }

    void restorePositions() {

        try {
            if (Arrays.stream(astah.currentDiagram().getPresentations()).noneMatch(e -> e.equals(selectedNode))) {
                clear();
                return;
            }

            AstahTransaction transaction = new AstahTransaction();

            transaction.execute(() -> {
                try {

                    for (INodePresentation child : selectedNode.getChildren()) {
                        Point2D storedPoint = childNodesCoordinates.get(child.getModel().getId());
                        child.setLocation(storedPoint);
                    }

                    for (ILinkPresentation link : selectedNode.getLinks()) {
                        link.setAllPoints(childLinkCoordinates.get(link.getID()));
                    }

                } catch (InvalidEditingException e) {
                    throw new AstahRuntimeException(e);
                }

            });

            clear();

        } catch (Exception e) {
            eventBus.publish(new ExceptionOccurredEvent(e));
        }
    }

    private void clear() {
        this.button.setText("Save position");
        this.status = Status.EMPTY;
        this.childNodesCoordinates.clear();
        this.childLinkCoordinates.clear();
        this.selectedNode = null;
    }

    @Override
    public List<Class<? extends Event>> subscribedEvents() {
        return Collections.singletonList(SaveRestoreRequestedEvent.class);
    }

    enum Status {
        EMPTY, SAVED
    }
}
