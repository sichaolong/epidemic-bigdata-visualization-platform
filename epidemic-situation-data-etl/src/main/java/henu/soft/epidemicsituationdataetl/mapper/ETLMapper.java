package henu.soft.epidemicsituationdataetl.mapper;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.SneakyThrows;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.text.SimpleDateFormat;


/**
 * 数据清洗Mapper
 */
/*
输入 :
    keyin 偏移量 LongWritable
    valuein 一行的文本 Text

输出 :
    keyout : null NullWritable
    valueout : 整理后的数据 Text
 */
public class ETLMapper extends Mapper<LongWritable, Text, NullWritable,Text> {
    // 重写父类的map
    @SneakyThrows
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        // 将value 转换成 string
        String line = value.toString(); // 一行一个json对象
        // 将line转成json对象
        JSONObject jsonObject = JSON.parseObject(line);
        // 获取城市的名称
        String name = jsonObject.getString("name");
        // 获取更新的时间
        JSONArray updateDate = jsonObject.getJSONArray("updateDate");
        // 获取疫情数据
        JSONArray list = jsonObject.getJSONArray("list");
        // 从list中获取具体的疫情数据
        // 确诊数
        JSONObject object_diagnose = JSON.parseObject(list.get(0).toString());
        JSONArray data_diagnose = object_diagnose.getJSONArray("data");

        // 治愈数
        JSONObject object_cure = JSON.parseObject(list.get(1).toString());
        JSONArray data_cure = object_cure.getJSONArray("data");

        // 死亡数
        JSONObject object_death = JSON.parseObject(list.get(2).toString());
        JSONArray data_death = object_death.getJSONArray("data");

        // 以时间为循环的基础,遍历组装数据
        // 使用字符串缓冲区封装拼接的数据
        StringBuilder builder = new StringBuilder();
        String prefix = "2022.";
        // 输入时间的格式
        SimpleDateFormat in_sdf = new SimpleDateFormat("yyyy.MM.dd");
        // 输出时间的格式
        SimpleDateFormat out_sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (int i = 0; i < updateDate.size(); i++) {
            String today = data_diagnose.get(i).toString();
            String yesterday = data_diagnose.get(i==0 ? 0 : (i-1)).toString();
            int newAdd = computeNewAdd(i, yesterday, today);
            // date 时间 data 数据
            String date = prefix + updateDate.get(i).toString();

            // 转换时间格式,和MySQL的日期格式保持统一 , 避免使用sqoop导出数据时报错
            String date_format = out_sdf.format(in_sdf.parse(date));

            String pj = name + "\t" + date_format + "\t" +
                    data_diagnose.get(i).toString() + "\t" +
                    data_cure.get(i).toString() + "\t" +
                    data_death.get(i) + "\t" +
                    newAdd + "\r";
            // 将pj 添加到 builder中
            builder.append(pj);
            // 判断时间有没有到达 2020.12.31 , 如果到了接下来就是2021年
            if ("2020.12.31".equals(date)){
                // 改变前缀
                prefix = "2021.";
            }
        }
        // 使用上下文对象写出数据
        Text valueout = new Text(builder.toString());
        context.write(NullWritable.get(),valueout);

    }

    // 计算每天新增的数量
    private int computeNewAdd(int i , String yesterday , String today){
        if(i == 0) return 0;
        int today_N = Integer.parseInt(today);
        int yesterday_N = Integer.parseInt(yesterday);
        return today_N - yesterday_N;
    }
}
