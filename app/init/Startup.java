/*
 * This file is written by reyoung, reyoung@126.com.
 */

package init;

import models.User;
import play.jobs.Job;
import play.jobs.OnApplicationStart;
import play.test.Fixtures;

/**
 *
 * @author Reyoung
 */
@OnApplicationStart
public class Startup extends Job{
    public void doJob(){
        if(User.count()==0){
            Fixtures.loadModels("data.yml");
        }
    }
}
