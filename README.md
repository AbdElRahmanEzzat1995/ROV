# ROV
Remotely Operated Vehicle (ROV)

![](README.assets/Pasted%20image%2020230531130031.png)

Figure1

## 1. Introduction :

ROV abbreviation refers to "Remotely Operated Vehicle", It is a tethered underwater robot that can be controlled by an operator on the surface without the need for a manual diving mechanism, ROV can perform different tasks in different fields underwater.

Our objective is to develop and improve the ROV graduation project and make it more suitable for industry use. The challenge is to build a micro ROV to perform specific tasks with the support of specific features. We successfully implemented the design and controlled it. The developed ROV is smaller and lighter, with a suitable design to move smoothly. Control and monitoring had been implemented, in addition to minimizing the cost, which made it suitable for wide-spread deep-water industrial applications.

![](README.assets/Pasted%20image%2020230531130452.png)

Figure2

#### 1.1 Project Main Idea

- On our frame structure design, the body of the ROV is opened from all directions, which means that there is no pressure acting on the submerged object, so the pressure approaches zero and is ignored.
- The only pressure to be calculated is the pressure acting on the volume of the PVC tubes, which can be neglected.
- The industry of regular or exploratory submarines needs high costs for applying ideas, management, control modes, etc.
- In addition to the highly expensive components that require regular maintenance.
- For this, our vehicle, Submarine, is made of tools and equipment that are not heavy, almost primitive, and traditional, which allows us to reduce our budget for implementation costs.

![](README.assets/Pasted%20image%2020230531130601.png)

Figure1.1-1

![](README.assets/Pasted%20image%2020230531130611.png)

Figure1.1-2

#### 1.2 Project Implementation Flowchart

![](README.assets/Pasted%20image%2020230531131519.png)

Figure1.2 Implementation Flowchart

## 2. Hardware Architecture

Our physical implementation actually divides into mechanical hardware construction and electrical hardware construction.

#### 2.1 Materials and Tools

- polyvinyl chloride (PVC) tubes for building the body (frame) of the ROV (the lengths are determined according to the desired size).
- Microcontroller chip, or any other standard board, as in our case we use "Arduino Uno" (we recommend Galileo kit, Raspberry Pi board).
- A specific number of motors is needed according to the design (M100 brushless).
- Vision system components (camera and lights); in our case, we use an ordinary webcam.
- Floatation and submerging system components like pressure sensors, ballast, and floats (in our case, we don’t have to use a pressure sensor)
- Connection cables consist of an Ethernet cable for control, a USB cable for the monitor, and an insulated copper wire for power.
- Electronic ICs for building the internal circuit and Hbridge (resistors, transistors, potentiometers, relays, PCB)
- Power source; in our case, we use GTL chargeable batteries.
- Isolation materials like wax, electrical tapes, silicon, pvc caps, and a reinforced box.
- Plastic grid and ligament for tightness.

#### 2.2 Design Concept

- Our desired concept for the body design was based on the simplest challenges of diving operations.

#### 2.3 ROV Structured Model

![](README.assets/Pasted%20image%2020230531133027.png)

Figure2.3

#### 2.4 Frame Design

![](README.assets/Pasted%20image%2020230531132736.png)

Figure2.4-1

![](README.assets/Pasted%20image%2020230531133712.png)

Figure2.4-2 Initial frame shape

#### 2.5 Circuit Design

- Establishing an electronic structure by integrating all parts, components, and plants that consume voltage from the source of power.
- For more precision, take an overview of the most effective and influential block in the vehicle, known as the control box.

![](README.assets/Pasted%20image%2020230531133431.png)

Figure2.5 Control box before and after insulation

#### 2.5.1 MicroController Board

In our ROV, we use the microcontroller power by using an **Arduino Uno** board, which is sufficient to control a number of connected plants and afford them high processing power.
![](README.assets/Pasted%20image%2020230531130835.png)

Figure2.5.1 

#### 2.5.2 H-Bridge Board

- It is an electronic circuit that is designed to provide us with a technique to change the motor's direction; this can be reached by switches.

![](README.assets/Pasted%20image%2020230531130850.png)

Figure2.5.2-1 H-bridge mechanism

- These switches can be electronic as transistors or electromechanical as relays, and each one of them has its own datasheet and properties.

![](README.assets/Pasted%20image%2020230531130924.png)

Figure2.5.2-2 Transistor vs Relay

- Our desire is to use electromechanical relays in the circuit design that have much more robustness over high voltages. Also,  its famous general rule is to separate any two devices or components, each of which operates at a different voltage level.

![](README.assets/Pasted%20image%2020230531130945.png)

Figure2.5.2-3 PCB initial circuit design

- The circuit has 9 relays; each relay's function is to control the direction of only one motor, and the last relay is for enabling or disabling lights.

![](README.assets/Pasted%20image%2020230531131111.png)

Figure2.5.2-4 PCB initial circuit design using Proteus

- On the margins of the PCB, there are a number of **rosettes**’ to handle touch wires to the whole electronic hardware subsystem components.

![](README.assets/Pasted%20image%2020230531131208.png)

Figure2.5.2-5 H-bridge connection

#### 2.5.3 Power Source

- The eccentric thing about the formation of the structure is that the source of power that feeds all of the electronic components with charges is fixed and isolated internally inside the control box.
- We have used **GTL lithium** batteries, which can be charged a number of times without wearing
- Each battery supplies from (3.7 ˷ 4) voltages, and so we connect or merge a number of 6 batteries in a series connection to supply the whole circuit with an approximation of 24 voltages. (i.e Every motor operates in a 24 voltage).

![](README.assets/Pasted%20image%2020230531131233.png)

Figure2.5.3

#### 2.5.4 Cables & Wires

- Our vehicle has a wired connection mechanism that provides a limit of distance between it and the operator at the surface.

![](README.assets/Pasted%20image%2020230531134744.png)

Figure2.5.4-1

- Due to the several tasks that are managing the vehicle's operation, there are three links connected from the operator to the vehicle in a simplex connection mode that is a one-way flow of signal.

![](README.assets/Pasted%20image%2020230531134910.png)

Figure2.5.4-2

1. **Ethernet "UTP" cable**, whose rule is to carry the digital order signals from the operator's computer "laptop" to the microcontroller to drive the vehicle through its motor's direction. (i.e., the UTP cable we use is from category 5, which is suitable for data rates up to 100 Mbps.)

![](README.assets/Pasted%20image%2020230531135039.png)

Figure2.5.4-3

2. **Electrical "copper" wire**, whose rule is to carry the charges that are supplied from the integrated block of batteries externally to provide the ROV with power.

![](README.assets/Pasted%20image%2020230531135118.png)

Figure2.5.4-4

3.  **Universal Serial Bus ("USB") cable**, whose rule is to take control over the connection between the vision (monitoring) system inside the vehicle and the operator by carrying a video stream and images from the fixed camera directly to the operator's screen.

![](README.assets/Pasted%20image%2020230531135128.png)

Figure2.5.4-5

#### 2.5.5 Motors and Propulsion System

- The best choice for the thrusters of a designed ROV is **brushless DC motors** to achieve the minimum dimension for the same power rating.

![](README.assets/Pasted%20image%2020230531135424.png)

Figure2.5.5-1

- We have used 4 DC motors from the category M100 brushless, which are used in printing machines.

![](README.assets/Pasted%20image%2020230531135435.png)

Figure2.5.5-2

#### 2.6 Monitoring (Vision) System

- We are using a Vega WebCam, which has moderate quality for streaming videos and capturing images.

![](README.assets/Pasted%20image%2020230531135726.png)

Figure2.6-1

- Also, there is an effective component in the monitoring system that is the **LED**, or **lights**, which helps the camera capture what is going on under conditions such as darkness and non-clear water.

![](README.assets/Pasted%20image%2020230531135735.png)

Figure2.6-2

- We use epoxy to cover the start of the camera cable as shown in Figure2.6-3.

![](README.assets/Pasted%20image%2020230531140335.png)

Figure2.6-3


## 3. Software Architecture

#### 3.1 Fact of Architecture

- The system software onomatopoeia may imply a logically programmed system with an appropriate programming language, although it also includes a physical hardware implementation to complete the system integration.

![](README.assets/Pasted%20image%2020230531153421.png)

Figure3.1 software architecture block diagram

#### 3.2 Control System

- The main concept of control system structure is that it receives an order in a signal or wave form, processes it, and then gives an output in a certain format and function.

![](README.assets/Pasted%20image%2020230531155032.png)

Figure3.2-1

![](README.assets/Pasted%20image%2020230531154941.png)

Figure3.2-2 An example of open loop control system


#### 3.3 Software Programming

#### 3.3.1.  Embedded Code

- Lately we informed that the microcontroller board that is used as the vehicle brain is an **Arduino Uno** board.

![](README.assets/Pasted%20image%2020230531130835.png)

Figure3.3.1-1 

- Simply by using an AVR microcontroller for the Arduino Uno board, we embed or burn an efficient, organized program for the vehicle movement conditions, operations, and interrupts, which receive multiple instructions from crusty over a wired medium.

![](README.assets/Pasted%20image%2020230531155357.png)

Figure 3.3.1-2 Screenshot from the embedded code

- The desired specific conditions and tasks are determined by sending the parameter term, which will determine the order to be executed over an external **_Ethernet module_** connected to the Arduino.

- (Note that an **_Ethernet shield_** can be used instead of the module; that can differ in the number of bins connected and the cost.)

![](README.assets/Pasted%20image%2020230531155643.png)

Figure3.3.1-3 Ethernet Module


#### 3.3.2.  Desktop Application

- In order to control the vehicle crossing landfall, we have developed a reliable desktop application program to be able to give orders for movement directions, floating, submerging, and stabilizing at a desired level.

- Also to be considered as a user interface for receiving an underwater video stream are statistics from sensors.

![](README.assets/Pasted%20image%2020230531155947.png)

Figure3.3.1-4 Desktop application user interface

- We have used the Java programming language because of its high efficiency and the huge number of built-in libraries and functions that offer more facilitation in analysis and implementation.

- For driving the vehicle, there are two methods for controlling its movement and sending instructions:

![](README.assets/Pasted%20image%2020230531160155.png)

Figure3.3.1-5 Joysticks vs Keyboard

- The connection between Java and Arduino requires some specific standard steps to maintain it :

  o You can run Java SE Embedded or Java ME on a Raspberry Pi, but the Arduino is a bit too constrained to run Java directly. However, with the help of serial port communication software, you can communicate with and control an Arduino from Java running on another computer.
  
  o Although the code to do so has been published on the Arduino site, it's still a little tricky. In this blog, I'll go over how to make this work on different host operating systems as well as write an Arduino sketch to do something useful.
  
  o Locate and download the RXTX library. When you unzip the downloaded file, you'll notice directories for various operating systems (OSs). Make note of which ones you're using, as you'll need those specific files.
  
  o Create a new Java project in the IDE of your choice, and be sure to copy the following RXTX files (from the download in the first step) into the project directory.
  
  o For Windows: rxtxSeial.dll (from the Windows subdirectory).
  
  o Modify the project's settings to include RXTXcomm.jar on the class path and the path to the native library on the command line via the "**-Djava.library.path**" parameter.

![](README.assets/Pasted%20image%2020230531160532.png)

Figure3.3.1-6 Screenshot of the interface between java & Arduino.

- Through the **_serial port monitor,_** we can send the desired instructions for the desired conditions to be executed.

![](README.assets/Pasted%20image%2020230531160646.png)

Figure3.3.1-7 Serial Port Monitoring.



## Build

- Make sure to have the "**rxtxSerial.dll**" file on the JRE path : `C:\Program Files\Java\jre1.8.0_121\bin`
- Make sure the "**res**" folder is exists with the "**ROV_Application.jar**".
- You can download the "**rxtxSerial.dll**" file from here [rxtxSerial.dll](https://github.com/jajberni/sts-java/raw/master/rxtxSerial.dll.x64)


## Run

**Before you run the desktop ROV application, you must burn the hardware code to the Arduino board using the USB serial port.**

- You have two program code scenarios to communicate with the ROV through the ROV desktop application, either by USB or Ethernet cable.

1. [ROV Control using Serial (USB)](ROV_Control/Serial.ino)

2. [ROV Control using Ethernet](ROV_Control/Ethernet.ino)


**You can run the desktop application by :**

- Double click on the "[ROV_Application.jar](dist/ROV_Application.jar)" file.
**OR**
- Run the following command on the command prompt :
`> java -jar ROV_Application.jar`


## References :

- [Wikipedia ROV](https://en.wikipedia.org/wiki/Remotely_operated_underwater_vehicle)
- [Marine technology society](http://www.rov.org/industry_manufacturers.cfm)
- [HomeBulid ROV’s](http://www.homebuiltrovs.com/firstdesign.html)
- [SeaPerch Vehicle](https://www.youtube.com/watch?v=t4FOMOiRISE)
- [Archimedes law of buoyancy](https://www.youtube.com/watch?v=B-F2YXKq4Yc)
- [Archimedes law of buoyancy](http://amrita.olabs.edu.in/?sub=1&brch=1&sim=72&cnt=1)
- [Archimedes law of buoyancy](http://hyperphysics.phy-astr.gsu.edu/hbase/pbuoy.html)

**Books**
- Build Underwater Robot & other wet projects
- High Institute of Engineering-Mechatronics department graduation project 2014.
- الغواصات الروبوتيه من التصميم الى التصنيع
