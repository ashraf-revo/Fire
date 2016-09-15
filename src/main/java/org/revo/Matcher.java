package org.revo;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by ashraf on 15/09/16.
 */
public class Matcher {
    private boolean match=true;
    private Map<String, String> urlParams = new HashMap<>();

    public boolean isMatch() {
        return match;
    }

    private Matcher setMatch(boolean match) {
        this.match = match;
        return this;
    }

    public Map<String, String> getUrlParams() {
        return urlParams;
    }

    private Map<String, String> putParam(String key, String value) {
        urlParams.put(key, value);
        return urlParams;
    }

    private Matcher setUrlParams(Map<String, String> urlParams) {
        this.urlParams = urlParams;
        return this;
    }
    static Matcher match(String url, String reqUrl) {
        String[] UrlSplit = url.split("/");
        String[] ReqUrlSplit = reqUrl.split("/");
        if (UrlSplit.length != ReqUrlSplit.length) return new Matcher().setMatch(false);
        Matcher matcher = new Matcher();
        for (int i = 0; i < UrlSplit.length; i++) {
            if ((UrlSplit[i].startsWith("{") && UrlSplit[i].endsWith("}"))) {
                matcher.putParam(UrlSplit[i].replace("{", "").replace("}", ""), ReqUrlSplit[i]);
            } else if (!Objects.equals(UrlSplit[i], ReqUrlSplit[i])) {
                matcher.setMatch(false);
                matcher.setUrlParams(new HashMap<>());
                break;
            }
        }
        return matcher;
    }

}
