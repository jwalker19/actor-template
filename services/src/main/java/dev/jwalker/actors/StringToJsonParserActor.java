package dev.jwalker.actors;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.UUID;

import dev.jwalker.messages.BaseRequestMessage;
import dev.jwalker.messages.BaseResponseMessage;

/**
 * Created by jwalker1 on 1/20/17.
 */
public class StringToJsonParserActor extends JsonHandlingActor{

    public StringToJsonParserActor(){
        super();
    }

    public void onReceive(Object message) throws Exception {
        if(message instanceof StringToJson){
            StringToJson request = (StringToJson)message;
            try{
                JsonNode resultBody = objectMapper.readTree(request.payload);
            }catch(Exception e){
                log.error(e, "Encountered error while attempting to parse payload: " + request.payload);
                JsonHandlingActor.StringToJsonResponse result = new JsonHandlingActor.StringToJsonResponse(e.getMessage());
            }
        }
    }

    public static class StringToJson extends BaseRequestMessage<String>{
        public final String payload;

        public StringToJson(String payload){
            super(UUID.randomUUID().toString(), "text/plain");
            this.payload = payload;
        }

        @Override
        public byte[] payloadToBytes() throws Exception {
            return payload.getBytes();
        }
    }

    public static class StringToJsonResult extends BaseResponseMessage<JsonNode>{
        public StringToJsonResult(BaseRequestMessage requestMessage){
            super(requestMessage.id);
        }
    }
}
