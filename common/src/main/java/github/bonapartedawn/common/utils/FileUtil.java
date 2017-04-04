package github.bonapartedawn.common.utils;

import github.bonapartedawn.common.service.security.MD5Util;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.util.Assert;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fuzhong.Yan on 17/1/19.
 */
public class FileUtil {
    /**单位:B*/
    public final static int BYTE = 1;
    /**单位:KB*/
    public final static int KB = 1024*BYTE;
    /**单位:M*/
    public final static int M = 1024*KB;
    /**单位:G*/
    public final static int G = 1024*M;
    /**
     * 读取classpath下的文件
     * @param file
     * @return
     * @throws IOException
     */
    public static InputStream readClasspath(String file) throws IOException {
        Assert.hasLength(file,"fileUtil_file_len0");
        Resource resource = new ClassPathResource(file);
        InputStream inputStream = null;
        if (resource.exists()){
            inputStream = resource.getInputStream();
        }
        return inputStream;
    }
    /**
     * 读取带有classpath前缀的文件
     * @param file
     * @return
     * @throws IOException
     */
    public static InputStream readClasspathWithClasspathPre(String file) throws IOException {
        Assert.hasLength(file,"fileUtil_file_len0");
        Resource resource = new PathMatchingResourcePatternResolver().getResource(file);
        InputStream inputStream = null;
        if (resource.exists()){
            inputStream = resource.getInputStream();
        }
        return inputStream;
    }

    /**
     * 清空该文件下的所有的数据
     * @param workspaceRootPath
     */
    public static void clearFiles(String workspaceRootPath){
        File file = new File(workspaceRootPath);
        if(file.exists()){
            clearFiles(file);
        }
    }
    /**
     * 清空该文件下的所有的数据
     * @param file
     */
    public static void clearFiles(File file){
        if(file.isDirectory()){
            File[] files = file.listFiles();
            for(int i=0; i<files.length; i++){
                clearFiles(files[i]);
                files[i].delete();
            }
        }
    }

    /**
     * 获得文件的MD5值
     * @param inputStream
     * @return
     * @throws IOException
     */
    public static String md5(InputStream inputStream) throws IOException {
        Assert.notNull(inputStream,"fileUtil_inputStream_null");
        MD5Util md5Util = new MD5Util();
        return md5Util.productSummary(inputStream);
    }
    public static boolean delete(String filePath){
        Assert.hasLength(filePath,"fileUtil_filePath_len0");
        boolean res = false;
        File file = new File(filePath);
        if (file.exists()){
            res = file.delete();
        }
        return res;
    }
    /**
     * 路径转换
     * @param postions
     * @return
     */
    public static String path(String ... postions){
        Assert.notEmpty(postions,"fileUtil_postions_empty");
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < postions.length; i++) {
            builder.append(File.separator+postions[i]);
        }
        return builder.toString();
    }
    /**
     * 在指定文件后面追加指定内容
     * @param filePath
     * @param content
     * @return
     */
    public static boolean append(String filePath,String content){
        Assert.hasLength(filePath, "fileUtil_filePath_len0");
        Assert.hasLength(content, "fileUtil_content_len0");
        List<String> contents = new ArrayList<String>();
        contents.add(content);
        return append(filePath, contents);
    }
    /**
     * 在指定文件后面追加指定内容
     * @param filePath
     * @param contents
     * @return
     */
    public static boolean append(String filePath,List<String> contents){
        Assert.hasLength(filePath, "fileUtil_filePath_len0");
        Assert.notEmpty(contents,"fileUtil_contents_len0");
        List<byte[]> bytes = new ArrayList<byte[]>();
        for (String content : contents) {
            bytes.add(content.getBytes());
        }
        return appendBytes(filePath, bytes);
    }
    /**
     * 在指定文件后面追加一行指定内容
     * @param filePath
     * @param content
     * @return
     */
    public static boolean appendLine(String filePath,String content) {
        Assert.hasLength(filePath, "fileUtil_filePath_len0");
        Assert.hasLength(content, "fileUtil_content_len0");
        List<String> contents = new ArrayList<String>();
        contents.add(content);
        return appendLine(filePath, contents);
    }
    /**
     * 在指定文件后面追加多行指定内容
     * @param filePath
     * @return
     */
    public static boolean appendLine(String filePath,List<String> contents) {
        Assert.hasLength(filePath, "fileUtil_filePath_len0");
        Assert.notEmpty(contents,"fileUtil_contents_len0");
        for (int i = 0; i < contents.size(); i++) {
            contents.set(i, "\r\n"+contents.get(i));
        }
        return append(filePath, contents);
    }
    /**
     * 在指定文件后面追加字节
     * @param filePath
     * @param bytes
     * @return
     */
    public static boolean appendBytes(String filePath,byte[] bytes){
        Assert.hasLength(filePath, "fileUtil_filePath_len0");
        if (bytes == null) {
            return false;
        }
        List<byte[]> temp = new ArrayList<byte[]>();
        temp.add(bytes);
        return appendBytes(filePath, temp);
    }
    /**
     * 在指定文件后面追加字节
     * @param filePath
     * @param bytes
     * @return
     */
    public static boolean appendBytes(String filePath,List<byte[]> bytes){
        Assert.hasLength(filePath, "fileUtil_filePath_len0");
        Assert.notEmpty(bytes, "fileUtil_bytes_len0");
        boolean res = false;
        RandomAccessFile randomFile = null;
        try {
            randomFile = new RandomAccessFile(filePath, "rw");
            for (byte[] content : bytes) {
                randomFile.seek(randomFile.length());
                randomFile.write(content);
            }
            res = true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            if(randomFile != null){
                try {
                    randomFile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return res;
    }
    /**
     * 读取指定位置后面指定长度的内容
     * @param filePath
     * @param skipBytesLength
     * @param readLength
     * @return
     */
    public static byte[] readBytes(String filePath,long skipBytesLength,int readLength){
        Assert.hasLength(filePath, "fileUtil_filePath_len0");
        if (skipBytesLength < 0) {
            skipBytesLength = 0;
        }
        if (readLength < 0 ) {
            readLength = 0;
        }
        byte[] bytes = null;
        RandomAccessFile randomFile = null;
        try {
            randomFile = new RandomAccessFile(filePath, "r");
            randomFile.seek(skipBytesLength);
            bytes = new byte[readLength];
            int len = randomFile.read(bytes);
            if (len != readLength) {
                byte[] temp = new byte[len];
                System.arraycopy(bytes, 0, temp, 0, len);
                bytes = temp;
            }
        } catch (IOException e) {
            e.printStackTrace();
            bytes = null;
        } finally{
            if(randomFile != null){
                try {
                    randomFile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    bytes = null;
                }
            }
        }
        return bytes;
    }
    /**
     * 将所有行的数据读取出来
     * @param filePath
     * @return
     * @throws IOException
     */
    public static List<String> readLines(String filePath) throws IOException {
        Assert.hasLength(filePath, "fileUtil_filePath_len0");
        return readLines(filePath, "UTF-8");
    }
    /**
     * 将所有行的数据读取出来
     * @param filePath
     * @param charSet 文件编码
     * @return
     * @throws IOException
     */
    public static List<String> readLines(String filePath, String charSet) throws IOException {
        Assert.hasLength(filePath, "fileUtil_filePath_len0");
        Assert.hasLength(charSet, "fileUtil_charSet_len0");
        List<String> res = new ArrayList<String>();
        BufferedReader br = convert2BufferedReader(filePath, charSet);
        if (br != null) {
            String line = null;
            while ((line = br.readLine()) != null) {
                res.add(line);
            }
            br.close();
        }
        return res;
    }
    /**
     * 获得数据流
     * @param filePath
     * @return
     * @throws FileNotFoundException
     */
    public static InputStream convert2InputStream(String filePath) throws FileNotFoundException{
        Assert.hasLength(filePath, "fileUtil_filePath_len0");
        return new FileInputStream(filePath);
    }
    /**
     * 获得字符读取器
     * @param filePath
     * @param charSet
     * @return
     * @throws FileNotFoundException
     * @throws UnsupportedEncodingException
     */
    public static Reader convert2Reader(String filePath,String charSet) throws FileNotFoundException, UnsupportedEncodingException{
        Assert.hasLength(filePath, "fileUtil_filePath_len0");
        Assert.hasLength(charSet, "fileUtil_charSet_len0");
        File file = new File(filePath);
        Reader reader = null;
        if (file.exists()) {
            InputStream inputStream = convert2InputStream(filePath);
            if (inputStream != null) {
                reader = new InputStreamReader(inputStream , charSet);
            }
        }else {
            throw new FileNotFoundException("FileNotFoundException");
        }
        return reader;
    }
    /**
     * 获得文件的缓存数据流
     * @param filePath
     * @return
     * @throws IOException
     */
    public static BufferedInputStream convert2BufferdInputStream(String filePath) throws IOException{
        Assert.hasLength(filePath, "fileUtil_filePath_len0");
        BufferedInputStream bufferedInputStream = null;
        InputStream inputStream = convert2InputStream(filePath);
        if (inputStream != null) {
            bufferedInputStream = IOUtils.convert2Buffer(inputStream);
        }
        return bufferedInputStream;
    }
    /**
     * 获得文件字符的阅读器
     * @param filePath
     * @param charSet
     * @return
     * @throws IOException
     */
    public static BufferedReader convert2BufferedReader(String filePath,String charSet) throws IOException{
        Assert.hasLength(filePath, "fileUtil_filePath_len0");
        Assert.hasLength(charSet, "fileUtil_charSet_len0");
        BufferedReader bufferedReader = null;
        Reader reader = convert2Reader(filePath, charSet);
        if (reader != null) {
            bufferedReader = IOUtils.convert2Buffer(reader);
        }
        return bufferedReader;
    }
    /**
     * 获得文件的输出流
     * @param filePath
     * @return
     * @throws IOException
     */
    public static OutputStream convert2OutputStream(String filePath) throws IOException{
        Assert.hasLength(filePath, "fileUtil_filePath_len0");
        return new FileOutputStream(filePath);
    }
    /**
     * 获得文件的字符写入器
     * @param filePath
     * @param charSet
     * @return
     * @throws IOException
     */
    public static Writer convert2Writer(String filePath,String charSet) throws IOException{
        Assert.hasLength(filePath, "fileUtil_filePath_len0");
        Assert.hasLength(charSet, "fileUtil_charSet_len0");
        File file = new File(filePath);
        Writer writer = null;
        if (file.exists()) {
            OutputStream outputStream = convert2OutputStream(filePath);
            if (outputStream != null) {
                writer = new OutputStreamWriter(outputStream, charSet);
            }
        }else {
            throw new FileNotFoundException("FileNotFoundException");
        }
        return writer;
    }
    /**
     * 获得文件的缓冲输出数据流
     * @param filePath
     * @return
     * @throws IOException
     */
    public static BufferedOutputStream convert2BufferedOutputStream(String filePath) throws IOException{
        Assert.hasLength(filePath, "fileUtil_filePath_len0");
        BufferedOutputStream bufferedOutputStream = null;
        OutputStream outputStream = convert2OutputStream(filePath);
        if (outputStream != null) {
            bufferedOutputStream = IOUtils.convert2Buffer(outputStream);
        }
        return bufferedOutputStream;
    }
    /**
     * 获得文件的缓冲字符写入器
     * @param filePath
     * @param charSet
     * @return
     * @throws IOException
     */
    public static BufferedWriter convert2BufferedWriter(String filePath,String charSet) throws IOException{
        Assert.hasLength(filePath, "fileUtil_filePath_len0");
        Assert.hasLength(charSet, "fileUtil_charSet_len0");
        BufferedWriter bufferedWriter = null;
        Writer writer = convert2Writer(filePath, charSet);
        if (writer != null) {
            bufferedWriter = IOUtils.convert2Buffer(writer);
        }
        return bufferedWriter;
    }

}
