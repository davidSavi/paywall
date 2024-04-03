package org.paywall.subscription.infrastructure.dto;

import java.util.Set;
import java.util.UUID;

public record SubscriptionDto(UUID id, Set<UUID> userIds){}
