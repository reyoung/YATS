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
     * Pair
     * @param <T1>
     * @param <T2>
     */
    static public class Pair<T1, T2> {

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
    public static int GetPaperQuestionCount(long paperid) {
        return ((Paper) Paper.findById(paperid)).questions.size();
    }

    /**
     * 返回PaperId和Paper中的题目序号
     * @param questionid    question_id
     * @return  Pair,前者是PaperID，后者是Paper中题目的序号。
     */
    static public Pair<Long, Integer> GetPaperIdNQuestionNoByQuestionId(long questionid) {
        Question qs = Question.findById(questionid);
        Paper paper = qs.paper;
        List<Question> qsList = Question.find("byPaper", paper).fetch();
        Integer No = qsList.indexOf(qs);
        return new Pair<Long, Integer>(qs.paper.id, No);
    }

    /**
     * 添加一个桩问题
     * @param paper_id
     * @return  返回问题的序号
     */
    static public int NewStubQuestion(long paper_id) {
        Paper paper = Paper.findById(paper_id);
        Question qs = new Question("This is a Stub Question", 1, "Seletion A", "Selection B", "Selection", paper);
        qs.save();
        return (int) Question.count("byPaper", paper);
    }

    /**
     * 删除一个Question,若所在试卷已发布，则返回false
     * @param question_id  问题所在试卷的ID
     * @return  是否成功
     */
    static public boolean DeleteQuestion(long question_id) {
        Question qs = Question.findById(question_id);
        if (qs.paper.isPublished == true) {
            return false;
        }
        qs.delete();
        return true;
    }

    /**
     * 返回一个学生ID所能参加的所有考试
     * @param student_id
     * @return
     */
    static public List<Paper> GetAvailablePaperByStudentName(String name) {
        List<Paper> tPaper = Paper.find("byIsPublished", true).fetch();
        User student = User.find("byName", name).first();
        List<ResultInfo> rinfo = ResultInfo.find("byUser", student).fetch();
        for (ResultInfo r : rinfo) {
            tPaper.remove(r.paper);
        }
        return tPaper;
    }

    static public Paper GetUnfinishedPaperByStudentName(String name) {
        User student = User.find("byName", name).first();
        List<ResultInfo> rinfo = ResultInfo.find("byUser", student).fetch();
        Paper paper = null;
        for (ResultInfo r : rinfo) {
            if (r.hasComplete() == false) {
                paper = r.paper;
                break;
            }
        }
        return paper;
    }

    /**
     * 返回所有当前学生已完成的试卷
     * @param username
     * @return
     */
    public static List<MenuItem> GetPaperDoneByStudent(String username) {
        List<MenuItem> retv = new ArrayList<MenuItem>();
        User student = User.find("byName", username).first();
        List<ResultInfo> rinfo = ResultInfo.find("byUser", student).fetch();
        for (ResultInfo r : rinfo) {
            retv.add(new MenuItem(StudentPaperId2URL(r.paper.id), r.paper.name));
//            retv.add(new MenuItem(TeacherPaperId2URL(p.id),p.name));
        }
        return retv;
    }

    /**
     * 向数据库中新建一个试卷
     * @param username      出题人
     * @param papername     试卷名称
     * @param hour          回答时间
     * @return              新建试卷的ID
     */
    public static long AddNewPaper(String username, String papername, double hour) {
        User paperAuthor = User.find("byName", username).first();
        Paper pap = new Paper(papername, false, (int) (hour * 3600), paperAuthor);
        pap.save();
        return pap.id;
    }

    /**这个返回的是未发布的还是已经发布的试卷啊？？
     * 返回一个Teacher的所有Paper ID 和名称
     * @param username
     * @return
     */
    public static List<MenuItem> GetPaperByTeacher(String username, boolean published) {
        //! TODO Complete the published Param
        List<MenuItem> retv = new ArrayList<MenuItem>();
        User paperAuthor = User.find("byName", username).first();
        List<Paper> lp = Paper.find("byAuthor", paperAuthor).fetch();
        if (published) {
            for (Paper p : lp) {
                if(p.isPublished)
                {
                    retv.add(new MenuItem(TeacherPaperId2URL(p.id), p.name));
                }
            }
        }
        else
        {
            for (Paper p : lp) {
                if(!p.isPublished)
                {
                    retv.add(new MenuItem(TeacherPaperId2URL(p.id), p.name));
                }
            }
        }
        return retv;
    }

    /**
     * 存储一道题目（老师专用）
     * @param paperId
     * @param question
     * @return
     */
    public static boolean SaveQuestionByTeacher(long paperId, String title, int answer, List<String> selections) {
        boolean ans = false;
        if (selections.size() > 2) {
            Paper paper = Paper.findById(paperId);
            Question qs = new Question(title, answer, selections.get(0), selections.get(1), selections.get(2), paper);
            qs.saveSelection(selections);
            return true;
        }
        return ans;
    }

    public static boolean UpdateQuestionByTeacher(long questionId, String title, int answer, List<String> seletions) {
        boolean ans = false;
        if (seletions.size() > 2) {
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
    public static Question GetQuestionByPaperAndNo(long paperId, int questionNo) {
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
    static public boolean RemovePaperByID(String username, long id) {
        Paper rep = Paper.find("byId", id).first();
        if (rep.author.name.equals(username)) {
            List<Question> qs = Question.find("byPaper", rep).fetch();
            for (Question q : qs) {
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
    static public boolean PublishPaperByID(String username, long id) {
        Paper rep = Paper.find("byId", id).first();
        if (rep.author.name.equals(username)) {
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
    private static String TeacherPaperId2URL(long id) {
        return String.format("/teacher/draft/edit?paper_id=%d", id);
    }

    /**
     * 返回所有当前学生未完成的试卷
     * @param username
     * @return
     */
    public static List<MenuItem> GetPaperUnDoneByStudent(String username) {
        List<MenuItem> retv = new ArrayList<MenuItem>();
        User student = User.find("byName", username).first();
        List<ResultInfo> rinfo = ResultInfo.find("byUser", student).fetch();
        List<Paper> totalPaper = Paper.findAll();
        for (ResultInfo r : rinfo) {
            totalPaper.remove(r.paper);
//            retv.add(new MenuItem(TeacherPaperId2URL(p.id),p.name));
        }
        for (Paper p : totalPaper) {
            retv.add(new MenuItem(StudentPaperId2URL(p.id), p.name));
        }
//        retv.add(new MenuItem(TeacherPaperId2URL(2), "Paper 2"));
        return retv;
    }

    /**
     * 学生存储答题信息
     * @param userId
     * @param questionId
     * @param answer
     * @return
     */
    public static void SaveQuestionByStudent(long userId, long questionId, int answer) {
        UserDoneQuestion udq = new UserDoneQuestion((User) User.findById(userId),
                (Question) Question.findById(questionId), answer);
        udq.save();
    }

    /**
     * 保存考试开始信息
     * @param userId
     * @param paperId
     */
    public static void StartPaper(long userId, long paperId) {
        ResultInfo ri = new ResultInfo((User) User.findById(userId),
                (Paper) Paper.findById(paperId), new Date(System.currentTimeMillis()));
        ri.save();
    }

    /**
     * 存储/返回学生的某张试卷的成绩
     * @param userId
     * @param paperId
     * @return
     */
    public static double GetScore(long userId, long paperId) {
        ResultInfo ri = ResultInfo.find("byUserAndPaper",
                (User) User.findById(userId), (Paper) Paper.findById(paperId)).first();
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
    public static Pair<Question, Integer> GetQuestionByStudent(long userId, long paperId, int questionNo, int answer) {
        User user = User.findById(userId);
        Paper paper = Paper.findById(paperId);
        List<Question> qsList = Question.find("byPaper", paper).fetch();
        Pair ret = new Pair<Question, Integer>(qsList.get(questionNo), new Integer(-1));
        UserDoneQuestion udq = UserDoneQuestion.find("byUserAndQuestion", user, qsList.get(questionNo)).first();
        if (udq != null) {
            ret.second = answer;
        }
        return ret;
    }

    /**
     * 由Paper_id转成URL
     * @param id
     * @return
     */
    private static String StudentPaperId2URL(long id) {
        return String.format("/student/test/edit?paper_id=%d", id);
    }
}
