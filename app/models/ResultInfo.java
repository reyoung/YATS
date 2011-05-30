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
}
