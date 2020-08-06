package utils;

import model.VectorCamera;
import org.json.JSONObject;


public class JsonParser {

    /**
     * This method receives the MQTT-Message as a JSON and gets filtered
     * @param json the MQTT-MEssage that already arrived
     * @return
     */
    public VectorCamera receiveDataAsCamVector(String json){
        JSONObject obj = new JSONObject(json);
        if(!obj.isNull("location")){
            float xValue = Float.parseFloat(obj.getJSONObject("location").optString("x"));
            float yValue = Float.parseFloat(obj.getJSONObject("location").optString("y"));
            double tStamp = obj.optDouble("stamp");
            VectorCamera camVector = new VectorCamera(xValue, yValue,1.00f, tStamp);
            return camVector;
        }
        return null;
    }
}