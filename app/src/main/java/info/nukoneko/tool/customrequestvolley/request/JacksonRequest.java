package info.nukoneko.tool.customrequestvolley.request;

import android.content.Context;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import info.nukoneko.tool.customrequestvolley.Params;

final public class JacksonRequest<T> extends BaseRequest<T> {
    public JacksonRequest(
            Context context,
            int method,
            String url,
            Params params,
            Class<T> clazz,
            Response.Listener<T> listener,
            Response.ErrorListener errorListener) {
        super(context, method, url, params, clazz, listener, errorListener);
    }

    // StackOverflow
    // http://stackoverflow.com/a/17441709
    @Override
    protected Response<T> networkResponse(NetworkResponse response) {
        try
        {
            String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            return Response.success(objectOrThrow(jsonString, getClazz()), HttpHeaderParser.parseCacheHeaders(response));
        }
        catch (Exception e)
        {
            return Response.error(new ParseError(e));
        }
    }
    private static ObjectMapper MAPPER;
    public static <T> T objectOrThrow(String data, Class<T> type) throws IOException
    {
        if (MAPPER == null) {
            MAPPER = new ObjectMapper();
            MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        }
        return MAPPER.readValue(data, type);
    }
}
