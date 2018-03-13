# Print org charts from the Salesforce User table

This is a simple command line tool that leverages [force-rest-api](jesperfj/force-rest-api) to connect to Salesforce API and retrieve records from the User object to print an org chart.

## Build

    $ mvn install

## Run

First authenticate:

    $ java -cp "target/classes:target/dependency/*" com.frejo.sfuser.Main login

This will prompt you for username and password, log in to Salesforce, and save a session token to `.sfsession`. Once authenticated, you can run the reports command:

    $ java -cp "target/classes:target/dependency/*" com.frejo.sfuser.Main reports ceo@acme.com 

As the last argument, pass the Username of the person whose organization you want to print. Users in the organization will be printed to standard out with username, full name and title.

# Run on Heroku

If you don't want to bother with installing Java locally and building the app on your machine, then just use Heroku:

```
$ heroku create
$ git push heroku master
...
$ heroku run bash
$ bin/sfuser login
```
