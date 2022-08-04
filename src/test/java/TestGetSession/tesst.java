package TestGetSession;

import GetSession.ReadSessionFromFile;
import GetSession.GetSession;
import com.google.gson.JsonSyntaxException;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import Class.SessionClass;

public class tesst {
    public static void getNewSession() throws JsonSyntaxException, IOException{
        GetSession getSession = new GetSession();
        getSession.setUrl_login_page("https://www.updiagram.com/login");
        getSession.setUrl_api("https://www.updiagram.com/api/authenticate");
        getSession.setUser_name("bee.n@itcgroup.io");
        getSession.setPassword("Baunguyenpr7!@");
        getSession.setPath("./src/test/java/DataSession/session.json");
        getSession.writeSessionToFile();
    }


    public static Map<String,SessionClass> hashSession = new HashMap<>();
    public static void main(String[] args) throws IOException, JsonSyntaxException {

        File myFile = new File("./src/test/java/DataSession/session.json");
        if(hashSession.isEmpty()){
            if(myFile.exists()&& !myFile.isDirectory() ){
                SessionClass session = new SessionClass(new GetSession("./src/test/java/DataSession/session.json").getSessionFromFile());
                LocalDateTime expired_date = LocalDateTime.parse(session.getExpired_date());
                LocalDateTime dateTimeNow = LocalDateTime.now();
                if(dateTimeNow.isBefore(expired_date)){
                    hashSession.put("session",session);
                    System.out.println("file exits and session not expired:"+session.getSession_id());
                    System.out.println(hashSession);
                }else{
                    System.out.println("file exits and session expired");
                    getNewSession();
                }
            }else{
                System.out.println("File not exist");
                getNewSession();
            }
        }else {
            System.out.println("hash:"+hashSession.get("Session"));
        }

    }
}
