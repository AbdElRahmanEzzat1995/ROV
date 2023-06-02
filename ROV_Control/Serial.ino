void setup() {
  pinMode(2,OUTPUT);
  pinMode(3,OUTPUT);
  pinMode(4,OUTPUT);
  pinMode(5,OUTPUT);
  pinMode(6,OUTPUT);
  pinMode(7,OUTPUT);
  pinMode(8,OUTPUT);
  pinMode(9,OUTPUT);
  pinMode(10,OUTPUT);
  Serial.begin(9600);
}

void loop() {
  if (Serial.available()) {
        char Byte = Serial.read();
        if(Byte == 'F'){
        digitalWrite(2,HIGH);
        digitalWrite(4,LOW);
        digitalWrite(5,LOW);
        digitalWrite(6,HIGH);
        Serial.println("Forward");
        }else if(Byte == 'B'){
        digitalWrite(2,LOW);
        digitalWrite(4,HIGH);
        digitalWrite(5,HIGH);
        digitalWrite(6,LOW);
        Serial.println("Backward");
        }else if(Byte == 'U'){
        digitalWrite(7,LOW);
        digitalWrite(8,HIGH);
        digitalWrite(9,HIGH);
        digitalWrite(10,LOW);
        Serial.println("Up");
        }else if(Byte == 'D'){
        digitalWrite(7,HIGH);
        digitalWrite(8,LOW);
        digitalWrite(9,LOW);
        digitalWrite(10,HIGH);
        Serial.println("Down");
        }else if(Byte == 'R'){
        digitalWrite(2,HIGH);
        digitalWrite(4,LOW);
        digitalWrite(5,HIGH);
        digitalWrite(6,LOW);
        Serial.println("Right");
        }else if(Byte == 'L'){
        digitalWrite(2,LOW);
        digitalWrite(4,HIGH);
        digitalWrite(5,LOW);
        digitalWrite(6,HIGH);
        Serial.println("Left");
        }else if(Byte == 'M'){
        digitalWrite(2,LOW);
        digitalWrite(4,LOW);
        digitalWrite(5,LOW);
        digitalWrite(6,LOW);
        digitalWrite(7,LOW);
        digitalWrite(8,LOW);
        digitalWrite(9,LOW);
        digitalWrite(10,LOW);
        Serial.println("Neutral");
        }else if(Byte == ','){
        digitalWrite(2,LOW);
        digitalWrite(4,LOW);
        digitalWrite(5,LOW);
        digitalWrite(6,LOW);
        Serial.println("NeutralFB");
        }else if(Byte == '.'){
        digitalWrite(7,LOW);
        digitalWrite(8,LOW);
        digitalWrite(9,LOW);
        digitalWrite(10,LOW);
        Serial.println("NeutralUD");
        }else if(Byte == 'N'){
        digitalWrite(3,HIGH);
        Serial.println("LedON");
        }else if(Byte == 'O'){
        digitalWrite(3,LOW);
        Serial.println("LedOFF");
        }         
   }
}

