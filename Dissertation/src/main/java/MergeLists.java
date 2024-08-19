import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MergeLists {

    public static List<HashMap<String, String>> mergeProjectMaps(
            List<HashMap<String, String>> listOne, List<HashMap<String, String>> listTwo) {
        HashMap<String, HashMap<String, String>> projectMap = new HashMap<>();

        // Process the first list, adding it to the map with 'id' as the key
        for (HashMap<String, String> project : listOne) {
            if (project.containsKey("id")) {
                projectMap.put(project.get("id"), new HashMap<>(project));
            }
        }

        // Processing the second list, updating items
        for (HashMap<String, String> project : listTwo) {
            if (project.containsKey("clockifyID")) {
                String clockifyID = project.get("clockifyID");
                if (projectMap.containsKey(clockifyID)) {
                    HashMap<String, String> existingProject = projectMap.get(clockifyID);
                    existingProject.putAll(project);
                }
            }
        }

        return new ArrayList<>(projectMap.values());
    }
}

