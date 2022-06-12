package henu.soft.epidemicsituationdata.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import henu.soft.epidemicsituationdata.pojo.City;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;

@Service
public class CityEpidemicDataService {

    @Autowired
    private RestTemplate restTemplate;

    // 编写一个爬虫的方法 , 将数据写出到文件
    public void getData2File(String url,String cityName,String fileName){
        url += "&area=" + cityName;
        String template = restTemplate.getForObject(url, String.class);
        System.out.println(template);
        // 将拿到的数据,解析封装为City对象
        convertAndWriteOutToFile(template,fileName);
    }

    private void convertAndWriteOutToFile(String template, String fileName) {
        // 解析JSON fastJSON
        // 将 template 转换成JSON 对象
        JSONObject object = JSON.parseObject(template);
        String data = object.getString("need-etl-data");
        // 把 data 转换成JSONArray
        JSONArray jsonArray = JSON.parseArray(data);
        // 如果jsonArray不为空才处理
        if (jsonArray != null){
            // 从jsonArray中取出对象,并转成字符串
            String string = jsonArray.get(0).toString();
            // 将string转成对象,
            JSONObject jsonObject = JSON.parseObject(string);
            // 使用 jsonObject 获取 trend , 从而得到updateDate 和 list
            String trend = jsonObject.getString("trend");
            // 把trend封装成City对象
            City city = JSON.parseObject(trend, City.class);
            // 为 city 设置name
            city.setName(jsonObject.getString("name"));
            // 将city转成JSON字符串
            String jsonString = JSON.toJSONString(city)+"\n";// 写出一行以后,换行
            // 写出到文件 FileUtils
            try {
                FileUtils.writeStringToFile(new File(fileName),jsonString,"utf-8",true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }






}
