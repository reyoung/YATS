
import controllers.MenuItem;
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
        ModelProxy.SaveQuestionByStudent(1, 1, 2);
    }
    @Test
    public void ModelProxyStartPaperTest()
    {
        ModelProxy.StartPaper(1, 1, new Date(111, 5, 9));
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
        Question qs = ModelProxy.GetQuestionByStudent(1, 2, 0, ans);
        assertNotNull(qs);
        //assertNotSame(ans, -1);
    }
}
