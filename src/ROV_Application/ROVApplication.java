package ROV_Application;

import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import gnu.io.UnsupportedCommOperationException;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import static java.lang.Thread.sleep;
import static java.time.Clock.system;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.TooManyListenersException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.VideoWriter;
import net.java.games.input.*;

public class ROVApplication extends javax.swing.JFrame {

    static private VideoCapture Camera;
    static private Mat Frame;
    static private MatOfByte Mem;
    static private VideoWriter VideoWriter;
    static private int PhotosCounter;
    static private int VideosCounter;
    static private StreamThread VideoStreamThread;
    static private StreamThread SaveThread;
    static private StreamThread RecordThread;
    static private StreamThread JoyStickThread;
    static boolean Rec = false;
    static private HashMap<String, CommPortIdentifier> Ports;
    static private SerialPort SerialPort;
    static private ArduinoSerial SerialListner;
    static private BufferedReader Input;
    static private OutputStream Output;
    static private final int TimeOut = 2000;
    static private final int BaudRate = 9600;
    static private Key_Listner KeyListner;
    static private boolean Cursor;
    static private Controller JoyStick;
    static private ArrayList<Boolean> ButtonsValues;
    static private byte RJoyStickState;
    static private byte LJoyStickState;

    public ROVApplication() throws IOException {
        initComponents();
        FrameDesign();
        Initialize();
    }

    public void FrameDesign() throws IOException {
        setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("res/Background1.jpg")))));
        setIconImage(ImageIO.read(new File("res/ROV.png")));
        setTitle("Remotely Operated Vehicle Application");
        add(jPanel1);
        add(Save);
        add(Start);
        add(Pause);
        add(Record);
        add(Stop);
        add(jLabel1);
        add(jLabel2);
        add(PortsList);
        add(Refresh);
        add(ConnectionButton);
        add(Up);
        add(Down);
        add(Forward);
        add(Backward);
        add(Right);
        add(Left);
        add(Neutral);
        add(Torch);
        add(Exit);
    }

    public void Initialize() throws IOException {
        KeyListner = new Key_Listner();
        PhotosCounter = 1;
        VideosCounter = 1;
        Cursor = false;
        InitializePorts();
//        InitializeJoyStick();
        Pause.setEnabled(false);
        Save.setEnabled(false);
        Stop.setEnabled(false);
        Record.setEnabled(false);
        Up.setEnabled(false);
        Down.setEnabled(false);
        Forward.setEnabled(false);
        Backward.setEnabled(false);
        Right.setEnabled(false);
        Left.setEnabled(false);
        Torch.setEnabled(false);
        Neutral.setEnabled(false);
        setFocusable(true);
        addKeyListener(KeyListner);
    }

    private void InitializeJoyStick() {
        Controller[] controllers = ControllerEnvironment.getDefaultEnvironment().getControllers();
        for (int i = 0; i < controllers.length; i++) {
            if (controllers[i].getType() == Controller.Type.STICK) {
                JoyStick = controllers[i];
                //break;
            }
        }
        ButtonsValues = new ArrayList<>();
        RJoyStickState = (byte) '.';
        LJoyStickState = (byte) ',';
    }

    public void InitializePorts() {
        PortsList.removeAllItems();
        Ports = new HashMap<>();
        Enumeration PortsEnum = CommPortIdentifier.getPortIdentifiers();
        while (PortsEnum.hasMoreElements()) {
            CommPortIdentifier PortID = (CommPortIdentifier) PortsEnum.nextElement();
            PortsList.addItem(PortID.getName());
            Ports.put(PortID.getName(), PortID);
        }
        if (Ports.size() > 0) {
            ConnectionButton.setEnabled(true);
        } else {
            ConnectionButton.setEnabled(false);
        }
    }

    public void ArduinoStartCommunication() throws PortInUseException, UnsupportedCommOperationException, IOException, TooManyListenersException {
        CommPortIdentifier portId = Ports.get(PortsList.getSelectedItem());
        SerialPort = (SerialPort) portId.open(this.getClass().getName(), TimeOut);
        SerialPort.setSerialPortParams(BaudRate, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
        Input = new BufferedReader(new InputStreamReader(SerialPort.getInputStream()));
        Output = SerialPort.getOutputStream();
        SerialListner = new ArduinoSerial();
        SerialPort.notifyOnDataAvailable(true);
        SerialPort.addEventListener(SerialListner);
    }

    public void ArduinoCloseCommunication() {
        SerialPort.removeEventListener();
        SerialPort.close();
    }

    private void Control(byte Byte) throws IOException {
        if (Byte == 'N' || Byte == 'O') {
            Output.write(Byte);
            System.out.println(Byte);
        } else if (Byte != RJoyStickState && Byte != LJoyStickState) {
            Output.write(Byte);
            System.out.println(Byte);
            if (Byte == 'U' || Byte == 'D') {
                RJoyStickState = Byte;
            } else {
                LJoyStickState = Byte;
            }
        }
    }

    private void CursorChange() {
        if (Cursor) {
            setCursor(DEFAULT_CURSOR);
            Cursor = false;
        } else {
            setCursor(12);
            Cursor = true;
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Down = new javax.swing.JButton();
        Exit = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        Start = new javax.swing.JButton();
        Backward = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        Record = new javax.swing.JButton();
        Refresh = new javax.swing.JButton();
        Up = new javax.swing.JButton();
        Save = new javax.swing.JButton();
        Pause = new javax.swing.JButton();
        Right = new javax.swing.JButton();
        PortsList = new javax.swing.JComboBox<>();
        Left = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        Stop = new javax.swing.JButton();
        ConnectionButton = new javax.swing.JButton();
        Forward = new javax.swing.JButton();
        Torch = new javax.swing.JButton();
        Neutral = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setIconImages(null);
        setType(java.awt.Window.Type.POPUP);

        Down.setBackground(java.awt.SystemColor.activeCaption);
        Down.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        Down.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ROV_Application/Down.png"))); // NOI18N
        Down.setText("Down");
        Down.setAlignmentX(0.5F);
        Down.setAutoscrolls(true);
        Down.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                DownMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                DownMouseExited(evt);
            }
        });
        Down.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DownActionPerformed(evt);
            }
        });

        Exit.setBackground(java.awt.SystemColor.activeCaption);
        Exit.setForeground(new java.awt.Color(255, 255, 255));
        Exit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ROV_Application/Exit.png"))); // NOI18N
        Exit.setAlignmentX(0.5F);
        Exit.setAutoscrolls(true);
        Exit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                ExitMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                ExitMouseExited(evt);
            }
        });
        Exit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ExitActionPerformed(evt);
            }
        });

        jPanel1.setBackground(java.awt.SystemColor.activeCaption);
        jPanel1.setForeground(new java.awt.Color(255, 255, 255));
        jPanel1.setAutoscrolls(true);
        jPanel1.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jPanel1.setPreferredSize(new java.awt.Dimension(640, 480));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 640, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 480, Short.MAX_VALUE)
        );

        Start.setBackground(java.awt.SystemColor.activeCaption);
        Start.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        Start.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ROV_Application/Play.png"))); // NOI18N
        Start.setText("Start");
        Start.setToolTipText("");
        Start.setAlignmentX(0.5F);
        Start.setAutoscrolls(true);
        Start.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                StartMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                StartMouseExited(evt);
            }
        });
        Start.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                StartActionPerformed(evt);
            }
        });

        Backward.setBackground(java.awt.SystemColor.activeCaption);
        Backward.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        Backward.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ROV_Application/Backward.png"))); // NOI18N
        Backward.setText("Backward");
        Backward.setAlignmentX(0.5F);
        Backward.setAutoscrolls(true);
        Backward.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                BackwardMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                BackwardMouseExited(evt);
            }
        });
        Backward.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BackwardActionPerformed(evt);
            }
        });

        jLabel1.setBackground(java.awt.SystemColor.activeCaption);
        jLabel1.setFont(new java.awt.Font("Script MT Bold", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ROV_Application/Control.png"))); // NOI18N
        jLabel1.setText("ROV Control");
        jLabel1.setAlignmentX(0.5F);
        jLabel1.setAutoscrolls(true);

        Record.setBackground(java.awt.SystemColor.activeCaption);
        Record.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        Record.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ROV_Application/Record.png"))); // NOI18N
        Record.setText("Record");
        Record.setAlignmentX(0.5F);
        Record.setAutoscrolls(true);
        Record.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                RecordMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                RecordMouseExited(evt);
            }
        });
        Record.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RecordActionPerformed(evt);
            }
        });

        Refresh.setBackground(java.awt.SystemColor.activeCaption);
        Refresh.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        Refresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ROV_Application/refresh.png"))); // NOI18N
        Refresh.setText("Refresh");
        Refresh.setAlignmentX(0.5F);
        Refresh.setAutoscrolls(true);
        Refresh.setFocusable(false);
        Refresh.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Refresh.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        Refresh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                RefreshMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                RefreshMouseExited(evt);
            }
        });
        Refresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RefreshActionPerformed(evt);
            }
        });

        Up.setBackground(java.awt.SystemColor.activeCaption);
        Up.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        Up.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ROV_Application/Up.png"))); // NOI18N
        Up.setText("Up");
        Up.setAlignmentX(0.5F);
        Up.setAutoscrolls(true);
        Up.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                UpMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                UpMouseExited(evt);
            }
        });
        Up.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UpActionPerformed(evt);
            }
        });

        Save.setBackground(java.awt.SystemColor.activeCaption);
        Save.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        Save.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ROV_Application/Save.png"))); // NOI18N
        Save.setText("Save");
        Save.setAlignmentX(0.5F);
        Save.setAutoscrolls(true);
        Save.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                SaveMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                SaveMouseExited(evt);
            }
        });
        Save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SaveActionPerformed(evt);
            }
        });

        Pause.setBackground(java.awt.SystemColor.activeCaption);
        Pause.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        Pause.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ROV_Application/Pause.png"))); // NOI18N
        Pause.setText("Pause");
        Pause.setAlignmentX(0.5F);
        Pause.setAutoscrolls(true);
        Pause.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                PauseMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                PauseMouseExited(evt);
            }
        });
        Pause.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PauseActionPerformed(evt);
            }
        });

        Right.setBackground(java.awt.SystemColor.activeCaption);
        Right.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        Right.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ROV_Application/Right.png"))); // NOI18N
        Right.setText("Right");
        Right.setAlignmentX(0.5F);
        Right.setAutoscrolls(true);
        Right.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                RightMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                RightMouseExited(evt);
            }
        });
        Right.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RightActionPerformed(evt);
            }
        });

        PortsList.setBackground(java.awt.SystemColor.activeCaption);
        PortsList.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        PortsList.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        PortsList.setAutoscrolls(true);
        PortsList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                PortsListMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                PortsListMouseExited(evt);
            }
        });
        PortsList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PortsListActionPerformed(evt);
            }
        });

        Left.setBackground(java.awt.SystemColor.activeCaption);
        Left.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        Left.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ROV_Application/Left.png"))); // NOI18N
        Left.setText("Left");
        Left.setAlignmentX(0.5F);
        Left.setAutoscrolls(true);
        Left.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                LeftMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                LeftMouseExited(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                LeftMouseReleased(evt);
            }
        });
        Left.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LeftActionPerformed(evt);
            }
        });

        jLabel2.setBackground(java.awt.SystemColor.activeCaption);
        jLabel2.setFont(new java.awt.Font("Script MT Bold", 1, 36)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ROV_Application/Camera.png"))); // NOI18N
        jLabel2.setText("ROV Camera");
        jLabel2.setAlignmentX(0.5F);
        jLabel2.setAutoscrolls(true);

        Stop.setBackground(java.awt.SystemColor.activeCaption);
        Stop.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        Stop.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ROV_Application/Stop.png"))); // NOI18N
        Stop.setText("Stop");
        Stop.setAlignmentX(0.5F);
        Stop.setAutoscrolls(true);
        Stop.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                StopMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                StopMouseExited(evt);
            }
        });
        Stop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                StopActionPerformed(evt);
            }
        });

        ConnectionButton.setBackground(java.awt.SystemColor.activeCaption);
        ConnectionButton.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        ConnectionButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ROV_Application/Usb.png"))); // NOI18N
        ConnectionButton.setText("Connect");
        ConnectionButton.setAlignmentX(0.5F);
        ConnectionButton.setAutoscrolls(true);
        ConnectionButton.setFocusable(false);
        ConnectionButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ConnectionButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        ConnectionButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                ConnectionButtonMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                ConnectionButtonMouseExited(evt);
            }
        });
        ConnectionButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ConnectionButtonActionPerformed(evt);
            }
        });

        Forward.setBackground(java.awt.SystemColor.activeCaption);
        Forward.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        Forward.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ROV_Application/Forward.png"))); // NOI18N
        Forward.setText("Forward");
        Forward.setAlignmentX(0.5F);
        Forward.setAutoscrolls(true);
        Forward.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                ForwardMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                ForwardMouseExited(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                ForwardMouseReleased(evt);
            }
        });
        Forward.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ForwardActionPerformed(evt);
            }
        });

        Torch.setBackground(java.awt.SystemColor.activeCaption);
        Torch.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        Torch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ROV_Application/Torch.png"))); // NOI18N
        Torch.setText("On");
        Torch.setAlignmentX(0.5F);
        Torch.setAutoscrolls(true);
        Torch.setFocusable(false);
        Torch.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Torch.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        Torch.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                TorchMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                TorchMouseExited(evt);
            }
        });
        Torch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TorchActionPerformed(evt);
            }
        });

        Neutral.setBackground(java.awt.SystemColor.activeCaption);
        Neutral.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        Neutral.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ROV_Application/N.png"))); // NOI18N
        Neutral.setText("Neutral");
        Neutral.setAlignmentX(0.5F);
        Neutral.setAutoscrolls(true);
        Neutral.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                NeutralMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                NeutralMouseExited(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                NeutralMouseReleased(evt);
            }
        });
        Neutral.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NeutralActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(176, 176, 176)
                        .addComponent(jLabel2))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(Start)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(Pause)
                        .addGap(18, 18, 18)
                        .addComponent(Save)
                        .addGap(18, 18, 18)
                        .addComponent(Record)
                        .addGap(18, 18, 18)
                        .addComponent(Stop)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(264, 264, 264)
                        .addComponent(Backward, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(87, 87, 87)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jLabel1)
                                .addGap(144, 144, 144)
                                .addComponent(Exit))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addComponent(Torch))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(Down, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(Up, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(Left, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(Neutral, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(Right, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(101, 101, 101))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(194, 194, 194)
                                .addComponent(PortsList, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(Refresh)
                                .addGap(31, 31, 31)
                                .addComponent(ConnectionButton))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(263, 263, 263)
                                .addComponent(Forward, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {Pause, Record, Save, Start, Stop});

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel1, jLabel2});

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {Backward, Forward, Left, Neutral, Right});

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {ConnectionButton, PortsList, Refresh, Torch});

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {Down, Up});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1))
                        .addGap(18, 18, 18)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Stop)
                            .addComponent(Record)
                            .addComponent(Save, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Pause)
                            .addComponent(Start, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(101, 101, 101)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(PortsList, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(Refresh)
                                    .addComponent(ConnectionButton, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(15, 15, 15)
                                .addComponent(Torch, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(4, 4, 4)
                                .addComponent(Forward, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(Left, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(Right, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(Neutral, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(Backward))
                            .addComponent(Exit))
                        .addGap(61, 61, 61)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Down)
                            .addComponent(Up))))
                .addContainerGap(125, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {Pause, Record, Save, Start, Stop});

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {Backward, Forward, Left, Neutral, Right});

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {Down, Up});

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {ConnectionButton, PortsList, Refresh, Torch});

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel1, jLabel2});

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void SaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SaveActionPerformed
        if (!Start.isEnabled() && Save.isEnabled()) {
            SaveThread = new StreamThread(2);
            if (VideoStreamThread.isAlive()) {
                SaveThread.start();
            }
        }
        requestFocus();
    }//GEN-LAST:event_SaveActionPerformed

    private void StartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_StartActionPerformed
        if (Start.isEnabled()) {
            Start.setEnabled(false);
            Pause.setEnabled(true);
            Save.setEnabled(true);
            Record.setEnabled(true);
            VideoStreamThread = new StreamThread(1);
            VideoStreamThread.start();
        }
        requestFocus();
    }//GEN-LAST:event_StartActionPerformed

    private void PauseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PauseActionPerformed
        if (!Start.isEnabled() && Pause.isEnabled()) {
            VideoStreamThread.PauseVideoStream();
            Pause.setEnabled(false);
            Start.setEnabled(true);
            Save.setEnabled(false);
            Record.setEnabled(false);
        }
        requestFocus();
    }//GEN-LAST:event_PauseActionPerformed

    private void UpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UpActionPerformed
        try {
            Control((byte) 'U');
        } catch (IOException ex) {
            Logger.getLogger(ROVApplication.class.getName()).log(Level.SEVERE, null, ex);
        }
        requestFocus();
    }//GEN-LAST:event_UpActionPerformed

    private void BackwardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BackwardActionPerformed
        try {
            Control((byte) 'B');
        } catch (IOException ex) {
            Logger.getLogger(ROVApplication.class.getName()).log(Level.SEVERE, null, ex);
        }
        requestFocus();
    }//GEN-LAST:event_BackwardActionPerformed

    private void LeftActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LeftActionPerformed
        try {
            Control((byte) 'L');
        } catch (IOException ex) {
            Logger.getLogger(ROVApplication.class.getName()).log(Level.SEVERE, null, ex);
        }
        requestFocus();
    }//GEN-LAST:event_LeftActionPerformed

    private void RightActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RightActionPerformed
        try {
            Control((byte) 'R');
        } catch (IOException ex) {
            Logger.getLogger(ROVApplication.class.getName()).log(Level.SEVERE, null, ex);
        }
        requestFocus();
    }//GEN-LAST:event_RightActionPerformed

    private void DownActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DownActionPerformed
        try {
            Control((byte) 'D');
        } catch (IOException ex) {
            Logger.getLogger(ROVApplication.class.getName()).log(Level.SEVERE, null, ex);
        }
        requestFocus();
    }//GEN-LAST:event_DownActionPerformed

    private void StopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_StopActionPerformed
        if (!Start.isEnabled() && !Record.isEnabled() && Stop.isEnabled()) {
            Pause.setEnabled(true);
            Stop.setEnabled(false);
            Record.setEnabled(true);
            Rec = false;
        }
        requestFocus();
    }//GEN-LAST:event_StopActionPerformed

    private void RecordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RecordActionPerformed
        if (!Start.isEnabled() && Record.isEnabled()) {
            Pause.setEnabled(false);
            Stop.setEnabled(true);
            Record.setEnabled(false);
            Rec = true;
            RecordThread = new StreamThread(3);
            if (VideoStreamThread.isAlive()) {
                RecordThread.start();
            }
        }
        requestFocus();
    }//GEN-LAST:event_RecordActionPerformed

    private void ExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ExitActionPerformed
        if (Camera != null) {
            Camera.release();
        }
        if (VideoWriter != null) {
            VideoWriter.release();
        }
        if (!PortsList.isEnabled()) {
            ArduinoCloseCommunication();
        }
        System.exit(0);
        requestFocus();
    }//GEN-LAST:event_ExitActionPerformed

    private void ConnectionButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ConnectionButtonActionPerformed
        if (ConnectionButton.getText().equalsIgnoreCase("Connect")) {
            try {
                ArduinoStartCommunication();
                JoyStickStart();
                ConnectionButton.setText("DisConnect");
                Up.setEnabled(true);
                Down.setEnabled(true);
                Forward.setEnabled(true);
                Backward.setEnabled(true);
                Right.setEnabled(true);
                Left.setEnabled(true);
                Torch.setEnabled(true);
                Neutral.setEnabled(true);
                PortsList.setEnabled(false);
                Refresh.setEnabled(false);
            } catch (PortInUseException | UnsupportedCommOperationException | IOException | TooManyListenersException ex) {
                Logger.getLogger(ROVApplication.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            ArduinoCloseCommunication();
            ConnectionButton.setText("Connect");
            Up.setEnabled(false);
            Down.setEnabled(false);
            Forward.setEnabled(false);
            Backward.setEnabled(false);
            Right.setEnabled(false);
            Left.setEnabled(false);
            Torch.setEnabled(false);
            Neutral.setEnabled(false);
            PortsList.setEnabled(true);
            Refresh.setEnabled(true);
        }
        requestFocus();
    }//GEN-LAST:event_ConnectionButtonActionPerformed

    private void RefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RefreshActionPerformed
        InitializePorts();
        InitializeJoyStick();
        requestFocus();
    }//GEN-LAST:event_RefreshActionPerformed

    private void ForwardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ForwardActionPerformed
        try {
            Control((byte) 'F');
        } catch (IOException ex) {
            Logger.getLogger(ROVApplication.class.getName()).log(Level.SEVERE, null, ex);
        }
        requestFocus();
    }//GEN-LAST:event_ForwardActionPerformed

    private void ForwardMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ForwardMouseEntered
        CursorChange();
    }//GEN-LAST:event_ForwardMouseEntered

    private void ForwardMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ForwardMouseReleased
    }//GEN-LAST:event_ForwardMouseReleased

    private void LeftMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LeftMouseEntered
        CursorChange();
    }//GEN-LAST:event_LeftMouseEntered

    private void LeftMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LeftMouseReleased

    }//GEN-LAST:event_LeftMouseReleased

    private void ForwardMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ForwardMouseExited
        CursorChange();
    }//GEN-LAST:event_ForwardMouseExited

    private void LeftMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LeftMouseExited
        CursorChange();
    }//GEN-LAST:event_LeftMouseExited

    private void RightMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RightMouseEntered
        CursorChange();
    }//GEN-LAST:event_RightMouseEntered

    private void RightMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RightMouseExited
        CursorChange();
    }//GEN-LAST:event_RightMouseExited

    private void BackwardMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BackwardMouseEntered
        CursorChange();
    }//GEN-LAST:event_BackwardMouseEntered

    private void BackwardMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BackwardMouseExited
        CursorChange();
    }//GEN-LAST:event_BackwardMouseExited

    private void ExitMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ExitMouseEntered
        CursorChange();
    }//GEN-LAST:event_ExitMouseEntered

    private void ExitMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ExitMouseExited
        CursorChange();
    }//GEN-LAST:event_ExitMouseExited

    private void ConnectionButtonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ConnectionButtonMouseEntered
        CursorChange();
    }//GEN-LAST:event_ConnectionButtonMouseEntered

    private void ConnectionButtonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ConnectionButtonMouseExited
        CursorChange();
    }//GEN-LAST:event_ConnectionButtonMouseExited

    private void PortsListMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PortsListMouseEntered
        CursorChange();
    }//GEN-LAST:event_PortsListMouseEntered

    private void PortsListMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PortsListMouseExited
        CursorChange();
    }//GEN-LAST:event_PortsListMouseExited

    private void RefreshMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RefreshMouseEntered
        CursorChange();
    }//GEN-LAST:event_RefreshMouseEntered

    private void RefreshMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RefreshMouseExited
        CursorChange();
    }//GEN-LAST:event_RefreshMouseExited

    private void UpMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_UpMouseEntered
        CursorChange();
    }//GEN-LAST:event_UpMouseEntered

    private void UpMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_UpMouseExited
        CursorChange();
    }//GEN-LAST:event_UpMouseExited

    private void DownMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DownMouseEntered
        CursorChange();
    }//GEN-LAST:event_DownMouseEntered

    private void DownMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DownMouseExited
        CursorChange();
    }//GEN-LAST:event_DownMouseExited

    private void StopMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_StopMouseEntered
        CursorChange();
    }//GEN-LAST:event_StopMouseEntered

    private void StopMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_StopMouseExited
        CursorChange();
    }//GEN-LAST:event_StopMouseExited

    private void RecordMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RecordMouseEntered
        CursorChange();
    }//GEN-LAST:event_RecordMouseEntered

    private void RecordMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RecordMouseExited
        CursorChange();
    }//GEN-LAST:event_RecordMouseExited

    private void SaveMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SaveMouseEntered
        CursorChange();
    }//GEN-LAST:event_SaveMouseEntered

    private void SaveMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SaveMouseExited
        CursorChange();
    }//GEN-LAST:event_SaveMouseExited

    private void PauseMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PauseMouseEntered
        CursorChange();
    }//GEN-LAST:event_PauseMouseEntered

    private void PauseMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PauseMouseExited
        CursorChange();
    }//GEN-LAST:event_PauseMouseExited

    private void StartMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_StartMouseEntered
        CursorChange();
    }//GEN-LAST:event_StartMouseEntered

    private void StartMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_StartMouseExited
        CursorChange();
    }//GEN-LAST:event_StartMouseExited

    private void PortsListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PortsListActionPerformed
        requestFocus();
    }//GEN-LAST:event_PortsListActionPerformed

    private void TorchMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TorchMouseEntered
        CursorChange();
    }//GEN-LAST:event_TorchMouseEntered

    private void TorchMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TorchMouseExited
        CursorChange();
    }//GEN-LAST:event_TorchMouseExited

    private void TorchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TorchActionPerformed
        if (Torch.getText().equals("On")) {
            try {
                Control((byte) 'N');
            } catch (IOException ex) {
                Logger.getLogger(ROVApplication.class.getName()).log(Level.SEVERE, null, ex);
            }
            Torch.setText("Off");
        } else {
            try {
                Control((byte) 'O');
            } catch (IOException ex) {
                Logger.getLogger(ROVApplication.class.getName()).log(Level.SEVERE, null, ex);
            }
            Torch.setText("On");
        }
        requestFocus();
    }//GEN-LAST:event_TorchActionPerformed

    private void NeutralMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_NeutralMouseEntered
        CursorChange();
    }//GEN-LAST:event_NeutralMouseEntered

    private void NeutralMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_NeutralMouseExited
        CursorChange();
    }//GEN-LAST:event_NeutralMouseExited

    private void NeutralMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_NeutralMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_NeutralMouseReleased

    private void NeutralActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NeutralActionPerformed
        Neutral();
    }//GEN-LAST:event_NeutralActionPerformed

    public void Neutral() {
        try {
            Control((byte) 'M');
        } catch (IOException ex) {
            Logger.getLogger(ROVApplication.class.getName()).log(Level.SEVERE, null, ex);
        }
        requestFocus();
    }

    public void NeutralUD() {
        try {
            Control((byte) '.');
        } catch (IOException ex) {
            Logger.getLogger(ROVApplication.class.getName()).log(Level.SEVERE, null, ex);
        }
        requestFocus();
    }

    public void NeutralFB() {
        try {
            Control((byte) ',');
        } catch (IOException ex) {
            Logger.getLogger(ROVApplication.class.getName()).log(Level.SEVERE, null, ex);
        }
        requestFocus();
    }

    public static void main(String args[]) throws IOException {
        ROVApplication R = new ROVApplication();
        R.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        R.setSize(1360, 768);
        R.setExtendedState(JFrame.MAXIMIZED_BOTH);
        R.setVisible(true);
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Backward;
    private javax.swing.JButton ConnectionButton;
    private javax.swing.JButton Down;
    private javax.swing.JButton Exit;
    private javax.swing.JButton Forward;
    private javax.swing.JButton Left;
    private javax.swing.JButton Neutral;
    private javax.swing.JButton Pause;
    private javax.swing.JComboBox<String> PortsList;
    private javax.swing.JButton Record;
    private javax.swing.JButton Refresh;
    private javax.swing.JButton Right;
    private javax.swing.JButton Save;
    private javax.swing.JButton Start;
    private javax.swing.JButton Stop;
    private javax.swing.JButton Torch;
    private javax.swing.JButton Up;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables

//*************************************CameraThread************************************//
    public class StreamThread extends Thread {

        private int Type;

        public StreamThread(int Type) {
            this.Type = Type;
        }

        @Override
        public void run() {
            switch (this.Type) {
                case 1:
                    this.StartVideoStream();
                    break;
                case 2:
                    this.Save();
                    break;
                case 3: {
                    try {
                        this.RecordVideo();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(ROVApplication.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                break;
                case 4: {
                    this.CheckJoystick();
                }
                default:
                    break;
            }
        }

        public void StartVideoStream() {
            Camera = new VideoCapture(0);
            Frame = new Mat();
            Mem = new MatOfByte();
            while (Camera.retrieve(Frame)) {
                Imgcodecs.imencode(".bmp", Frame, Mem);
                try {
                    BufferedImage im = ImageIO.read(new ByteArrayInputStream(Mem.toArray()));
                    Graphics g = jPanel1.getGraphics();
                    g.drawImage(im, 0, 0, null);
                } catch (IOException ex) {
                    Logger.getLogger(ROVApplication.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        public void PauseVideoStream() {
            Camera.release();
        }

        public void Save() {
            Imgcodecs.imwrite("Documents/Photos/" + (PhotosCounter++) + ".jpg", Frame);
        }

        public void RecordVideo() throws InterruptedException {
            VideoWriter = new VideoWriter();
            VideoWriter.open(("Documents/Videos/" + (VideosCounter++) + ".avi"), VideoWriter.fourcc('M', 'J', 'P', 'G'), 10, new Size(640, 480), true);
            while (Rec) {
                VideoWriter.write(Frame);
                sleep(80);
            }
            VideoWriter.release();
        }

        //**************************************Joystick****************************************//
        public void CheckJoystick() {
            if (!isControllerConnected()) {
                return;
            }
            while (pollController()) {
                for (int i = 0; i < ButtonsValues.size(); i++) {
                    if (ButtonsValues.get(i)) {
                        switch (i) {
                            case 0:
                                UpActionPerformed(null);
                                break;
                            case 2:
                                DownActionPerformed(null);
                                break;
                            case 6:
                                TorchActionPerformed(null);
                                break;
                            default:
                                break;
                        }
                    }
                }
                //***********************************LeftJoyStick*****************************************//
                int XL = getX_LeftJoystick_Percentage();
                int YL = getY_LeftJoystick_Percentage();
                if (XL == 100 && YL == 49) {
                    RightActionPerformed(null);
                } else if (XL == 0 && YL == 49) {
                    LeftActionPerformed(null);
                } else if (XL == 49 && YL == 0) {
                    ForwardActionPerformed(null);
                } else if (XL == 49 && YL == 100) {
                    BackwardActionPerformed(null);
                } else if (XL == 100 && YL == 0) {
                    //System.out.println("Forward&Right");
                } else if (XL == 0 && YL == 0) {
                    //System.out.println("Forward&Left");
                } else if (XL == 0 && YL == 100) {
                    //System.out.println("Backward&Left");
                } else if (XL == 100 && YL == 100) {
                    //System.out.println("Backward&Right");
                }
                //***********************************RightJoyStick*****************************************//
                int XR = getZRotationPercentage();
                int YR = getZAxisPercentage();
                if (XR == 100 && YR == 49) {
                    //System.out.println("R-Right");
                } else if (XR == 0 && YR == 49) {
                    //System.out.println("R-Left");
                } else if (XR == 49 && YR == 0) {
                    UpActionPerformed(null);
                } else if (XR == 49 && YR == 100) {
                    DownActionPerformed(null);
                } else if (XR == 100 && YR == 0) {
                    //System.out.println("R-Forward&Right");
                } else if (XR == 0 && YR == 0) {
                    //System.out.println("R-Forward&Left");
                } else if (XR == 0 && YR == 100) {
                    //System.out.println("R-Backward&Left");
                } else if (XR == 100 && YR == 100) {
                    //System.out.println("R-Backward&Right");
                }
                //********************************************************************************************//
                float hatSwitchPosition = getHatSwitchPosition();
                if (hatSwitchPosition == 0.25) {
                    ForwardActionPerformed(null);
                } else if (hatSwitchPosition == 0.5) {
                    RightActionPerformed(null);
                } else if (hatSwitchPosition == 0.75) {
                    BackwardActionPerformed(null);
                } else if (hatSwitchPosition == 1.0) {
                    LeftActionPerformed(null);
                }
                if (XR == 49 && YR == 49 && hatSwitchPosition == 0.0 && XL == 49 && YL == 49) {
                    Neutral();
                } else if (XR == 49 && YR == 49) {
                    NeutralUD();
                } else if (hatSwitchPosition == 0.0 && XL == 49 && YL == 49) {
                    NeutralFB();
                }
            }
        }
    }

//********************************************************************************************//
    public void JoyStickStart() {
        JoyStickThread = new StreamThread(4);
        if (isControllerConnected() && ConnectionButton.getText().equalsIgnoreCase("Connect")) {
            JoyStickThread.start();
        }
    }

    public void JoyStickStop() {
        if (JoyStickThread != null && JoyStickThread.isAlive()) {
            JoyStickThread.stop();
            JoyStickThread = null;
        }
    }

    public boolean isControllerConnected() {
        try {
            return JoyStick.poll();
        } catch (Exception e) {
            return false;
        }
    }

    public float getComponentValue(Component.Identifier identifier) {
        return JoyStick.getComponent(identifier).getPollData();
    }

    public boolean pollController() {
        boolean isControllerValid;
        ButtonsValues.clear();
        isControllerValid = JoyStick.poll();
        if (!isControllerValid) {
            return false;
        }
        Component[] components = JoyStick.getComponents();
        for (int i = 0; i < components.length; i++) {
            Component component = components[i];
            if (component.getName().contains("Button")) {
                if (component.getPollData() == 1.0f) {
                    ButtonsValues.add(Boolean.TRUE);
                } else {
                    ButtonsValues.add(Boolean.FALSE);
                }
            }
        }
        return isControllerValid;
    }

    public float getXAxisValue() {
        Component.Identifier identifier = Component.Identifier.Axis.X;
        return JoyStick.getComponent(identifier).getPollData();
    }

    public int getXAxisPercentage() {
        return (int) ((2 - (1 - getXAxisValue())) * 100) / 2;
    }

    public float getYAxisValue() {
        Component.Identifier identifier = Component.Identifier.Axis.Y;
        return JoyStick.getComponent(identifier).getPollData();
    }

    public int getYAxisPercentage() {
        return (int) ((2 - (1 - getYAxisValue())) * 100) / 2;
    }

    public float getXRotationValue() {
        Component.Identifier identifier = Component.Identifier.Axis.RX;
        return JoyStick.getComponent(identifier).getPollData();
    }

    public int getXRotationPercentage() {
        return (int) ((2 - (1 - getXRotationValue())) * 100) / 2;
    }

    public float getYRotationValue() {
        Component.Identifier identifier = Component.Identifier.Axis.RY;
        return JoyStick.getComponent(identifier).getPollData();
    }

    public int getYRotationPercentage() {
        return (int) ((2 - (1 - getYRotationValue())) * 100) / 2;
    }

    public float getX_LeftJoystick_Value() {
        return getXAxisValue();
    }

    public int getX_LeftJoystick_Percentage() {
        return getXAxisPercentage();
    }

    public float getY_LeftJoystick_Value() {
        return getYAxisValue();
    }

    public int getY_LeftJoystick_Percentage() {
        return getYAxisPercentage();
    }

    public float getX_RightJoystick_Value() {
        return getXRotationValue();
    }

    public int getX_RightJoystick_Percentage() {
        return getXRotationPercentage();
    }

    public float getY_RightJoystick_Value() {
        return getYRotationValue();
    }

    public int getY_RightJoystick_Percentage() {
        return getYRotationPercentage();
    }

    public float getZRotationValue() {
        Component.Identifier identifier = Component.Identifier.Axis.RZ;
        return JoyStick.getComponent(identifier).getPollData();
    }

    public int getZRotationPercentage() {
        return (int) ((2 - (1 - getZRotationValue())) * 100) / 2;
    }

    public float getZAxisValue() {
        Component.Identifier identifier = Component.Identifier.Axis.Z;
        return JoyStick.getComponent(identifier).getPollData();
    }

    public int getZAxisPercentage() {
        return (int) ((2 - (1 - getZAxisValue())) * 100) / 2;
    }

    public float getHatSwitchPosition() {
        Component.Identifier identifier = Component.Identifier.Axis.POV;
        return JoyStick.getComponent(identifier).getPollData();

    }

    //*******************************ArduinoSerial***************************************//
    public class ArduinoSerial implements SerialPortEventListener {

        @Override
        public void serialEvent(SerialPortEvent Event) {
            if (Event.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
                try {
                    System.out.println("Serial Input :-> " + Input.readLine());
                } catch (IOException ex) {
                    Logger.getLogger(ROVApplication.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }

//********************************************************************************************//
    public class Key_Listner implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getExtendedKeyCode()) {
                case 38:
                    ForwardActionPerformed(null);
                    break;
                case 40:
                    BackwardActionPerformed(null);
                    break;
                case 39:
                    RightActionPerformed(null);
                    break;
                case 37:
                    LeftActionPerformed(null);
                    break;
                case 33:
                    UpActionPerformed(null);
                    break;
                case 34:
                    DownActionPerformed(null);
                    break;
                case 79: {
                    StartActionPerformed(null);
                }
                break;
                case 80: {
                    PauseActionPerformed(null);
                }
                break;
                case 83: {
                    SaveActionPerformed(null);
                }
                break;
                case 86: {
                    RecordActionPerformed(null);
                }
                break;
                case 66: {
                    StopActionPerformed(null);
                }
                break;
                case 82:
                    RefreshActionPerformed(null);
                    break;
                case 67: {
                    ConnectionButtonActionPerformed(null);
                }
                break;
                case 70:
                    TorchActionPerformed(null);
                    break;
                case 78:
                    NeutralActionPerformed(null);
                    break;
                case 81:
                    ExitActionPerformed(null);
                    break;
                default:
                    break;
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {

        }

    }
}
