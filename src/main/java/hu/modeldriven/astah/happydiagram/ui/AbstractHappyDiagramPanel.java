/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package hu.modeldriven.astah.happydiagram.ui;

/**
 * @author zsolt
 */
public class AbstractHappyDiagramPanel extends javax.swing.JPanel {

    protected javax.swing.JButton applyButton;
    protected javax.swing.JTextField heightInputField;
    protected javax.swing.JTextField leftInputField;
    protected javax.swing.JButton matchAllExceptEndPointsButton;
    protected javax.swing.JButton matchAllPointsButton;
    protected javax.swing.JButton resetItemFlowButton;
    protected javax.swing.JLabel selectedItemLabel;
    protected javax.swing.JButton snapToPixelButton;
    protected javax.swing.JButton straightenLineButton;
    protected javax.swing.JTextField topInputField;
    protected javax.swing.JTextField widthInputField;
    private javax.swing.JPanel coordinatePanel;
    private javax.swing.JPanel infoPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel linePanel;

    public AbstractHappyDiagramPanel() {
        initComponents();
    }

    private void initComponents() {

        infoPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        selectedItemLabel = new javax.swing.JLabel();
        coordinatePanel = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        leftInputField = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        topInputField = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        widthInputField = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        heightInputField = new javax.swing.JTextField();
        applyButton = new javax.swing.JButton();
        linePanel = new javax.swing.JPanel();
        snapToPixelButton = new javax.swing.JButton();
        straightenLineButton = new javax.swing.JButton();
        matchAllPointsButton = new javax.swing.JButton();
        matchAllExceptEndPointsButton = new javax.swing.JButton();
        resetItemFlowButton = new javax.swing.JButton();

        setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.PAGE_AXIS));

        infoPanel.setBackground(new java.awt.Color(255, 255, 255));
        infoPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEADING));

        jLabel1.setText("Selected item:");
        infoPanel.add(jLabel1);

        selectedItemLabel.setText("None");
        infoPanel.add(selectedItemLabel);

        add(infoPanel);

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

        add(coordinatePanel);

        linePanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEADING));

        snapToPixelButton.setText("Snap to pixel");
        linePanel.add(snapToPixelButton);

        straightenLineButton.setText("Straighten line");
        linePanel.add(straightenLineButton);

        matchAllPointsButton.setText("Match all points");
        linePanel.add(matchAllPointsButton);

        matchAllExceptEndPointsButton.setText("Match all except end points");
        linePanel.add(matchAllExceptEndPointsButton);

        resetItemFlowButton.setText("Reset item flow");
        linePanel.add(resetItemFlowButton);

        add(linePanel);
    }
}
