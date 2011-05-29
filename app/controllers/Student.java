/*
 * This file is written by reyoung, reyoung@126.com.
 */

package controllers;

import play.mvc.Controller;
import play.mvc.With;

/**
 *
 * @author Reyoung
 */
@Check("student")
@With(Secure.class)
public class Student extends Controller{
    public static void index(){
        String name = Security.connected();
        render(name);
    }
}
