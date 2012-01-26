package br.com.icrm.bean.tree;

import br.com.icrm.bean.ModuleBean;
import br.com.icrm.persistence.entity.Module;
import br.com.icrm.persistence.entity.Page;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

@RequestScoped
@ManagedBean()
public class PageTreeBean implements Serializable {

    private static final long serialVersionUID = -1L;
    private TreeNode node = new DefaultTreeNode("Aplicação", null);
    private TreeNode[] selectedNodes;
    private List<Page> selectedPages = new ArrayList<Page>();

    public PageTreeBean() {
        init();
    }

    public TreeNode getNode() {
        return node;
    }

    public void setNode(TreeNode node) {
        this.node = node;
    }

    public TreeNode[] getSelectedNodes() {
        return selectedNodes;
    }

    public void setSelectedNodes(TreeNode[] selectedNodes) {
        this.selectedNodes = selectedNodes;
    }

    public List<Page> getSelectedPages() {
        return selectedPages;
    }

    public void setSelectedPages(List<Page> selectedPages) {
        this.selectedPages = selectedPages;
    }

    @PostConstruct
    private void init() {
        FacesContext fc = FacesContext.getCurrentInstance();
        ModuleBean mb = (ModuleBean) fc.getELContext().getELResolver().getValue(fc.getELContext(), null, "moduleBean");
        getNodes(mb.getOrphanModules(), node);
    }

    public void clearSelection() {
        this.selectedNodes = null;
        this.selectedPages.clear();
        this.node = new DefaultTreeNode("Aplicação", null);
        init();
    }
    
    public void defineSelected(List<Page> pages) {
        this.selectedPages.clear();
        this.selectedPages = pages;
        this.node = new DefaultTreeNode("Aplicação", null);
        init();
    }

    private void getNodes(List<Module> modules, TreeNode root) {
        for (Module m : modules) {
            TreeNode n = new DefaultTreeNode(m.getNmModule(), root);
            n.setExpanded(true);
            if (m.getPages() != null) {
                for (Page p : m.getPages()) {
                    boolean isSelected = false;
                    for (Page p1 : selectedPages) {
                        if (p.equals(p1)) {
                            isSelected = true;
                            break;
                        }
                    }
                    TreeNode newNode = new DefaultTreeNode(p, n);
                    newNode.setSelected(isSelected);
                }
            }
            getNodes(m.getChildren(), n);
        }
    }
}
