#codecat
this middleware inspiration comes from the opensource programme ***tomcat***  made by apache. This is the first version for codecat so
it only have two function  Imitation from ***tomcat***
- **http request response**
This is an essential function of a http server.
- **hot deploy**
So that's a really useful and valuable function. codecat use ***JNotify*** to monitor the **apps** directory to start a real server to server client. and this also need a "proxy like" middleware to forward your request to real servers,and get response from them. so codecat's author  integrated [**jproxy**](https://github.com/rpgmakervx/jproxy) into it.
The only and the hardest thing I haven't finished yet is **classload** operation. **classload** could help codecat to load a jar file and get the class from jar ( **a jar file means a plugin to enhance the function of the real server **), then load it's method to a new real server  so that it can serve client as  plugin adder wants.
