Feature: Is Selenium Download Page displayed?
  Validate if user is on the Selenium Download Page

  Scenario: Selenium Download Page displayed
    Given I navigate to website Selenium
    Then I should see the title "What is Selenium?" in xpath "(//div[@id='mainContent']/h2)[1]"
    When I click on linkText "Download"
    Then I should see "Downloads" heading in xpath "(//div[@id='mainContent']/h2)[1]"
    Then Shutdown test