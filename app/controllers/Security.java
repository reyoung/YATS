/*
 * This file is written by reyoung, reyoung@126.com.
 */

package controllers;

import models.User;
import models.UserType;

/**
 *
 * @author Reyoung
 */
public class Security extends Secure.Security   {
    static boolean authentify(String username, String password) {
        return User.connect(username, password) != null;
    }
    static boolean check(String profile){
        User curUser = User.find("byName",  Secure.Security.connected()).first();
        if(profile.equals("login")){
            return curUser!=null;
        }else if(profile.equals("admin")){
            return curUser!=null&&curUser.type == UserType.ADMINISTRATOR;
        }else if(profile.equals("student")){
            return curUser!=null&&curUser.type == UserType.STUDENT;
        }else if(profile.equals("teacher")){
            return curUser!=null&&curUser.type == UserType.TEACHER;
        }
        return false;
    }
    static void onDisconnected() {
        Application.index();
    }
}
