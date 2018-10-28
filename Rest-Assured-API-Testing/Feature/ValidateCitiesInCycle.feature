Feature: Validate Cities in Cycle

Scenario Outline: Validate Count, Latitude, Longitude from the response
	Given Launch the API
	Then The Response code is successful for "<URI>"
	Then Validate count from response for "<URI>"
	Then Validate latitude for Cities in Cycle for "<URI>"
	Then Validate longitude for Cities in Cycle for "<URI>"

Examples:
      |URI|
      |data/2.5/find?lat=55.5&lon=37.5&cnt=10&appid=bf5a1563c57360118e92fa138279fbf1|