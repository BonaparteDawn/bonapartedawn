package github.bonapartedawn.common.utils;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 图片处理工具类
 */
public class ImageUtil {
    /**
     * 压缩到指定长度(并且保证不变形)
     * @time 2016年12月7日
     * @author Fuzhong.Yan
     */
    public static void zipImageFile(InputStream source,OutputStream target,Integer widthValue,Integer heightValue,float quality) throws IOException{
        Image image = ImageIO.read(source);
        int w = image.getWidth(null);
        int h = image.getHeight(null);
        Integer width =  w;
        Integer height =  h;
        double percent = 1.0*width / height;
        if (widthValue == null && heightValue != null) {
            widthValue = width;
        }else if (widthValue != null && heightValue == null) {
            heightValue =height;
        }else if (widthValue == null && heightValue == null) {
            widthValue = Integer.valueOf(String.valueOf(width));
            heightValue =Integer.valueOf(String.valueOf(height));
        }
        if (widthValue <= 0) {
            widthValue =Integer.valueOf(String.valueOf(width));
        }
        if (heightValue <= 0) {
            heightValue = Integer.valueOf(String.valueOf(height));
        }
        double percentValue = 1.0*widthValue/heightValue;
        if (percent < percentValue) {
            height = heightValue;
            width = (int) (heightValue * percent);
        }else {
            width = widthValue;
            height = (int) (widthValue/percent);
        }
        /** 宽,高设定 */
        BufferedImage tag = new BufferedImage(Integer.valueOf(String.valueOf(width)), Integer.valueOf(String.valueOf(height)), BufferedImage.TYPE_INT_RGB);
        tag.getGraphics().drawImage(image, 0, 0, Integer.valueOf(String.valueOf(width)), Integer.valueOf(String.valueOf(height)), null);
        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(target);
        JPEGEncodeParam jep = JPEGCodec.getDefaultJPEGEncodeParam(tag);
        /** 压缩质量 */
        jep.setQuality(quality, true);
        encoder.encode(tag, jep);
    }

    /**
     * 压缩到指定比例
     * @time 2016年12月7日
     * @author Fuzhong.Yan
     */
    public static void zipImageFile(InputStream source,OutputStream target,Double widthPercentage,Double heightPercentage,float quality) throws IOException{
        Image image = ImageIO.read(source);
        int w = image.getWidth(null);
        int h = image.getHeight(null);
        int width = w;
        int height = h;
        if (!(widthPercentage == null || widthPercentage >1 || widthPercentage <=0)) {
            width = (int) (width * widthPercentage);
        }
        if (!(heightPercentage == null || heightPercentage >1 || heightPercentage <=0)) {
            height = (int) (height * heightPercentage);
        }
        /** 宽,高设定 */
        BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        tag.getGraphics().drawImage(image, 0, 0, width, height, null);
        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(target);
        JPEGEncodeParam jep = JPEGCodec.getDefaultJPEGEncodeParam(tag);
        /** 压缩质量 */
        jep.setQuality(quality, true);
        encoder.encode(tag, jep);
    }
}
