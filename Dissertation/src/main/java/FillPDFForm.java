import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDTextField;

import java.io.File;

public class FillPDFForm {
    public static void main(String[] args) {
        try {
            // 打开现有的PDF文档
            PDDocument document = PDDocument.load(new File("C:/Users/13016/Desktop/Dissertation/ir-template.pdf"));
            // 获取文档中的表单
            PDAcroForm form = document.getDocumentCatalog().getAcroForm();

            // 假设我们获取了数据并要填充表单域
            String dataToFill = "data";

            // 填充表单域，假设表单域的名称是 'exampleField'
            PDTextField field = (PDTextField) form.getField("exampleField");
            field.setValue(dataToFill);

            // 保存修改后的文档到新文件
            document.save("path/to/your/filled.pdf");
            document.close();

            System.out.println("PDF filled successfully");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
