2CPI Project
============
The repository now holds the framework of our application (the base).

Architecture
------------
The application is devided into 4 main packages:
* `core` : holds the application base logic, from creating the primary stage to connecting and closing the database. This package is the framework and should not be modified.
* `models`: holds the MVC models, these files are used to query the database to retrieve the results and parse it inside the model class
* `controllers`: holds the JavaFX controllers 
* `views`: holds the FXML Layout files

Core
----
The code consists of two files:
#### App
create the main stage and provide the following :
* `show` : method that shows the window
* `hide` : method that hides the window
* `setView([window, ] view_name [, args])` : method that changes the scene displayed in the main window
* `newWindow(view_name [, args])` : method that creates a new window
* `entry` : property the sets the main scene to be displayed
#### DB
the database connector and query handler
* `query(sql [, ...vals])` :
* ex : `query("SELECT * FROM doctors WHERE id = ?", 1)`

Controllers
-----------
Should provide the init method for controllers communication
* `init()`
* `init(HashMap args)`

Views
-----
The only constraint to these files is that it must include a controller

Creating new window
-------------------
To create a new window use the method newWindow in the App core class.
* `newWindow` returns the new window (Stage object) that can be later used in setView
Example :
```java
// create new window with the view "alert_one"
Stage alert_window = App.newWindow("alert_one");

// change the view to "alert_two"
App.setView(alert_window, "alert_two");
``` 
