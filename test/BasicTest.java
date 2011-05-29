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
    
}
