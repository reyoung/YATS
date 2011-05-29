/*
 * This file is written by reyoung, reyoung@126.com.
 */
package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.UniqueConstraint;
import play.data.validation.Required;
import play.db.jpa.Model;

/**
 *
 * @author Reyoung
 */
@Entity
public class User extends Model {
    @Required
    @Column(name = "name", nullable = false,unique = true)
    public String name;
    @Required
    @Column(name = "passwd", nullable = false)
    public String passwd;
    @Required
    @Column(name = "type", nullable = false)
    public int type;

    public User(String name, String passwd) {
        this.name = name;
        this.passwd = passwd;
    }

    public static User connect(String name,String passwd){
        return User.find("byNameAndPasswd", name,passwd).first();
    }
    /**
     * For CRUD
     * @return
     */
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        switch(this.type){
            case UserType.ADMINISTRATOR:
                sb.append("Administrator ");
                break;
            case UserType.STUDENT:
                sb.append("Student ");
                break;
            case UserType.TEACHER:
                sb.append("Teacher ");
                break;
        }
        sb.append(name);
        return sb.toString();
    }
}
