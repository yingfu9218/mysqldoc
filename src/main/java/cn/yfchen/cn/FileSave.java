package cn.yfchen.cn;
import org.pegdown.Extensions;
import org.pegdown.PegDownProcessor;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileSave {
    /**
     * 保存markdown 文档
     * @param db
     * @param text
     * @throws IOException
     */
    public static void  saveMarkdown(String db,String text) throws IOException{

        mkdirIfNotExist("./md");
        FileWriter mdFile = new FileWriter("./md/" + db+".md");
        mdFile.write(text);
        mdFile.close();
        System.out.println("markdonw文档生成成功：md/"+db+".md");
    }
    /**
     * 判断目录是否存在，不存在则创建
     * @param path
     */
    public static void mkdirIfNotExist(String path) {
        File dirpath = new File(path);
        if (dirpath.exists()) {
        } else {
            dirpath.mkdir();
        }
    }
    /**
     * 保存Html文档
     * @param db
     * @param text
     */
    public  static  void  saveHtml(String db,String text) throws IOException {
        //postContent 是 markdown 格式的文件读取到的字符串
        PegDownProcessor md =new PegDownProcessor(Extensions.ALL_WITH_OPTIONALS);
        String html= md.markdownToHtml(text);

        mkdirIfNotExist("./html");
        FileWriter mdFile = new FileWriter("./html/" + db+".html");
        mdFile.write(html);
        mdFile.close();
        System.out.println("html文档生成成功：html/"+db+".html");
    }
}
