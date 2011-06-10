/*
 * This file is written by reyoung, reyoung@126.com.
 */
package controllers;

import models.*;
import java.util.ArrayList;
import java.util.List;
import javax.xml.ws.RequestWrapper;
import models.ModelProxy;
import models.User;
import play.data.validation.Required;
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
        headeritem.add(new MenuItem("/student/exam", "Attend Exam"));
        headeritem.add(new MenuItem("/student/result", "Results"));
        headeritem.add(new MenuItem("/about", "About"));
        renderArgs.put("headeritem", headeritem);
        renderArgs.put("headerimg", "/public/images/logo.bmp");
    }

    public static void index() {
        exam();
    }

    public static void exam() {
        exam_list();
    }

    public static void exam_list() {
        addAction_exam_0();
        List<models.Paper> plist = ModelProxy.GetAvailablePaperByStudentName(Security.connected());
        List<MenuItem> paperlist = _getPaperListUrl("/student/exam/attend?paper_id=", plist);
        render(paperlist);
    }

    public static void exam_attend(@Required long paper_id) {
        addAction_exam_0();
        render(paper_id);
    }

    public static void exam_start(@Required long paper_id) {
        ModelProxy.StartPaper(Security.connected(), paper_id);
        exam_take(paper_id, 0);
    }

    public static void exam_take(@Required long paper_id, @Required int question_no) {
        _renderQuestionStatue(paper_id);
        render();
    }

    private static void _renderQuestionStatue(long paper_id) {
        List<Boolean> list = ModelProxy.GetQuestionsStatusByStudentName(Security.connected(), paper_id);
        boolean[] StatusList = new boolean[list.size()];
        for (int i = 0; i < list.size(); ++i) {
            StatusList[i] = list.get(i).booleanValue();
        }
        String EditPrefix = String.format("/student/exam/take?paper_id=%d&question_no=", paper_id);
        renderArgs.put("StatusList", StatusList);
        renderArgs.put("EditPrefix", EditPrefix);
    }

    private static List<MenuItem> _getPaperListUrl(String prefix, List<Paper> plist) {
        List<MenuItem> retv = new ArrayList<MenuItem>();
        for (Paper p : plist) {
            retv.add(new MenuItem(prefix + String.valueOf(p.id), p.name));
        }
        return retv;
    }

    private static void addAction_exam_0() {
        List<MenuItem> acts = new ArrayList<MenuItem>();
        models.Paper unfinish = ModelProxy.GetUnfinishedPaperByStudentName(Security.connected());
        if (unfinish != null) {
            acts.add(new MenuItem(String.format("/student/exam/do?paper_id=%d", unfinish.id), "Unfinished Test"));
        }
        acts.add(new MenuItem("/student/exam/list", "All Available Exam"));
        renderArgs.put("actioncontext", acts);
    }
}
