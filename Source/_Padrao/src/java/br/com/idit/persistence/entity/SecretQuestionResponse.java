package br.com.idit.persistence.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "GU_SECRET_QUESTION_RESPONSE")
public class SecretQuestionResponse implements Serializable {

    private static final long serialVersionUID = 6374829376976123L;

    @Id
    @Column(name = "CD_RESPONSE")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cdResponse;

    @ManyToOne(targetEntity = User.class, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "CD_USER")
    private User user;

    @ManyToOne(targetEntity = SecretQuestion.class)
    @JoinColumn(name = "CD_QUESTION")
    private SecretQuestion question;

    @Column(name = "DS_RESPONSE")
    @Length(max = 100, message = "A Resposta da Frase de acesso não deve conter mais do que 100 caracteres.")
    @NotEmpty(message = "A Resposta da Frase de acesso não deve estar VAZIA.")
    private String dsResponse;

    @Column(name = "CREATED", insertable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date created = new Date();

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getDsResponse() {
        return dsResponse;
    }

    public void setDsResponse(String dsResponse) {
        this.dsResponse = dsResponse;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getCdResponse() {
        return cdResponse;
    }

    public void setCdResponse(Long cdResponse) {
        this.cdResponse = cdResponse;
    }

    public SecretQuestion getQuestion() {
        return question;
    }

    public void setQuestion(SecretQuestion question) {
        this.question = question;
    }

    @Override
    public boolean equals(Object obj) {
        boolean isEqual = true;
        if (obj == null) {
            isEqual = false;
        }
        if (isEqual && (obj instanceof SecretQuestionResponse)) {
            isEqual = false;
        }
        final SecretQuestionResponse other = (SecretQuestionResponse) obj;
        if (isEqual && (this.cdResponse != other.cdResponse
                && (this.cdResponse == null
                || !this.cdResponse.equals(other.cdResponse)))) {
            isEqual = false;
        }
        return isEqual;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + (this.cdResponse != null ? this.cdResponse.hashCode() : 0);
        return hash;
    }
}
