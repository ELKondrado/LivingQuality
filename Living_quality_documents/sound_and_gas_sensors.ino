/* Modified code by ScottyD www.youtube.com/channel/UCLx3Gi9s_5uvVwURRIsWXKg
 * 
 * Carbon Dioxide Parts Per Million Meter
 * CO2PPM
 
 * Original code by www.youtube.com/c/learnelectronics
/*
 * Atmospheric CO2 Level..............400ppm
 * Average indoor co2.............350-450ppm
 * Maxiumum acceptable co2...........1000ppm
 * Dangerous co2 levels.............>2000ppm
 */

const int sampleWindow = 50;                              // Sample window width in mS (50 mS = 20Hz)
unsigned int sample;

#define SENSOR_PIN A2

#define anInput     A0                        //analog feed from MQ135
#define co2Zero     55                        //calibrated CO2 0 level

void setup() 
{
  pinMode (SENSOR_PIN, INPUT);
  
  pinMode(anInput,INPUT);                     //MQ135 analog feed set for input
  Serial.begin(9600);                         //serial comms for debuging
}

String gas_sensor(){
  int co2now[10];                               //int array for co2 readings
  int co2raw = 0;                               //int for raw value of co2
  int co2ppm = 0;                               //int for calculated ppm
  int zzz = 0;                                  //int for averaging


  for (int x = 0;x<10;x++)  //samplpe co2 10x over 2 seconds
  {                   
    co2now[x]=analogRead(A0);
    delay(100);
  }

  for (int x = 0;x<10;x++)  //add samples together
  {                     
    zzz=zzz + co2now[x];  
  }
  
  co2raw = zzz/10;                            //divide samples by 10
  co2ppm = co2raw - co2Zero; 

  return String(co2ppm);
}

String sound_sensor(){
  unsigned long startMillis= millis();                   // Start of sample window
   float peakToPeak = 0;                                  // peak-to-peak level
 
   unsigned int signalMax = 0;                            //minimum value
   unsigned int signalMin = 1024;                         //maximum value
 
                                                          // collect data for 50 mS
   while (millis() - startMillis < sampleWindow)
   {
      sample = analogRead(SENSOR_PIN);                    //get reading from microphone
      if (sample < 1024)                                  // toss out spurious readings
      {
         if (sample > signalMax)
         {
            signalMax = sample;                           // save just the max levels
         }
         else if (sample < signalMin)
         {
            signalMin = sample;                           // save just the min levels
         }
      }
   }
 
   peakToPeak = signalMax - signalMin;                    // max - min = peak-peak amplitude
   int db = map(peakToPeak,20,900,49.5,90);

   String dbString = String(db);

   return dbString;
}
  
void loop() 
{
  Serial.println(gas_sensor() + "," + sound_sensor());  // prints the value read
//  Serial.println(" PPM");
  delay(50);             

}
