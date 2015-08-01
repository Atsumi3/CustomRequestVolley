package info.nukoneko.tool.customrequestvolley.client;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;

import java.lang.reflect.InvocationTargetException;

import info.nukoneko.tool.customrequestvolley.AppController;
import info.nukoneko.tool.customrequestvolley.Params;
import info.nukoneko.tool.customrequestvolley.request.BaseRequest;
import info.nukoneko.tool.customrequestvolley.request.StringRequest;


final public class VolleyClient<ReturnObject>
        extends AsyncTaskLoader<ReturnObject>
        implements LoaderManager.LoaderCallbacks<ReturnObject>{

    //final Class<? extends BaseRequest> request = GsonRequest.class;
    //final Class<? extends BaseRequest> request = JacksonRequest.class;
    final Class<? extends BaseRequest> request = StringRequest.class;

    private Response.Listener<ReturnObject> responseListener = null;
    private Response.ErrorListener errorListener = null;
    private Params params = null;

    final AppCompatActivity context;
    final Integer method;
    final String url;
    final Class<ReturnObject> clazz;

    public VolleyClient(
            @NonNull AppCompatActivity context,
            @NonNull String url,
            @NonNull Class<ReturnObject> clazz,
            @Nullable Response.Listener<ReturnObject> responseListener){
        this(context, url, Request.Method.GET, null, clazz, responseListener, null);
    }

    public VolleyClient(
            @NonNull AppCompatActivity context,
            @NonNull String url,
            int method,
            @Nullable Params params,
            @NonNull Class<ReturnObject> clazz,
            @Nullable Response.Listener<ReturnObject> responseListener,
            @Nullable Response.ErrorListener errorListener){
        super(context);
        this.context = context;
        this.clazz = clazz;
        this.method = method;
        this.url = url;
        this.params = Request.Method.GET == this.method?null:params;
        this.responseListener = responseListener;
        this.errorListener = errorListener;
        context.getSupportLoaderManager().initLoader(0, null, this);
    }

    private void send() {
        try {
            Object[] args = new Object[] {
                    this.context,
                    this.method,
                    this.url,
                    this.params,
                    this.clazz,
                    this.responseListener,
                    this.errorListener
            };
            Request req = (Request) request.getConstructors()[0].newInstance(args);
            AppController.get().addToRequestQueue(req, this.url);
        } catch (InvocationTargetException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ReturnObject loadInBackground() {
        send();
        return null;
    }

    @Override
    final protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public Loader<ReturnObject> onCreateLoader(int id, Bundle args) {
        return this;
    }

    @Override
    public void onLoadFinished(Loader<ReturnObject> loader, ReturnObject data) {

    }

    @Override
    public void onLoaderReset(Loader<ReturnObject> loader) {

    }
}
