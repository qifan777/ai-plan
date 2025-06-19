package io.github.qifan777.server.user.root.event;

import lombok.Data;

@Data
public class UserEvent {

    public record UserCreateEvent(String userId) {

    }
}