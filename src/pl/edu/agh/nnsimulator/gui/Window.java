package pl.edu.agh.nnsimulator.gui;

import au.com.bytecode.opencsv.CSVReader;
import pl.edu.agh.nnsimulator.CPNetwork;
import pl.edu.agh.nnsimulator.BPNetwork;
import pl.edu.agh.nnsimulator.KohonenNetwork;
import pl.edu.agh.nnsimulator.LearningParameters;
import pl.edu.agh.nnsimulator.activationFunctions.ActivationFunctionType;
import pl.edu.agh.nnsimulator.exceptions.TooMuchInputLayersException;
import pl.edu.agh.nnsimulator.layers.InvalidDimensionsException;
import pl.edu.agh.nnsimulator.layers.NetworkLayer;
import pl.edu.agh.nnsimulator.neurons.NeuronData;
import pl.edu.agh.nnsimulator.weightsInitializers.RandomWeightsInitializer;
import pl.edu.agh.nnsimulator.weightsInitializers.WeightsInitializer;
import pl.edu.agh.nnsimulator.weightsInitializers.ZeroWeightsInitializer;

import javax.swing.*;
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
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

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
    private ButtonGroup _randomOrCustomWeights;
    private JPanel _randomOrCustomJPanel;
    private JTextField _minTextField;
    private JTextField _maxTextField;
    private JTextField textField;
    private JTextField _alfa;
    private JTextField _iterationCount;
    private KohonenNetwork _kohonenNetwork;
    private ButtonGroup _activationFunctionType;
    private JTextField _neuronsInKohonenLayer;
    private LinkedList<double[]> _inputs;
    private LinkedList<double[]> _outputs;
    private int _inputNumber;
    private int _outputNumber;
    private WeightsInitializer _weightsInitializer;
    private LearningParameters _learningParameters = new LearningParameters();
    private JPanel _biasOnOff;
    private JTextField _layersCount;
    private Box _scrollPane;
    private JScrollPane  _jScrollPane;


    private ButtonGroup _biasOnOrOff;
    private BPNetwork _bpNetwork; //= new CPNetwork(3,9,1,1,new RandomWeightsInitializer(-0.1,0.1), ActivationFunctionType.PURELIN);
    private JTextField _momentum;


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

        _inputCount = new JTextField();
        _inputCount.setText("2");
        _inputCount.setBounds(96, 12, 32, 19);
        //frame.getContentPane().add(_inputCount);
        _inputCount.setColumns(10);

        JLabel lblLayersCount = new JLabel("Layers Count");
        lblLayersCount.setBounds(10, 130, 114, 30);
        frame.getContentPane().add(lblLayersCount);

        _layersCount = new JTextField();

        _layersCount.setText("2");
        _layersCount.setBounds(90, 135, 32, 19);
        frame.getContentPane().add(_layersCount);
        _layersCount.setColumns(10);




        _biasOnOff= new JPanel();
        _biasOnOff.setBounds(20,200,250,30);
        _biasOnOrOff = new ButtonGroup();

        JLabel label_1 = new JLabel("Bias");
        _biasOnOff.add(label_1);

        JRadioButton jRadioButton3 = new JRadioButton("On", true);
        jRadioButton3.setActionCommand("On");
        _biasOnOff.add(jRadioButton3);
        _biasOnOrOff.add(jRadioButton3);

        JRadioButton jRadioButton4 = new JRadioButton("Off", false);
        jRadioButton4.setActionCommand("Off");
        _biasOnOff.add(jRadioButton4);
        _biasOnOrOff.add(jRadioButton4);



        _biasOnOff.setBounds(0, 70, 180, 30);
        //_biasOnOff.setSize(300,30);

        _biasOnOff.setVisible(true);
        _biasOnOff.revalidate();
        //_biasOnOff.set
        frame.getContentPane().add(_biasOnOff);


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
        _jScrollPane.setBounds(10,160,340,400);
        //_jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        frame.getContentPane().add(_jScrollPane);



        _randomOrCustomJPanel= new JPanel();
        _randomOrCustomJPanel.setBounds(20,200,300,30);
        _randomOrCustomWeights = new ButtonGroup();

        JLabel lblWeights = new JLabel("Weig");
        _randomOrCustomJPanel.add(lblWeights);

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
        _inputTextArea.setSize(148, 228);
        _inputTextArea.setLocation(0, 0);
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
        _countButton.setBounds(650,5,100,30);

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

        _alfa = new JTextField();
        _alfa.setText("2");
        _alfa.setBounds(40, 10, 50, 20);
        frame.getContentPane().add(_alfa);

        JLabel lblIterationCount = new JLabel("Iteration count");
        lblIterationCount.setBounds(160, 40, 114, 30);
        frame.getContentPane().add(lblIterationCount);

        _iterationCount = new JTextField();
        _iterationCount.setText("2");
        _iterationCount.setBounds(250, 45, 50, 20);
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
        JButton btnLearnGrossberg = new JButton();

        btnLearnGrossberg.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    learnBp();
                } catch (InvalidDimensionsException e1) {
                    e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }
        });
        btnLearnGrossberg.setText("Learn");
        btnLearnGrossberg.setBounds(525, 5, 120, 30);
        frame.getContentPane().add(btnLearnGrossberg);
        _activationFunctionType = new ButtonGroup();

        boolean checkActivationFunctionType = true;

        for (ActivationFunctionType aft : ActivationFunctionType.values()){
            if (aft.name().equals("HARDLIM")) continue;
            JRadioButton jRadioButtonInside = new JRadioButton(aft.name(), checkActivationFunctionType);
            jRadioButtonInside.setActionCommand(aft.name());
            //System.out.println(aft.name());
            //_activationPanel.add(jRadioButtonInside);
            _activationFunctionType.add(jRadioButtonInside);
            //add(jRadioButton);
            checkActivationFunctionType = false;
        }

        JLabel lblAlfa_1 = new JLabel("Alfa");
        lblAlfa_1.setBounds(10, 5, 104, 30);
        frame.getContentPane().add(lblAlfa_1);

        JLabel lblMomentum = new JLabel("Momentum");
        lblMomentum.setBounds(10, 40, 114, 30);
        frame.getContentPane().add(lblMomentum);

        _momentum = new JTextField();
        _momentum.setText("2");
        _momentum.setBounds(75, 45, 50, 20);
        frame.getContentPane().add(_momentum);

        JButton rms = new JButton();
        rms.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent arg0) {
                _bpNetwork.printRmsError();
            }
        });
        rms.setText("Print RMS Error");
        rms.setBounds(710, 47, 130, 30);
        frame.getContentPane().add(rms);

        JButton weightPrinter = new JButton();
        weightPrinter.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                _bpNetwork.printWeights();
            }
        });
        weightPrinter.setText("Print weights");
        weightPrinter.setBounds(710, 80, 130, 30);
        frame.getContentPane().add(weightPrinter);

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

    private void learnBp() throws InvalidDimensionsException {
        //To change body of created methods use File | Settings | File Templates.
        try {
            readFiles();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        _learningParameters = new LearningParameters();
        _learningParameters.setAlpha(new Double(_alfa.getText()));
        _learningParameters.setMomentum(new Double(_momentum.getText()));
        _bpNetwork.setLearningParameters(_learningParameters);
        _bpNetwork.setWithBias(_biasOnOrOff.getSelection().getActionCommand().equals("On"));

        _bpNetwork.setLearningMode(true);


        for(int i=0; i < new Integer(_iterationCount.getText());i++){
            int j=0;
            for(double[] input : _inputs){
                _bpNetwork.setInputs(input);
                _bpNetwork.setExpectedOutput(_outputs.get(j));
                _bpNetwork.calculate();
                j++;
            }
        }
    }

    private void learnKohonen() throws IOException, InvalidDimensionsException{
        readFiles();

        if (_bpNetwork == null){
        }

        if (_learningParameters == null){
            _learningParameters = new LearningParameters();
            _bpNetwork.setLearningParameters(_learningParameters);
        }

        //_bpNetwork.setKohonenLearningMode(true);
        //_bpNetwork.setGrossbergLearningMode(false);
        _learningParameters.setAlpha(new Double(_alfa.getText()));
        //_learningParameters.setNeighborhood(new Integer(_neighbourhood.getText()));
        for(int i=0; i < new Integer(_iterationCount.getText());i++){
            for(double[] input : _inputs){
//                System.out.println(input[0]);
//                System.out.println(input[1]);
//                System.out.println(input[2]);
                _bpNetwork.setInputs(input);
                _bpNetwork.calculate();
            }
        }
    }

    private void learnGrossberg() throws IOException, InvalidDimensionsException {
        readFiles();

        _learningParameters = new LearningParameters();
        _learningParameters.setAlpha(new Double(_alfa.getText()));
        _learningParameters.setMomentum(new Double(_momentum.getText()));
        _bpNetwork.setLearningParameters(_learningParameters);
        //_bpNetwork.setWithBias(_biasOnOrOff.isSelected());

        LearningParameters learningParameters = new LearningParameters();
        _bpNetwork.setLearningParameters(learningParameters);

        learningParameters.setAlpha(new Double(_alfa.getText()));
        for(int i=0; i < new Integer(_iterationCount.getText());i++){
            int j=0;
            for(double[] input : _inputs){
                _bpNetwork.setInputs(input);
                _bpNetwork.setExpectedOutput(_outputs.get(j));
                _bpNetwork.calculate();
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

        _bpNetwork = new BPNetwork(_inputNumber);
        if (_randomOrCustomWeights.getSelection().getActionCommand().equals("Random")){
            _weightsInitializer = new RandomWeightsInitializer(new Double(_minTextField.getText()), new Double(_maxTextField.getText()));
        } else {
            _weightsInitializer = new ZeroWeightsInitializer();
        }

        int prev = _inputNumber;
        for(MyFrame myFrame : _myFrames){

            try {
                _bpNetwork.addLayer(new NetworkLayer(myFrame.getActivationFunctionType(),initializeWeights(prev, (int) myFrame.getNumberOfNeurons(), _weightsInitializer)));
            } catch (TooMuchInputLayersException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }

            prev = (int) myFrame.getNumberOfNeurons();

        }


        _learningParameters = new LearningParameters();
        _bpNetwork.setLearningParameters(_learningParameters);
    }

    private static NeuronData[] initializeWeights(int prevLayerNeurons, int neurons, WeightsInitializer weightsInitializer) {
        NeuronData[] neuronsData = new NeuronData[neurons];

        for (int i = 0; i < neurons; i++) {
            neuronsData[i] = new NeuronData(0.0, weightsInitializer.initialize(prevLayerNeurons));
        }

        return neuronsData;

    }


    private void count() throws TooMuchInputLayersException, InvalidDimensionsException, IOException {
        String[] inputsString = _inputTextArea.getText().split("\n");

        double[] inputs = new double[inputsString.length];
        int i = 0;
        System.out.println("wejscia: ");
        for (String string : inputsString){
            inputs[i] = Double.parseDouble(string);
            System.out.println(inputs[i]);
            i++;
        }

        _bpNetwork.setLearningMode(false);
        _bpNetwork.setInputs(inputs);
        double[] outputs = _bpNetwork.calculate();
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
            frame.validate();

        }
    }
}


