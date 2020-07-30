## AI ChatBot application for Android OS using JAVA socket
The goal of this assignment is to build an Artificial Intelligence based chatbot for Android smartphone.
The chatbot is built by the client-server model. The client side is a smartphone application and the server part is an artificial intelligence based bot.
## Features
* User's input is text
* User's input is audio
## Server
The server software is developed in Python. It includes a pre-trained machine learning model and socket server that communicates with android app. More about the pre-trained machine learning model is [🦄 here](https://medium.com/@Thomwolf/how-to-build-a-state-of-the-art-conversational-ai-with-transfer-learning-2d818ac26313).

Server give us selected model personality, server ip address and port numbers as shown below. If the defualt port numbers are already taken, users can set their own port numbers that is not used.

<img src="https://github.com/Lkham/ChatBot/blob/master/Demo/server.png" width="500">

### Installation

In order to run the server, please clone the repo and install the requirements:

```bash
git clone https://github.com/Lkham/ChatBot.git
cd ChatBot
pip install -r requirements.txt
python -m spacy download en
```
## Server DEMO
* **First** we need to start the server by runing the `Server.py` script in Server folder:

![Server_Demo](https://github.com/Lkham/ChatBot/blob/master/Demo/Server_Demo.gif)

## Client
The client application is developed in Java using Android Studio. Its Graphical User Interface consists of the following parts:
* **Icon** 
* **Splash Screen**  
* **Settings Window**  
* **Main Conversation Window** 
  * *Screen (for displaying the conversation log)*
  * *Text input field (for displaying the input text that is being typed)*
  * *Button-1 (for audio input)*
  * *Button-2 (for sending the text)*
  * *Toolbar (for going to Settings Window)*
  

<img src="https://github.com/Lkham/ChatBot/blob/master/Demo/icon.png" width="230"> <img src="https://github.com/Lkham/ChatBot/blob/master/Demo/splashScreen.png" width="230"> <img src="https://github.com/Lkham/ChatBot/blob/master/Demo/SettingsWindow.png" width="230"> <img src="https://github.com/Lkham/ChatBot/blob/master/Demo/MainWindow.png" width="230">


## Client DEMO

* **Second** we need to install our *ai chatbot app* on our android device using `chatbot.apk`.
  1. Copy `chatbot.apk` to your device
  2. Install it on your device
  3. Run the application
  4. Enter server IP address and port numbers on the settings window
  
* Finaly it works as folows:

![Android_Demo](https://github.com/Lkham/ChatBot/blob/master/Demo/Android-Demo.gif)
