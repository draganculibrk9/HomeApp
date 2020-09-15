package home.app.household.service;

import home.app.household.service.services.AuthServiceUnitTests;
import home.app.household.service.services.HouseholdServiceUnitTests;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
@SelectClasses({AuthServiceUnitTests.class, HouseholdServiceUnitTests.class})
public class TestSuiteJUnit5 {
}
