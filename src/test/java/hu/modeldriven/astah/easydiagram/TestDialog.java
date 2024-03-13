package hu.modeldriven.astah.easydiagram;

import hu.modeldriven.astah.easydiagram.ui.EasyDiagramPanel;
import hu.modeldriven.core.eventbus.EventBus;

import javax.swing.*;
import java.awt.BorderLayout;

public class TestDialog {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

            JFrame frame = new JFrame();
            JPanel panel = new JPanel(new BorderLayout());

            EventBus eventBus = new EventBus();
            EasyDiagramPanel diagramPanel = new EasyDiagramPanel(eventBus);
            panel.add(diagramPanel, BorderLayout.CENTER);

            frame.setContentPane(panel);

            frame.pack();
            frame.setVisible(true);
        });
    }
}
