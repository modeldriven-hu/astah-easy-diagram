package hu.modeldriven.astah.easydiagram.plugin;

import com.change_vision.jude.api.inf.AstahAPI;
import com.change_vision.jude.api.inf.ui.IPluginTabView;
import com.change_vision.jude.api.inf.ui.ISelectionListener;
import com.change_vision.jude.api.inf.view.IDiagramViewManager;
import com.change_vision.jude.api.inf.view.IEntitySelectionEvent;
import com.change_vision.jude.api.inf.view.IEntitySelectionListener;
import hu.modeldriven.astah.easydiagram.ui.EasyDiagramPanel;
import hu.modeldriven.astah.easydiagram.ui.event.DiagramSelectionChangedEvent;
import hu.modeldriven.core.eventbus.EventBus;

import javax.swing.*;
import java.awt.*;

public class EasyDiagramView extends JPanel implements IPluginTabView, IEntitySelectionListener {

    private final EventBus eventBus;

    public EasyDiagramView() {
        this.eventBus = new EventBus();
    }

    @Override
    public void addSelectionListener(ISelectionListener listener) {
        // nothing to do here
    }

    @Override
    public void removeSelectionListener(ISelectionListener iSelectionListener) {
        // nothing to do here
    }

    @Override
    public Component getComponent() {
        return this;
    }

    @Override
    public Object[] getSelectedModels() {
        return new Object[0];
    }

    @Override
    public void initTreeModel() {
        setLayout(new BorderLayout());

        try {
            IDiagramViewManager viewManager = AstahAPI.getAstahAPI()
                    .getViewManager()
                    .getDiagramViewManager();

            viewManager.addEntitySelectionListener(this);

        } catch (Exception e) {
            e.printStackTrace();
        }

        var panel = new EasyDiagramPanel(eventBus);
        add(panel, BorderLayout.CENTER);
    }

    @Override
    public String getDescription() {
        return "Easy Diagram View";
    }

    @Override
    public String getTitle() {
        return "Easy Diagram";
    }

    @Override
    public void entitySelectionChanged(IEntitySelectionEvent iEntitySelectionEvent) {
        eventBus.publish(new DiagramSelectionChangedEvent());
    }
}

