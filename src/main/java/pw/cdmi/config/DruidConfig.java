package pw.cdmi.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
@Configuration
public class DruidConfig {
    @Value("${spring.datasource.druid.connection-init-sqls")
    private List<String> connectionInitSqls;

    @Bean
    public DruidDataSource dataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setConnectionInitSqls(connectionInitSqls);
        return dataSource;
    }
}
