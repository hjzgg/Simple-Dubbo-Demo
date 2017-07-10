package com.hjzgg.simulation.consumer.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Created by hujunzheng on 2017/7/7.
 */

@Configuration
@Import(Registrar.class)
public class Config {
}
