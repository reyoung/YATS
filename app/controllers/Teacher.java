/*
 * This file is written by reyoung, reyoung@126.com.
 */
package controllers;

import java.util.ArrayList;
import java.util.List;
import models.User;
import play.mvc.Before;
import play.mvc.Controller;
import play.mvc.With;

/**
 *
 * @author Reyoung
 */
@Check("teacher")
@With(Secure.class)
public class Teacher extends Controller {

    @Before
    static void setConnectedUser() {
        if (Security.isConnected()) {
            User user = User.find("byName", Security.connected()).first();
            renderArgs.put("user", user);
        }
        List<HeaderItem> headeritem = new ArrayList<HeaderItem>();
        headeritem.add(new HeaderItem("/teacher/draft", "Draft"));
        headeritem.add(new HeaderItem("/teacher/published","Published"));
        headeritem.add(new HeaderItem("/about", "About"));
        renderArgs.put("headeritem", headeritem);
        renderArgs.put("headerimg", "/public/images/logo.bmp");
    }
    /**
     * 草稿页面
     */
    public static void draft(){
        render();
    }
    public static void index() {
        draft();                //! 默认跳转到草稿页面
    }
}
