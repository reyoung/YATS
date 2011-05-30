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
        List<MenuItem> headeritem = new ArrayList<MenuItem>();
        headeritem.add(new MenuItem("/teacher/draft", "Draft"));
        headeritem.add(new MenuItem("/teacher/published","Published"));
        headeritem.add(new MenuItem("/about", "About"));
        renderArgs.put("headeritem", headeritem);
        renderArgs.put("headerimg", "/public/images/logo.bmp");
    }
    /**
     * 草稿页面
     */
    public static void draft(){
        draft_list();           //! 草稿页面，默认跳转到List页面
    }
    public static void index() {
        draft();                //! 默认跳转到草稿页面
    }

    public static void draft_list(){
        addAction_draft_0();
        render();
    }
    public static void draft_new(){
        addAction_draft_0();
        render();
    }
    public static void draft_new_addPaper(String PaperName){
        addAction_draft_0();
        render(PaperName);
    }



    /**
     * 添加草稿0层的action
     */
    private static void addAction_draft_0(){
        List<MenuItem> actions = new ArrayList<MenuItem>();
        actions.add(new MenuItem("/teacher/draft/list","List"));
        actions.add(new MenuItem("/teacher/draft/new","New"));
        renderArgs.put("actioncontext", actions);
    }
}
