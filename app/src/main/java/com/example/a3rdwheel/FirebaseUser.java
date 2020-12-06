package com.example.a3rdwheel;

//This is the user object representing the JSON structure of User in Firebase.

import java.util.List;

public class FirebaseUser {
    int User_ID;
    String First_Name, Last_Name;
    int Age;
    String Gender;
    String Email;
    String Drive_Licence;
    List<String> User_Role;
    int Rating;
    String Profile_Photo;       //TODO:how to Profile photo from JSON?
}
