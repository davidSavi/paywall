package org.paywall.application.dto;

import java.util.Set;
import java.util.UUID;

public class RequestDto {
  public record CreateUserRequest(UUID id, String name){}
  public record DeleteUserRequest(UUID id){}

  public record UpdateUserNameRequest(UUID id, String name){}

  public record GetUserRequest(UUID userId){}
  public record AddSubscriptionToUserRequest(UUID subscriptionId, UUID userId){}

}
