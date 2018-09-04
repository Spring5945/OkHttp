package com.zhengyu.sh.configuration;

import okhttp3.Dispatcher;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.internal.Util;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.net.ProxySelector;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhengyu.nie on 2018/9/1.
 */
@Configuration
@ComponentScan("com.zhengyu.sh")
public class OkHttpConfiguration {
    @Bean
    public OkHttpClient okHttpClient(@Qualifier("okHttpLoggingInterceptor") Interceptor interceptor, @Qualifier("okHttpProxySelector") ProxySelector proxySelector) {

        Dispatcher dispatcher = new Dispatcher();
        dispatcher.setMaxRequests(64);
        dispatcher.setMaxRequestsPerHost(10);

        return new OkHttpClient.Builder()
                .connectTimeout(3, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .addInterceptor(interceptor)
                .proxySelector(proxySelector)
                .dispatcher(dispatcher)
                .build();
    }
}
