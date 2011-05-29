/*
 * This file is written by reyoung, reyoung@126.com.
 */

package controllers;

import controllers.Check;
import controllers.Secure;
import models.User;
import models.UserType;
import play.mvc.Before;
import play.mvc.Controller;
import play.mvc.With;

/**
 *
 * @author Reyoung
 */
@Check("admin")
@With(Secure.class)
public class Admin extends Controller{
    @Before
    static void setConnectedUser() {
        if(Security.isConnected()) {
            User user = User.find("byName", Security.connected()).first();
            renderArgs.put("user", user);
        }
    }
    public static void index(){
        render();
    }
}
