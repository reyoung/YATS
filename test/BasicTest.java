import org.junit.*;
import java.util.*;
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
        reyoung.addPaper("maogai");
        Paper mao = Paper.find("byName", "maogai").first();
        assertNotNull(mao);
    }
}
