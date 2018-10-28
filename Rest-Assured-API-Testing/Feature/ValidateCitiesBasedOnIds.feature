Feature: Validate Cities based on given City IDs

Scenario Outline: Validate Count and Cities from the response
	Given Launch the API
	Then The Response code is successful for "<URI>"
	Then Validate Count and City details for "<URI>"

Examples:
      |URI|
      |data/2.5/group?id=524901,703448,2643743&units=metric&appid=bf5a1563c57360118e92fa138279fbf1|