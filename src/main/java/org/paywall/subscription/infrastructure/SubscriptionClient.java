package org.paywall.subscription.infrastructure;

import java.util.UUID;
import org.paywall.subscription.infrastructure.dto.SubscriptionDto;

public interface SubscriptionClient {
  SubscriptionDto get(UUID id);
  void update(SubscriptionDto subscriptionDto);
}
