package br.com.idit.datamodel;

import br.com.idit.persistence.entity.Module;
import java.util.List;
import javax.faces.model.ListDataModel;
import org.primefaces.model.SelectableDataModel;

public class ModuleDataModel extends ListDataModel<Module> implements SelectableDataModel<Module> {

    public ModuleDataModel() {
    }

    public ModuleDataModel(List<Module> modules) {
        super(modules);
    }

    @Override
    public Object getRowKey(Module module) {
        return module.getCdModule();
    }

    @Override
    public Module getRowData(final String string) {
        final List<Module> modules = (List<Module>) getWrappedData();
        for(Module module : modules) {
            if (module.getCdModule()==new Long(string)) {
                return module;
            }
        }
        return null;
    }
}
