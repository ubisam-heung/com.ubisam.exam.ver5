package com.ubisam.exam;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import com.ubisam.exam.domain.Bbs;
import com.ubisam.exam.domain.BbsList;

import io.u2ware.common.data.jpa.config.EnableRestfulJpaRepositories;

@Configuration
@EnableRestfulJpaRepositories
@EnableJpaRepositories
public class ApplicationApiConfig implements RepositoryRestConfigurer{
    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
        // /api 주소 설정
        config.setBasePath("/api");
        // id 노출 설정
        config.exposeIdsFor(Bbs.class, BbsList.class);
        config.setReturnBodyForPutAndPost(true);
        // CORS 설정 - 모든 주소 허용, 모든 메소드 허용, 쿠키 허용 안함, maxAge 설정
        cors.addMapping("/**")
            .allowedOrigins("*")
            .allowedMethods("*")
            .allowCredentials(false)
            .maxAge(999999);
    }
}
