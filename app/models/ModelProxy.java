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
    /**
     * 返回一个Teacher的所有Paper ID 和名称
     * @param username
     * @return
     */
    public static List<MenuItem>    GetPaperByTeacher(String username){
        List<MenuItem> retv = new ArrayList<MenuItem>();
        retv.add(new MenuItem(TeacherPaperId2URL(1), "Paper 1"));
        retv.add(new MenuItem(TeacherPaperId2URL(2), "Paper 2"));
        return retv;
    }

    /**
     * 删除一个Paper
     * @param username  教师姓名
     * @param id        试卷id
     * @return          返回是否删除成功,
     *                   如果这个试卷不是这个教师出的，那么没有权利删除。
     */
    static public boolean RemovePaperByID(String username,long id){
        return false;
    }
    /**
     * 发布一个paper
     * @param username  教师姓名
     * @param id        试卷ID
     * @return          返回是否发布成功，
     *                   如果这个试卷不是这个老师出的，没有权利发布。
     */
    static public boolean PublishPaperByID(String username,long id){
        return false;
    }

    /**
     * 由Paper_id转成URL
     * @param id
     * @return
     */
    private static String TeacherPaperId2URL(long id){
        return String.format("/teacher/draft/edit?paper_id=%d", id);
    }
}
