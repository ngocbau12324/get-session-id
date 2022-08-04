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
        getSession.setUrl_login_page("https://access.line.me/oauth2/v2.1/login?loginState=EwYN43BKenNRmx9CvRaqAE&loginChannelId=1576775644&returnUri=%2Foauth2%2Fv2.1%2Fauthorize%2Fconsent%3Fscope%3Dprofile%26response_type%3Dcode%26redirect_uri%3Dhttps%253A%252F%252Faccount.line.biz%252Flogin%252Fline-callback%26state%3DbCpefxrpryO2msGq%26client_id%3D1576775644#/");
        getSession.setUrl_api("https://twoav.line-beta.biz/api/v1/session");
        getSession.setUser_name("lw81112@v.linecorp.com");
        getSession.setPassword("lw81112");
        getSession.setPath("./src/test/java/DataSession/session.json");
        getSession.writeSessionToFile();
    }

    private static HashMap<String,Object> hashSession = new HashMap<>();
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
                    System.out.println("hashsession"+hashSession);
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
