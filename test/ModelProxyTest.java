
import controllers.MenuItem;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import models.ModelProxy;
import models.Question;
import org.junit.*;
import play.test.*;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Kuziki
 */
public class ModelProxyTest extends UnitTest{
    @Test
    public void  ModelProxyGetPaperUnDoneByStudentTest()
    {
        List<MenuItem> lm = ModelProxy.GetPaperUnDoneByStudent("reyoung");
        assertNotNull(lm);
    }
    @Test
    public void ModelProxyGetPaperDoneByStudentTest()
    {
        List<MenuItem> lm = ModelProxy.GetPaperUnDoneByStudent("kuziki");
        assertNotNull(lm);
    }
    @Test
    public void ModelProxySaveQuestionByStudentTest()
    {
        //ModelProxy.SaveQuestionByStudent(1, 1, 2);
    }
    @Test
    public void ModelProxyStartPaperTest()
    {
        ModelProxy.StartPaper(1, 1);
    }
    @Test
    public void ModelProxyGetScoreTest()
    {
        double score = ModelProxy.GetScore(1, 1);
        //System.out.print(score);
    }
    @Test
    public void ModelProxyGetQuestionByStudentTest()
    {
        Integer ans = -1;
        //Question qs = ModelProxy.GetQuestionByStudent(1, 2, 0, ans);
        //assertNotNull(qs);
        //assertNotSame(ans, -1);
    }
    @Test
    public void ModelProxyUpdateQuestionByTeacher()
    {
        boolean ans = false;
        List<String> ls = new ArrayList<String>();
        ls.add("Very");
        ls.add("just so so ");
        ls.add("hate");
        ls.add("Unknown");
        ans = ModelProxy.UpdateQuestionByTeacher(2,"Do you love Sola" , 2, ls);
    }
    @Test
    public void ModelProxyDeleteQuestionTest()
    {
        boolean ans = false;
        ans = ModelProxy.DeleteQuestion(1);
        assertNotSame(ans, false);
    }
    @Test
    public void ModelProxyNewStubQuestionTest()
    {
        int ans = -1;
        ans = ModelProxy.NewStubQuestion(2);
        assertNotSame(ans, -1);
    }
}
