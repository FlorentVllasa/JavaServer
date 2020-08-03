package interfaces;

import model.VectorMerged;

import java.io.IOException;
import java.util.List;

public interface DataCollector {
    List<VectorMerged> collectData() throws IOException;
    List<VectorMerged> processData(List<VectorMerged> collectedData);
}
