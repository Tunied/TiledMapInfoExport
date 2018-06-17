package utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class FileUtils {

    /**
     * 取得文件名去掉后缀后的名字,比如example.xls 返回example
     *
     * @param _fullName
     * @return
     */
    public static String GetFileNameWithoutPrefix(String _fullName) {
        String prefix = _fullName.substring(_fullName.lastIndexOf("."));
        int num = prefix.length();//得到后缀名长度
        return _fullName.substring(0, _fullName.length() - num);
    }


    public static void writeStringToFile(String _str, String _filePath) {
        try {
            File f = new File(_filePath);
            if (f.exists()) {
                f.delete();
            }
            f.createNewFile();
            OutputStreamWriter ow = new OutputStreamWriter(new FileOutputStream(f, true), "UTF-8");
            ow.write(_str);
            ow.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
