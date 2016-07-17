package com.Test.CourseAPI;

import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/*
 * Base class for testing all the applications in the project
 * Contains common code among all testers
 * Added only logger in this case
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes=Application.class)
public class AbstractTest
{
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
}
