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
    private static final String API_URL = "https://rseadmin.azurewebsites.net/api/projects?filters[stage][$nei]=completed";
    private static final String API_TOKEN = "e7b992ecdb5db9719313414f824e459b62cd0043e7addd78e14427ef8a279e1b4e5d61d8f1ed14d33c7171dba9a980b5793e4c9d6a87263dab11c293ac23c4a1881d0144f44db70a8b9f9d42e333c3eefd3edd966dc6e77eb31e5f54d860bf0921cbfa3ec1fc0e67a72e78e596a801222c8ef162bf22057ed11dc640600fcc4b";

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
