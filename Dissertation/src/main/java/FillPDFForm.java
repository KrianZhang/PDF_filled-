import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDTextField;

import java.io.File;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.time.Duration;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FillPDFForm {
    public static void main(String[] args) {
        ClockifyAPI clockifyapi = new ClockifyAPI();
        RSEAPI rseapi = new RSEAPI();


        for (int month = 1; month <= 12; month++) {
            List<HashMap<String, String>> clockifyList = clockifyapi.fetchClockify(month);
            List<HashMap<String, String>> RSEList = rseapi.fetchRSE();
            System.out.println(clockifyList);
            System.out.println(RSEList);
            List<HashMap<String, String>> projectData = MergeLists.mergeProjectMaps(clockifyList, RSEList);
            System.out.println(projectData);

            // Creating a month folder
            String monthFolder = String.format("C:/Users/13016/Desktop/test/%02d", month);
            File folder = new File(monthFolder);
            if (!folder.exists()) {
                folder.mkdirs();
            }

            int count = 0;
            for (HashMap<String, String> dataMap : projectData) {
                try {
                    PDDocument document = PDDocument.load(new File("C:/Users/13016/Desktop/Dissertation/ir-template.pdf"));
                    PDAcroForm form = document.getDocumentCatalog().getAcroForm();

                    LocalDateTime now = LocalDateTime.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    String formattedDateTime = now.format(formatter);

                    // 填充duration, name, Quantity, price字段
                    PDTextField dateField = (PDTextField) form.getField("Date_af_date");
                    PDTextField nameField = (PDTextField) form.getField("Description");
                    PDTextField quantityField = (PDTextField) form.getField("Quantity");
                    PDTextField totalField = (PDTextField) form.getField("Total");
                    PDTextField priceField = (PDTextField) form.getField("Price");
                    PDTextField TimeField = (PDTextField) form.getField("Created");

                    int duration = Integer.parseInt(dataMap.get("duration"));

                    double days = duration / 3600 / 7.4;
                    DecimalFormat format = new DecimalFormat("#.##");
                    String quantity = String.valueOf(format.format(days));
                    double money = days * 403.14;
                    String total = String.valueOf(format.format(money));
                    priceField.setValue(String.valueOf(403.14));

                    if (dateField != null && dataMap.containsKey("date")) {
                        dateField.setValue(dataMap.get("date"));
                    }
                    if (nameField != null && dataMap.containsKey("name")) {
                        nameField.setValue(dataMap.get("name"));
                    }
                    if (quantityField != null && dataMap.containsKey("duration")) {
                        quantityField.setValue(quantity);
                    }
                    if (totalField != null && dataMap.containsKey("duration")) {
                        totalField.setValue(total);
                    }
                    if (TimeField != null) {
                        TimeField.setValue(formattedDateTime);  // 设置当前日期和时间
                    }

                    // Save the modified document to a new file, with each file name based on a counter to avoid duplication
                    String outputFilePath = String.format("%s/filled-%d.pdf", monthFolder, count++);
                    document.save(outputFilePath);
                    document.close();
                    System.out.println("PDF filled successfully: " + outputFilePath);
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Failed to fill PDF for data map: " + dataMap);
                }
            }
        }
    }
}
