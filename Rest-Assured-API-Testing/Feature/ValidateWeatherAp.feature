Feature: Open WeatherMap API 

  Scenario Outline: Validate the Openweather map api with different uri
    Given Launch the API
    Then The Response code is successful for "<URI>"
    Then The Response is in JSON format for "<URI>"
    Then Validate the temperature range for "<URI>"
    Then The Key name is available in the response for "<URI>"
    Then The Key IsInvisible is not available in the response for "<URI>"
    Then Validate response time for "<URI>"
    Then Validate standard assertions using ResponseSpec for "<URI>" is "<name>"

    Examples:
      |URI|name| 
      |data/2.5/weather?id=2172797&appid=bf5a1563c57360118e92fa138279fbf1|Cairns|
      |data/2.5/weather?q=London,uk&appid=bf5a1563c57360118e92fa138279fbf1|London|
      |data/2.5/weather?lat=35&lon=139&appid=bf5a1563c57360118e92fa138279fbf1|Shuzenji|
      |data/2.5/weather?zip=94040,us&appid=bf5a1563c57360118e92fa138279fbf1|Mountain View|
      


	
