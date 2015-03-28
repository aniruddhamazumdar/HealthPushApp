package com.healthpush.healthpushapp.common;

import com.android.volley.Request;
import com.android.volley.Response.ErrorListener;
import com.android.volley.error.VolleyError;

public abstract class PractoBaseRequest<T> extends Request<T> {

	public PractoBaseRequest(int method, String url, ErrorListener listener) {
		super(method, url, listener);
		setShouldCache(false);
	}

	@Override
	public void deliverError(VolleyError error) {
		super.deliverError(error);
        try {
        } catch (Exception e) {
        }
    }
}