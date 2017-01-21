package dev.jwalker.actors;

import com.fasterxml.jackson.databind.ObjectMapper;

import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public abstract class BaseActor extends UntypedActor{
    protected LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    public static Props props(Class clazz){
        return Props.create(clazz);
    }
}
