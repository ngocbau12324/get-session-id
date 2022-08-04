package GetSession;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.microsoft.playwright.*;

import java.io.*;
import java.time.LocalDateTime;
import Class.SessionClass;


public class GetSession {

    private String url_login_page;
    private String url_api;
    private String user_name;
    private String password;
    private String path;
    public GetSession() {
    }

    public GetSession(String path) {
        this.path = path;
    }

    public GetSession(String url_login_page, String url_api, String user_name, String password, String path) {
        this.url_login_page = url_login_page;
        this.url_api = url_api;
        this.user_name = user_name;
        this.password = password;
        this.path = path;
    }

    public String getUrl_login_page() {
        return url_login_page;
    }

    public void setUrl_login_page(String url_login_page) {
        this.url_login_page = url_login_page;
    }

    public String getUrl_api() {
        return url_api;
    }

    public void setUrl_api(String url_api) {
        this.url_api = url_api;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }


    public void writeSessionToFile() throws JsonSyntaxException, IOException {
        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright
                    .chromium()
                    .launch(new BrowserType.LaunchOptions().setHeadless(false));
            Page page = browser.newPage();
            page.navigate(this.url_login_page);

            //get data api
            String api_url = this.url_api;
            // Waits for the next response matching some conditions
            Response res = page.waitForResponse(response -> api_url.equals(response.url()) && response.status() == 200, () -> {
                // Triggers the response
                String user_name_selector = "#login-form > div > div > form > input:nth-child(1)";
                String password_selector = "#login-form > div > div > form > input:nth-child(2)";
                String btnLogin_selector = "#login-form > div > div > form > div > button";

                String user_name_value = this.user_name;
                String password_value = this.password;
                page.fill(user_name_selector, user_name_value);
                page.fill(password_selector, password_value);
                page.click(btnLogin_selector);
            });
            //Get response and convert it to jsonObject
            Gson gson = new Gson();
            JsonObject response_data = gson.fromJson(res.text(), JsonObject.class);
            //Get token and expired date
            String token = response_data.get("token").getAsString();
            LocalDateTime dateTime = LocalDateTime.now();
            String created_date = dateTime.toString();
            String expired_date = dateTime.plusDays(1).toString();

            //Create json_session_object
            SessionClass session = new SessionClass(token,created_date,expired_date);
            String session_json_object = new Gson().toJson(session);

            //Write to file
            FileWriter myWriter = new FileWriter(this.path);
            myWriter.write(session_json_object);
            myWriter.close();
        }
    }
    public SessionClass getSessionFromFile() throws FileNotFoundException {
        //Read to file
        FileReader reader = new FileReader(this.path);
        Object jsonObject = JsonParser.parseReader(reader);
        SessionClass sessionClass = new SessionClass(new Gson().fromJson(jsonObject.toString(), SessionClass.class));
        return sessionClass;
    }
}
