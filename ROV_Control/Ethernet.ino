#include <UIPEthernet.h>

EthernetUDP udp;

bool StringEqual(char* Str1,int Size1,char* Str2,int Size2)
{
    bool Equal=true;
    if(Size1 != Size2)
    {
        Equal=false;
    }
    else
    {
        for(int i=0; i<Size1; i++)
        {
            if(Str1[i]!=Str2[i])
            {
                Equal=false;
                break;
            }
        }
    }
    return Equal;
}


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
  uint8_t mac[6] = {0x00,0x01,0x02,0x03,0x04,0x05};
  Ethernet.begin(mac,IPAddress(192,168,0,2));
  int success = udp.begin(5000);
}

void loop() {
  int size = udp.parsePacket();
  if (size > 0) {
    do
      {
        char* msg = (char*)malloc(size+1);
        int len = udp.read(msg,size+1);
        msg[len]=0;
        if(StringEqual(msg,size,"Forward",7)){
        digitalWrite(2,HIGH);
        digitalWrite(4,LOW);
        digitalWrite(5,LOW);
        digitalWrite(6,HIGH);
        Serial.println("Forward");
        }else if(StringEqual(msg,size,"Backward",8)){
        digitalWrite(2,LOW);
        digitalWrite(4,HIGH);
        digitalWrite(5,HIGH);
        digitalWrite(6,LOW);
        Serial.println("Backward");
        }else if(StringEqual(msg,size,"Up",2)){
        digitalWrite(7,LOW);
        digitalWrite(8,HIGH);
        digitalWrite(9,HIGH);
        digitalWrite(10,LOW);
        Serial.println("Up");
        }else if(StringEqual(msg,size,"Down",4)){
        digitalWrite(7,HIGH);
        digitalWrite(8,LOW);
        digitalWrite(9,LOW);
        digitalWrite(10,HIGH);
        Serial.println("Down");
        }else if(StringEqual(msg,size,"Right",5)){
        digitalWrite(2,HIGH);
        digitalWrite(4,LOW);
        digitalWrite(5,HIGH);
        digitalWrite(6,LOW);
        Serial.println("Right");
        }else if(StringEqual(msg,size,"Left",4)){
        digitalWrite(2,LOW);
        digitalWrite(4,HIGH);
        digitalWrite(5,LOW);
        digitalWrite(6,HIGH);
        Serial.println("Left");
        }else if(StringEqual(msg,size,"Neutral",7)){
        digitalWrite(2,LOW);
        digitalWrite(4,LOW);
        digitalWrite(5,LOW);
        digitalWrite(6,LOW);
        digitalWrite(7,LOW);
        digitalWrite(8,LOW);
        digitalWrite(9,LOW);
        digitalWrite(10,LOW);
        Serial.println("Neutral");
        }else if(StringEqual(msg,size,"NeutralFB",9)){
        digitalWrite(2,LOW);
        digitalWrite(4,LOW);
        digitalWrite(5,LOW);
        digitalWrite(6,LOW);
        Serial.println("NeutralFB");
        }else if(StringEqual(msg,size,"NeutralUD",9)){
        digitalWrite(7,LOW);
        digitalWrite(8,LOW);
        digitalWrite(9,LOW);
        digitalWrite(10,LOW);
        Serial.println("NeutralUD");
        }else if(StringEqual(msg,size,"LedON",5)){
        digitalWrite(3,HIGH);
        Serial.println("LedON");
        }else if(StringEqual(msg,size,"LedOFF",6)){
        digitalWrite(3,LOW);
        Serial.println("LedOFF");
        } 
        free(msg);
      }
    while ((size = udp.available())>0);
    udp.flush();
    udp.begin(5000);  
}
}

