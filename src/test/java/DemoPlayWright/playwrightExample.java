package DemoPlayWright;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.microsoft.playwright.*;


import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import Class.SessionClass;


public class playwrightExample {
    public static void main(String[] args) throws JsonSyntaxException, IOException {
        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright
                    .chromium()
                    .launch(new BrowserType.LaunchOptions().setHeadless(false));
            Page page = browser.newPage();
            page.navigate("https://www.updiagram.com/login");

            //get data api
            String api_url = "https://www.updiagram.com/api/authenticate";
            // Waits for the next response matching some conditions
            Response res = page.waitForResponse(response -> api_url.equals(response.url()) && response.status() == 200, () -> {
                // Triggers the response
                String user_name_selector = "#login-form > div > div > form > input:nth-child(1)";
                String password_selector = "#login-form > div > div > form > input:nth-child(2)";
                String btnLogin_selector = "#login-form > div > div > form > div > button";

                String user_name_value = "bee.n@itcgroup.io";
                String password_value = "Baunguyenpr7!@";
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
            String path = "./src/test/java/DataSession/session.json";
            FileWriter myWriter = new FileWriter(path);
            myWriter.write(session_json_object);
            myWriter.close();

            //Read to file
            FileReader reader = new FileReader(path);
            Object jsonObject = JsonParser.parseReader(reader);
            SessionClass sessionClass = new SessionClass(new Gson().fromJson(jsonObject.toString(), SessionClass.class));
            System.out.println(sessionClass);
        }
    }
}
