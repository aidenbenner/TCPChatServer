# ChatServer
A centralized network chat application that allows text communication acrosslan or the internet. Created as a grade 11 CS project. 


# Server 
The server is implemented using sockets and a very simple custom communcation protocol. An unlimited amount of of clients can connect to the server, and a list of all connected users will be shown in the sidebar.

Simply start the chat server on the machine intended to be used as the server. The server listens on port 2020, so this port will need to be opened from the computer's firewall (or portforwaded if behind a nat and used over the internet). The server will need to directly connect to all clients.  

# Client
Upon opening the executable, you will be prompted for an ip. The machine with the server application will print the local ip into the chat box. 

Once connected, your username will default to your computer's hostname. To change this use the /USER=newusername command. Your username will then be updated to everyone connected to the server the next time you send a message.


# Demo

![Server Perspective](http://i.imgur.com/ZxJbRvc.png)

Server Perspective

![Server Perspective](http://i.imgur.com/eCjab4o.png)

Server Perspective: showing multiple users connected

![IP Request](http://imgur.com/53TbBc1)

Initial client request for a server IP

![Client perspective](http://i.imgur.com/3KqtNaE.png)

/USER command

![Goos ascii art](http://i.imgur.com/LOGk850.png)

Great way to send goose ascii art!

