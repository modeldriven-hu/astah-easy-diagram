package hu.modeldriven.astah.core;

import com.change_vision.jude.api.inf.AstahAPI;
import com.change_vision.jude.api.inf.editor.BlockDefinitionDiagramEditor;
import com.change_vision.jude.api.inf.model.IDiagram;
import com.change_vision.jude.api.inf.project.ProjectAccessor;
import hu.modeldriven.astah.core.exception.AstahRuntimeException;

public class AstahRepresentation {

    public IDiagram getCurrentDiagram() throws AstahRuntimeException {
        try {
            return AstahAPI.getAstahAPI()
                    .getViewManager()
                    .getDiagramViewManager()
                    .getCurrentDiagram();
        } catch (Exception e) {
            throw new AstahRuntimeException(e);
        }
    }

    public BlockDefinitionDiagramEditor getDiagramEditor() throws AstahRuntimeException {
        try {
            AstahAPI api = AstahAPI.getAstahAPI();
            ProjectAccessor accessor = api.getProjectAccessor();
            IDiagram diagram = accessor.getViewManager().getDiagramViewManager().getCurrentDiagram();
            BlockDefinitionDiagramEditor editor = accessor.getDiagramEditorFactory().getBlockDefinitionDiagramEditor();
            editor.setDiagram(diagram);
            return editor;
        } catch (Exception e) {
            throw new AstahRuntimeException(e);
        }
    }

}
