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

See if you can access the application from the browser.

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
* Login to the server using ssh
 * Login to the server using root
* Link the server to a domain name
  * Email the IP address of your server to mentors@projectcodex.co
  * The mentors will link your server to domain name for you.
  * `yourname.projectcodex.net`
  
* Run a Java Web App on the server
  * install java on the server
  * install maven
  * install git on the server
  * clone the java project to the server
  * run these maven commands:
   * `mvn package`
   * `mvn dependency:copy-dependencies`
   * In your java app folder run this command to start the application: 
    `java -cp .:target/* vps.app.App`
   * See if others were able to access your application
   
  * get the app you want to run on the server using git
  * run your app in the background using this command:
    ```
    nohup java -cp .:target/* vps.app.App > vps.log 2>&1 &
    ```
    
    You can also see this command to the the process id for the app:
    
    ```
    ps -eafw | grep vps.app.App
    ```
    
  * now you can logout of your server using the `logout` command.
  
