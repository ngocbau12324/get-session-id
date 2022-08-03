package GetSession;

import com.google.gson.Gson;
import com.google.gson.JsonParser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import Class.SessionClass;

public class ReadSessionFromFile {
    private String path;

    public ReadSessionFromFile(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
    public SessionClass getSessionFromFile() throws FileNotFoundException {
        //Read to file
            FileReader reader = new FileReader(this.path);
            Object jsonObject = JsonParser.parseReader(reader);
            SessionClass sessionClass = new SessionClass(new Gson().fromJson(jsonObject.toString(), SessionClass.class));
            return sessionClass;
    }
}
