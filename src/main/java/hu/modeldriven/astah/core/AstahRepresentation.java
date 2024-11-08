package hu.modeldriven.astah.core;

import com.change_vision.jude.api.inf.AstahAPI;
import com.change_vision.jude.api.inf.editor.SysmlModelEditor;
import com.change_vision.jude.api.inf.exception.InvalidEditingException;
import com.change_vision.jude.api.inf.exception.InvalidUsingException;
import com.change_vision.jude.api.inf.model.IDiagram;
import com.change_vision.jude.api.inf.model.INamedElement;
import com.change_vision.jude.api.inf.model.IValueType;
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

    public SysmlModelEditor modelEditor() throws AstahRuntimeException {
        try {
            return AstahAPI.getAstahAPI().getProjectAccessor().getModelEditorFactory().getSysmlModelEditor();
        } catch (Exception e) {
            throw new AstahRuntimeException(e);
        }
    }

    public IDiagram currentDiagram() throws AstahRuntimeException {
        try {
            return AstahAPI.getAstahAPI()
                    .getViewManager()
                    .getDiagramViewManager()
                    .getCurrentDiagram();
        } catch (Exception e) {
            throw new AstahRuntimeException(e);
        }
    }

    public List<IPresentation> selectedPresentations() throws AstahRuntimeException {
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

    public List<INodePresentation> selectedNodes() throws AstahRuntimeException {
        return selectedPresentations().stream()
                .filter(INodePresentation.class::isInstance)
                .map(INodePresentation.class::cast)
                .toList();
    }

    public List<ILinkPresentation> selectedLinks() throws AstahRuntimeException {
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
        try{
            AstahAPI.getAstahAPI()
                    .getViewManager()
                    .getDiagramViewManager()
                    .selectAll();
        } catch (ClassNotFoundException | InvalidUsingException e){
            throw new AstahRuntimeException(e);
        }
    }
}
