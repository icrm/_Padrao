package br.com.icrm.utils;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ViewScoped
@ManagedBean(name = "stringUtils")
public class StringUtils {

    public String strip(final String toStrip, final int length) {
        if (toStrip.length() < length) {
            return toStrip;
        } else {
            return toStrip.substring(0, length) + "...";
        }
    }
}
