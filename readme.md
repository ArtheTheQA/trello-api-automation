# The interfaces games (API vs UI)

First thing first, we need to understand what'a hell is an interface

  - Interface is the name given to how communication (approaching to programming, is the inputs and outputs) works between two distinct parties that can not connect directly.

#### API (Application Programming Interface)

  - I like to say that is a big cloud of back-end programming
  - Basically it's a normal application without user interface
  - It's a programming interface, you can send and receive data in the programming way 😎
  -
#### UI (User Interface)

  - It's the way of user interact with the application
  - There are a lot of graphic material (images, animations, icons, text and etc)

#### UI vs API

  - The main difference it's the way, how and who is using

# Testing the API first it's better than test directly the UI

  - If you're testing an API probably you gonna have better results specially with the time. You're gonna save time and guarante the quality almost the same way as the UI testing.

# But... WHY??

  - If you test the API, you're testing the inputs and outputs without loading a lot of unnecessary (if it's your case) CSS, HTML and JavaScript on your test.
  - You can guarantee the quality as the same way.
  - UI test are very fragile.

  # So it's always better test the API than UI? Uhull!! GOOD BYE WEBDRIVERS!

  - WWAAIIT! It's not like that. Depends on the necessity of your test. If you need to test the UI interface, if the buttons and images still there, you need the webdrivers.
  - But, as the testing pyramid said to us. More API tests than UI tests.