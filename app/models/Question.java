/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package models;
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
public class Question extends Model{
    @Required
    @Column(name = "title", nullable = false)
    public String title;
    @Required
    @Column(name = "answer", nullable = false)
    public int answer;
    @Column(name = "seletion", nullable = false)
    public List<String> seletion;

    @ManyToOne
    public Paper paper;

    public Question(String title, int answer, List<String> seletion, Paper paper) {
        this.title = title;
        this.answer = answer;
        this.seletion = seletion;
        this.paper = paper;
    }
}
