package cn.yanghuisen.gateway.config;

import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Y
 */
@Configuration
public class MybatisPlusConfig {


    /**
     * 分页
     * @return
     */
    @Bean
    public PaginationInterceptor  paginationInterceptor() {
        return new PaginationInterceptor();
    }

    /**
     * 乐观锁
     * @return
     */
    @Bean
    public OptimisticLockerInterceptor OptimisticLockerInnerInterceptor() {
        return new OptimisticLockerInterceptor();
    }
}