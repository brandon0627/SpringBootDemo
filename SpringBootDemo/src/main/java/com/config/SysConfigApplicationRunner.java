/**
 * 
 */
package com.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @author brandon
 *
 */
@Component
public class SysConfigApplicationRunner implements ApplicationRunner {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	

	@Override
	public void run(ApplicationArguments args) throws Exception {
		logger.info("初始化数据");

	}

}
