# SPDX-FileCopyrightText: 2021 ladyada for Adafruit Industries
# SPDX-License-Identifier: MIT

import time
import board
import adafruit_dht
import pyrebase
import random
import RPi.GPIO as GPIO
import time
import serial

ser = serial.Serial('/dev/ttyACM0', 9600)

#GPIO.setmode(GPIO.BOARD)

lightResistorPin = 7

config = {
    'apiKey' : 'AIzaSyASfw2yRRvGdCMMCiVk7LzJFrQhG0cQD5g',
    'authDomain' : 'iotca-2b9a5.firebaseapp.com',
    'databaseURL' : 'https://iotca-2b9a5-default-rtdb.firebaseio.com/',
    'storageBucket' : 'iotca-2b9a5.appspot.com'
}

firebase = pyrebase.initialize_app(config)

db = firebase.database()

# Initial the dht device, with data pin connected to:
# dhtDevice = adafruit_dht.DHT11(board.D23)

# you can pass DHT22 use_pulseio=False if you wouldn't like to use pulseio.
# This may be necessary on a Linux single board computer like the Raspberry Pi,
# but it will not work in CircuitPython.
dhtDevice = adafruit_dht.DHT11(board.D23, use_pulseio=False)

def sound_and_gas_sensor():
    x = str(ser.readline()).replace("\\r\\n'", "").replace("b'", "").split(",")
    data = {
        'air' : int(x[0].strip()),
        'sound' : int(x[1].strip())
        }
    return data

def light_sensor():
    GPIO.setup(lightResistorPin, GPIO.OUT)
    GPIO.output(lightResistorPin, GPIO.LOW)
    time.sleep(0.1)
    
    GPIO.setup(lightResistorPin, GPIO.IN)
    currentTime = time.time()
    diff = 0
    
    #print('br:', diff)
    
    while(GPIO.input(lightResistorPin) == GPIO.LOW):
        #print(GPIO.input(resistorPin) == GPIO.LOW)
        diff = time.time() - currentTime
    
    return {'light' : int((150 - diff * 1000) * 10 / 15)}

def humidity_sensor():
    temperature_c = dhtDevice.temperature
    temperature_f = temperature_c * (9 / 5) + 32
    humidity = dhtDevice.humidity
    data = {
            'temperature' : temperature_c,
            'humidity' : humidity
        }
    return data

while True:
    try:
        
        data = {}
        data.update(humidity_sensor())        
        data.update(light_sensor())
        data.update(sound_and_gas_sensor())
        
        db.child('LiveInformation').push(data)
        db.child('Information').push(data)
        # db.update(data)
        
        
        print(data)
        print('Sent To Firebase')

    except RuntimeError as error:
        # Errors happen fairly often, DHT's are hard to read, just keep going
        print(error.args[0])
        time.sleep(2.0)
        continue
    except Exception as error:
        dhtDevice.exit()
        raise error

    time.sleep(1.0)

