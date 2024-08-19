import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ListPDFFields {
    public static void main(String[] args) {
        try {
            // Loading PDF files
            PDDocument document = PDDocument.load(new File("C:/Users/13016/Desktop/Dissertation/ir-template.pdf"));
            // Get Forms in PDF
            PDAcroForm form = document.getDocumentCatalog().getAcroForm();

            if (form != null) {
                // Get and iterate over all fields
                List<PDField> fields = form.getFields();
                System.out.println("Field Names Found in the Form:");
                for (PDField field : fields) {
                    // Printing field names and other information that may be useful
                    System.out.println("Field name: " + field.getFullyQualifiedName());
                    System.out.println("Field value: " + field.getValueAsString());
                    System.out.println("Field type: " + field.getFieldType());
                }
            } else {
                System.out.println("No AcroForm found in PDF document.");
            }

            document.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
