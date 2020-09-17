package home.app.household.service;

import home.app.household.service.repositories.HouseholdRepositoryIntegrationTests;
import home.app.household.service.services.AuthServiceIntegrationTests;
import home.app.household.service.services.AuthServiceUnitTests;
import home.app.household.service.services.HouseholdServiceIntegrationTests;
import home.app.household.service.services.HouseholdServiceUnitTests;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
@SelectClasses({AuthServiceUnitTests.class, HouseholdServiceUnitTests.class, HouseholdRepositoryIntegrationTests.class,
        AuthServiceIntegrationTests.class, HouseholdServiceIntegrationTests.class})
public class TestSuiteJUnit5 {
}
