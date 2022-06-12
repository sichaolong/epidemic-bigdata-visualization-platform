package henu.soft.epidemicsituationdatachinahive.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class MapController {

    // 注入 JdbcTemplate  , 用于连接数据库
    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Controller层的类 , 用于处理来自于前段页面的访问请求
    @GetMapping(value = "/mapData")
    public Object mapData(String queryDate){

        // 向数据库中请求数据
        //1, 编写sql
        String sql = "select * from gn where gn.cdate ='"+queryDate+"'";
        log.info("查询数据的SQL为：{}",sql);
        // 2, 请求数据
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql);

        // 创建一个HashMap 封装结果
        HashMap<String, ArrayList> resultMap = new HashMap<>();
        if (maps.size() >0){
            // 创建两个集合, 用于保存 城市的名称 和 确诊数
            ArrayList<String> cname = new ArrayList<>();
            ArrayList<Integer> diagnose = new ArrayList<>();
            // 遍历maps 将数据取出 , 添加到集合中
            for (int i = 0; i < maps.size(); i++) {
                cname.add(maps.get(i).get("gn.cname").toString());
                diagnose.add(Integer.parseInt(maps.get(i).get("gn.diagnose").toString()));
            }
            // 将上面两个集合添加到map中
            resultMap.put("cname",cname);
            resultMap.put("diagnose",diagnose);
        }
        // 返回map
        return resultMap;
    }
}
