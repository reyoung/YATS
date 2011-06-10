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

    public static void StudentFinishPaper(String username,long paper_id){
        ModelProxy.GetScore(((User)User.find("byName", username).first()).id, paper_id);
    }

    public static int GetQuestionNoOfPaper(long q_id,long p_id){
        Paper p = Paper.findById(p_id);
        for(int i=0;i<p.questions.size();++i){
            if(p.questions.get(i).id==q_id)
                return i;
        }
        return -1;
    }
    public static int GetQuestionCountOfPaper(long q_id){
        Paper p = Paper.findById(q_id);
        return p.questions.size();
    }

    /**
     * 返回学生当前的问题状态。true表示已回答过，false表示未回答过。
     * @param studentname
     * @param paper_id
     * @return
     */
    public static List<Boolean>   GetQuestionsStatusByStudentName(String studentname,long paper_id){
        List<Boolean> retv = new ArrayList<Boolean>();
        Paper paper = Paper.findById(paper_id);
        List<Question> qsList = Question.find("byPaper", paper).fetch();
        for(Question q : qsList)
        {
            if(UserDoneQuestion.find("byQuestion", q).first() != null)retv.add(Boolean.TRUE);
            else    retv.add(Boolean.FALSE);
        }
        return retv;
    }



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
        Question qs = new Question("This is a Stub Question", 1, "Seletion A", "Selection B", "SelectionC", paper);
        qs.save();
        List<Question> qsList = Question.find("byPaper", paper).fetch();
        int ans = -1;
        for(Question q : qsList)
        {
            if(q.id == qs.id)
            {
                ans = qsList.indexOf(q);
            }
        }
        return ans;
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
            retv.add(new MenuItem(StudentPaperId2URL(r.paper.id,true), r.paper.name));
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

    /**
     * 返回一个Teacher的所有Paper ID 和名称
     * @param username
     * @return
     */
    public static List<MenuItem> GetPaperByTeacher(String username, boolean published) {
        List<MenuItem> retv = new ArrayList<MenuItem>();
        User paperAuthor = User.find("byName", username).first();
        List<Paper> lp = Paper.find("byAuthor", paperAuthor).fetch();
        if (published) {
            for (Paper p : lp) {
                if(p.isPublished)
                {
                    retv.add(new MenuItem(TeacherPaperId2URL_Published(p.id), p.name));
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

    private static String TeacherPaperId2URL_Published(long id){
        return String.format("/teacher/published/stat?paper_id=%d", id);
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
            retv.add(new MenuItem(StudentPaperId2URL(p.id,false), p.name));
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
    public static void SaveQuestionByStudent(String username, long questionId, int answer) {
        User user = User.find("byName", username).first();
        Question qs = Question.findById(questionId);
        UserDoneQuestion ud = UserDoneQuestion.find("byUserAndQuestion", user,qs).first();
        if(ud != null)
        {
            ud.answer = answer;
            ud.save();
            return;
        }
        else
        {
            UserDoneQuestion udq = new UserDoneQuestion((User) User.find("byName", username).first(),
                (Question) Question.findById(questionId), answer);
            udq.save();
        }
    }

    /**
     * 保存考试开始信息
     * @param username
     * @param paperId
     */
    public static void StartPaper(String username, long paperId) {
        ResultInfo ri = new ResultInfo((User) User.find("byName", username).first(),
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
     * @param username
     * @param paperId
     * @param questionNo
     * @param answer
     * @return
     */
    public static Pair<Question, Integer> GetQuestionByStudent(String username, long paperId, int questionNo) {
        User user = User.find("byName", username).first();
        Paper paper = Paper.findById(paperId);
        List<Question> qsList = Question.find("byPaper", paper).fetch();
        Pair ret = new Pair<Question, Integer>(qsList.get(questionNo), new Integer(-1));
        UserDoneQuestion udq = UserDoneQuestion.find("byUserAndQuestion", user, qsList.get(questionNo)).first();
        if (udq != null) {
            ret.second = udq.answer;
        }
        return ret;
    }

    /**
     * 由Paper_id转成URL
     * @param id
     * @return
     */
    private static String StudentPaperId2URL(long id,boolean done) {
        return done?String.format("/student/result/show?paper_id=%d", id):String.format("/student/exam/start?paper_id=%d", id);
    }

    /**
     * 获得参加考试的人数
     * @param paper_id
     * @return
     */
    public static int   GetAttendExamStudentCount(long paper_id){
        Paper paper = Paper.findById(paper_id);
        List <ResultInfo> rsList = ResultInfo.find("byPaper", paper).fetch();
        int ans = 0;
        for(ResultInfo r : rsList)
        {
            if(r.hasComplete())ans++;
        }
        return ans;
    }

    /**
     * 获得试卷平均分
     * @param paper_id
     * @return
     */
    public static double GetAvgScoreByPaperId(long paper_id){
        double ans = 0;
        int count = 0;
        Paper paper = Paper.findById(paper_id);
        List <ResultInfo> rsList = ResultInfo.find("byPaper", paper).fetch();
        for(ResultInfo r : rsList)
        {
            if(r.hasComplete())
            {
                ans += r.result;
                count++;
            }
        }
        if(count != 0)return ans/count;
        else return 0;
    }
    /**
     * 获得学生分数分布情况
     * @param paper_id
     * @param demension 分布区间
     * @return 返回一个demension长度的int数组。
     * @note 例如，100分的试卷，demension是2，则求前50分人数，和后50分人数，返回数组。
     *                          demonsion是4，则每25分返回人数。
     */
    public static List<Integer> GetDistributePeopleNumberByPaperId(long paper_id,int demension){
        List<Integer> ans = new ArrayList<Integer>();
        if(demension <= 0)return ans;
        Paper paper = Paper.findById(paper_id);
        List <ResultInfo> rsList = ResultInfo.find("byPaper", paper).fetch();
        for(int i = 0 ; i < demension ; ++i)ans.add(0);
        for(ResultInfo r : rsList)
        {
            if(r.hasComplete())
            {
                int index = (int)(r.result / (100/demension));
                if(index == ans.size())index--;
                int origin = ans.get(index);
                ans.set(index, origin + 1);
            }
        }
        return ans;
    }
    /**
     * 返回试卷的正确率
     * @param paper_id
     * @return  数组，数组序号表示题号，Pair的第1项表示正确人数，Pair第二项表示错误人数
     * @note 例如 { <2,3>，<3,2>,<1,4> },表示第一道题2个人对，3个人错，第二题3个人对，两个人错。
     */
    public static List<Pair<Integer,Integer> >  GetCorrectRateByPaperId(long paper_id){
        List<Pair<Integer,Integer> > ans = new ArrayList<Pair<Integer, Integer>>();
        Paper paper = Paper.findById(paper_id);
        List<Question>  qsList = Question.find("byPaper", paper).fetch();
        for(Question q : qsList)
        {
            List<UserDoneQuestion> udqList = UserDoneQuestion.find("byQuestion", q).fetch();
            int tNum = 0,fNum = 0;
            for(UserDoneQuestion udq : udqList)
            {
                if(udq.answer == q.answer)tNum++;
                else    fNum++;
            }
            ans.add(new Pair<Integer,Integer>(tNum,fNum));
        }
        return ans;
    }
    /**
     * 返回考试详细信息（每道题的学生做的答案和正确答案）
     * @param username
     * @param paper_id
     * @return
     */
    public static List<Pair<Integer,Integer> > GetTestDetail(String username, long paper_id)
    {
        User user = User.find("byName", username).first();
        Paper paper = Paper.findById(paper_id);
        List<Question> qsList = Question.find("byPaper", paper).fetch();
        List<Pair<Integer,Integer> > ret = new ArrayList<Pair<Integer, Integer>>();
        for(Question q : qsList)
        {
            UserDoneQuestion r = UserDoneQuestion.find("byUserAndQuestion", user,q).first();
            if(r != null)ret.add(new Pair<Integer,Integer>(r.answer,q.answer));
            else ret.add(new Pair<Integer,Integer>(-1,q.answer));
        }
        return ret;
    }
}
