# test-crm

Installation guide:
 -backend: 
    -configure postgresSql connection in application.properties
    -install redis to docker and set host and port in application.properties
    -database and tables will be created automatically for more convienience
 -frontend:
    -install node.js and npm
    -run command: npm start

Not all features were fully implemented or tested properly due to lack of time

admin credentials: admin houseofwolves20

implemented:
 -both backend and frontend:
    authorization with JWT token, read all contacts/clients/tasks
 -backend:
    CRUD for client, task, contact, updating status and assigning contact to task
    Cron job to notify contact about task deadline
    Security, pre-installed and configured redis for further usage
    web-sockets for notifying contacts about task changes(NOT TESTED)
    junit tests for services using mockito framework(SKIPPED IN POM.XML)

    