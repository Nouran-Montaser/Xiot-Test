xiottask is an app which send and receive messages using mqtt protocol.

I used MQTTBox for testing . I used as a host this server: mqttbrocker server URL and export user name and password and CliedId : a to verify the user .
the first activity use to connect the user with the following data : URL , username , password and port , if the connection succeed it will send the user to the second activity with has two buttons the first send "HI" message with topic chat and the second send "BY" message with topic chat and it add to the RecyclerView and the database .
and it has a menu with log_out button which close the current activity and send to the first activity.