/*
 * This file is written by reyoung, reyoung@126.com.
 */

package models;

import controllers.MenuItem;
import java.util.ArrayList;
import java.util.List;

/**
 *  是Model层的代理类，外部的方便接口
 * @author Reyoung
 */
public class ModelProxy {
    /**
     * 向数据库中新建一个试卷
     * @param username      出题人
     * @param papername     试卷名称
     * @param hour          回答时间
     * @return              新建试卷的ID
     */
    public static long AddNewPaper(String username,String papername,double hour){
        System.out.printf("ModelProxy.AddNewPaper(%s,%s,%f) called", username,papername,hour);
        return -1;
    }

    public static List<MenuItem>    GetPaperByTeacher(String username){
        List<MenuItem> retv = new ArrayList<MenuItem>();
        retv.add(new MenuItem(TeacherPaperId2URL(1), "Paper 1"));
        retv.add(new MenuItem(TeacherPaperId2URL(2), "Paper 2"));
        return retv;
    }

    private static String TeacherPaperId2URL(long id){
        return String.format("/teacher/edit?paper_id=%d", id);
    }
}
