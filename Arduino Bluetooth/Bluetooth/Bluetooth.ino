#include <SoftwareSerial.h>
SoftwareSerial Bluetooth(0, 1);
#define TRIGGER 7
bool stop = false;

void setup() {
  Bluetooth.begin(9600);
  delay(100);
}

void loop() {
  if (Bluetooth.available()) {
    if (digitalRead(TRIGGER) && stop == true)
    {
      Bluetooth.print("1");
      stop = false;
    }
    else if (!digitalRead(TRIGGER) && stop == false)
    {
      Bluetooth.print("0");
      stop = true;
    }
  }
}
