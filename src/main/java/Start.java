import database.InfluxClient;
import datacollection.MQTTCollector;
import datacollection.ESP32Collector;
import interfaces.DataCollector;
import model.VectorMerged;
import org.eclipse.paho.client.mqttv3.MqttException;

import java.io.IOException;
import java.util.List;

public class Start {
    public static void main(String[] args) throws MqttException, IOException {
        DataCollector serverCollector = new ESP32Collector(8080, 250);
        MQTTCollector mqttCollector = new MQTTCollector("tcp://192.168.29.106", "florent",
                                                        "hardware/visual-object-tracking/locations/None");
        InfluxClient influxClient = new InfluxClient("http://kil-pm.htwsaar.de:8086", "2020-pib-gyro",
                                        "XijDYxG6QAP9Eee".toCharArray(), "", "2020-pib-data-merged");
        mqttCollector.subscribe();
        System.out.println("Subscribed to: " + mqttCollector.getTopicPath());

        System.out.println("Collecting data...");
        List<VectorMerged> vectorMergedList = serverCollector.collectData();
        System.out.println("Writing data to database...");
        for (int i = 0; i < vectorMergedList.size(); i++) {
            influxClient.writeDataVectorMerged(vectorMergedList.get(i));
        }
        System.out.println("Done!");
        influxClient.closeConnection();
    }
}
