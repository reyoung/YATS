/*
 * This file is written by reyoung, reyoung@126.com.
 */

package controllers;
import controllers.Check;
import controllers.Secure;
import models.User;
import play.mvc.With;

/**
 *
 * @author Reyoung
 */
@CRUD.For(User.class)
@Check("admin")
@With(Secure.class)
public class UserCRUD extends CRUD{
    @Override
    public String toString(){
        return "User CRUD Operations";
    }
}
