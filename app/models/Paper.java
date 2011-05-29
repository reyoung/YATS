/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package models;
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
public class Paper extends Model{
    @Required
    @Column(name = "name", nullable = false)
    public String name;
    @Required
    @Column(name = "isPublished", nullable = false)
    public boolean isPublished;
    @Required
    @ManyToOne
    public User author;
    
    public Paper(String name, boolean isPublished, User author) {
        this.name = name;
        this.isPublished = isPublished;
        this.author = author;
    }
}
