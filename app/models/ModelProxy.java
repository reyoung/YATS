/*
 * This file is written by reyoung, reyoung@126.com.
 */

package models;

import controllers.MenuItem;
import java.util.ArrayList;
import java.util.List;

/**
 *  是Model层的代理类，外部的方便接口
 * @author Reyoung
 */
public class ModelProxy {
    /**
     * Pair
     * @param <T1>
     * @param <T2>
     */
    static public class Pair<T1,T2>{
        public T1 first;
        public T2 second;
        public Pair() {
        }
        public Pair(T1 first, T2 second) {
            this.first = first;
            this.second = second;
        }
    }
    /**
     * 返回一个Paper的问题数量
     * @param paperid   paper_id
     * @return
     */
    public static int GetPaperQuestionCount(long paperid){
        return ((Paper)Paper.findById(paperid)).questions.size();
    }
    /**
     * 返回PaperId和Paper中的题目序号
     * @param questionid    question_id
     * @return  Pair,前者是PaperID，后者是Paper中题目的序号。
     */
    static public Pair<Long,Integer>  GetPaperIdNQuestionNoByQuestionId(long questionid){
        //! TODO Complete The Stub
        return new Pair<Long, Integer>(new Long(2),new Integer(1));
    }

    /**
     * 删除一个Question
     * @param paper_id  问题所在试卷的ID
     * @param question_no   问题的序号
     * @return  是否成功
     */
    static public boolean DeleteQuestion(long paper_id,int question_no){
        //! TODO Complete The Stub
        return true;
    }


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
     * 返回一个Paper 下指定题号的题
     * @param username
     * @return
     */
    public static Question  GetQuestionByPaperAndNo(long paperId,int questionId){
        Paper paper = Paper.findById(paperId);
        List<Question> qs = Question.find("byPaper", paper).fetch();
        return qs.get(questionId);
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
     * 由Paper_id转成URL
     * @param id
     * @return
     */
    private static String StudentPaperId2URL(long id){
        return String.format("/student/test/edit?paper_id=%d", id);
    }
}
