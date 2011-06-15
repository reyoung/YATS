/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package models;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.UniqueConstraint;
import play.data.validation.Required;
import play.db.jpa.Model;
/**
 *
 * @author Kuziki
 */
@Entity
public class ResultInfo extends Model{
    @ManyToOne
    public User user;
    @ManyToOne
    public Paper paper;
    @Required
    @Column(name = "startTime", nullable = false)
    public Date startTime;
    @Column(name = "result", nullable = true)
    public double result;

    public ResultInfo(User user, Paper paper, Date startTime) {
        this.user = user;
        this.paper = paper;
        this.startTime = startTime;
        this.result = -1;
    }
    public boolean hasComplete()
    {
        return (this.result > -0.5);
    }
    public double score()
    {
        if(hasComplete())
        {
            return result;
        }
        double res = 0;
        List<Question> qs = Question.find("select q from Question q where q.paper = ?",
                paper).fetch();
        int qnum = qs.size();
        double scorePerQus = 100.0 / qnum;
        List<UserDoneQuestion> udq = UserDoneQuestion.find("byUser", this.user).fetch();
        if(udq == null || udq.size() == 0)
        {
            result = res;
            this.save();
            return result;
        }
        for(UserDoneQuestion u : udq)
        {
            if(u.question.paper.id == paper.id){
                if(u.answer == u.question.answer)
                    res+=scorePerQus;
            }
        }
        result = res;
        this.save();
        return result;
    }
}
