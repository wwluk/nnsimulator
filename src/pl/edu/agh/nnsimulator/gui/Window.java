package pl.edu.agh.nnsimulator.gui;

import au.com.bytecode.opencsv.CSVReader;
import pl.edu.agh.nnsimulator.NeuralNetwork;
import pl.edu.agh.nnsimulator.activationFunctions.ActivationFunctionType;
import pl.edu.agh.nnsimulator.exceptions.TooMuchInputLayersException;
import pl.edu.agh.nnsimulator.layers.InvalidDimensionsException;
import pl.edu.agh.nnsimulator.layers.NetworkLayer;
import pl.edu.agh.nnsimulator.neurons.NeuronData;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class Window {

    private JFrame frame;
    private JTextField _layersCount;
    private List<MyFrame> _myFrames = new LinkedList<MyFrame>();
    private JScrollPane _scrollPane;
    private JButton _countButton;
    private JTextField _input1TextField;
    private JTextField _input2TextField;
    private File _fileChoosenToImport;
    private JButton _btnChooseImportingFile;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Window window = new Window();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public Window() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel lblLayersCount = new JLabel("Layers Count");
        lblLayersCount.setBounds(0, 14, 114, 15);
        frame.getContentPane().add(lblLayersCount);

        _layersCount = new JTextField();

        _layersCount.setText("2");
        _layersCount.setBounds(96, 12, 32, 19);
        frame.getContentPane().add(_layersCount);
        _layersCount.setColumns(10);

        JLabel lblNeuronsInLayer = new JLabel("Neurons in layer count");
        lblNeuronsInLayer.setBounds(225, 58, 181, 15);
        frame.getContentPane().add(lblNeuronsInLayer);

        JLabel lblActivationFunction = new JLabel("Activation function");
        lblActivationFunction.setBounds(550, 58, 158, 15);
        frame.getContentPane().add(lblActivationFunction);

        JLabel lblLayerNumber = new JLabel("Layer number");
        lblLayerNumber.setBounds(12, 58, 102, 15);
        frame.getContentPane().add(lblLayerNumber);

        _layersCount.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                changeLayersCount();
            }
            public void removeUpdate(DocumentEvent e) {
                changeLayersCount();
            }
            public void insertUpdate(DocumentEvent e) {
                changeLayersCount();
            }
        });

        _scrollPane = new JScrollPane();
        _scrollPane.setBounds(10,80,780,510);
        frame.getContentPane().add(_scrollPane);

        _countButton = new JButton();
        _countButton.setText("Count");
        _countButton.setBounds(650,5,100,40);

        _countButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    try {
                        count();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    } catch (IOException e) {
                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    }
                } catch (TooMuchInputLayersException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                } catch (InvalidDimensionsException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }

        });

        _input1TextField = new JTextField("0");
        _input1TextField.setBounds(550, 5, 30, 30);
        frame.add(_input1TextField);

        _input2TextField = new JTextField("0");
        _input2TextField.setBounds(600, 5, 30, 30);
        frame.add(_input2TextField);

        _btnChooseImportingFile = new JButton("Choose CSV file");
        _btnChooseImportingFile.setBounds(300, 5, 200, 50);


        _btnChooseImportingFile.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                JFileChooser fc = new JFileChooser(".");
                int returnVal = fc.showOpenDialog(fc);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    _fileChoosenToImport = fc.getSelectedFile();
                }
                //txtFieldImportingFile.setText(fileChoosenToImport.getName());
            }
        });

        frame.add(_btnChooseImportingFile);


        frame.add(_countButton);
    }

    private void count() throws TooMuchInputLayersException, InvalidDimensionsException, IOException {
        NeuralNetwork nn = new NeuralNetwork(2);

        CSVReader reader = new CSVReader(new FileReader(_fileChoosenToImport));
        int j = 0;
        System.out.println("_myFrame size: "+_myFrames.size());
        for(MyFrame myFrame : _myFrames){

            NeuronData[] neuronDatas = new NeuronData[(int) myFrame.getNumberOfNeurons()];
            int neuronNumber = (int) myFrame.getNumberOfNeurons();
            System.out.println("j"+ j);
            for( int i = 0 ; i < neuronNumber ; i++){
                String [] nextLine = reader.readNext();
                // nextLine[] is an array of values from the line
                System.out.println(nextLine[0] + " " + nextLine[1] + " " + nextLine[2]);
                double waga = Double.parseDouble(nextLine[0]);
                neuronDatas[i] = new NeuronData(waga , new double[]{Double.parseDouble(nextLine[1]), Double.parseDouble(nextLine[2])});
            }
            System.out.println("po tym blad");
            nn.addLayer(new NetworkLayer(myFrame.getActivationFunctionType(),neuronDatas));
            j++;
        }

        double[] inputs = {Double.parseDouble(_input1TextField.getText()), Double.parseDouble(_input2TextField.getText())};
        nn.setInputs(inputs);
        double[] outputs = nn.calculate();
        for(Double output : outputs){
            System.out.println(output);
        }

    }

    private void changeLayersCount(){
        if (!_layersCount.getText().equals("")){
            long layersCount = new Long(_layersCount.getText());
            System.out.println(layersCount);

            if (layersCount > _myFrames.size()){
                //_scrollPane.setVisible(false);
                int height = 1 + (_myFrames.size()) * 30;
                MyFrame myFrame;
                for (long i = _myFrames.size() - 1 ; i < layersCount ; i++){
                    if (i < 0){
                        i = 0;
                    }
                    myFrame = new MyFrame(i,height);
                    myFrame.setBounds(1, height, 150, 30);
                    _scrollPane.add(myFrame);
                    _myFrames.add(myFrame);
                    height += 30;
                }
            } else {
                System.out.println("asd");
                System.out.println(_myFrames.size() - 1);
                for (int i = _myFrames.size() - 1; i > layersCount ; i--){
                    _myFrames.get(i).setVisible(false);
                    _myFrames.remove(i);
                }
            }
            frame.revalidate();

        }
    }
}

