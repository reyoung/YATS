/*
 * This file is written by reyoung, reyoung@126.com.
 */

package models;

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
}
