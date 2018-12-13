package com.xxlllq.dataprovider.config;

import com.xxlllq.dataprovider.util.DateUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.File;

/**
 * @类名称： RegularTaskConfiguration
 * @类描述：系统中的相关定时任务（比如：数据库备份）
 * @创建人：xiangxl
 * @创建时间：2018/12/12 10:10
 * @version：
 */
@Configuration
@EnableScheduling
public class RegularTaskConfiguration {
    private Logger logger = Logger.getLogger(this.getClass());



    //可多个，自己定义就行
}
