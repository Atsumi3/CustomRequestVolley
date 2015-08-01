package info.nukoneko.tool.customrequestvolley.request;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;

import java.util.Map;

import info.nukoneko.tool.customrequestvolley.Params;

abstract public class BaseRequest<T> extends Request<T> {

    private final Map<String, String> header;
    private final Context context;
    private final Class<T> clazz;
    private final Response.Listener<T> listener;
    private final Params mParams;

    public BaseRequest(
            @NonNull Context context,
            int method,
            @NonNull String url,
            @Nullable Params p,
            @NonNull Class<T> clazz,
            @NonNull Response.Listener<T> listener,
            @NonNull Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        this.context = context;
        this.clazz = clazz;
        this.mParams = p;
        this.listener = listener;
        this.header = getDefaultHeader();
    }

    final public Context getContext(){
        return this.context;
    }

    final public Class<T> getClazz(){
        return this.clazz;
    }

    protected abstract Response<T> networkResponse(NetworkResponse response);

    @Override
    final protected void deliverResponse(T response){
        listener.onResponse(response);
    }

    @Override
    final protected Map<String, String> getParams(){
        assert mParams != null;
        return mParams.getMapString();
    }

    @Override
    final public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> customHeaders = super.getHeaders();
        Map<String, String> newHeaders = this.header;
        newHeaders.putAll(customHeaders);
        return newHeaders;
    }


    @Override
    final protected Response<T> parseNetworkResponse(NetworkResponse response) {
        // e.g. SessionManager.saveCookie(getLatteCookie(response.apacheHeaders));
        return networkResponse(response);
    }

    private Map<String, String> getDefaultHeader() {
        Params ret = new Params();
        ret.put("Content-Type", "application/x-www-form-urlencoded");
        // etc...
        return ret.getMapString();
    }
}
