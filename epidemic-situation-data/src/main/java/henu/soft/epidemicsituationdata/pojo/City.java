package henu.soft.epidemicsituationdata.pojo;

import lombok.Data;
import java.util.List;

/*
City
国内 : 省/直辖市
国外 : 整个国家
 */
@Data
public class City {
    private String name; // 城市名称
    private List<String> updateDate; // 疫情更新时间
    private List<Object> list; // 疫情数据对象
}

