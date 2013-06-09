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

import static javax.swing.JScrollBar.*;

public class Window_1 {

    private JFrame frame;
    private JTextField _layersCount;
    private List<MyFrame> _myFrames = new LinkedList<MyFrame>();
    private Box _scrollPane;
    private JButton _countButton;
    private File _fileChoosenToImport;
    private JButton _btnChooseImportingFile;
    private JScrollPane  _jScrollPane;
    private JTextArea _inputTextArea;
    private JTextArea _outputTextArea;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Window_1 window = new Window_1();
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
    public Window_1() {
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
        lblLayersCount.setBounds(0, 5, 114, 30);
        frame.getContentPane().add(lblLayersCount);

        _layersCount = new JTextField();

        _layersCount.setText("2");
        _layersCount.setBounds(96, 12, 32, 19);
        frame.getContentPane().add(_layersCount);
        _layersCount.setColumns(10);

        JLabel lblNeuronsInLayer = new JLabel("Neurons in layer count");
        lblNeuronsInLayer.setBounds(225, 45, 181, 15);
        //frame.getContentPane().add(lblNeuronsInLayer);

        JLabel lblActivationFunction = new JLabel("Activation function");
        lblActivationFunction.setBounds(550, 45, 158, 15);
        //frame.getContentPane().add(lblActivationFunction);

        JLabel lblLayerNumber = new JLabel("<html>Layer<br>number</html>");
        lblLayerNumber.setBounds(12, 45, 102, 30);
        //frame.getContentPane().add(lblLayerNumber);

        JLabel lblLayers = new JLabel("Layers (number, neurons, activation function)");
        lblLayers.setBounds(12, 40, 330, 45);
        frame.getContentPane().add(lblLayers);

        JLabel lblInputs = new JLabel("Inputs");
        lblInputs.setBounds(430, 40, 100, 45);
        frame.getContentPane().add(lblInputs);

        _inputTextArea = new JTextArea();
        frame.add(_inputTextArea);

        JScrollPane jScrollPane = new JScrollPane(_inputTextArea);
        jScrollPane.setBounds(380,80,150,490);
        frame.add(jScrollPane);

        JLabel lblOutputs = new JLabel("Outputs");
        lblOutputs.setBounds(590, 40, 100, 45);
        frame.getContentPane().add(lblOutputs);

        _outputTextArea = new JTextArea();
        frame.add(_outputTextArea);

        JScrollPane jScrollPane2 = new JScrollPane(_outputTextArea);
        jScrollPane2.setBounds(550,80,150,490);
        frame.add(jScrollPane2);


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

        _scrollPane = Box.createVerticalBox();
        //_scrollPane.setBounds(10,80,500,500);
        frame.getContentPane().add(_scrollPane);



        _jScrollPane = new JScrollPane(_scrollPane);
        _jScrollPane.setBounds(10,80,340,490);
        //_jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        frame.getContentPane().add(_jScrollPane);

//        _scrollPane.setBounds(10,80,500,500);
//        _scrollPane.setHorizontalScrollBar(new JScrollBar(HORIZONTAL));
//        _scrollPane.setVerticalScrollBar(new JScrollBar(VERTICAL));
//
//        //_scrollPane.setWheelScrollingEnabled(true);
//        _scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
//        _scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
//        frame.getContentPane().add(_scrollPane);

        _countButton = new JButton();
        _countButton.setText("Count");
        _countButton.setBounds(600,5,100,30);

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

        _btnChooseImportingFile = new JButton("Choose CSV file");
        _btnChooseImportingFile.setBounds(380, 5, 200, 30);

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
        //Stringi bedace kolejnymi wejsciami
        String[] inputsString = _inputTextArea.getText().split("\n");

        //Tworzymy siec o ilosci wejsc rownej 2
        NeuralNetwork nn = new NeuralNetwork(inputsString.length);

        CSVReader reader = new CSVReader(new FileReader(_fileChoosenToImport));

        int j = 0;
        for(MyFrame myFrame : _myFrames){
            //twrorzymy tablice neuronow w danej warstwie
            NeuronData[] neuronDatas = new NeuronData[(int) myFrame.getNumberOfNeurons()];

            //liczba neuronow w danej warstwie
            int neuronNumber = (int) myFrame.getNumberOfNeurons();

            System.out.println("j"+ j);
            for( int i = 0 ; i < neuronNumber ; i++){
                String [] nextLine = reader.readNext();

                //czytamy bias
                double bias = Double.parseDouble(nextLine[0]);

                //tablica na wagi dla pojedynczego neuronu
                double[] neuronWages = new double[nextLine.length-1];

                //czytamy wagi
                for (int k = 0 ; k < nextLine.length - 1; k++){
                    neuronWages[k] = Double.parseDouble(nextLine[k+1]);
                }

                //zapisujemy do tablicy neuronow dany neuron
                neuronDatas[i] = new NeuronData(bias , neuronWages);
            }

            //tworzymy warstwe uzywajac funkcji aktywacji i tablicy neuronow
            nn.addLayer(new NetworkLayer(myFrame.getActivationFunctionType(),neuronDatas));
            j++;
        }

        double[] inputs = new double[inputsString.length];
        int i = 0;
        System.out.println("wejscia: ");
        for (String string : inputsString){
            inputs[i] = Double.parseDouble(string);
            System.out.println(inputs[i]);
            i++;
        }

        System.out.println("inputs length: " + inputs.length);

        nn.setInputs(inputs);
        double[] outputs = nn.calculate();


        String output = new String();
        for(Double out : outputs){
            output += out + "\n";
        }

        _outputTextArea.setText(output);

        System.out.println("wyjscie");
        System.out.println(output);

    }

    private void changeLayersCount(){
        if (!_layersCount.getText().equals("")){
            long layersCount = new Long(_layersCount.getText());
            System.out.println(layersCount);

            if (layersCount > _myFrames.size()){
                //_scrollPane.setVisible(false);
                int height = 1 + (_myFrames.size()) * 30;
                MyFrame myFrame;
                for (long i = _myFrames.size() ; i < layersCount ; i++){
                    myFrame = new MyFrame(i,height);
                    myFrame.setBounds(1, height, 320, 30);
                    _scrollPane.add(myFrame);
                    _myFrames.add(myFrame);
                    height += 30;
                }
            } else {
                System.out.println("asd");
                System.out.println(_myFrames.size());
                for (int i = _myFrames.size() - 1; i >= layersCount ; i--){
                    _myFrames.get(i).setVisible(false);
                    _myFrames.remove(i);
                }
            }
            frame.revalidate();

        }
    }
}