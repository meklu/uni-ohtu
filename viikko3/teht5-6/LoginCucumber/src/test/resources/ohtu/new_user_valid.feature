Feature: A new user account can be created if a proper unused username and password are given

    Scenario: creation is successful with valid username and password
        Given command new is selected
        When  username "pat" and password "aieou555" are entered
        Then  system will respond with "new user registered"

    Scenario: creation fails with already taken username and valid password
        Given command new is selected
        When  username "pekka" and password "aieou555" are entered
        Then  system will respond with "new user not registered"

    Scenario: creation fails with too short username and valid password
        Given command new is selected
        When  username "pa" and password "aieou555" are entered
        Then  system will respond with "new user not registered"

    Scenario: creation fails with long enough username with characters outside a-z and valid password
        Given command new is selected
        When  username "pat55" and password "aieou555" are entered
        Then  system will respond with "new user not registered"

    Scenario: creation fails with valid username and too short password
        Given command new is selected
        When  username "pat" and password "abc123" are entered
        Then  system will respond with "new user not registered"

    Scenario: creation fails with valid username and password long enough but consisting of only letters
        Given command new is selected
        When  username "pat" and password "aeiouxyz" are entered
        Then  system will respond with "new user not registered"
