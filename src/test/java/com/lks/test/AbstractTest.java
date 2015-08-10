package com.lks.test;

import com.lks.facade.MainController;
import com.lks.orm.dao.DocumentUploadDao;
import com.lks.security.IUserService;
import com.lks.uploader.IDocumentUploadService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by lokkur on 6/20/2015.
 */

@ContextConfiguration(locations = {"classpath:test-application-context.xml","file:src/main/webapp/WEB-INF/spring-database.xml"})
public class AbstractTest extends AbstractTransactionalTestNGSpringContextTests {

    @Resource(name = "controller")
    protected MainController controller;

    @Resource(name = "documentUploadService")
    IDocumentUploadService documentUploadService;

    @Resource(name = "documentUploadDao")
    DocumentUploadDao documentUploadDao;

    @Resource(name = "userDetailService")
    IUserService userService;
}
