package dev.jwalker.actors;

import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedActor;

import static dev.jwalker.actors.StringToJsonParserActor.StringToJson;
import static dev.jwalker.actors.StringToJsonParserActor.StringToJsonResult;
import static dev.jwalker.actors.JsonToStringParserActor.JsonToStringRequest;
import static dev.jwalker.actors.JsonToStringParserActor.JsonToStringResponse;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * Created by jwalker1 on 1/20/17.
 */
public class JsonParseTests{
    private ActorSystem actorSystem;
    Props stringJsonParseTestProps;
    Props stringToJsonParserProps;
    Props jsonToStringParserProps;

    public static final String CORRECT_STRING_TO_JSON_ID = "correct_string_to_json";
    public static final String CORRECT_JSON_TO_STRING_ID = "correct_json_to_string";
    public static final String MALFORMED_JSON_TO_STRING_ID = "malformed_json_to_string";
    public static final String INCORRECT_STRING_TO_JSON_ID = "incorrect_string_to_json";
    public static final String INCORRECT_JSON_TO_STRING_ID = "incorrect_json_to_string";
    public static final String VALID_JSON_PAYLOAD = "{\n" +
            "    \"name\":\"test\",\n" +
            "    \"list\":[\n" +
            "        {\"name\":\"element1\"},\n" +
            "        {\"name\":\"element2\"}\n" +
            "    ],\n" +
            "    \"object1\":{\n" +
            "        \"field1\":\"foo\",\n" +
            "        \"field2\":\"bar\"\n" +
            "    }\n" +
            "}";

    public static final String INVALID_JSON_PAYLOAD = "{\n" +
            "    \"name\":\"test\",\n" +
            "    \"list\":[\n" +
            "        {\"name\":\"element1\"},\n" +
            "        {\"name\":\"element2\"}\n" +
            "    ]\n" +
            "    \"object1\":{\n" +
            "        \"field1\":\"foo\",\n" +
            "        \"field2\":\"bar\"\n" +
            "    }\n" +
            "}";

    private static JsonNode jsonNodePayload(){
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode rootNode = objectMapper.createObjectNode();
        ArrayNode arrayNode = rootNode.arrayNode();
        ObjectNode nestedObj = rootNode.objectNode();

        ObjectNode arrayElement1 = arrayNode.addObject();
        ObjectNode arrayElement2 = arrayNode.addObject();
        arrayElement1.set("name", arrayElement1.textNode("element1"));
        arrayElement2.set("name", arrayElement2.textNode("element2"));
        arrayNode.add(arrayElement1);
        arrayNode.add(arrayElement2);

        nestedObj.set("name", nestedObj.textNode("test"));
        nestedObj.set("list", arrayNode);
        rootNode.set("object1", nestedObj);
        return rootNode;
    }

    public static class StringJsonParseTest extends BaseActor{
        public static final String PATH = "StringJsonParseTest";
        public void onReceive(Object message) throws Exception {
            if(message instanceof StringToJsonResult){
                StringToJsonResult result = (StringToJsonResult)message;
                if(result.id.equalsIgnoreCase(CORRECT_JSON_TO_STRING_ID)){
                    log.info("StringToJsonResult: {}, Outcome: {}", result.id, result.outcomeString());
                }
            }else if(message instanceof JsonToStringResponse){
                JsonToStringResponse response = (JsonToStringResponse)message;
            }
        }
    }


    @Before
    public void setupStringToJsonTest(){
        actorSystem = ActorSystem.create("test");
        stringJsonParseTestProps = StringJsonParseTest.props(StringJsonParseTest.class);
        stringToJsonParserProps = StringToJsonParserActor.props(StringToJsonParserActor.class);
        jsonToStringParserProps = JsonToStringParserActor.props(JsonToStringParserActor.class);
    }

    @Test
    public void stringToJsonTest(){
        ActorRef testActor = actorSystem.actorOf(stringJsonParseTestProps, StringJsonParseTest.PATH);
        ActorRef stringToJsonActor = actorSystem.actorOf(stringToJsonParserProps, StringToJsonParserActor.PATH);
        JsonNode referenceNode = jsonNodePayload();
        Assert.assertTrue(JsonHandlingActor.equals(referenceNode, referenceNode));
        Assert.assertFalse(JsonHandlingActor.equals(referenceNode, "{\"name\":\"foo\"}"));
        StringToJson stringToJsonRequest = new StringToJson(CORRECT_STRING_TO_JSON_ID, VALID_JSON_PAYLOAD);
        StringToJson invalidStringToJsonRequest = new StringToJson(MALFORMED_JSON_TO_STRING_ID, INVALID_JSON_PAYLOAD);
        stringToJsonActor.tell(stringToJsonRequest, testActor);
        stringToJsonActor.tell(invalidStringToJsonRequest, testActor);
    }
}
