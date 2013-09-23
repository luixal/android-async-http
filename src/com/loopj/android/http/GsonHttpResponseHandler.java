package com.loopj.android.http;

import java.lang.reflect.Type;

import org.apache.http.Header;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;

public class GsonHttpResponseHandler<T> extends AsyncHttpResponseHandler {
	
    protected static final int SUCCESS_JSON_MESSAGE = 100;
    
    private Gson gson;
    private Type type;
    
    public GsonHttpResponseHandler() {
    	throw new RuntimeException("You must provide Gson and Type objects in order to use GsonHttpResponseHandler!");
    }
    
    public GsonHttpResponseHandler(Gson gson, Type type) {
    	this.gson = gson;
    	this.type = type;
    }
    
    public GsonHttpResponseHandler(Gson gson, Class<?> clazz) {
    	this.gson = gson;
    	this.type = clazz;
    }
    
    public void onSuccess(T response) {}
    
    @Override
    protected void sendSuccessMessage(int statusCode, Header[] headers, String responseBody) {
    	try {
    		T result = this.gson.fromJson(responseBody, this.type);
    		this.onSuccess(result);
    	} catch (JsonParseException ex) {
    		this.sendFailureMessage(ex, responseBody);
    	}
    }

}
