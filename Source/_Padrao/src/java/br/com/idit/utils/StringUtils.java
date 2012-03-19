package br.com.idit.utils;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@RequestScoped
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
