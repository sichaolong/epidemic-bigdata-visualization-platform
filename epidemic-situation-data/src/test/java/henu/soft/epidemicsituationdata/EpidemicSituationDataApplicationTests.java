package henu.soft.epidemicsituationdata;

import henu.soft.epidemicsituationdata.config.URLConfig;
import henu.soft.epidemicsituationdata.service.CityEpidemicDataService;
import henu.soft.epidemicsituationdata.utils.CityUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EpidemicSituationDataApplicationTests {

    // 注入 URL配置对象
    @Autowired
    private URLConfig urlConfig;

    // 注入service类的对象
    @Autowired
    private CityEpidemicDataService cityEpidemicDataService;


    @Test
    void contextLoads() {
    }

    @Test
    public void getData(){
        // 爬取所有的国内数据
        String[] gn_city = CityUtils.gn_city;
        for (String gn : gn_city) {
            System.out.println("----------------"+gn+"---------------");
            cityEpidemicDataService.getData2File(urlConfig.getGurl(),gn,"E:\\项目\\epidemic-bigdata-visualization-platform\\epidemic-situation-data\\src\\main\\resources\\gn.txt");
        }
        //爬取所有国外城市的数据
//		String[] hw_city = CityUtils.hw_city;
//		for (String hw : hw_city) {
//			System.out.println("----------------"+hw+"---------------");
//			cityService.getData2File(urlConfig.getHurl(),hw,"D:\\教学工作\\实训\\2021\\山西工程技术学院\\疫情数据\\data\\hw.txt");
//		}
    }

}


