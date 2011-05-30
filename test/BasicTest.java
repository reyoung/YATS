import org.junit.*;
import java.util.*;
import java.util.ArrayList;
import play.test.*;
import models.*;

public class BasicTest extends UnitTest {
    @Before
    public void setup(){
        Fixtures.deleteAll();
        Fixtures.loadModels("data.yml");
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
        Paper mao = new Paper("cmmi",false,reyoung);
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
}
