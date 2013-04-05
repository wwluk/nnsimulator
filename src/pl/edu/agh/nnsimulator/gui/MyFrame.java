package pl.edu.agh.nnsimulator.gui;

/**
 * Created with IntelliJ IDEA.
 * User: greg
 * Date: 26.03.13
 * Time: 13:47
 * To change this template use File | Settings | File Templates.
 */
import javax.swing.*;

import pl.edu.agh.nnsimulator.activationFunctions.ActivationFunctionType;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MyFrame extends JPanel {
    private final JLabel _layerNumber = new JLabel("0");
    private JTextField _neuronsNumber;
    private ButtonGroup _activationFunctionType;
    private JPanel _activationPanel;

    /**
     * Create the panel.
     */
    public MyFrame(long layerNumber, int height) {
        super();
        this.setBounds(2, 2, 700, 60);
        _layerNumber.setText(String.valueOf(layerNumber));
        _layerNumber.setBounds(10,10,500,30);
        add(_layerNumber);

        _neuronsNumber = new JTextField();
        _neuronsNumber.setText("2");
//        _neuronsNumber.setSize(500,30);
        add(_neuronsNumber);

        _activationPanel = new JPanel();
        _activationPanel.setBounds(0,0,300,30);
        _activationFunctionType = new ButtonGroup();

        boolean checkActivationFunctionType = true;

        for (ActivationFunctionType aft : ActivationFunctionType.values()){
            JRadioButton jRadioButton = new JRadioButton(aft.name(), checkActivationFunctionType);
            jRadioButton.setActionCommand(aft.name());
            //System.out.println(aft.name());
            _activationPanel.add(jRadioButton);
            _activationFunctionType.add(jRadioButton);
            //add(jRadioButton);
            checkActivationFunctionType = false;
        }

        _activationPanel.setSize(300,30);

        _activationPanel.setVisible(true);
        _activationPanel.revalidate();
        add(_activationPanel);

        this.setSize(700,60);
        //this.setMinimumSize(new Dimension(700, 30));
    }

    public void setLayerNumber(long layerNumber){
        _layerNumber.setText(new Long(layerNumber).toString());
        _layerNumber.revalidate();
        revalidate();
    }

    public long getNumberOfNeurons(){
        return new Long(_neuronsNumber.getText());
    }

    public ActivationFunctionType getActivationFunctionType(){
        return ActivationFunctionType.valueOf(_activationFunctionType.getSelection().getActionCommand());
    }

}

