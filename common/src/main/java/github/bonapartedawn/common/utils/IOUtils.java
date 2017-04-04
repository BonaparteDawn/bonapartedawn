package github.bonapartedawn.common.utils;

import github.bonapartedawn.common.enums.Constant;
import org.springframework.util.Assert;

import java.io.*;

/**
 * Created by Fuzhong.Yan on 17/1/3.
 */
public class IOUtils {
    /**
     * 获取输入流中的指定块的字节数据流
     * @param inputStream
     * @param skip_size
     * @param block_size
     * @param untilNull(true:直到数据流结束,否则必须返回指定块大小的数据)
     * @return
     * @throws IOException
     */
    public static InputStream copy(InputStream inputStream,long skip_size,int block_size,boolean untilNull) throws IOException {
        if (inputStream == null){
            return null;
        }
        if (skip_size < 0){
            skip_size = 0;
        }
        if (block_size < 0){
            block_size = 1*FileUtil.M;
        }
        byte[] bytes = null;
        if (untilNull){
            bytes = copy2Bytes(inputStream,skip_size,block_size,true);
        }else {
            bytes = copy2Bytes(inputStream,skip_size,block_size);
        }
        if (bytes == null){
            return null;
        }
        return new ByteArrayInputStream(bytes);
    }
    /**
     * 获取输入流中的指定块的字节数据流
     * @param inputStream
     * @param skip_size
     * @param block_size
     * @return
     * @throws IOException
     */
    public static InputStream copy(InputStream inputStream,long skip_size,int block_size) throws IOException {
        if (inputStream == null){
            return null;
        }
        if (skip_size < 0){
            skip_size = 0;
        }
        if (block_size < 0){
            block_size = 1*FileUtil.M;
        }
        byte[] bytes = copy2Bytes(inputStream,skip_size,block_size);
        if (bytes == null){
            return null;
        }
        return new ByteArrayInputStream(bytes);
    }
    /**
     * 获取输入流中的指定块的字节数据流
     * @param inputStream
     * @param skip_size
     * @param block_size
     * @return
     * @throws IOException
     */
    public static byte[] copy2Bytes(InputStream inputStream,long skip_size,int block_size) throws IOException {
        if (inputStream == null){
            return null;
        }
        if (skip_size < 0){
            skip_size = 0;
        }
        if (block_size < 0){
            block_size = 1*FileUtil.M;
        }
        inputStream.skip(skip_size);
        byte[] bytes = new byte[block_size];
        int size = inputStream.read(bytes);
        if (size < 0){
            return null;
        }
        byte[] temp = new byte[size];
        System.arraycopy(bytes,0,temp,0,size);
        return temp;
    }
    /**
     * 获取输入流中的指定块的字节数据流
     * @param inputStream
     * @param skip_size
     * @param block_size
     * @param untilNull(true:直到数据流结束,否则必须返回指定块大小的数据)
     * @return
     * @throws IOException
     */
    public static byte[] copy2Bytes(InputStream inputStream,long skip_size,int block_size,boolean untilNull) throws IOException {
        if (inputStream == null){
            return null;
        }
        if (skip_size < 0){
            skip_size = 0;
        }
        if (block_size < 0){
            block_size = 1*FileUtil.M;
        }
        byte[] res = null;
        if (untilNull){
            int remain = block_size;
            while (remain > 0){
                byte [] temp= IOUtils.copy2Bytes(inputStream,skip_size,remain);
                if (temp == null){
                    break;
                }
                res = ByteUtils.append(res,temp);
                remain-=temp.length;
                skip_size = 0;
            }
        }else {
            res = IOUtils.copy2Bytes(inputStream,skip_size,block_size);
        }
        return res;
    }
    /**
     * 将输入流里面的数据写入到输出流里面
     * @param inputStream
     * @param outputStream
     * @param max_block_num
     * @param block_size
     * @return
     * @throws IOException
     */
    public static boolean write2outputStream(InputStream inputStream, OutputStream outputStream,long max_block_num,int block_size) throws IOException {
        if (inputStream == null || outputStream == null){
            return false;
        }
        if (max_block_num < 0){
            max_block_num = 1;
        }
        if (block_size < 0){
            block_size = 1*FileUtil.M;
        }
        for (int i =0; i < max_block_num ; i++){
            byte[] bytes = new byte[block_size];
            int a_size = inputStream.read(bytes);
            if (a_size < 0){
                break;
            }
            byte[] temp = new byte[a_size];
            System.arraycopy(bytes,0,temp,0,a_size);
            outputStream.write(temp);
            outputStream.flush();
        }
        return true;
    }
    /**
     * 将输入流里面的数据写入到输出流里面
     * @param inputStream
     * @param outputStream
     * @return
     * @throws IOException
     */
    public static boolean write2outputStream(InputStream inputStream, OutputStream outputStream) throws IOException {
        boolean res = false;
        if (inputStream == null || outputStream == null){
            return false;
        }
        byte[] bytes = new byte[inputStream.available()];
        int a_size = inputStream.read(bytes);
        if (a_size > 0){
            outputStream.write(bytes);
            outputStream.flush();
            res = true;

        }
        return res;
    }
    /**
     * 将输入流里面的数据写入到输出流里面
     * @param bytes
     * @param outputStream
     * @return
     * @throws IOException
     */
    public static boolean write2outputStream(byte[] bytes, OutputStream outputStream) throws IOException {
        if (bytes == null || outputStream == null){
            return false;
        }
        outputStream.write(bytes);
        outputStream.flush();
        return true;
    }

    /**
     * 将数据流里面的数据读取出来,保存成字符串
     * @param inputStream
     * @return
     */
    public static String read(InputStream inputStream) throws IOException {
        if (inputStream == null){
            return null;
        }
        String res = null;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        if (write2outputStream(inputStream, outputStream)){
            res = outputStream.toString();
        }
        outputStream.close();
        return res;
    }

    /**
     * 将数据流里面的数据全部读取出来
     */
    public static byte[] copy2Bytes(InputStream inputStream) throws IOException {
        if (inputStream == null){
            return null;
        }
        byte[] bytes = new byte[inputStream.available()];
        inputStream.read(bytes);
        return bytes;
    }

    /**
     * 将字符写入器转换为缓冲字符写入器
     * @param writer
     * @return
     * @throws IOException
     */
    public static BufferedWriter convert2Buffer(Writer writer) throws IOException{
        Assert.notNull(writer, "fileUtil_writer_null");
        BufferedWriter bufferedWriter = null;
        if (writer != null) {
            bufferedWriter = new BufferedWriter(writer);
        }
        return bufferedWriter;
    }
    /**
     * 将普通数据流转换为缓存数据流
     * @return
     * @throws IOException
     */
    public static BufferedOutputStream convert2Buffer(OutputStream outputStream) throws IOException{
        Assert.notNull(outputStream, "fileUtil_outputStream_null");
        BufferedOutputStream bufferedOutputStream = null;
        if (outputStream != null) {
            bufferedOutputStream = new BufferedOutputStream(outputStream);
        }
        return bufferedOutputStream;
    }
    /**
     * 将普通数据流转换为缓存数据流
     * @param inputStream
     * @return
     * @throws IOException
     */
    public static BufferedInputStream convert2Buffer(InputStream inputStream) throws IOException{
        Assert.notNull(inputStream, "fileUtil_inputStream_null");
        BufferedInputStream bufferedInputStream = null;
        if (inputStream != null) {
            bufferedInputStream = new BufferedInputStream(inputStream);
        }
        return bufferedInputStream;
    }
    /**
     * 将字符阅读器转换为缓冲字符阅读器
     * @return
     * @throws IOException
     */
    public static BufferedReader convert2Buffer(Reader reader) throws IOException{
        Assert.notNull(reader, "fileUtil_reader_null");
        BufferedReader bufferedReader = null;
        if (reader != null) {
            bufferedReader = new BufferedReader(reader);
        }
        return bufferedReader;
    }

    /**
     * 将字节输入流转为字符阅读器
     * @param inputStream
     * @return
     * @throws IOException
     */
    public static  Reader convert2Reader(InputStream inputStream) throws IOException{
        Assert.notNull(inputStream, "fileUtil_inputStream_null");
        InputStreamReader reader = new InputStreamReader(inputStream);
        return reader;
    }

    /**
     * 将字节输出流转为字符输出器
     * @param outputStream
     * @return
     * @throws IOException
     */
    public static Writer convert2Writer(OutputStream outputStream) throws IOException{
        Assert.notNull(outputStream, "fileUtil_outputStream_null");
        OutputStreamWriter writer = new OutputStreamWriter(outputStream);
        return writer;
    }
}
