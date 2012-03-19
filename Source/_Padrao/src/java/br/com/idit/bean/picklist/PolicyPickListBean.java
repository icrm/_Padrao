package br.com.idit.bean.picklist;

import br.com.idit.bean.PolicyBean;
import br.com.idit.persistence.entity.Policy;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import org.primefaces.model.DualListModel;

@RequestScoped
@ManagedBean(name = "policyPickListBean")
public class PolicyPickListBean implements Serializable {
    private static final long serialVersionUID = -1L;
    
    private DualListModel<Policy> policies;
    
    public PolicyPickListBean() {
        PolicyBean pb = (PolicyBean) FacesContext.getCurrentInstance().getELContext().getELResolver().getValue(FacesContext.getCurrentInstance().getELContext(), null, "policyBean");
        List<Policy> source = new ArrayList<Policy>();
        List<Policy> target = new ArrayList<Policy>();
        
        source = pb.getPolicies();
        policies = new DualListModel(source, target);
    }

    public DualListModel<Policy> getPolicies() {
        return policies;
    }

    public void setPolicies(DualListModel<Policy> policies) {
        this.policies = policies;
    }

}
