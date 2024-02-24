package hu.modeldriven.astah.easydiagram;

import com.change_vision.jude.api.inf.AstahAPI;
import com.change_vision.jude.api.inf.ui.IPluginExtraTabView;
import com.change_vision.jude.api.inf.ui.ISelectionListener;
import com.change_vision.jude.api.inf.view.IDiagramViewManager;
import com.change_vision.jude.api.inf.view.IEntitySelectionEvent;
import com.change_vision.jude.api.inf.view.IEntitySelectionListener;
import hu.modeldriven.astah.easydiagram.ui.EasyDiagramPanel;
import hu.modeldriven.astah.easydiagram.ui.event.DiagramSelectionChangedEvent;
import hu.modeldriven.core.eventbus.EventBus;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;

public class EasyDiagramView extends JPanel implements IPluginExtraTabView, IEntitySelectionListener {

    private final EventBus eventBus;

    public EasyDiagramView() {
        this.eventBus = new EventBus();
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        add(createContentPane(), BorderLayout.CENTER);
    }

    private Container createContentPane() {

        try {
            IDiagramViewManager viewManager = AstahAPI.getAstahAPI()
                    .getViewManager()
                    .getDiagramViewManager();

            viewManager.addEntitySelectionListener(this);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new EasyDiagramPanel(eventBus);
    }

    @Override
    public void addSelectionListener(ISelectionListener listener) {
        // nothing to do here
    }

    @Override
    public Component getComponent() {
        return this;
    }

    @Override
    public String getDescription() {
        return "Easy Diagram View";
    }

    @Override
    public String getTitle() {
        return "Easy Diagram";
    }

    public void activated() {
        // nothing to do here
    }

    public void deactivated() {
        // nothing to do here
    }

    @Override
    public void entitySelectionChanged(IEntitySelectionEvent iEntitySelectionEvent) {
        eventBus.publish(new DiagramSelectionChangedEvent());
    }
}

