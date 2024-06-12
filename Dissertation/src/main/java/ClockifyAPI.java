import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ClockifyAPI {
    private static final String API_KEY = "CLOCKIFY_KEY";
    private static final String WORKSPACE_ID = "CLOCKIFY_WORKSPACE";

    public static void main(String[] args) {
        OkHttpClient client = new OkHttpClient();
        String url = "https://api.clockify.me/api/v1/workspaces/" + WORKSPACE_ID + "/projects";

        Request request = new Request.Builder()
                .url(url)
                .addHeader("X-Api-Key", API_KEY)
                .build();

        try {
            Response response = client.newCall(request).execute();

            if (response.isSuccessful()) {
                String responseData = response.body().string();
                System.out.println("Original Response: " + responseData);

                // 解析JSON数据
                JsonElement jsonElement = JsonParser.parseString(responseData);
                JsonArray jsonArray = jsonElement.getAsJsonArray();

                for (JsonElement element : jsonArray) {
                    JsonObject jsonObject = element.getAsJsonObject();

                    // 提取你感兴趣的信息，例如项目名称和ID
                    String projectId = jsonObject.get("id").getAsString();
                    String projectName = jsonObject.get("name").getAsString();

                    System.out.println("Project ID: " + projectId + ", Project Name: " + projectName);
                }
            } else {
                System.out.println("Failed to fetch data: " + response.message());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
