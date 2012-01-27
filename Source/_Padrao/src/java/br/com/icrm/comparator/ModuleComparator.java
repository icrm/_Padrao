package br.com.icrm.comparator;

import br.com.icrm.persistence.entity.Module;
import java.util.Comparator;

public class ModuleComparator implements Comparator<Module> {

    @Override
    public int compare(Module m1, Module m2) {
        if (m1.getCdModule() > m2.getCdModule()) {
            return ((Long) (m1.getCdModule() - m2.getCdModule())).intValue();
        } else if (m1.getCdModule() < m2.getCdModule()) {
            return ((Long) (m1.getCdModule() - m2.getCdModule())).intValue();
        } else {
            return 0;
        }
    }
}
