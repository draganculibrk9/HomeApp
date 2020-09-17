package home.app.services.service;

import home.app.services.service.repositories.AccommodationRequestRepositoryIntegrationTests;
import home.app.services.service.repositories.ServiceRepositoryIntegrationTests;
import home.app.services.service.services.ServicesServiceIntegrationTests;
import home.app.services.service.services.ServicesServiceUnitTests;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
@SelectClasses({AccommodationRequestRepositoryIntegrationTests.class, ServicesServiceUnitTests.class,
        ServiceRepositoryIntegrationTests.class, ServicesServiceIntegrationTests.class})
public class TestSuiteJUnit5 {
}
