package com.lks.test;

import com.lks.facade.MainController;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by lokkur on 6/20/2015.
 */

@ContextConfiguration(locations = {"classpath:test-application-context.xml","file:src/main/webapp/WEB-INF/spring-database.xml"})
public class AbstractTest extends AbstractTestNGSpringContextTests {

    @Resource(name = "controller")
    protected MainController controller;
}
