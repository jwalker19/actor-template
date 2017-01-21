package dev.jwalker.actors;

import org.junit.Before;
import org.junit.Test;

import akka.actor.ActorSystem;
import akka.actor.Props;

/**
 * Created by jwalker1 on 1/20/17.
 */
public class JsonParseTests {
    private ActorSystem actorSystem;
    Props stringToJsonParserProps;
    Props jsonToStringParserProps;

    @Before
    public void setupStringToJsonTest(){
        actorSystem = ActorSystem.create("test");
        stringToJsonParserProps = StringToJsonParserActor.props(StringToJsonParserActor.class);
        jsonToStringParserProps = JsonToStringParserActor.props(JsonToStringParserActor.class);

    }

    @Test
    public void stringToJsonTest(){

    }

    @Before
    public void setupJsonToStringTest(){
        jsonToStringParserProps = JsonToStringParserActor.props(JsonToStringParserActor.class);
    }

    @Test
    public void jsonToStringTest(){

    }
}
