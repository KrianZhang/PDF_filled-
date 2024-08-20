import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RSEAPI {
    private static final String API_URL = "YOUR-API_URL";
    private static final String API_TOKEN = "YOUR-API_TOKEN";

    public static List<HashMap<String, String>> fetchRSE() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(API_URL)
                .addHeader("Authorization", "Bearer " + API_TOKEN)
                .build();

        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                String responseData = response.body().string();
                System.out.println("Response Data: " + responseData);

                // Get the JSON array under the "data" key.
                JsonElement jsonElement = JsonParser.parseString(responseData);
                JsonObject jsonObject = jsonElement.getAsJsonObject();
                JsonArray jsonArray = jsonObject.getAsJsonArray("data");  //
                List<HashMap<String, String>> projects = new ArrayList<>();

                for (JsonElement element : jsonArray) {
                    JsonObject jsonProject = element.getAsJsonObject();
                    HashMap<String, String> project = new HashMap<>();
                    project.put("clockifyID", jsonProject.get("clockifyID").getAsString());
                    project.put("name", jsonProject.get("name").getAsString());
                    projects.add(project);
                }
                return projects;
            } else {
                System.err.println("Failed to fetch data: " + response.message());
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        List<HashMap<String, String>> projects = fetchRSE();
        if (projects != null) {
            for (HashMap<String, String> project : projects) {
                System.out.println("Project ID: " + project.get("clockifyID") + ", Project Name: " + project.get("name"));
            }
        } else {
            System.out.println("No projects found or there was an error fetching the projects.");
        }
    }
}
