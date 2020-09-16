package home.app.auth.service;

import home.app.auth.service.services.AuthServiceIntegrationTests;
import home.app.auth.service.services.AuthServiceUnitTests;
import home.app.auth.service.services.UserDetailsServiceImpIntegrationTests;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
@SelectClasses({AuthServiceUnitTests.class, AuthServiceIntegrationTests.class, UserDetailsServiceImpIntegrationTests.class})
public class TestSuiteJUnit5 {
}
