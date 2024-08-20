import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class ClockifyAPI {
    private static final String API_KEY = "YOUR-API_KEY";
    private static final String WORKSPACE_ID = "YOUR-WORKSPACE_ID";
    private OkHttpClient client = new OkHttpClient();

    public List<HashMap<String, String>> fetchClockify(int month) {
        LocalDateTime startDateTime = LocalDateTime.of(2024, month, 1, 0, 0, 0);
        LocalDateTime endDateTime = startDateTime.withDayOfMonth(startDateTime.getMonth().maxLength()).withHour(23).withMinute(59).withSecond(59);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'");
        String dateRangeStart = startDateTime.format(formatter);
        String dateRangeEnd = endDateTime.format(formatter);
        System.out.println("startDateTime :" + dateRangeStart);
        System.out.println("endDateTime :" + dateRangeEnd);

        String url = "https://reports.api.clockify.me/v1/workspaces/" + WORKSPACE_ID + "/reports/summary";

        // 构建包含 summaryFilter 的请求体
        String jsonBody = String.format("{"
                + "\"dateRangeStart\": \"%s\","
                + "\"dateRangeEnd\": \"%s\","
                + "\"summaryFilter\":{"
                +     "\"groups\":[\"PROJECT\"],"
                +     "\"sortOrder\":\"ASCENDING\","
                +     "\"sortColumn\":\"DURATION\""
                + "}"
                + "}", dateRangeStart, dateRangeEnd);

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonBody);

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("X-Api-Key", API_KEY)
                .addHeader("Content-Type", "application/json")
                .build();

        List<HashMap<String, String>> projects = new ArrayList<>();

        try {
            Response response = client.newCall(request).execute();

            if (response.isSuccessful()) {
                String responseData = response.body().string();
                JsonElement jsonElement = JsonParser.parseString(responseData);
                JsonObject jsonObject = jsonElement.getAsJsonObject();
                JsonArray jsonArray = jsonObject.getAsJsonArray("groupOne");

                // 检查 jsonArray 是否为空
                if (jsonArray != null && jsonArray.size() > 0) {
                    for (JsonElement element : jsonArray) {
                        JsonObject project = element.getAsJsonObject();
                        HashMap<String, String> projectDetails = new HashMap<>();
                        projectDetails.put("id", project.get("_id").getAsString());
                        projectDetails.put("name", project.get("name").getAsString());
                        projectDetails.put("duration", project.get("duration").getAsString());
                        projects.add(projectDetails);
                    }
                }
            } else {
                System.err.println("Request failed: " + response.code() + " - " + response.message());
                System.err.println("Failed to fetch data: " + response.message());
                return new ArrayList<>(); // 
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>(); // 
        }

        return projects;
    }

    public static void main(String[] args) {
        ClockifyAPI api = new ClockifyAPI();

        // Call the method to get the data and print it
        for (int month = 1; month <= 12; month++) {
            System.out.println("Fetching data for month " + month + ":");
            List a = api.fetchClockify(month);
            System.out.println(a); // Print blank lines as separators
        }
    }
}
