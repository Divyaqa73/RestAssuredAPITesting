Feature: Open WeatherMap API 

  Scenario Outline: Validate the Openweather map api with different uri
    Given Launce the API
    Then The Response code is succesfull for "<URI>"
    Then The Response is in JSON format for "<URI>"
    Then Validate the temperature range for "<URI>"
    Then The Key IsInvisible is not availabe in the response for "<URI>"
    Then Validate response time for "<URI>"
    Then Validate standard assertions using ResponseSpec for "<URI>" is "<name>"

    Examples:
      |URI																																			|	name	| 
      |data/2.5/weather?id=2172797&appid=651587654bcd0392c12915b211ad8efa       |Cairns	|
      |data/2.5/weather?q=London,uk&appid=651587654bcd0392c12915b211ad8efa      |London	|
      |data/2.5/weather?lat=35&lon=139&appid=651587654bcd0392c12915b211ad8efa		|Shuzenji	|
      |data/2.5/weather?q=London,uk&appid=651587654bcd0392c12915b211ad8efa			|London	|
      |data/2.5/weather?zip=94040,us&appid=651587654bcd0392c12915b211ad8efa			|Mountain View	|
      


	
