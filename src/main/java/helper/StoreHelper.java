package helper;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.ElementInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public enum StoreHelper {

    INSTANCE;
    protected static Logger logger = LogManager.getLogger(StoreHelper.class);
    private static final String DEFAULT_DIRECTORY_PATH = "elementValues";
    ConcurrentMap<String, Object> elementMapList;

    StoreHelper() {
        initMap(getFileList());
    }

    private void initMap(File[] fileList) {
        elementMapList = new ConcurrentHashMap<>();
        Type elementType = new TypeToken<List<ElementInfo>>() {  //tür bilgilerinin alınmasını sağlar
        }.getType();
        Gson gson = new Gson();  // json ile java arasında serileştirme
        List<ElementInfo> elementInfoList = null;

        // json okuyoruz
        for (File file : fileList) {
            try {
                elementInfoList = gson
                        .fromJson(new FileReader(file), elementType); //
                elementInfoList.parallelStream() //paralel okuma
                        .forEach(elementInfo -> elementMapList.put(elementInfo.getKey(), elementInfo));  // elementMaplist doldurduk
            } catch (FileNotFoundException e) {
                logger.warn("{} not found", e);
            }
        }
    }
    //blok halinde aldık
    private File[] getFileList() { // .json a element values içinden ulaşır file olarak döndürür
        File[] fileList = new File(
                this.getClass().getClassLoader().getResource(DEFAULT_DIRECTORY_PATH).getFile())
                .listFiles(pathname -> !pathname.isDirectory() && pathname.getName().endsWith(".json"));
        if (fileList == null) {
            // logger.warn(
            //     "File Directory Is Not Found! Please Check Directory Location. Default Directory Path = {}",
            //      DEFAULT_DIRECTORY_PATH);
            throw new NullPointerException();
        }
        return fileList;
    }



    public ElementInfo findElementInfoByKey(String key) {
        return (ElementInfo) elementMapList.get(key);
    }

    public void saveValue(String key, String value) {
        elementMapList.put(key, value);
    }

    public String getValue(String key) {
        return elementMapList.get(key).toString();
    }
}
