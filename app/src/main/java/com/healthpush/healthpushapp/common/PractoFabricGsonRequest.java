package com.healthpush.healthpushapp.common;

import android.support.v4.util.ArrayMap;
import android.text.TextUtils;

import com.android.volley.NetworkResponse;
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

import static com.healthpush.healthpushapp.HealthPushApplication.AUTH_HEADER;

public class PractoFabricGsonRequest<T> extends PractoBaseRequest<T> {

    public static final String AUTH_HEADER = "X-AUTH-TOKEN";
    public static final String PROFILE_TOKEN_HEADER = "X-PROFILE-TOKEN";
    public static final String FABRIC_TOKEN_HEADER = "X-FABRIC-API-TOKEN";
    public static final String DROID_HEADER = "X-DROID-VERSION";
    public static final String API_VERSION = "API-Version";
    public static final String ACCEPT = "Accept";
    public static final String ACCEPT_TYPE_JSON = "application/json";
    public static final String TAG = "FabricVolley";

	private final Gson gson = new Gson();
	private final Class<T> clazz;
	private Map<String, String> headers;
	private final Map<String, String> params;
	private final Listener<T> listener;
	private final String token;

	// private final String auth;

	public PractoFabricGsonRequest(int type, String url, Class<T> clazz, String token, Map<String, String> params, Listener<T> listener,
			ErrorListener errorListener) {
		super(type, url, errorListener);
		this.clazz = clazz;
		this.params = params;
		this.listener = listener;
		this.token = token;
		init();
	}

	private void init() {
		if (headers == null) {
			headers = new ArrayMap<String, String>();
		}

        headers.put(AUTH_HEADER, HealthPushApplication.X_AUTH_TOKEN);
		headers.put(DROID_HEADER, "android");
		headers.put(ACCEPT, ACCEPT_TYPE_JSON);
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
			String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
			return Response.success(gson.fromJson(json, clazz), Utils.parseIgnoreCacheHeaders(response));
		} catch (UnsupportedEncodingException e) {
			return Response.error(new ParseError(e));
		} catch (JsonSyntaxException e) {
			return Response.error(new ParseError(e));
		}
	}
}
