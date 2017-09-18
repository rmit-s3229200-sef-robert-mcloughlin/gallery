# gallery
Application to replace paper booklets for the Royal Academy Summer Exhibition

## Instructions to run the app

*Initial setup*
- Download the git repository
*OSX:* - From a 'Terminal' window
1. git clone https://github.com/s3229200/gallery.git

*Windows:* - Run through the Git setup tutorial on the LMS - https://lms.rmit.edu.au/bbcswebdav/pid-8670388-dt-content-rid-20814030_1/xid-20814030_1

Clone the repository  as above

- Run the application:
*OSX:* From 'Terminal'
1. `cd ~/gallery/server (or where ever you put the repository)`
2. `./grailsw run-app`

*Windows:* From 'Command Prompt'
1. `cd ~/gallery/server (or where ever you put the repository)`
2. `grailsw.bat run-app`

Access localhost:8080 from a browser to view the running Grails server app


## Get Exlipse ready and import gallery application
*This should be the same for OSX and Windows environments, but let me know if it fails on Windows*
1. Install the 'Gradle Buildship Integration', in the taskbar head to
- "Help" > "Eclipse Marketplace" > Find: "Gradle" > Select the 'Gradle Buildship Integration' (Has the picture of an Elephant)
- Again "Help" > "Eclipse Marketplace" > Find: "EGradle" > Select the 'EGradle Editor 2.1.0'
2. Head to "File" > "Import" > "Gradle" - "Existing Gradle Project" > "Project Root Directory" == Where ever you cloned 'gallery' > Finish (This step might take some time to complete)


## Your all ready to get started :D
The folder structure of the application is as follows:

Server
|- grails-app
   |- controllers (These are the 'C' in MVC - they control the URL endpoints for each of the domain models)
   |- domain (These are the 'M' in MVC - they are the "classes" and control the database schema)
   |- utils (Where I'm keeping general functional helpers - such as the 'Authentication' class, which doesn't really belong in either)
|- src
    |- test
        |- groovy
            |- gallery (These are the test specs, which is where we'll be writing our JUnit tests in the next week or so)

To create a new domain model, the easiest way to do this is from the command line, head to the 'server' folder and run the following `grailsw[.bat] create-domain-class {DOMAIN CLASS NAME}` - this will generate a boilerplate class in domain/gallery/{DOMAIN CLASS NAME}.groovy

To create a new set of controllers off this, run `grailsw[.bat] generate-all {DOMAIN CLASS NAME}` - this will generate a class controller in controllers/gallery/{DOMAIN CLASS NAME}Controller.groovy *AND* a testing spec in src/test/groovy/{DOMAIN CLASS NAME}ControlerSpec.groovy && src/test/groovy/{DOMAIN CLASS NAME}Spec.groovy