/*
 * This file is written by reyoung, reyoung@126.com.
 */
package controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import models.ModelProxy;
import models.User;
import play.data.validation.Required;
import play.data.validation.Validation;
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
        List<MenuItem> paperlist = ModelProxy.GetPaperByTeacher(Security.connected());
        render(paperlist);
    }
    public static void draft_new(){
        addAction_draft_0();
        render();
    }
    public static void draft_edit(@Required long paper_id){
        if(Validation.hasErrors()){
            Application.index();
        }
        addAction_draft_1(paper_id);
        render();
    }


    public static void draft_new_addPaper(@Required String PaperName,@Required double TestTime){
        if(Validation.hasErrors()){
            flash.error("All Field is Required or Input Format Not Correctly");
            draft_new();
        }
        long id = ModelProxy.AddNewPaper(Security.connected(), PaperName, TestTime);
        draft_edit(id);
    }
    public static void draft_result(@Required boolean ok){
        if(Validation.hasErrors()){
            draft();
            return ;
        }
        String draft_result = ok?"Success":"Fail";
        render(draft_result);
    }
    public static void draft_remove(long paper_id){
        boolean ok = ModelProxy.RemovePaperByID(Security.connected(), paper_id);
        draft_result(ok);
    }
    public static void draft_publish(long paper_id){
        boolean ok = ModelProxy.PublishPaperByID(Security.connected(), paper_id);
        draft_result(ok);
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
    /**
     * 添加草稿1层的action
     */
    private static void addAction_draft_1(long paper_id){
        List<MenuItem> actions = new ArrayList<MenuItem>();
        actions.add(new MenuItem(String.format("/teacher/draft/publish?paper_id=%d", paper_id),"Publish"));
        actions.add(new MenuItem(String.format("/teacher/draft/remove?paper_id=%d", paper_id),"Remove"));
        renderArgs.put("actioncontext", actions);
    }
}
