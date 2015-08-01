package info.nukoneko.tool.customrequestvolley.request;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.UnsupportedEncodingException;

import info.nukoneko.tool.customrequestvolley.Params;

final public class GsonRequest<T> extends BaseRequest<T> {
    private final Gson gson = new Gson();
    public GsonRequest(
            @NonNull Context context,
            int method,
            @NonNull String url,
            @Nullable Params p,
            @NonNull Class<T> clazz,
            @NonNull Response.Listener<T> listener,
            @NonNull Response.ErrorListener errorListener) {
        super(context, method, url, p, clazz, listener, errorListener);
    }

    @Override
    protected Response<T> networkResponse(NetworkResponse response) {
        try {
            String json = new String(
                    response.data, HttpHeaderParser.parseCharset(response.headers));
            return Response.success(
                    gson.fromJson(json, getClazz()), HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e) {
            return Response.error(new ParseError(e));
        }
    }
}

