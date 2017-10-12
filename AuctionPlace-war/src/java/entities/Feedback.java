package entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * An entity representing a Feedback.
 * 
 * A feedback is a comment that can be submitted to a product. Once a Customer 
 * has won an auction, the Customer can choose to write a Feedback to the 
 * received product. The language is always assumed to be English.
 */
@Entity
@XmlRootElement
public class Feedback implements Serializable {

    private static final long serialVersionUID = 3L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String comment;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AUTHOR_ID", referencedColumnName = "ID")
    private Customer author;

    /**
     * Constructs an empty Feedback object.
     */
    public Feedback() {
        this.comment = null;
        this.author = null;
    }
    
    /**
     * Constructs a Feedback object with the specified information.
     * 
     * @param comment the text comment
     * @param author the Customer writing the Feedback
     */
    public Feedback(String comment, Customer author) {
        this.comment = comment;
        this.author = author;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Customer getAuthor() {
        return author;
    }

    public void setAuthor(Customer author) {
        this.author = author;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Feedback)) {
            return false;
        }
        Feedback other = (Feedback) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Feedback[ id=" + id + " ]";
    }
    
}
