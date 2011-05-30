/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package models;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.UniqueConstraint;
import play.data.validation.Required;
import play.db.jpa.Model;
/**
 *
 * @author Kuziki
 */
@Entity
public class UserDoneQuestion extends Model{
    @ManyToOne
    public User user;
    @ManyToOne
    public Question question;
    @Required
    @Column(name = "answer", nullable = false)
    public int answer;

    public UserDoneQuestion(User user, Question question, int answer) {
        this.user = user;
        this.question = question;
        this.answer = answer;
    }
}
