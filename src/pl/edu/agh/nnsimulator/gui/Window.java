package pl.edu.agh.nnsimulator.gui;

import au.com.bytecode.opencsv.CSVReader;
import pl.edu.agh.nnsimulator.KohonenNetwork;
import pl.edu.agh.nnsimulator.LearningParameters;
import pl.edu.agh.nnsimulator.exceptions.TooMuchInputLayersException;
import pl.edu.agh.nnsimulator.layers.InvalidDimensionsException;
import pl.edu.agh.nnsimulator.weightsInitializers.RandomWeightsInitializer;
import pl.edu.agh.nnsimulator.weightsInitializers.WeightsInitializer;
import pl.edu.agh.nnsimulator.weightsInitializers.ZeroWeightsInitializer;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.swing.*;

public class Window {

    private JFrame frame;
    private JTextField _inputCount;
    private List<MyFrame> _myFrames = new LinkedList<MyFrame>();
    private JButton _countButton;
    private File _fileChoosenToImport;
    private JButton _btnChooseImportingFile;
    private JTextArea _inputTextArea;
    private JTextArea _outputTextArea;
    private JLabel label;
    private JTextField _columnsTextField;
    private JTextField _rowsTextField;
    private ButtonGroup _randomOrCustomWeights;
    private JPanel _randomOrCustomJPanel;
    private JTextField _minTextField;
    private JTextField _maxTextField;
    private JTextField textField;
    private JTextField _alfa;
    private JTextField _neighbourhood;
    private JTextField _iterationCount;
    private KohonenNetwork _kohonenNetwork;

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

        JLabel lblInputCount = new JLabel("Input count");
        lblInputCount.setBounds(0, 5, 114, 30);
        frame.getContentPane().add(lblInputCount);

        _inputCount = new JTextField();
        _inputCount.setText("2");
        _inputCount.setBounds(96, 12, 32, 19);
        frame.getContentPane().add(_inputCount);
        _inputCount.setColumns(10);

        JLabel lblColumns = new JLabel("Columns");
        lblColumns.setBounds(0, 35, 114, 30);
        frame.getContentPane().add(lblColumns);

        _columnsTextField = new JTextField();
        _columnsTextField.setText("2");
        _columnsTextField.setBounds(76, 42, 32, 19);
        frame.getContentPane().add(_columnsTextField);
        _columnsTextField.setColumns(10);

        JLabel lblRows = new JLabel("Rows");
        lblRows.setBounds(200, 35, 114, 30);
        frame.getContentPane().add(lblRows);

        _rowsTextField = new JTextField();
        _rowsTextField.setText("2");
        _rowsTextField.setBounds(256, 42, 32, 19);
        frame.getContentPane().add(_rowsTextField);
        _rowsTextField.setColumns(10);

        JLabel lblWeights = new JLabel("Weights");
        lblWeights.setBounds(0, 75, 114, 30);
        frame.getContentPane().add(lblWeights);




        _randomOrCustomJPanel= new JPanel();
        _randomOrCustomJPanel.setBounds(0,0,300,30);
        _randomOrCustomWeights = new ButtonGroup();

        JRadioButton jRadioButton = new JRadioButton("Zero", true);
        jRadioButton.setActionCommand("Zero");
        _randomOrCustomJPanel.add(jRadioButton);
        _randomOrCustomWeights.add(jRadioButton);

        JRadioButton jRadioButton2 = new JRadioButton("Random", false);
        jRadioButton2.setActionCommand("Random");
        _randomOrCustomJPanel.add(jRadioButton2);
        _randomOrCustomWeights.add(jRadioButton2);

        _randomOrCustomJPanel.setBounds(0, 100, 180, 30);
        //_randomOrCustomJPanel.setSize(300,30);

        _randomOrCustomJPanel.setVisible(true);
        _randomOrCustomJPanel.revalidate();
        //_randomOrCustomJPanel.set
        frame.getContentPane().add(_randomOrCustomJPanel);



        _minTextField = new JTextField();
        _minTextField.setText("2");
        _minTextField.setBounds(183, 107, 50, 20);
        frame.getContentPane().add(_minTextField);

        _maxTextField = new JTextField();
        _maxTextField.setText("2");
        _maxTextField.setBounds(260, 107, 50, 20);
        frame.getContentPane().add(_maxTextField);

        JLabel _pause = new JLabel("-");
        _pause.setBounds(240, 107, 15, 20);
        frame.getContentPane().add(_pause);





        lblInputCount.setBounds(0, 5, 114, 30);
        frame.getContentPane().add(lblInputCount);

        JLabel lblNeuronsInLayer = new JLabel("Neurons in layer count");
        lblNeuronsInLayer.setBounds(225, 45, 181, 15);
        //frame.getContentPane().add(lblNeuronsInLayer);

        JLabel lblActivationFunction = new JLabel("Activation function");
        lblActivationFunction.setBounds(550, 45, 158, 15);
        //frame.getContentPane().add(lblActivationFunction);

        JLabel lblLayerNumber = new JLabel("<html>Layer<br>number</html>");
        lblLayerNumber.setBounds(12, 45, 102, 30);

        JLabel lblInputs = new JLabel("Inputs");
        lblInputs.setBounds(430, 40, 100, 45);
        frame.getContentPane().add(lblInputs);

        _inputTextArea = new JTextArea();
        frame.getContentPane().add(_inputTextArea);

        JScrollPane jScrollPane = new JScrollPane(_inputTextArea);
        jScrollPane.setBounds(380,80,150,490);
        frame.getContentPane().add(jScrollPane);

        JLabel lblOutputs = new JLabel("Outputs");
        lblOutputs.setBounds(590, 40, 100, 45);
        frame.getContentPane().add(lblOutputs);

        _outputTextArea = new JTextArea();
        frame.getContentPane().add(_outputTextArea);

        JScrollPane jScrollPane2 = new JScrollPane(_outputTextArea);
        jScrollPane2.setBounds(550,80,150,490);
        frame.getContentPane().add(jScrollPane2);

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
        _btnChooseImportingFile.setBounds(256, 5, 200, 30);

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

        frame.getContentPane().add(_btnChooseImportingFile);


        frame.getContentPane().add(_countButton);

        JLabel lblAlfa = new JLabel("Alfa");
        lblAlfa.setBounds(0, 155, 114, 30);
        frame.getContentPane().add(lblAlfa);

        _alfa = new JTextField();
        _alfa.setText("2");
        _alfa.setBounds(50, 160, 50, 20);
        frame.getContentPane().add(_alfa);

        JLabel lblAlfa2 = new JLabel("Neighbourhood");
        lblAlfa2.setBounds(0, 220, 114, 30);
        frame.getContentPane().add(lblAlfa2);

        _neighbourhood = new JTextField();
        _neighbourhood.setText("2");
        _neighbourhood.setBounds(130, 225, 50, 20);
        frame.getContentPane().add(_neighbourhood);

        JLabel lblIterationCount = new JLabel("Iteration count");
        lblIterationCount.setBounds(0, 270, 114, 30);
        frame.getContentPane().add(lblIterationCount);

        _iterationCount = new JTextField();
        _iterationCount.setText("2");
        _iterationCount.setBounds(130, 275, 50, 20);
        frame.getContentPane().add(_iterationCount);

        JButton btnInitialize = new JButton();
        btnInitialize.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                initializeNetwork();

            }


        });
        btnInitialize.setText("Initialize");
        btnInitialize.setBounds(479, 5, 100, 30);
        frame.getContentPane().add(btnInitialize);
    }

    private void initializeNetwork() {
        WeightsInitializer weightsInitializer;

        if (_randomOrCustomWeights.getSelection().getActionCommand().equals("Random")){
            weightsInitializer = new RandomWeightsInitializer(new Double(_minTextField.getText()), new Double(_maxTextField.getText()));
        } else {
            weightsInitializer = new ZeroWeightsInitializer();
        }


        _kohonenNetwork = new KohonenNetwork(new Integer(_inputCount.getText()),new Integer(_rowsTextField.getText()),
                new Integer(_columnsTextField.getText()),weightsInitializer);
    }

    private void count() throws TooMuchInputLayersException, InvalidDimensionsException, IOException {
        LearningParameters learningParameters = new LearningParameters();
        learningParameters.setAlpha(new Double(_alfa.getText()));
        learningParameters.setNeighborhood(new Integer(_neighbourhood.getText()));
        _kohonenNetwork.setLearningParametrs(learningParameters);
        _kohonenNetwork.setLearningMode(true);

        CSVReader reader = new CSVReader(new FileReader(_fileChoosenToImport));



        String [] nextLine = reader.readNext();
        LinkedList<double[]> inputs = new LinkedList<double[]>();
        //double[][] inputs = new double[][];

        while (nextLine != null){
            double[] line = new double[nextLine.length];
            for (int k = 0 ; k < nextLine.length; k++){
                line[k] = Double.parseDouble(nextLine[k]);
            }
            inputs.add(line);
            nextLine = reader.readNext();
        }


        for(int i=0;i< new Integer(_iterationCount.getText());i++){
            for (double[] input: inputs){
                _kohonenNetwork.setInputs(input);
                _kohonenNetwork.calculate();
            }
        }

        System.out.println("After learning:");
        _kohonenNetwork.setLearningMode(false);


        for (double[] input: inputs){
            kohonenTest(_kohonenNetwork, input);
        }
    }


    private static void kohonenTest(KohonenNetwork kohonenNetwork, double[] inputs) throws InvalidDimensionsException {
        StringBuilder sb = new StringBuilder();
        for(double input: inputs){
            sb.append(input).append("  ");
        }
        System.out.println("Testing Kohonen with input " + sb.toString());

        kohonenNetwork.setInputs(inputs);
        double[] outputs = kohonenNetwork.calculate();
        for(double output : outputs){
            System.out.println(output);
        }
    }
}

