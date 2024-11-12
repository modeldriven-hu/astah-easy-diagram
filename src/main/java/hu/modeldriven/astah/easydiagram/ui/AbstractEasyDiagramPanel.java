/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package hu.modeldriven.astah.easydiagram.ui;

import hu.modeldriven.astah.easydiagram.ui.layout.WrapLayout;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;

/**
 * @author zsolt
 */
public class AbstractEasyDiagramPanel extends javax.swing.JPanel {

    protected JButton applyButton;
    protected JTextField heightInputField;
    protected JTextField leftInputField;
    protected JButton matchAllExceptEndPointsButton;
    protected JButton matchAllPointsButton;
    protected JButton resetItemFlowButton;
    protected JButton snapSelectedElementsToPixelButton;
    protected JButton snapDiagramToPixelButton;

    protected JButton straightenLineButton;
    protected JButton saveRestoreButton;
    protected JTextField topInputField;
    protected JTextField widthInputField;

    protected JTextField valueNameInputField;

    protected JTextField valueTypeInputField;

    protected JTextField valueConstraintInputField;

    protected JButton createValueButton;

    protected JButton horizontalCenterAlignButton;
    protected JButton verticalCenterAlignButton;

    protected JButton unmarshallInputButton;
    protected JButton unmarshallOutputButton;

    public AbstractEasyDiagramPanel() {
        initComponents();
    }

    private void initComponents() {

        JPanel coordinatePanel = new javax.swing.JPanel();
        JLabel jLabel2 = new javax.swing.JLabel();
        leftInputField = new javax.swing.JTextField();
        JLabel jLabel3 = new javax.swing.JLabel();
        topInputField = new javax.swing.JTextField();
        JLabel jLabel4 = new javax.swing.JLabel();
        widthInputField = new javax.swing.JTextField();
        JLabel jLabel5 = new javax.swing.JLabel();
        heightInputField = new javax.swing.JTextField();
        applyButton = new javax.swing.JButton();

        JPanel createValuePanel = new javax.swing.JPanel();
        JLabel valueNameLabel = new javax.swing.JLabel();
        JLabel valueTypeLabel = new javax.swing.JLabel();
        JLabel valueConstraintLabel = new javax.swing.JLabel();
        valueNameInputField = new javax.swing.JTextField();
        valueTypeInputField = new javax.swing.JTextField();
        valueConstraintInputField = new javax.swing.JTextField();
        createValueButton = new javax.swing.JButton();

        JPanel linePanel = new javax.swing.JPanel();
        snapSelectedElementsToPixelButton = new javax.swing.JButton();
        snapDiagramToPixelButton = new javax.swing.JButton();
        straightenLineButton = new javax.swing.JButton();
        matchAllPointsButton = new javax.swing.JButton();
        matchAllExceptEndPointsButton = new javax.swing.JButton();
        resetItemFlowButton = new javax.swing.JButton();
        saveRestoreButton = new javax.swing.JButton();
        horizontalCenterAlignButton = new javax.swing.JButton();
        verticalCenterAlignButton = new javax.swing.JButton();
        unmarshallInputButton = new javax.swing.JButton();
        unmarshallOutputButton = new javax.swing.JButton();

        setLayout(new MigLayout(
                "fillx, hidemode 3",
                "[fill]",
                "[][][]"
        ));

        coordinatePanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Coordinate system"));
        java.awt.FlowLayout flowLayout1 = new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 10, 10);
        flowLayout1.setAlignOnBaseline(true);
        coordinatePanel.setLayout(flowLayout1);

        jLabel2.setText("Left (X):");
        coordinatePanel.add(jLabel2);
        coordinatePanel.add(leftInputField);

        jLabel3.setText("Top (Y):");
        coordinatePanel.add(jLabel3);
        coordinatePanel.add(topInputField);

        jLabel4.setText("Width:");
        coordinatePanel.add(jLabel4);

        coordinatePanel.add(widthInputField);

        jLabel5.setText("Height:");
        coordinatePanel.add(jLabel5);
        coordinatePanel.add(heightInputField);

        applyButton.setText("Apply");
        coordinatePanel.add(applyButton);

        add(coordinatePanel, "cell 0 0");

        createValuePanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Field"));
        java.awt.FlowLayout flowLayout2 = new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 10, 10);
        flowLayout2.setAlignOnBaseline(true);
        createValuePanel.setLayout(flowLayout2);

        valueNameLabel.setText("Name:");
        createValuePanel.add(valueNameLabel);
        createValuePanel.add(valueNameInputField);

        valueTypeLabel.setText("Type:");
        createValuePanel.add(valueTypeLabel);
        createValuePanel.add(valueTypeInputField);

        // FIXME removed until we figure out how to create constraints
//        valueConstraintLabel.setText("Constraint:");
//        createValuePanel.add(valueConstraintLabel);
//        createValuePanel.add(valueConstraintInputField);

        createValueButton.setText("Create");
        createValuePanel.add(createValueButton);

        add(createValuePanel, "cell 0 1");

        linePanel.setLayout(new WrapLayout());

        snapSelectedElementsToPixelButton.setText("Snap selected to pixel");
        linePanel.add(snapSelectedElementsToPixelButton);

        snapDiagramToPixelButton.setText("Snap diagram to pixel");
        linePanel.add(snapDiagramToPixelButton);

        straightenLineButton.setText("Straighten line");
        linePanel.add(straightenLineButton);

        resetItemFlowButton.setText("Reset item flow");
        linePanel.add(resetItemFlowButton);

        saveRestoreButton.setText("Save position");
        linePanel.add(saveRestoreButton);

        horizontalCenterAlignButton.setText("Horizontal center align");
        linePanel.add(horizontalCenterAlignButton);

        verticalCenterAlignButton.setText("Vertical center align");
        linePanel.add(verticalCenterAlignButton);

        unmarshallInputButton.setText("Unmarshall input");
        linePanel.add(unmarshallInputButton);

        unmarshallOutputButton.setText("Unmarshall output");
        linePanel.add(unmarshallOutputButton);

        add(linePanel, "cell 0 2");
    }
}
