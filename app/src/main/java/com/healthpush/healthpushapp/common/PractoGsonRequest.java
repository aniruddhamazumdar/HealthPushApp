package com.healthpush.healthpushapp.common;

import android.support.v4.util.ArrayMap;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.ParseError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.healthpush.healthpushapp.HealthPushApplication;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import static com.healthpush.healthpushapp.HealthPushApplication.ACCEPT;
import static com.healthpush.healthpushapp.HealthPushApplication.AUTH_HEADER;
import static com.healthpush.healthpushapp.HealthPushApplication.DROID_HEADER;

/**
 * Volley adapter for JSON requests that will be parsed into Java objects by Gson.
 */
public class PractoGsonRequest<T> extends PractoBaseRequest<T> {
    public static final String TAG = "FabricVolley";

    private final Gson gson = new Gson();
    private final Class<T> clazz;
    private Map<String, String> headers;
    private final Map<String, String> params;
    private final Listener<T> listener;
    private final String auth;

    public PractoGsonRequest(String url, Class<T> clazz, String auth,
                             Listener<T> listener, ErrorListener errorListener) {
        super(Request.Method.GET, url, errorListener);
        this.clazz = clazz;
        this.params = null;
        this.listener = listener;
        this.auth = auth;
        init();
    }

    public PractoGsonRequest(int type, String url, Class<T> clazz, String auth,
                             Map<String, String> params,
                             Listener<T> listener, ErrorListener errorListener) {
        super(type, url, errorListener);
        this.clazz = clazz;
        this.params = params;
        this.listener = listener;
        this.auth = auth;
        init();
    }

    private void init() {
        if (headers == null) {
            headers = new ArrayMap<String, String>();
        }
        headers.put(AUTH_HEADER, HealthPushApplication.X_AUTH_TOKEN);
        headers.put(DROID_HEADER, "android");
        headers.put(ACCEPT, "application/json");
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return headers != null ? headers : super.getHeaders();
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return params != null ? params : super.getParams();
    }

    @Override
    protected void deliverResponse(T response) {
        if (null != listener) {
            listener.onResponse(response);
        }
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String json = new String(
                    response.data, HttpHeaderParser.parseCharset(response.headers));
            return Response.success(
                    gson.fromJson(json, clazz), Utils.parseIgnoreCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e) {
            return Response.error(new ParseError(e));
        }
    }
}