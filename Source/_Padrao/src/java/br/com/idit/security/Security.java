package br.com.idit.security;

import br.com.idit.persistence.entity.Policy;
import br.com.idit.persistence.entity.User;
import java.util.List;
import org.apache.log4j.Logger;

public class Security {

    private static final Logger logger;

    static {
        logger = Logger.getLogger(Security.class);
    }

    public static boolean checkPolicy(final User user
            , final String policyFunction) {
        if (user == null) {
            return false;
        }
        logger.debug("Usuário tentando executar a função : " + policyFunction);
        logger.debug("Verificando as permissões do Usuário : "
                + user.getNmUser());
        boolean check = false;
        final List<Policy> policies = user.getGroup().getPolicies();
        for (Policy policy : policies) {
            if (policy.getNmFunction().equals(policyFunction)) {
                check = true;
                break;
            }
        }
        if (check) {
            logger.info("Usuário [" + user.getNmUser()
                    + "] autorizado executar a função ["
                    + policyFunction + "]");
        } else {
            logger.info("Usuário [" + user.getNmUser()
                    + "] não tem permissão para executar a função ["
                    + policyFunction + "]");
        }
        return check;
    }

    public static boolean checkPolicy(final User user
            , final String... policyFunctions) {
        boolean check = false;
        for (int i = 0; i < policyFunctions.length; i++) {
            if (Security.checkPolicy(user, policyFunctions[i])) {
                check = true;
                break;
            }
        }
        return check;
    }
}
