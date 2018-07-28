package cn.yfchen.cn;

import com.alibaba.fastjson.JSON;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.io.File;
import java.sql.*;
import java.util.HashMap;
import java.util.List;


/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        SampleCmdOption option=new SampleCmdOption();
        CmdLineParser parser = new CmdLineParser(option);
        try{
            parser.parseArgument(args);
            if (option.help) {
                parser.printUsage(System.err);
                System.exit(0);
            }

            System.out.println("host:"+option.host);
            System.out.println("user:"+option.user);
            System.out.println("password:"+option.password);
            System.out.println("db:"+option.db);
            DatabaseInfo databaseInfo=new DatabaseInfo(option.host,option.user,option.password,option.db);
            HashMap tablelistsInfo= databaseInfo.getDatabaseAllInfo();
            MarkDownBuild markDownBuild=new MarkDownBuild();
            //解析markdown成markdown
            String text= markDownBuild.buildMarkdown(tablelistsInfo);
            //保存markdown 文件
            FileSave.saveMarkdown(option.db,text);
//            //保存html 文件
            FileSave.saveHtml(option.db,text);

            System.out.println("success");
        }catch (Exception e){
            parser.printUsage(System.err);
            System.out.println("Error in main: " + e.getMessage());

        }

    }


}
