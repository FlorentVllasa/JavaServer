# Jannotator
Dieses Programm ermöglicht die Erfassung von Sensordaten (MPU-9250)
und Annotierung dieser Daten mit Positionsdaten aus einem externen System.
Für den ESP32 Code bitte diesem Link folgen: Link
Für die Anomalien Detektion diesem Link folgen: Link

## Architektur 
![](src/main/java/doc/pictures/UML-Klasse.png)

## Technologien

Sprachen:

    * Java

Frameworks:

    * Maven
    * MQTT Library
    * Java Influx API

## Voraussetzungen

    * JDK Version 8
    * Maven 3.6.3
    * Zugriff auf eine Influx Datenbank
    * Visual Object Tracking (HTW saar Projekt)
    
## Maven Installation
Bevor das Programm genutzt werden kann ist es wichtig, dass Maven 
auf dem Rechner installiert ist. Dazu kann man diesem Link folgen wo
erklärt wird wie man Maven und die JDK installiert. Des Weiteren wird 
auch erklärt wie man die PATH Variablen jeweils richtig setzt. <br/>
Für Windows: https://mkyong.com/maven/how-to-install-maven-in-windows/
<br/>Für Linux: https://www.educative.io/edpresso/how-to-install-maven-in-windows-and-linux-unix
<br/> Für Mac: https://mkyong.com/maven/install-maven-on-mac-osx/

## Verwendung des Programms
![](src/main/java/doc/pictures/foerderbahn.jpg)

### Vorbereitende Maßnahmen
Bevor das Programm gestartet werden kann, sollten die einzelnen Daten bezüglich des Serverports, MQTT-Daten und
InfluxDaten mit den eigenen Daten angepasst werden. Die kann man in der Start.java Klasse durchführen.

Es ist es notwendig den ESP32 mit dem MPU-9250 auf der Plattform(unten rechts im Bild) zu platzieren. Dies sollte
möglichst robust gemacht werden um saubere Daten zu erhalten. Der ESP32 und der MPU-9250 kommunizieren
mittels einer I2C Verbindung. Die einzelnen Kabelverbindungen sind hier aufgeführt:

ESP32 | MPU9250
------ | ------
3V3  | VCC 
GND  | GND
G32  | SDA
G33  | SCL

SDA und SCL können relativ frei gewählt werden mit Ausnahme der GPIO 6 - 11 Pins. Näheres kann man hier nachlesen:https://randomnerdtutorials.com/esp32-i2c-communication-arduino-ide/ 
<br/> Des Weiteren wird auch beschrieben wie man die I2C Pins wechseln kann.
Der ESP32 Code sollte auch in angepasster Art und Weise auf dem ESP32 hochgeladen werden, das heißt eigene 
WLAN-Daten eintragen je nachdem in welchem Netzwerk man sich befindet. Näheres hierzu findet man im oben verlinkten
ESP32 Projekt.

### Praktisches Starten
Wenn alle Vorbereitungen durchgeführt wurden kann man das Programm starten. Zuerst muss in das Root-Verzeichnis des
Projektes gewechselt werden:

    * cd /my/dir/Jannotator

Danach sollte zur Sicherheit dieses Kommando ausgeführt werden um das Projekt zu säubern und zu kompilieren:

    * mvn clean compile
    
Nochmal alles kontrollieren und dann abschließend dieses Kommando zum eigentlichen Starten ausführen:

    * mvn exec:java

## Ablauf
Wenn alles geklappt hat sollte das Programm erkennen, dass es sich in der Startzone des Fördersystems
befindet. Sobald ein Auftrag gestartet wird und sich die Platform außerhalb dieser Startzone bewegt, fängt
die Aufnahme der Sensordaten und der MQTT Daten an und sie werden gesammelt. Wenn sich die Plattform wieder
in der Startzone befindet, werden alle gesammelten Daten in die Influx Datenbank geschrieben.
    



