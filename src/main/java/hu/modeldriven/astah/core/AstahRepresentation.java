package hu.modeldriven.astah.core;

import com.change_vision.jude.api.inf.AstahAPI;
import com.change_vision.jude.api.inf.editor.ActivityDiagramEditor;
import com.change_vision.jude.api.inf.editor.SysmlModelEditor;
import com.change_vision.jude.api.inf.exception.InvalidEditingException;
import com.change_vision.jude.api.inf.exception.InvalidUsingException;
import com.change_vision.jude.api.inf.model.*;
import com.change_vision.jude.api.inf.presentation.ILinkPresentation;
import com.change_vision.jude.api.inf.presentation.INodePresentation;
import com.change_vision.jude.api.inf.presentation.IPresentation;
import hu.modeldriven.astah.core.exception.AstahRuntimeException;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Arrays;
import java.util.List;

public class AstahRepresentation {

    public IValueType findValueTypeByName(String typeName) {

        try {
            INamedElement[] elements = AstahAPI.getAstahAPI()
                    .getProjectAccessor()
                    .findElements(iNamedElement -> iNamedElement instanceof IValueType &&
                            iNamedElement.getName().equals(typeName));

            if (elements.length != 1) {
                return null;
            }

            return (IValueType) elements[0];

        } catch (Exception e) {
            throw new AstahRuntimeException(e);
        }

    }

    public SysmlModelEditor modelEditor() {
        try {
            return AstahAPI.getAstahAPI().getProjectAccessor().getModelEditorFactory().getSysmlModelEditor();
        } catch (Exception e) {
            throw new AstahRuntimeException(e);
        }
    }

    public IDiagram currentDiagram() {
        try {
            return AstahAPI.getAstahAPI()
                    .getViewManager()
                    .getDiagramViewManager()
                    .getCurrentDiagram();
        } catch (Exception e) {
            throw new AstahRuntimeException(e);
        }
    }

    public List<IEntity> selectedStructureElements() {
        try {
            return Arrays.asList(AstahAPI.getAstahAPI().getProjectAccessor().getViewManager().getProjectViewManager().getSelectedEntities());
        } catch (Exception e) {
            throw new AstahRuntimeException(e);
        }
    }

    public List<IPresentation> selectedPresentations() {
        try {
            return Arrays.stream(AstahAPI.getAstahAPI()
                            .getViewManager()
                            .getDiagramViewManager()
                            .getSelectedPresentations())
                    .filter(e -> !(e.getModel() instanceof IDiagram))
                    .toList();
        } catch (Exception e) {
            throw new AstahRuntimeException(e);
        }
    }

    public List<INodePresentation> selectedNodes() {
        return selectedPresentations().stream()
                .filter(INodePresentation.class::isInstance)
                .map(INodePresentation.class::cast)
                .toList();
    }

    public List<ILinkPresentation> selectedLinks() {
        return selectedPresentations().stream()
                .filter(ILinkPresentation.class::isInstance)
                .map(ILinkPresentation.class::cast)
                .toList();
    }

    public void setBounds(INodePresentation node, Rectangle2D rectangle) {
        try {
            node.setLocation(rectangle.getBounds().getLocation());
            node.setWidth(rectangle.getWidth());
            node.setHeight(rectangle.getHeight());
        } catch (InvalidEditingException e) {
            throw new AstahRuntimeException(e);
        }
    }

    public void setPoints(ILinkPresentation presentation, Point2D[] points) {
        try {
            presentation.setAllPoints(points);
        } catch (InvalidEditingException e) {
            throw new AstahRuntimeException(e);
        }
    }

    public void selectCurrentDiagramElements() {
        try {
            AstahAPI.getAstahAPI()
                    .getViewManager()
                    .getDiagramViewManager()
                    .selectAll();
        } catch (ClassNotFoundException | InvalidUsingException e) {
            throw new AstahRuntimeException(e);
        }
    }

    private ActivityDiagramEditor activityDiagramEditor() {
        try {
            return AstahAPI.getAstahAPI()
                    .getProjectAccessor()
                    .getDiagramEditorFactory()
                    .getActivityDiagramEditor();
        } catch (InvalidUsingException | ClassNotFoundException e) {
            throw new AstahRuntimeException(e);
        }
    }

    public INodePresentation createObjectNode(IActivityDiagram diagram, String name, IValueType type, Point2D location) {
        try {
            var editor = activityDiagramEditor();
            editor.setDiagram(diagram);
            return editor.createObjectNode(name, type, location);
        } catch (InvalidEditingException e) {
            throw new AstahRuntimeException(e);
        }
    }

    public void createObjectFlow(IActivityDiagram diagram, INodePresentation source, INodePresentation target) {
        try {
            var editor = activityDiagramEditor();
            editor.setDiagram(diagram);
            var flow = editor.createFlow(source, target);
        } catch (InvalidEditingException e) {
            throw new AstahRuntimeException(e);
        }
    }

    public INodePresentation createAction(IActivityDiagram diagram, String name, Point2D location) {
        try {
            var editor = activityDiagramEditor();
            editor.setDiagram(diagram);
            return editor.createAction(name, location);
        } catch (InvalidEditingException e) {
            throw new AstahRuntimeException(e);
        }
    }

    public INodePresentation createPin(IActivityDiagram diagram, INodePresentation actionNode, String pinName, IClass pinType, boolean isInput, Point2D location) {
        try {
            var editor = activityDiagramEditor();
            editor.setDiagram(diagram);
            return editor.createPin(pinName, pinType, isInput, actionNode, location);
        } catch (InvalidEditingException e) {
            throw new AstahRuntimeException(e);
        }
    }

    public void saveProject(){
        try {
            var projectAccessor = AstahAPI.getAstahAPI().getProjectAccessor();

            if (projectAccessor.hasProject()){
                projectAccessor.save();
            }

        } catch (Exception e){
            throw new AstahRuntimeException(e);
        }
    }

}
