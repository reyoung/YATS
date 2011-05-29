/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package model;
import java.util.*;
import javax.persistence.*;

import play.db.jpa.*;
import play.data.validation.*;
/**
 *
 * @author Kuziki
 */
@Entity
public class User extends Model{
    @Required
    public String name;
    @Required
    public String pwd;
    @Required
    public int type;
    public User(String uname,String upwd,int utype)
    {
        this.name = uname;
        this.pwd = upwd;
        this.type = utype;
    }
    public static User connect(String uname,String upwd)
    {
        return find("byNameAndPwd",uname,upwd).first();
    }
}
