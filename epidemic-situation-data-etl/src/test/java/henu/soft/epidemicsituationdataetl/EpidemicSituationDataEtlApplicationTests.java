package henu.soft.epidemicsituationdataetl;

import henu.soft.epidemicsituationdataetl.driver.ETLDriver;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
class EpidemicSituationDataEtlApplicationTests {

    @Test
    void contextLoads() {
    }
    @Test
    public void testETL() throws InterruptedException, IOException, ClassNotFoundException {
        // 创建Driver的对象
        ETLDriver driver = new ETLDriver();
        // 处理国内数据
        String input = "E:\\项目\\epidemic-bigdata-visualization-platform\\epidemic-situation-data\\src\\main\\resources\\need-etl-data";
        String output = "E:\\项目\\epidemic-bigdata-visualization-platform\\epidemic-situation-data\\src\\main\\resources\\etl";
        driver.etl(input,output);

    }

}



