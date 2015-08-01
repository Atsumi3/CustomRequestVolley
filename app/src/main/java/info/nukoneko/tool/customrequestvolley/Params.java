package info.nukoneko.tool.customrequestvolley;

import java.util.HashMap;
import java.util.Map;

public class Params extends HashMap<String, Object> {
    public Params(){}

    public Params(String k, Object v){
        if(v != null) super.put(k,v);
    }

    public Params put(String k, Object v){
        if(v != null) super.put(k, v);
        return this;
    }

    /***
     * put params
     * @param k name
     * @param v value
     * @param exclusionBlankOrNull boolean
     * @return
     */
    public Params put(String k, Object v, boolean exclusionBlankOrNull){
        if(exclusionBlankOrNull && (v== null || String.valueOf(v).equals(""))) return this;
        super.put(k, String.valueOf(v));
        return this;
    }

    public Map<String, String> getMapString(){
        Map<String, String> ret = new HashMap<>();
        for (Entry<String, Object> k : this.entrySet()){
            ret.put(k.getKey(), String.valueOf(k.getValue()));
        }
        return ret;
    }
}
