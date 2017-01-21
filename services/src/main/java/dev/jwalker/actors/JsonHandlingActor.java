package dev.jwalker.actors;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.MissingNode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jwalker1 on 1/19/17.
 */
public abstract class JsonHandlingActor extends BaseActor{
    public static final String PATH = "jsonParser";
    protected ObjectMapper objectMapper = new ObjectMapper();

    public JsonHandlingActor(){
        log.info("Json Actor for {}", self().path().toString());
    }

    public static class StringToJsonResponse {
        private List<String> reasons = new ArrayList<String>();
        public final JsonNode payload;

        public StringToJsonResponse(String errorReason){
            this.reasons.add(errorReason);
            payload = MissingNode.getInstance();
        }

        public StringToJsonResponse(JsonNode payload){
            this.payload = payload;
        }

        public boolean successful(){
            return reasons.size() == 0;
        }

        public boolean failure(){
            return reasons.size() > 0;
        }

        public void addFailureReason(List<String> reasons){
            this.reasons.addAll(reasons);
        }

        public void addFailureReason(String reason){
            reasons.add(reason);
        }
    }
}
