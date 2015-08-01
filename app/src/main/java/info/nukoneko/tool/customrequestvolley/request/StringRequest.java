package info.nukoneko.tool.customrequestvolley.request;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import java.io.UnsupportedEncodingException;

import info.nukoneko.tool.customrequestvolley.Params;

final public class StringRequest<T> extends BaseRequest<T> {
    public StringRequest(@NonNull Context context, Integer method, @NonNull String url, @Nullable Params p, @NonNull Class<T> clazz, @NonNull Response.Listener<T> listener, @NonNull Response.ErrorListener errorListener) {
        super(context, method, url, p, clazz, listener, errorListener);
    }

    @Override
    protected Response<T> networkResponse(NetworkResponse response) {
        String parsed;
        try {
            parsed = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
        } catch (UnsupportedEncodingException e) {
            parsed = new String(response.data);
        }
        return (Response<T>) Response.success(parsed, HttpHeaderParser.parseCacheHeaders(response));
    }
}
