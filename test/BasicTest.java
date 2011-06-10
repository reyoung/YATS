import controllers.MenuItem;
import org.junit.*;
import java.util.*;
import java.util.ArrayList;
import play.test.*;
import models.*;

public class BasicTest extends UnitTest {
    @Before
    public void setup(){
//        Fixtures.deleteAll();
//        Fixtures.loadModels("data.yml");
    }

    @Test
    public void UserModelTest(){
        User reyoung = User.find("byName", "reyoung").first();
        assertNotNull(reyoung);
    }
    @Test
    public void PaperModelTest()
    {
        User reyoung = User.find("byName", "reyoung").first();
        Paper mao = new Paper("cmmi",false,7200,reyoung);
        mao.save();
        assertNotNull(mao);
    }
    @Test
    public void QuestionModelTest()
    {
        Paper mao = Paper.find("byName", "cmmi").first();
        assertNotNull(mao);
        List<String> ans = new ArrayList<String>();
        ans.add("a");
        ans.add("b");
        ans.add("c");
        ans.add("d");
        ans.add("e");
        ans.add("f");
        ans.add("g");
        ans.add("h");
        Question qs = new Question("first", 2, "a","b","c", mao);
        qs.saveSelection(ans);
    }
    @Test
    public void QuestionSaveModelTest()
    {
        Paper mao = Paper.find("byName", "cmmi").first();
        mao.addQuestion("q1", 2, "1", "2", "3");
        Question qs = Question.find("byTitle", "q1").first();
        assertNotNull(qs);
    }
    @Test
    public void UsrDoQuesModelTest()
    {
        User reyoung = User.find("byName", "reyoung").first();
        Question qs = Question.find("byTitle", "q1").first();
        Question qs2 = Question.find("byTitle", "first").first();
        UserDoneQuestion udq = new UserDoneQuestion(reyoung, qs, 3);
        UserDoneQuestion udq2 = new UserDoneQuestion(reyoung, qs2, 2);
        udq.save();
        udq2.save();
        assertNotSame(0, UserDoneQuestion.count());
    }
    @Test
    public void ResultInfoModelTest()
    {
        User reyoung = User.find("byName", "reyoung").first();
        Paper mao = Paper.find("byName", "cmmi").first();
        Date start = new Date(111,4,30);
        ResultInfo ri = new ResultInfo(reyoung, mao, start);
        ri.save();
        assertNotSame(0, ResultInfo.count());
    }
    @Test
    public void ResultInfoScoreModelTest()
    {
        long id = 1;
        ResultInfo ri = ResultInfo.findById(id);
        ri.score();
        assertTrue((ri.result - 50) < 0.1);
    }
    @Test
    public void ModelProxyAddNewPaperTest()
    {
        long id = ModelProxy.AddNewPaper("reyoung", "paper_3", 2);
        assertNotSame(id, 0);
    }
    @Test
    public void ModelProxyGetPaperByTeacherTest()
    {
        //List<MenuItem> retv = ModelProxy.GetPaperByTeacher("reyoung");
        //assertNotNull(retv);
    }
    @Test
    public void ModelProxyPublishPaperByIDTest()
    {
        boolean ans = ModelProxy.PublishPaperByID("reyoung", 1);
        assertNotSame(ans, false);
    }
    @Test
    public void ModelProxyRemovePaperByIDTest()
    {
        boolean ans = ModelProxy.RemovePaperByID("reyoung", 2);
        assertNotSame(ans, false);
    }
    @Test
    public void ModelProxyNewStubQuestionTest()
    {
        int ans = -1;
        ans = ModelProxy.NewStubQuestion(1);
        assertNotSame(ans, -1);
    }
}
