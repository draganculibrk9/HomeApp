package home.app.grpc.api;

import home.app.grpc.api.services.StartupServiceIntegrationTests;
import home.app.grpc.api.services.UserDetailsServiceImplUnitTests;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
@SelectClasses({StartupServiceIntegrationTests.class, UserDetailsServiceImplUnitTests.class})
public class TestSuiteJUnit5 {
}
