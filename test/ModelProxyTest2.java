import controllers.MenuItem;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import models.ModelProxy;
import models.ModelProxy.Pair;
import models.Question;
import org.junit.*;
import play.test.*;/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Kuziki
 */
public class ModelProxyTest2 extends UnitTest{
    @Test
    public void GetAttendExamStudentCountTest()
    {
        int ans = -1;
        ans = ModelProxy.GetAttendExamStudentCount(2);
//        System.out.println(ans);
        assertNotSame(ans, -1);
    }
    @Test
    public void GetAvgScoreByPaperIdTest()
    {
        double ans = -1;
        ans = ModelProxy.GetAvgScoreByPaperId(2);
//        System.out.println(ans);
        assertNotSame(ans, -1);
    }
    @Test
    public void GetDistributePeopleNumberByPaperIdTest()
    {
        List <Integer> ans = ModelProxy.GetDistributePeopleNumberByPaperId(2, 3);
        assertNotNull(ans);
        for(Integer i : ans)
        {
//            System.out.println(i);
        }
    }
    @Test
    public void GetCorrectRateByPaperIdTest()
    {
        List<Pair<Integer,Integer> > ans = ModelProxy.GetCorrectRateByPaperId(2);
        assertNotNull(ans);
        for(Pair<Integer,Integer> p : ans)
        {
//            System.out.print(p.first);System.out.print("+");System.out.print(p.second);
        }
    }
    @Test
    public void GetQuestionsStatusByStudentNameTest()
    {
        List<Boolean> ans = ModelProxy.GetQuestionsStatusByStudentName("kuziki", 2);
        assertNotNull(ans);
        for(Boolean b : ans)
        {
            System.out.println(b);
        }
    }
    @Test
    public void StartPaperTest()
    {
        //ModelProxy.StartPaper("reyoung", 3);
    }
    @Test
    public void SaveQuestionByStudentTest()
    {
        //ModelProxy.SaveQuestionByStudent("reyoung", 4, 1);
    }
    @Test
    public void GetTestDetailTest()
    {
        List<Pair<Integer,Integer> > ans = ModelProxy.GetTestDetail("reyoung", 2);
        assertNotNull(ans);
        for(Pair<Integer,Integer> p : ans)
        {
            System.out.print(p.first);System.out.print("+");System.out.print(p.second);
            System.out.println();
        }
    }
}
