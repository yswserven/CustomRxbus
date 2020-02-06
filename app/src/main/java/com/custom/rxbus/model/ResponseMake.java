package com.custom.rxbus.model;

import com.custom.rxbus.RxBusRequest;
import com.custom.rxbus.RxBusResponse;
import com.custom.rxbus.core.TypeCenter;
import com.google.gson.Gson;

/**
 * Created by: Ysw on 2020/2/6.
 */
public abstract class ResponseMake {
    protected Class<?> resultClass;
    protected Object[] parameters;
    private Gson GSON = new Gson();
    protected TypeCenter typeCenter = TypeCenter.getInstance();
    protected static final ObjectCenter OBJECT_CENTER = ObjectCenter.getInstance();

    protected abstract Object invokeMethod();

    protected abstract void setMethod(RequestBean requestBean);

    public RxBusResponse makeResponse(RxBusRequest request) {
        RequestBean requestBean = GSON.fromJson(request.getData(), RequestBean.class);
        resultClass = typeCenter.getClassType(requestBean.getResultClassName());
        RequestParameter[] requestParameters = requestBean.getRequestParameters();
        if (requestParameters != null && requestParameters.length > 0) {
            parameters = new Object[requestParameters.length];
            for (int i = 0; i < requestParameters.length; i++) {
                RequestParameter requestParameter = requestParameters[i];
                Class<?> clazz = typeCenter.getClassType(requestParameter.getParameterClassName());
                parameters[i] = GSON.fromJson(requestParameter.getParameterValue(), clazz);
            }
        } else {
            parameters = new Object[0];
        }
        setMethod(requestBean);
        Object resultObject = invokeMethod();
        ResponseBean responseBean = new ResponseBean(resultObject);
        String data = GSON.toJson(responseBean);
        return new RxBusResponse(data);
    }

}
