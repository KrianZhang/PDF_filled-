import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ListPDFFields {
    public static void main(String[] args) {
        try {
            // 加载PDF文件
            PDDocument document = PDDocument.load(new File("C:/Users/13016/Desktop/Dissertation/ir-template.pdf"));
            // 获取PDF中的表单
            PDAcroForm form = document.getDocumentCatalog().getAcroForm();

            if (form != null) {
                // 获取并遍历所有字段
                List<PDField> fields = form.getFields();
                System.out.println("Field Names Found in the Form:");
                for (PDField field : fields) {
                    // 打印字段名和其他可能有用的信息
                    System.out.println("Field name: " + field.getFullyQualifiedName());
                    System.out.println("Field value: " + field.getValueAsString());
                    System.out.println("Field type: " + field.getFieldType());
                }
            } else {
                System.out.println("No AcroForm found in PDF document.");
            }

            // 关闭文档
            document.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
