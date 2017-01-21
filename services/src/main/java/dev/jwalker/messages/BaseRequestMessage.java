package dev.jwalker.messages;

import java.util.Map;

/**
 * Created by jwalker1 on 1/20/17.
 */
public abstract class BaseRequestMessage <T extends Object>{
    public final String id;
    public final String contentType;
    protected Map<String, Object> headers;
    protected T body;

    public BaseRequestMessage(String id, String contentType) {
        this.id = id;
        this.contentType = contentType;
    }

    public abstract byte[] payloadToBytes() throws Exception;
    public Map<String, Object> getHeaders() {return headers;}
    public void setHeaders(Map<String, Object> headers) {this.headers = headers;}
    public T getBody() {return body;}
    public void setBody(T body) {this.body = body;}
}
