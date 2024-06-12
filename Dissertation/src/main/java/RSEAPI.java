import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RSEAPI {
    public static void main(String[] args) {
        String url = "RSE_url"; // API URL
        String apiToken = "API_TOKEN"; // API Token

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", "Bearer " + apiToken)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                String responseData = response.body().string(); // Extract response data as String
                System.out.println("Response Data: " + responseData);
            } else {
                System.out.println("Failed to fetch data. Response code: " + response.code());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
