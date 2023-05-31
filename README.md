# ROV
Remotely Operated Vehicle (ROV)

![[Pasted image 20230531130031.png]]
Figure1

## 1. Introduction :

ROV abbreviation refers to "Remotely Operated Vehicle", It is a tethered underwater robot that can be controlled by an operator on the surface without the need for a manual diving mechanism, ROV can perform different tasks in different fields underwater.

Our objective is to develop and improve the ROV graduation project and make it more suitable for industry use. The challenge is to build a micro ROV to perform specific tasks with the support of specific features. We successfully implemented the design and controlled it. The developed ROV is smaller and lighter, with a suitable design to move smoothly. Control and monitoring had been implemented, in addition to minimising the cost, which made it suitable for wide-spread deep-water industrial applications.

![[Pasted image 20230531130452.png]]
Figure2

#### 1.1 Project Main Idea

- On our frame structure design, the body of the ROV is opened from all directions, which means that there is no pressure acting on the submerged object, so the pressure approaches zero and is ignored.
- The only pressure to be calculated is the pressure acting on the volume of the PVC tubes, which can be neglected.
- The industry of regular or exploratory submarines needs high costs for applying ideas, management, control modes, etc.
- In addition to the highly expensive components that require regular maintenance.
- For this, our vehicle, Submarine, is made of tools and equipment that are not heavy, almost primitive, and traditional, which allows us to reduce our budget for implementation costs.

![[Pasted image 20230531130601.png]]
Figure1.1-1

![[Pasted image 20230531130611.png]]
Figure1.1-2

#### 1.2 Project Implementation Flowchart

![[Pasted image 20230531131519.png]]
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

![[Pasted image 20230531133027.png]]
Figure2.3

#### 2.4 Frame Design

![[Pasted image 20230531132736.png]]
Figure2.4-1

![[Pasted image 20230531133712.png]]
Figure2.4-2 Initial frame shape

#### 2.5 Circuit Design

- Establishing an electronic structure by integrating all parts, components, and plants that consume voltage from the source of power.
- For more precision, take an overview of the most effective and influential block in the vehicle, known as the control box.
![[Pasted image 20230531133431.png]]
Figure2.5 Control box before and after insulation

#### 2.5.1 MicroController Board

In our ROV, we use the microcontroller power by using an **Arduino Uno** board, which is sufficient to control a number of connected plants and afford them high processing power.
![[Pasted image 20230531130835.png]]
Figure2.5.1 

#### 2.5.2 H-Bridge Board

- It is an electronic circuit that is designed to provide us with a technique to change the motor's direction; this can be reached by switches.
![[Pasted image 20230531130850.png]]
Figure2.5.2-1 H-bridge mechanism

- These switches can be electronic as transistors or electromechanical as relays, and each one of them has its own datasheet and properties.
![[Pasted image 20230531130924.png]]
Figure2.5.2-2 Transistor vs Relay

- Our desire is to use electromechanical relays in the circuit design that have much more robustness over high voltages. Also,  its famous general rule is to separate any two devices or components, each of which operates at a different voltage level.
![[Pasted image 20230531130945.png]]
Figure2.5.2-3 PCB initial circuit design

- The circuit has 9 relays; each relay's function is to control the direction of only one motor, and the last relay is for enabling or disabling lights.
![[Pasted image 20230531131111.png]]
Figure2.5.2-4 PCB initial circuit design using Proteus

- On the margins of the PCB, there are a number of **rosettes**’ to handle touch wires to the whole electronic hardware subsystem components.
![[Pasted image 20230531131208.png]]
Figure2.5.2-5 H-bridge connection

#### 2.5.3 Power Source

- The eccentric thing about the formation of the structure is that the source of power that feeds all of the electronic components with charges is fixed and isolated internally inside the control box.
- We have used **GTL lithium** batteries, which can be charged a number of times without wearing
- Each battery supplies from (3.7 ˷ 4) voltages, and so we connect or merge a number of 6 batteries in a series connection to supply the whole circuit with an approximation of 24 voltages. (i.e Every motor operates in a 24 voltage).
![[Pasted image 20230531131233.png]]
Figure2.5.3

#### 2.5.4 Cables & Wires

- Our vehicle has a wired connection mechanism that provides a limit of distance between it and the operator at the surface.
![[Pasted image 20230531134744.png]]
Figure2.5.4-1

- Due to the several tasks that are managing the vehicle's operation, there are three links connected from the operator to the vehicle in a simplex connection mode that is a one-way flow of signal.
![[Pasted image 20230531134910.png]]
Figure2.5.4-2

1. **Ethernet "UTP" cable**, whose rule is to carry the digital order signals from the operator's computer "laptop" to the microcontroller to drive the vehicle through its motor's direction. (i.e., the UTP cable we use is from category 5, which is suitable for data rates up to 100 Mbps.)
![[Pasted image 20230531135039.png]]
Figure2.5.4-3

2. **Electrical "copper" wire**, whose rule is to carry the charges that are supplied from the integrated block of batteries externally to provide the ROV with power.
![[Pasted image 20230531135118.png]]
Figure2.5.4-4

3.  **Universal Serial Bus ("USB") cable**, whose rule is to take control over the connection between the vision (monitoring) system inside the vehicle and the operator by carrying a video stream and images from the fixed camera directly to the operator's screen.
![[Pasted image 20230531135128.png]]
Figure2.5.4-5

#### 2.5.5 Motors and Propulsion System

- The best choice for the thrusters of a designed ROV is **brushless DC motors** to achieve the minimum dimension for the same power rating.
![[Pasted image 20230531135424.png]]
Figure2.5.5-1

- We have used 4 DC motors from the category M100 brushless, which are used in printing machines.
![[Pasted image 20230531135435.png]]
Figure2.5.5-2

#### 2.6 Monitoring (Vision) System

- We are using a Vega WebCam, which has moderate quality for streaming videos and capturing images.
![[Pasted image 20230531135726.png]]
Figure2.6-1

- Also, there is an effective component in the monitoring system that is the **LED**, or **lights**, which helps the camera capture what is going on under conditions such as darkness and non-clear water.
![[Pasted image 20230531135735.png]]
Figure2.6-2

- We use epoxy to cover the start of the camera cable as shown in Figure2.6-3.
![[Pasted image 20230531140335.png]]
Figure2.6-3


## 3. Software Architecture




## References :

- [Wikipedia ROV](https://en.wikipedia.org/wiki/Remotely_operated_underwater_vehicle)
- [Marine technology society](http://www.rov.org/industry_manufacturers.cfm)
- [HomeBulid ROV’s](http://www.homebuiltrovs.com/firstdesign.html)
- [SeaPerch Vehicle](https://www.youtube.com/watch?v=t4FOMOiRISE)
- [Archimedes law of buoyancy](https://www.youtube.com/watch?v=B-F2YXKq4Yc)
- [Archimedes law of buoyancy](http://amrita.olabs.edu.in/?sub=1&brch=1&sim=72&cnt=1)
- [Archimedes law of buoyancy](http://hyperphysics.phy-astr.gsu.edu/hbase/pbuoy.html)

**Books**
- الغواصات الروبوتيه من التصميم الى التصنيع
- Build Underwater Robot & other wet projects
- High Institute of Engineering-Mechatronics department graduation project 2014.
