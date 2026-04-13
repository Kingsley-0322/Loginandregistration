/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.loginandregistration;

import java.util.Scanner;

/**
 *
 * @author Student
 */
public class Loginandregistration {
    
    

  public class Login {
      
      String username;
      String password;
      String cellPhoneNumber;
      String firstName;
      String lastName;
      String enteredUsername;
      String enteredPassword;
      
      // Constuctor
      public Login(String firstName, String lastName,String username, String password, String cellPhoneNumber) {
          this.firstName = firstName;
          this.lastName = lastName;
          this.username = username;
          this.password= password;
          this.cellPhoneNumber = cellPhoneNumber;
}
// METHOD 1 : Chek username contains underscoreand is no more than 5 characters
      
public boolean checkUserName(){
    if (username.length() <=5 && username.contains ("_")){

return true;
        
    } else {
        return false;
    }
}

// METHOD 2 : Check password complexity

public boolean checkPasswordComplexity() {
    boolean hasUpperCase = false;
    boolean hasDigit = false;
    boolean hasSpecial = false;
    
    for (int i=0;i< password.length();i++){
        char c=password.charAt(i);
        if(Character.isUpperCase(c)) hasUpperCase = true;
        if(Character.isDigit(c)) hasDigit = true;
        if("!@#$%^&*()-_=+".indexOf(c) >= 0) hasSpecial = true;
    }
    if (password.length()>=8 && hasUpperCase && hasDigit && hasSpecial){
        return true;
    } else {
        return false;
    }
}
// METHOD 3 : Check cellphone number length and international code

public boolean checkCellPhoneNumber(){
    boolean startsWithPlus = cellPhoneNumber.startsWith("+");
    boolean correctLength = cellPhoneNumber.length()>=10 && cellPhoneNumber.length() <=13;
    boolean allDigitsAfterPlus = true;
    
    for (int i=1; i< cellPhoneNumber.length();i++){
        if (! Character.isDigit(cellPhoneNumber.charAt(i))){
            allDigitsAfterPlus =false;
        }
    }
    if (startsWithPlus && correctLength && allDigitsAfterPlus){
        return true;
    } else{
        return false;
    }
}
// METHOD 4: Register user and return appropriate message
public String registerUser(){
    if(!checkUserName()){
        return "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.";

    } else if(!checkPasswordComplexity()){
        return"Password is not correctly formatted; please ensure that the password contains at least a number, and a special character.";

} else if(!checkCellPhoneNumber()){

return "Cell phone incorretly formatted or does not contain international code,";

} else {
return "Username succefully captured.Password successfully captured.Cell phone number successfully added.User rwgistered successfully!";
}
}
// METHOD 5 : Verifies if entered login details match stored registration details

public boolean loginUser(String enteredUsername, String enteredPassword){
    this.enteredUsername = enteredUsername;
    this.enteredPassword = enteredPassword;
    
    if (enteredUsername.equals(username) && enteredPassword.equals(password)) {
        return true;
        
    } else {
        return false;
    }
}
// METHOD 6 : Returns the login status message based on loginUser()result

public String returnLoginStatus(){
    if (loginUser(enteredUsername ,enteredPassword)){
        return"Welcome" + firstName + "," + lastName + "it is greatto see you again.";
    } else {
        return"Username or password incorrect,pleasetry again";
    }
}

        
    
  }  
    public static void main(String[] args) {
        
        String FirstName;
        String LastName;
        String Username;
        String Password;
        String Cellphonenumber;
        
        
        
          Scanner input = new Scanner(System.in);
            
            //Registration
            
            System.out.print("Enter First Name: ");
            FirstName = input.next();
            
            System.out.print("Enter Last Name: ");
            LastName = input.next();
            
            System.out.print("Enter Username: ");
            Username = input.next();
            
            System.out.print("Enter Password<8: ");
            Password = input.next();
            
            System.out.print("Enter CellphoneNumber: ");
            Cellphonenumber = input.next();
            
            System.out.println("\n-----------------");
            System.out.println("Username: " + Username);
            System.out.println("Passowrd: " + Password);
            System.out.println("Cellphonenumber: " + Cellphonenumber);
            
            // Username validation
            
            boolean validUsername;
            if(Username.length() <= 5 && Username.contains ("_")){
                System.out.println("Username successfully captured.");
                validUsername = true;
            } else {
                System.out.println("Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.");
                validUsername = false;
            }
            
            // Password validation
            boolean validPassword;
            boolean hasUpperCase = false;
            boolean hasDigit = false;
            boolean hasSpecial= false;
            
for(int i = 0;i < Password.length(); i++) {
    char c = Password.charAt(i);
    if(Character.isUpperCase(c))
        hasUpperCase = true;
    if(Character.isDigit(c)) hasDigit = true;
    
    if("!@#$%^&*()-_=+".indexOf(c) >= 0) hasSpecial =true;
            }
if (Password.length()>= 8 && hasUpperCase && hasDigit && hasSpecial){
    System.out.println("Password successfully captured.");
    validPassword = true;
    
} else { 
    System.out.println("Password is not correctly formatted; please ensure that the password contains at least eight characters,a capital letter,a number,and a special character.");
    validPassword= false;
}
// Cellphone validation
boolean validCell;
boolean startsWithPlus = Cellphonenumber.startsWith("+");
boolean correctLength = Cellphonenumber.length() >= 10 && Cellphonenumber.length()<= 13;
boolean allDigitsAfterPlus = true;

for (int i= 1; i< Cellphonenumber.length();i++){
    if(!Character.isDigit(Cellphonenumber.charAt(i))){ 
        allDigitsAfterPlus =false;
    }
}
if (startsWithPlus && correctLength && allDigitsAfterPlus){
    System.out.println("Cellphone number successfully added.");
    validCell=true;
} else {
    System.out.println("Cellphone number incorrectly formatted or does not contain international code.");
    validCell= false;
}
      //-----LOGIN-----
      if (validUsername && validPassword && validCell){
          System.out.println("\n-----LOGIN-----");
          System.out.println("Enter Username: ");
          String loginUsername = input.next();
          
          System.out.print("Enter Password: ");
          String loginPassword = input.next();
          
          if (loginUsername.equals(Username) && loginPassword.equals(Password)){
              System.out.println("Welcome" + FirstName + "," + LastName + "it is great to see you again.");
              
          } else {
              System.out.println("Username or password incorrect ,please try again.");
          }
      } else {
          System.out.println("\nRegistration failed.Please fix the errors above before logging in.");
                  }
      input.close();
      }
    }



       
    

