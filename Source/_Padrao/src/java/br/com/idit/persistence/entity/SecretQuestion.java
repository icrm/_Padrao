package br.com.idit.persistence.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "GU_SECRET_QUESTION")
public class SecretQuestion implements Serializable {
    private static final long serialVersionUID = 6731233776342349L;

    @Id
    @Column(name = "CD_QUESTION")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cdQuestion;

    @Column(name = "DS_QUESTION")
    @Length(max = 100, message = "A Frase de acesso não deve conter mais do que 100 caracteres.")
    @NotEmpty(message = "A Frase de acesso não deve estar VAZIA.")
    private String dsQuestion;

    @Column(name = "ACTIVE")
    @Length(max = 1, message = "O Status não deve conter mais do que 1 caracter.")
    @NotEmpty(message = "O Status não deve estar VAZIO.")
    private String active;
    
    @Column(name = "CREATED", insertable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date created = new Date();

    @OneToMany(mappedBy = "question", targetEntity = SecretQuestionResponse.class)
    private List<SecretQuestionResponse> responses = new ArrayList<SecretQuestionResponse>();

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public List<SecretQuestionResponse> getResponses() {
        return responses;
    }

    public void setResponses(List<SecretQuestionResponse> responses) {
        this.responses = responses;
    }

    public Long getCdQuestion() {
        return cdQuestion;
    }

    public void setCdQuestion(Long cdQuestion) {
        this.cdQuestion = cdQuestion;
    }

    public String getDsQuestion() {
        return dsQuestion;
    }

    public void setDsQuestion(String dsQuestion) {
        this.dsQuestion = dsQuestion;
    }

    @Override
    public boolean equals(Object obj) {
        boolean isEqual = true;
        if (obj == null) {
            isEqual = false;
        }
        if (isEqual && (!(obj instanceof SecretQuestion))) {
            isEqual = false;
        }
        final SecretQuestion other = (SecretQuestion) obj;
        if (isEqual && (this.cdQuestion != other.cdQuestion
                && (this.cdQuestion == null 
                || !this.cdQuestion.equals(other.cdQuestion)))) {
            isEqual = false;
        }
        return isEqual;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 19 * hash + (this.cdQuestion != null ? this.cdQuestion.hashCode() : 0);
        return hash;
    }
}
