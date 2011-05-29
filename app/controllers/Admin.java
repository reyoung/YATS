/*
 * This file is written by reyoung, reyoung@126.com.
 */

package controllers;

import controllers.Check;
import controllers.Secure;
import java.util.ArrayList;
import java.util.List;
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
        List<HeaderItem> headeritem = new ArrayList<HeaderItem>();
        headeritem.add(new HeaderItem("/logout", "logout"));
        headeritem.add(new HeaderItem("/about", "about"));
        renderArgs.put("headeritem", headeritem);
        renderArgs.put("headerimg", "/public/images/logo.bmp");
    }
    public static void index(){
        render();
    }
}
