/*
 * This file is written by reyoung, reyoung@126.com.
 */
package controllers;

import java.util.ArrayList;
import java.util.List;
import models.ModelProxy;
import models.User;
import play.mvc.Before;
import play.mvc.Controller;
import play.mvc.With;

/**
 *
 * @author Reyoung
 */
@Check("student")
@With(Secure.class)
public class Student extends Controller {

    @Before
    static void setConnectedUser() {
        if (Security.isConnected()) {
            User user = User.find("byName", Security.connected()).first();
            renderArgs.put("user", user);
        }
        List<MenuItem> headeritem = new ArrayList<MenuItem>();
        headeritem.add(new MenuItem("/student/attend_exam", "Attend Exam"));
        headeritem.add(new MenuItem("/student/result", "Results"));
        headeritem.add(new MenuItem("/about", "About"));
        renderArgs.put("headeritem", headeritem);
        renderArgs.put("headerimg", "/public/images/logo.bmp");
    }

    public static void index() {
        render();
    }
}
