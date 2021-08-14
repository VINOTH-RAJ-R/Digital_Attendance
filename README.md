# Digital_Attendance
Attendance is a compulsory requirement of every organization. Maintaining
attendance register daily is a difficult and time-consuming task. There are many
automated methods for the same available like Biometric, RFID and many more.
This project provides an efficient and smart method for marking attendance. As it is
known that primary identification for any human is face, face recognition provides
an accurate system which overcomes the ambiguities like fake attendance, and time
consumption. This system uses camera to capture photo and recognize the faces of
students or employee's present in the photo will be marked present and attendance is
stored.The captured photo is sent to the server, the face of the students are recognised
and are marked present.

# The modules which are defined in this project are
➢ Splash screen

➢ Login and signup

➢ Camera and Gallery

➢ Firebase

➢ Face-Recognition

* Splash screen:
This module is used as the first screen visible to the user when the
applications launched. Splash screens are used to display some
animations typically of the application logo.
* Login and Signup:
This module initializes the user to verify that as profile is created or not and
allow the user to use the application.
* Camera and Gallery:
This module is used to capture the photos of the people and store it in the
mobile storage and from gallery choosing the picture and send it to the
firebase.
* Firebase:
This module will receive the photo from user and send it to the server for
Face Recognition.
* Face Recognition:
This module will receive the photo from the firebase to the server and the
faces will be recognition with pre-loaded photo of the users and match it and
load the names in excel sheet and upload it to the firebase.
