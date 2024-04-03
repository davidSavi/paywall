package org.paywall.application.dto;

import java.util.Set;
import java.util.UUID;

public class ResponseDto {
  public record UserResponse(UUID id, String name, Set<UUID> subscriptionIds){}
  public record SubscriptionResponse(UUID subscriptionId, UUID userId, boolean isSuccessful){}

}
