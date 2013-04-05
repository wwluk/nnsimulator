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

public class MyFrame extends JPanel {
    private final JLabel _layerNumber = new JLabel("0");
    private JTextField _neuronsNumber;
    private JComboBox _activationFunctionType;

    /**
     * Create the panel.
     */
    public MyFrame(long layerNumber, int height) {
        super();
        this.setBounds(2, 2, 700, 30);
        _layerNumber.setText(String.valueOf(layerNumber));
        _layerNumber.setBounds(10,10,500,30);
        add(_layerNumber);

        _neuronsNumber = new JTextField();
        _neuronsNumber.setText("2");
//        _neuronsNumber.setSize(500,30);
        Component added = add(_neuronsNumber);
        added.setSize(500,300);

        _activationFunctionType = new JComboBox(ActivationFunctionType.values());
        add(_activationFunctionType);
        this.setSize(700,30);
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
        return (ActivationFunctionType) _activationFunctionType.getSelectedItem();
    }

}

