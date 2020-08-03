package datacollection;

import org.eclipse.paho.client.mqttv3.*;

public class MQTTCollector implements MqttCallback {

    private MqttClient client;
    private String serverURI;
    private String clientID;

    private String topicPath;
    public static String camMessage;

    public MQTTCollector(String serverURI, String clientID, String topicPath) throws MqttException {
        this.serverURI = serverURI;
        this.clientID = clientID;
        this.topicPath = topicPath;
        client = new MqttClient(serverURI, clientID);
    }

    public void subscribe() {
        try {
            client.connect();
            client.setCallback(this);
            client.subscribe(topicPath);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void connectionLost(Throwable throwable) {

    }

    @Override
    public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
        camMessage = new String(mqttMessage.getPayload());
        ESP32Collector.setMQTTMessage(camMessage);
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

    }

    public String getTopicPath() {
        return topicPath;
    }
}
