package DemoPlayWright;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.microsoft.playwright.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Scanner;


public class playwrightExample {
    public static void main(String[] args) throws InterruptedException, IOException {
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
            String token = response_data.get("token").toString();
            LocalDateTime dateTime = LocalDateTime.now();
            String created_date = dateTime.toString();
            String expired_date = dateTime.plusDays(1).toString();

            //Write to file
            File myFile = new File("D:\\examplePlaywright\\src\\test\\java\\DataSession\\sessionId.txt");
            FileWriter myWriter = new FileWriter(myFile);
            myWriter.write("{ " +
                    "\"session_id\":" + token +
                    ",\"created_date\":" + "\"" + created_date + "\"" +
                    ",\"exprired_date\":" + "\"" + expired_date + "\"" +
                    "}");
            myWriter.close();

            //Read to file
            String data_session_id = "";
            Scanner myReader = new Scanner(myFile);
            while (myReader.hasNextLine()) {
                data_session_id = data_session_id + myReader.nextLine();
            }
            myReader.close();
            System.out.println(data_session_id);
        }
    }
}
