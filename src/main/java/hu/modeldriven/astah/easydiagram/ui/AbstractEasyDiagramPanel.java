/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package hu.modeldriven.astah.easydiagram.ui;

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
    protected JButton snapToPixelButton;
    protected JButton straightenLineButton;
    protected JButton saveRestoreButton;
    protected JTextField topInputField;
    protected JTextField widthInputField;

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
        JPanel linePanel = new javax.swing.JPanel();
        snapToPixelButton = new javax.swing.JButton();
        straightenLineButton = new javax.swing.JButton();
        matchAllPointsButton = new javax.swing.JButton();
        matchAllExceptEndPointsButton = new javax.swing.JButton();
        resetItemFlowButton = new javax.swing.JButton();
        saveRestoreButton = new javax.swing.JButton();

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

        linePanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEADING));

        snapToPixelButton.setText("Snap to pixel");
        linePanel.add(snapToPixelButton);

        straightenLineButton.setText("Straighten line");
        linePanel.add(straightenLineButton);

        resetItemFlowButton.setText("Reset item flow");
        linePanel.add(resetItemFlowButton);

        saveRestoreButton.setText("Save/restore position");
        linePanel.add(saveRestoreButton);

        add(linePanel, "cell 0 1");
    }
}
