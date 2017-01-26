package dev.jwalker.actors;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.UUID;

import dev.jwalker.messages.BaseRequestMessage;
import dev.jwalker.messages.BaseResponseMessage;

/**
 * Created by jwalker1 on 1/20/17.
 */
public class JsonToStringParserActor extends JsonHandlingActor{
    public JsonToStringParserActor(){

    }

    @Override
    public void onReceive(Object message) throws Exception {

    }

    public static class JsonToStringRequest extends BaseRequestMessage<JsonNode>{
        public JsonToStringRequest(JsonNode payload){
            super(UUID.randomUUID().toString(), "application/json");
            this.body = payload;
        }

        @Override
        public byte[] payloadToBytes() throws Exception {
            return body.binaryValue();
        }
    }

    public static class JsonToStringResponse extends BaseResponseMessage<String>{
        public JsonToStringResponse(BaseRequestMessage requestMessage){
            super(requestMessage.id);
        }
    }
}
