/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.UniqueConstraint;
import play.data.validation.Required;
import play.db.jpa.Model;

/**
 *
 * @author Kuziki
 */
@Entity
public class Question extends Model {

    @Required
    @Column(name = "title", nullable = false)
    public String title;
    @Required
    @Column(name = "answer", nullable = false)
    public int answer;
    @Required
    @Column(name = "seletionA", nullable = false)
    public String seletionA;
    @Required
    @Column(name = "seletionB", nullable = false)
    public String seletionB;
    @Required
    @Column(name = "seletionC", nullable = false)
    public String seletionC;
    @Column(name = "seletionD", nullable = true)
    public String seletionD;
    @Column(name = "seletionE", nullable = true)
    public String seletionE;
    @Column(name = "seletionF", nullable = true)
    public String seletionF;
    @Column(name = "seletionG", nullable = true)
    public String seletionG;
    @Column(name = "seletionH", nullable = true)
    public String seletionH;
    @ManyToOne
    public Paper paper;

    public Question(String title, int answer, String seletionA, String seletionB, String seletionC, Paper paper) {
        this.title = title;
        this.answer = answer;
        this.seletionA = seletionA;
        this.seletionB = seletionB;
        this.seletionC = seletionC;
        this.paper = paper;
    }

    public List<String> getSelections() {
        List<String> retv = new ArrayList<String>();
        if (!seletionA.isEmpty()) {
            retv.add(seletionA);
        }
        if (!seletionB.isEmpty()) {
            retv.add(seletionB);
        }
        if (!seletionC.isEmpty()) {
            retv.add(seletionC);
        }
        if (!seletionD.isEmpty()) {
            retv.add(seletionD);
        }
        if (!seletionE.isEmpty()) {
            retv.add(seletionE);
        }
        if (!seletionF.isEmpty()) {
            retv.add(seletionF);
        }
        if (!seletionG.isEmpty()) {
            retv.add(seletionG);
        }
        if (!seletionH.isEmpty()) {
            retv.add(seletionH);
        }
        return retv;
    }

    public void saveSelection(List<String> sel) {
        assert (sel.size() > 2);
        seletionA = sel.get(0);
        seletionB = sel.get(1);
        seletionC = sel.get(2);
        switch (sel.size()) {
            case 8:
                seletionH = sel.get(7);
            case 7:
                seletionG = sel.get(6);
            case 6:
                seletionF = sel.get(5);
            case 5:
                seletionE = sel.get(4);
            case 4:
                seletionD = sel.get(3);
            default:
        }
        this.save();
    }
}
