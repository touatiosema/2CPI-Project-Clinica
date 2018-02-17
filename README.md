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
* `setView(view_name, [... initial_args])` : method that changes the scene displayed in the main window
* `entry` : property the sets the main scene to be displayed
#### DB
* the database connector and query handler
* `query(sql, [... vals])` :
* ex : `query("SELECT * FROM doctors WHERE id = ?", 1)`

Models
------
Check example

Controllers
-----------
Check example

Views
-----
Check example
The only constraint to these files is that it must include a controller
