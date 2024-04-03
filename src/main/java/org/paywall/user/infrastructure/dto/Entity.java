package org.paywall.user.infrastructure.dto;

import java.util.Set;
import java.util.UUID;
import org.paywall.subscription.infrastructure.dto.SubscriptionDto;

public class Entity {
  public record UserDto(UUID id, String name, Set<UUID> subscriptionIds){}
}
