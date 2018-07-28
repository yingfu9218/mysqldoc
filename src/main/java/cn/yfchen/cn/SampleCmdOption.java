package cn.yfchen.cn;

import org.kohsuke.args4j.Option;

public class SampleCmdOption {

    @Option(name="--host", usage="mysql-host",required = true)
    public String host;

    @Option(name="--user",  usage="mysql-user",required = true)
    public String user ;

    @Option(name="--password",  usage="mysql-password",required = true)
    public String password ;

    @Option(name="--db",  usage="mysql-database",required = true)
    public String db ;

}
