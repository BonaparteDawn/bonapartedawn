package github.bonapartedawn.common.utils;

import com.itextpdf.text.DocumentException;
import org.xhtmlrenderer.pdf.ITextRenderer;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * pdf工具类
 * Created by Fuzhong.Yan on 17/1/28.
 */
public class PdfUtil {
    public static boolean html2pdf(String htmlPath,String desPath) throws IOException {
        boolean res = false;
        OutputStream os = new FileOutputStream(desPath);
        ITextRenderer renderer = new ITextRenderer();
        String url = new File(htmlPath).toURI().toURL().toString();
        renderer.setDocument(url);
        renderer.layout();
        try {
            renderer.createPDF(os);
            res = true;
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        os.flush();
        os.close();
        return res;
    }
}
