package pl.edu.agh.nnsimulator.gui;

import au.com.bytecode.opencsv.CSVReader;
import pl.edu.agh.nnsimulator.CPNetwork;
import pl.edu.agh.nnsimulator.KohonenNetwork;
import pl.edu.agh.nnsimulator.LearningParameters;
import pl.edu.agh.nnsimulator.activationFunctions.ActivationFunctionType;
import pl.edu.agh.nnsimulator.exceptions.TooMuchInputLayersException;
import pl.edu.agh.nnsimulator.layers.InvalidDimensionsException;
import pl.edu.agh.nnsimulator.weightsInitializers.RandomWeightsInitializer;
import pl.edu.agh.nnsimulator.weightsInitializers.WeightsInitializer;
import pl.edu.agh.nnsimulator.weightsInitializers.ZeroWeightsInitializer;

import java.awt.*;
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
    private File _inputFile;
    private File _outputFile;
    private JButton _btnChooseInputFile;
    private JButton _btnChooseOuputFile;
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
    private JPanel _activationPanel;
    private ButtonGroup _activationFunctionType;
    private JTextField _neuronsInKohonenLayer;
    private LinkedList<double[]> _inputs;
    private LinkedList<double[]> _outputs;
    private int _inputNumber;
    private int _outputNumber;
    private WeightsInitializer _weightsInitializer;
    private LearningParameters _learningParameters = new LearningParameters();

    private CPNetwork _cpNetwork; //= new CPNetwork(3,9,1,1,new RandomWeightsInitializer(-0.1,0.1), ActivationFunctionType.PURELIN);


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
        frame.setBounds(100, 100, 900, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel lblInputCount = new JLabel("Neurons in");
        lblInputCount.setBounds(0, 5, 150, 30);
        frame.getContentPane().add(lblInputCount);

        JLabel lblInputCount2 = new JLabel("kohonen layer");
        lblInputCount2.setBounds(0, 17, 150, 30);
        frame.getContentPane().add(lblInputCount2);

        _inputCount = new JTextField();
        _inputCount.setText("2");
        _inputCount.setBounds(96, 12, 32, 19);
        //frame.getContentPane().add(_inputCount);
        _inputCount.setColumns(10);

        JLabel lblColumns = new JLabel("Columns");
        lblColumns.setBounds(0, 35, 114, 30);
        frame.getContentPane().add(lblColumns);

        _columnsTextField = new JTextField();
        _columnsTextField.setText("2");
        _columnsTextField.setBounds(56, 42, 32, 19);
        frame.getContentPane().add(_columnsTextField);
        _columnsTextField.setColumns(10);

        JLabel lblRows = new JLabel("Rows");
        lblRows.setBounds(110, 35, 114, 30);
        frame.getContentPane().add(lblRows);

        _rowsTextField = new JTextField();
        _rowsTextField.setText("2");
        _rowsTextField.setBounds(150, 42, 32, 19);
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

        JLabel lblNeuronsInLayer = new JLabel("Neurons in kohonen layer number");
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
        _countButton.setBounds(750,5,100,30);

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

        _btnChooseInputFile = new JButton("Choose input file");
        _btnChooseInputFile.setBounds(150, 5, 130, 30);

        _btnChooseOuputFile = new JButton("Choose output file");
        _btnChooseOuputFile.setBounds(285, 5, 140, 30);

        _btnChooseOuputFile.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                JFileChooser fc = new JFileChooser(".");
                int returnVal = fc.showOpenDialog(fc);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    _outputFile = fc.getSelectedFile();
                }
                //txtFieldImportingFile.setText(fileChoosenToImport.getName());
            }
        });

        _btnChooseInputFile.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                JFileChooser fc = new JFileChooser(".");
                int returnVal = fc.showOpenDialog(fc);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    _inputFile = fc.getSelectedFile();
                }
                //txtFieldImportingFile.setText(fileChoosenToImport.getName());
            }
        });

        frame.getContentPane().add(_btnChooseInputFile);
        frame.getContentPane().add(_btnChooseOuputFile);

        frame.getContentPane().add(_countButton);

        JLabel lblAlfa = new JLabel("Alfa");
        lblAlfa.setBounds(0, 240, 114, 30);
        frame.getContentPane().add(lblAlfa);

        _alfa = new JTextField();
        _alfa.setText("2");
        _alfa.setBounds(130, 245, 50, 20);
        frame.getContentPane().add(_alfa);

        JLabel lblAlfa2 = new JLabel("Neighbourhood");
        lblAlfa2.setBounds(0, 270, 114, 30);
        frame.getContentPane().add(lblAlfa2);

        _neighbourhood = new JTextField();
        _neighbourhood.setText("2");
        _neighbourhood.setBounds(130, 275, 50, 20);
        frame.getContentPane().add(_neighbourhood);

        JLabel lblIterationCount = new JLabel("Iteration count");
        lblIterationCount.setBounds(0, 300, 114, 30);
        frame.getContentPane().add(lblIterationCount);

        _iterationCount = new JTextField();
        _iterationCount.setText("2");
        _iterationCount.setBounds(130, 305, 50, 20);
        frame.getContentPane().add(_iterationCount);

        JButton btnInitialize = new JButton();
        btnInitialize.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                initializeNetwork();

            }


        });
        btnInitialize.setText("Initialize");
        btnInitialize.setBounds(435, 5, 80, 30);
        frame.getContentPane().add(btnInitialize);

        JButton btnLearnKohonen = new JButton();
        JButton btnLearnGrossberg = new JButton();
        btnLearnKohonen.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    learnKohonen();
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                } catch (InvalidDimensionsException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        });

        btnLearnGrossberg.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    learnGrossberg();
                } catch (IOException e1) {
                    e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                } catch (InvalidDimensionsException e1) {
                    e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }
        });

        btnLearnKohonen.setText("Learn Koh");
        btnLearnKohonen.setBounds(520, 5, 100, 30);
        btnLearnGrossberg.setText("Learn Gross");
        btnLearnGrossberg.setBounds(625, 5, 120, 30);

        frame.getContentPane().add(btnLearnKohonen);
        frame.getContentPane().add(btnLearnGrossberg);

        JLabel lblGrossberg = new JLabel("Grossberg layer activation function type");
        lblGrossberg.setBounds(0, 135, 300, 45);
        frame.getContentPane().add(lblGrossberg);



        _activationPanel = new JPanel();
        _activationPanel.setBounds(0,160,300,30);
        _activationFunctionType = new ButtonGroup();

        boolean checkActivationFunctionType = true;

        for (ActivationFunctionType aft : ActivationFunctionType.values()){
            if (aft.name().equals("HARDLIM")) continue;
            JRadioButton jRadioButtonInside = new JRadioButton(aft.name(), checkActivationFunctionType);
            jRadioButtonInside.setActionCommand(aft.name());
            //System.out.println(aft.name());
            _activationPanel.add(jRadioButtonInside);
            _activationFunctionType.add(jRadioButtonInside);
            //add(jRadioButton);
            checkActivationFunctionType = false;
        }

        _activationPanel.setSize(300,30);

        _activationPanel.setVisible(true);
        _activationPanel.revalidate();
        frame.add(_activationPanel);

        JLabel lblKohonen = new JLabel("Neurons in Kohonen layer count");
        lblKohonen.setBounds(0, 190, 200, 45);
//        frame.getContentPane().add(lblKohonen);

        _neuronsInKohonenLayer = new JTextField();
        _neuronsInKohonenLayer.setText("2");
        _neuronsInKohonenLayer.setBounds(200, 202, 50, 20);
//        frame.getContentPane().add(_neuronsInKohonenLayer);



    }

    private void readFiles() throws IOException {
        CSVReader readerInput = new CSVReader(new FileReader(_inputFile));
        CSVReader readerOutput = new CSVReader(new FileReader(_outputFile));

        String [] nextLine = readerInput.readNext();
        String [] nextLineOutput = readerOutput.readNext();
        _inputs = new LinkedList<double[]>();
        _outputs = new LinkedList<double[]>();


        while (nextLine != null){
            _inputNumber = 0;
            double[] line = new double[nextLine.length];
            for (int k = 0 ; k < nextLine.length; k++){
                line[k] = Double.parseDouble(nextLine[k]);
                _inputNumber++;
            }
            _inputs.add(line);
            nextLine = readerInput.readNext();
        }

        while (nextLineOutput != null){
            _outputNumber = 0;
            double[] line = new double[nextLineOutput.length];
            for (int k = 0 ; k < nextLineOutput.length; k++){
                line[k] = Double.parseDouble(nextLineOutput[k]);
                _outputNumber++;
            }
            _outputs.add(line);
            nextLineOutput = readerOutput.readNext();
        }
    }

    private void learnKohonen() throws IOException, InvalidDimensionsException{
        readFiles();

        if (_cpNetwork == null){
            try {
                _cpNetwork = new CPNetwork(_inputNumber,new Integer(_rowsTextField.getText()), new Integer(_columnsTextField.getText()),
                        _outputNumber, _weightsInitializer, ActivationFunctionType.valueOf(_activationFunctionType.getSelection().getActionCommand()));
            } catch (TooMuchInputLayersException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }

        if (_learningParameters == null){
            _learningParameters = new LearningParameters();
            _cpNetwork.setLearningParameters(_learningParameters);
        }

        _cpNetwork.setKohonenLearningMode(true);
        _cpNetwork.setGrossbergLearningMode(false);
        _learningParameters.setAlpha(new Double(_alfa.getText()));
        _learningParameters.setNeighborhood(new Integer(_neighbourhood.getText()));
        for(int i=0; i < new Integer(_iterationCount.getText());i++){
            for(double[] input : _inputs){
//                System.out.println(input[0]);
//                System.out.println(input[1]);
//                System.out.println(input[2]);
                _cpNetwork.setInputs(input);
                _cpNetwork.calculate();
            }
        }
    }

    private void learnGrossberg() throws IOException, InvalidDimensionsException {
        readFiles();

        if (_cpNetwork == null){
            try {
                _cpNetwork = new CPNetwork(_inputNumber,new Integer(_rowsTextField.getText()), new Integer(_columnsTextField.getText()),
                        _outputNumber, _weightsInitializer, ActivationFunctionType.valueOf(_activationFunctionType.getSelection().getActionCommand()));
            } catch (TooMuchInputLayersException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }

        LearningParameters learningParameters = new LearningParameters();
        _cpNetwork.setLearningParameters(learningParameters);

        _cpNetwork.setKohonenLearningMode(false);
        _cpNetwork.setGrossbergLearningMode(true);
        learningParameters.setAlpha(new Double(_alfa.getText()));
        for(int i=0; i < new Integer(_iterationCount.getText());i++){
            int j=0;
            for(double[] input : _inputs){
                _cpNetwork.setInputs(input);
                _cpNetwork.setExpectedOutput(_outputs.get(j));
                _cpNetwork.calculate();
                j++;
            }
        }
    }

    private void initializeNetwork() {
        try {
            readFiles();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        _learningParameters = new LearningParameters();
        if (_randomOrCustomWeights.getSelection().getActionCommand().equals("Random")){
            _weightsInitializer = new RandomWeightsInitializer(new Double(_minTextField.getText()), new Double(_maxTextField.getText()));
        } else {
            _weightsInitializer = new ZeroWeightsInitializer();
        }


        try {
            _cpNetwork = new CPNetwork(_inputNumber,new Integer(_rowsTextField.getText()), new Integer(_columnsTextField.getText()),
                    _outputNumber, _weightsInitializer, ActivationFunctionType.valueOf(_activationFunctionType.getSelection().getActionCommand()));
        } catch (TooMuchInputLayersException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        _cpNetwork.setLearningParameters(_learningParameters);
    }


    private void count() throws TooMuchInputLayersException, InvalidDimensionsException, IOException {
        String[] inputsString = _inputTextArea.getText().split("\n");

        double[] inputs = new double[inputsString.length];
        int i = 0;
        System.out.println("wejscia: ");
        for (String string : inputsString){
            inputs[i] = Double.parseDouble(string);
            //System.out.println(inputs[i]);
            i++;
        }

        _cpNetwork.setKohonenLearningMode(false);
        _cpNetwork.setGrossbergLearningMode(false);
        _cpNetwork.setInputs(inputs);
        double[] outputs = _cpNetwork.calculate();
        for(double output : outputs){
            System.out.println(output);
        }

        String output = new String();
        for(Double out : outputs){
            output += out + "\n";
        }

        _outputTextArea.setText(output);
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


