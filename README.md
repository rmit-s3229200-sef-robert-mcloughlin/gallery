# gallery
Application to replace paper booklets for the Royal Academy Summer Exhibition

## Instructions to run the app

*Initial setup*
- Download the git repository
*OSX:* - From a 'Terminal' window
1. git clone https://github.com/s3229200/gallery.git

- Install MongoDB, 
*OSX:* from a 'Terminal' follow steps outlined below
1. cd ~
2. curl -O https://fastdl.mongodb.org/osx/mongodb-osx-x86_64-3.4.9.tgz
3. tar -zxvf mongodb-osx-x86_64-3.4.9.tgz
4. mkdir -p mongodb
5. cp -R -n mongodb-osx-x86_64-3.4.9/ mongodb
6. mkdir ~/data
7. chmod 777 ~/data
8. export PATH=/Users/{STUDENT NUMBER || LOGIN USERNAME}/bin:$PATH (You'll need to run this everytime you start a new Terminal)


In one terminal window
```
cd ./server
grails run-app
```

In a second window
```
cd ./client
npm start
```

Access localhost:3000 from a browser to view the running ReactJS client app

Access localhost:8080 from a browser to view the running Grails server app
