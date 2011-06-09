/*
 * This file is written by reyoung, reyoung@126.com.
 */

package models;

import controllers.MenuItem;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *  是Model层的代理类，外部的方便接口
 * @author Reyoung
 */
public class ModelProxy {
    /**
     * 向数据库中新建一个试卷
     * @param username      出题人
     * @param papername     试卷名称
     * @param hour          回答时间
     * @return              新建试卷的ID
     */
    public static long AddNewPaper(String username,String papername,double hour){
        User paperAuthor = User.find("byName", username).first();
        Paper pap = new Paper(papername,false,(int)(hour * 3600),paperAuthor);
        pap.save();
//        System.out.printf("ModelProxy.AddNewPaper(%s,%s,%f) called", username,papername,hour);
        return pap.id;
    }
    /**
     * 返回一个Teacher的所有Paper ID 和名称
     * @param username
     * @return
     */
    public static List<MenuItem>    GetPaperByTeacher(String username){
        List<MenuItem> retv = new ArrayList<MenuItem>();
        User paperAuthor = User.find("byName", username).first();
        List<Paper> lp = Paper.find("byAuthor", paperAuthor).fetch();
        for(Paper p : lp)
        {
            retv.add(new MenuItem(TeacherPaperId2URL(p.id),p.name));
        }
//        retv.add(new MenuItem(TeacherPaperId2URL(2), "Paper 2"));
        return retv;
    }
    /**
     * 存储一道题目（老师专用）
     * @param paperId
     * @param question
     * @return
     */
    public static boolean SaveQuestionByTeacher(long paperId ,String title, int answer,List<String> selections)
    {
        boolean ans = false;
        if(selections.size() > 2)
        {
            Paper paper = Paper.findById(paperId);
            Question qs = new Question(title, answer,selections.get(0),selections.get(1),selections.get(2), paper);
            qs.saveSelection(selections);
            return true;
        }
        return ans;
    }
    public static boolean UpdateQuestionByTeacher(long questionId,String title,int answer,List<String> seletions)
    {
        boolean ans = false;
        if(seletions.size() > 2)
        {
            Question qs = Question.findById(questionId);
            qs.title = title;
            qs.answer = answer;
            qs.saveSelection(seletions);
            return true;
        }
        return ans;
    }
    /**
     * 返回一个Paper 下指定题号的题
     * @param username
     * @return
     */
    public static Question  GetQuestionByPaperAndNo(long paperId,int questionNo){
        Paper paper = Paper.findById(paperId);
        List<Question> qs = Question.find("byPaper", paper).fetch();
        return qs.get(questionNo);
    }
    /**
     * 删除一个Paper
     * @param username  教师姓名
     * @param id        试卷id
     * @return          返回是否删除成功,
     *                   如果这个试卷不是这个教师出的，那么没有权利删除。
     */
    static public boolean RemovePaperByID(String username,long id){
        Paper rep = Paper.find("byId", id).first();
        if(rep.author.name.equals(username))
        {
            List<Question> qs = Question.find("byPaper", rep).fetch();
            for(Question q : qs)
            {
                q.delete();
            }
            rep.delete();
            return true;
        }
        return false;
    }
    /**
     * 发布一个paper
     * @param username  教师姓名
     * @param id        试卷ID
     * @return          返回是否发布成功，
     *                   如果这个试卷不是这个老师出的，没有权利发布。
     */
    static public boolean PublishPaperByID(String username,long id){
        Paper rep = Paper.find("byId", id).first();
        if(rep.author.name.equals(username))
        {
            rep.isPublished = true;
            rep.save();
            return true;
        }
        return false;
    }
    /**
     * 由Paper_id转成URL
     * @param id
     * @return
     */
    private static String TeacherPaperId2URL(long id){
        return String.format("/teacher/draft/edit?paper_id=%d", id);
    }
    /**
     * 返回所有当前学生未完成的试卷
     * @param username
     * @return
     */
    public static List<MenuItem>    GetPaperUnDoneByStudent(String username){
        List<MenuItem> retv = new ArrayList<MenuItem>();
        User student = User.find("byName", username).first();
        List <ResultInfo> rinfo = ResultInfo.find("byUser", student).fetch();
        List <Paper> totalPaper = Paper.findAll();
        for(ResultInfo r : rinfo)
        {
            totalPaper.remove(r.paper);
//            retv.add(new MenuItem(TeacherPaperId2URL(p.id),p.name));
        }
        for(Paper p : totalPaper)
        {
            retv.add(new MenuItem(StudentPaperId2URL(p.id),p.name));
        }
//        retv.add(new MenuItem(TeacherPaperId2URL(2), "Paper 2"));
        return retv;
    }
    /**
     * 返回所有当前学生已完成的试卷
     * @param username
     * @return
     */
    public static List<MenuItem>    GetPaperDoneByStudent(String username){
        List<MenuItem> retv = new ArrayList<MenuItem>();
        User student = User.find("byName", username).first();
        List <ResultInfo> rinfo = ResultInfo.find("byUser", student).fetch();
        for(ResultInfo r : rinfo)
        {
            retv.add(new MenuItem(StudentPaperId2URL(r.paper.id),r.paper.name));
//            retv.add(new MenuItem(TeacherPaperId2URL(p.id),p.name));
        }
        return retv;
    }
    /**
     * 学生存储答题信息
     * @param userId
     * @param questionId
     * @param answer
     * @return
     */
    public static void SaveQuestionByStudent(long userId , long questionId,int answer)
    {
        UserDoneQuestion udq = new UserDoneQuestion((User)User.findById(userId),
                (Question)Question.findById(questionId), answer);
        udq.save();
    }
    /**
     * 保存考试开始信息
     * @param userId
     * @param paperId
     * @param startTime
     */
    public static void StartPaper(long userId,long paperId,Date startTime)
    {
        ResultInfo ri = new ResultInfo((User)User.findById(userId),
                (Paper)Paper.findById(paperId), startTime);
        ri.save();
    }
    /**
     * 存储/返回学生的某张试卷的成绩
     * @param userId
     * @param paperId
     * @return
     */
    public static double GetScore(long userId,long paperId)
    {
        ResultInfo ri = ResultInfo.find("byUserAndPaper",
                (User)User.findById(userId),(Paper)Paper.findById(paperId)).first();
        return ri.score();
    }
    /**
     * 按照学生ID,试卷ID，试题序号返回试题，若已做过，则第三个参数返回答案
     * @param userId
     * @param paperId
     * @param questionNo
     * @param answer
     * @return
     */
    public static Question GetQuestionByStudent(long userId,long paperId,int questionNo,Integer answer)
    {
        User user = User.findById(userId);
        Paper paper = Paper.findById(paperId);
        List<Question> qsList = Question.find("byPaper", paper).fetch();
        UserDoneQuestion udq = UserDoneQuestion.find("byUserAndQuestion", user,qsList.get(questionNo)).first();
        if(udq != null)answer = udq.answer;
        return qsList.get(questionNo);
    }
     /**
     * 由Paper_id转成URL
     * @param id
     * @return
     */
    private static String StudentPaperId2URL(long id){
        return String.format("/student/test/edit?paper_id=%d", id);
    }
}
