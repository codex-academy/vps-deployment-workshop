# VPS deployment application

Use this project to learn how to deploy to a Digital Ocean VPS.

## Run the app locally first

Before you deploy see if you can run this application on your local machine from IntelliJ and from the terminal.

Clone the app locally: 

`git clone https://github.com/codex-academy/vps-deployment-workshop`

In the terminal run these commands

To change into the right folder

```
cd vps-deployment-workshop
```

Then compile and run the app:

```
mvn package
mvn dependency:copy-dependencies
java -cp .:target/* vps.app.App
```

See if you can access the application from the browser at `http://localhost:4567`


### Start the app in the background using this command:

```
nohup java -cp .:target/* vps.app.App > vps.log 2>&1 &
```

Note the process `id number` that is printed to the screen.

Use that to stop the app using:

```
kill -9 <the id number>
```

## VPS deployment steps

* Create a server on Digital Ocean
 * I sent you an invitation
 * Create an Ubuntu $5 server at a location of your choosing
 * Cloud servers are called `droplets`
 * Use password authentication
 * Rename your server to be called "YourFirstName-Server" in Digital Ocean
 * Please take a screen shot of this page - and keep it aside.
* Login to the server using ssh
 * Login to the server using root : `ssh  root@your.ip.address`
 
 * Server setup:

    * Run this command on your server: `apt update` to ensure you got all the latest packages. It will take a while.
    * install java on the server 
        * using `apt install default-jdk` - it might take a while to complete
        * after running the command the `java` will be available on your server.
    * install `git` on the server using: `apt install git` - **note** git was installed on my server by default.
    * install maven: `apt install mvn`

* Link the server to a domain name
    * Email the IP address of your server to `mentors@projectcodex.co`
    * The mentors will link your server to domain name for you.
    * `yourname.projectcodex.net`
  
* Run a Java Web App on the server
    * create an `apps` folder using `mkdir apps`
    * change into the folder using `cd apps`
    * clone the java project to the server:
        `git clone https://github.com/codex-academy/vps-deployment-workshop`
    * change into this folder: 
        `cd vps-deployment-workshop/`
    * run these maven commands:
        * `mvn package`
        * `mvn dependency:copy-dependencies`
        * In your java app folder run this command to start the application: 
    `java -cp .:target/* vps.app.App`
        * At this point your app should be running at: `http://your-server-ip-address:4567`
        * See if others were able to access your application
        * Please take some screenshots of :
            * your deployed application running the browser
            * your terminal window where you are running the application from
  * stop the process running in the terminal using the ctrl-c command - you should not be able to access your application now.
  
  * run your app in the background using this command:
    ```
    nohup java -cp .:target/* vps.app.App > vps.log 2>&1 &
    ```
    
    You can also see this command to the `process id` for the app:
    
    ```
    ps -eafw | grep vps.app.App
    ```
    
  * now you can logout of your server using the `exit` command
  * log back into your server and stop the java process using the `kill`

## Screen shots & list of commands used

Please ensure you have screenshots of:
* your Digital Configuration setup in Digital Ocean,
* showing your deployed app running on your domain,
* the terminal showing your app running.

Run the `history` command to keep a list of all the commands you used during this workshop.

## Delete your server

Please delete your server in Digital Ocean.
 
