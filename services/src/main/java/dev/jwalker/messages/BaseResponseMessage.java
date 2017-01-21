package dev.jwalker.messages;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Created by jwalker1 on 1/20/17.
 */
public abstract class BaseResponseMessage<T extends Object> {
    public final String id;
    private List<String> errorReasons = new ArrayList<>();
    private Optional<T> payload = Optional.empty();

    public BaseResponseMessage(){
        id = UUID.randomUUID().toString();
    }

    public BaseResponseMessage(String id){
        this.id = id;
    }

    public void setAsFailed(String errorReason){addReason(errorReason);}
    public void setAsSucceeded(){clearReasons();}
    public void addReason(String errorReason){this.errorReasons.add(errorReason);}
    public void clearReasons(){this.errorReasons = new ArrayList<>();}
    public boolean failed(){
        return errorReasons.size() > 0;
    }
    public boolean success(){
        return errorReasons.size() == 0;
    }
    public String outcomeString(){return errorReasons.size() == 0 ? "success" : "failure";}
    public List<String> getErrorReasons(){
        return errorReasons;
    }
    public void setPayload(T payload){
        this.payload = Optional.of(payload);
    }
    public Optional<T> getPayload(){
        return payload;
    }

    public String toString(){
        StringBuilder outputBuf = new StringBuilder();
        String cr = System.lineSeparator();

        outputBuf.append("[").append(id).append("] Request was a ").append(outcomeString());
        if(failed()){
            outputBuf.append(" failure reasons: ").append(cr);
            for(String curReason: errorReasons){
                outputBuf.append("    ").append(curReason).append(cr);
            }
        }
        return outputBuf.toString();
    }
}
