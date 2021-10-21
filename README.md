<a href="https://www.twilio.com">
  <img src="https://static0.twilio.com/marketing/bundles/marketing/img/logos/wordmark-red.svg" alt="Twilio" width="250" />
</a>

# IVR Call Recording and Agent Conference. Level: Intermediate. Powered by Twilio - Java | Servlets

An example application implementing an automated phone line using
Twilio and Java Servlets.

[Read the full tutorial here](https://www.twilio.com/docs/tutorials/walkthrough/ivr-screening/java/servlets)!

[![Java Servlet CI](https://github.com/TwilioDevEd/ivr-recording-servlets/actions/workflows/gradle.yml/badge.svg)](https://github.com/TwilioDevEd/ivr-recording-servlets/actions/workflows/gradle.yml)

## Setup

### Requirements
1. [Java 8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
   installed for your operative system.
2. A [Twilio Account](https://www.twilio.com/)

### Local Development

1. Clone the repository and `cd` into it.
    ```
    git clone git@github.com:TwilioDevEd/ivr-recording-servlets.git
    cd ivr-recording-servlets
    ```

1. Create the database.

    ```bash
    createdb ivr-recording

    ```
   _The application uses PostgreSQL as the persistence layer. If you
   don't have it already, you should install it. The easiest way is by
   using [Postgres.app](http://postgresapp.com/)._

1. Set your environment variables
    ```
     cp .env.example .env
    ```

1. Execute the migrations.
    ```bash
    ./gradlew flywayMigrate
    ```

1. Run the application.
    ```bash
    ./gradlew jettyRun
    ```

1. Expose the application to the wider Internet using [ngrok](https://ngrok.com/)

    ```
    ngrok http 8080 -host-header="localhost:8080"
    ```

1. Provision a number under the
   [Manage Numbers page](https://www.twilio.com/user/account/phone-numbers/incoming)
   on your account. Set the voice URL for the number to
   `http://<your-ngrok-subdomain>.ngrok.io/ivr/welcome`.

That's it!

## Meta

* No warranty expressed or implied. Software is as is. Diggity.
* [MIT License](http://www.opensource.org/licenses/mit-license.html)
* Lovingly crafted by Twilio Developer Education.
