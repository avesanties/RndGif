# RndGif Project
This is the home page of the RndGif project
## What is RndGif project?
This service calls the exchange rate service and displays a gif:
if the rate against USD for today has become higher than yesterday, then a random "rich" gif from [giphy API](https://developers.giphy.com/docs/api#quick-start-guide) is returned otherwise "broke" gif will be sent back.
Exchange rate is taken from [here](https://docs.openexchangerates.org/).

## How to run the project locally
At first you should perfom the following commands
```
git clone git@github.com:avesanties/RndGif.git
cd ./RndGif
```
Secondly web.properties and root.properties files are to be filled to make the app able to connect to foreign services.
You have to fill app_id field for both services. Other option may be left as it is.

At last you have two options to launch the app:
* put the .war archive `/target/rndgif.war` in `.../webapps` directory of Apache Tomcat container
* or start the app inside a docker container executing the following commands:
    ```
    docker image build -t rndgif ./
    docker run --name rndgif -d --rm -p 8080:8080 rndgif
    ```
## How to use the Project
In your web browser just navigate to http://localhost:8080/rndgif
