package controllers;

import play.*;
import play.mvc.*;

import java.util.*;

import models.*;

@With(Secure.class)
public class Application extends Controller {
    @Check("login")
    public static void index() {
        User curUser = User.find("byName",  Secure.Security.connected()).first();
        if(curUser.type==UserType.ADMINISTRATOR){
            redirect("/admin/crud");
        }else if(curUser.type == UserType.STUDENT){
            Student.index();
        }else{
            Teacher.index();
        }
    }
}