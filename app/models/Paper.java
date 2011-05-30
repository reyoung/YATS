/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package models;
import java.util.ArrayList;
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
public class Paper extends Model{
    @Required
    @Column(name = "name", nullable = false)
    public String name;
    @Required
    @Column(name = "isPublished", nullable = false)
    public boolean isPublished;
    @Required
    @Column(name = "time", nullable = false)
    public int time; //以秒计时的总考试时间
    @Required
    @ManyToOne
    public User author;

    @OneToMany(mappedBy="paper", cascade=CascadeType.ALL)
    public List<Question> questions;

    public Paper(String name, boolean isPublished,int time ,User author) {
        this.name = name;
        this.isPublished = isPublished;
        this.time = time;
        this.author = author;
        this.questions = new ArrayList<Question>();
    }

    public Paper addQuestion(String title, int answer, String seletionA, String seletionB, String seletionC)
    {
        Question qs = new Question(title, answer, seletionA, seletionB, seletionC, this);
        this.questions.add(qs);
        this.save();
        return this;
    }
}
