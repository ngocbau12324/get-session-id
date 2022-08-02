package DemoPlayWright;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.microsoft.playwright.*;

import java.io.FileWriter;
import java.io.IOException;


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
                page.fill(user_name_selector,user_name_value);
                page.fill(password_selector, password_value);
                page.click(btnLogin_selector);
            });
            String response_data = res.text();
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(response_data,JsonObject.class);
            String token = jsonObject.get("token").toString();
            System.out.println("Token = "+token);

            //Write to file
            FileWriter myWriter = new FileWriter("D:\\examplePlaywright\\src\\test\\java\\DataSession\\sessionId.txt");
            myWriter.write(token);
            myWriter.close();

        }
    }
}
