Feature: Validate Cities within Rectangle Zone

Scenario Outline: Validate Latitude, Longitude from the response for each cities
	Given Launch the API
	Then The Response code is successful for "<URI>"
	Then Validate latitude for each cities for "<URI>"
	Then Validate longitude for each cities for "<URI>"

Examples:
      |URI|
      |data/2.5/box/city?bbox=12,32,15,37,10&appid=bf5a1563c57360118e92fa138279fbf1|