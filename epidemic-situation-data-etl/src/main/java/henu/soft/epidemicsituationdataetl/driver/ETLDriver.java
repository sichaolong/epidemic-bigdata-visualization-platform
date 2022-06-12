package henu.soft.epidemicsituationdataetl.driver;

import henu.soft.epidemicsituationdataetl.mapper.ETLMapper;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * 数据清洗Driver
 */

public class ETLDriver {
    public void etl(String input,String output) throws IOException, ClassNotFoundException, InterruptedException {
        System.setProperty("hadoop.home.dir","E:\\hadoop-client\\hadoop-windows\\winutils\\winutils-ec63c2d802dd48e68582517bd623ba1a11eb34f9\\hadoop-3.2.1");

        Configuration conf = new Configuration();
        // 1, 创建job
        Job job = Job.getInstance(conf);

        // 2,配置job
        job.setJarByClass(ETLDriver.class); // 指定运行主类的类型
        job.setMapperClass(ETLMapper.class); // 指定mapper的类型

        // 指定输出的类型,只用指定map的输出类型
        job.setMapOutputKeyClass(NullWritable.class);
        job.setMapOutputValueClass(Text.class);

        // 指定输入输出的路径
        FileInputFormat.setInputPaths(job,new Path(input));
        FileOutputFormat.setOutputPath(job,new Path(output));

        // 3,提交job
        boolean completion = job.waitForCompletion(true);
        System.out.println(completion ? "清洗成功!" : "清洗失败!");
    }
}
