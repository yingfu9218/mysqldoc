package cn.yfchen.cn;

import java.util.HashMap;
import java.util.List;

public class MarkDownBuild {
    private String table_header="|字段名称|字段类型|字段含义|\n|:---:|:---:|:---:|\n";
    private  String table_content_template="|%s|%s|%s|\n";
    public  String buildMarkdown(HashMap databaseAllInfo){
        String text="# 数据库文档\n\n";
        text+="<a name=\"返回顶部\"></a>\n\n## 数据表列表\n\n";
        List<HashMap> tablelists= (List<HashMap>) databaseAllInfo.get("tablelists");
        HashMap tableColumnlists = (HashMap) databaseAllInfo.get("tableColumnlists");
//        数据表列表
        for (HashMap table :tablelists) {
            text = text + "* [" + table.get("TABLE_NAME")+ '(' + table.get("TABLE_COMMENT") + ")](#" + table.get("TABLE_NAME") +"_pointer)\n\n";
//            System.out.println(table.get("TABLE_NAME"));
        }
        text += "\n\n## 数据表说明\n\n";
//        数据表说明
        for (HashMap table :tablelists) {
            text = text + "* [" + table.get("TABLE_NAME")+ '(' + table.get("TABLE_COMMENT") + ")](#" + table.get("TABLE_NAME") +"_pointer)\n\n";
            String tableName= (String) table.get("TABLE_NAME");
            String tableCommnet= (String) table.get("TABLE_COMMENT");
            text = text + "<a name=\""+tableName+"_pointer\" ></a>\n\n";
            text = text + "* " + tableName + "表(" +tableCommnet + ")[↑](#返回顶部)\n\n";
            text += table_header;
            List<HashMap> columns= (List<HashMap>) tableColumnlists.get(tableName);
            for (HashMap column:columns){
                String columnComment= (String) column.get("COLUMN_COMMENT");
                columnComment=columnComment.replaceAll("\r|\n","");

                text = text + "|" + column.get("COLUMN_NAME") + '|' +column.get("DATA_TYPE") + '|' +columnComment + "|";
                text += "\n";
            }

        }
//        System.out.println(text);
        return  text;


    }
}
