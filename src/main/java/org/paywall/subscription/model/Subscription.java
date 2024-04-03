package org.paywall.subscription.model;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import org.paywall.subscription.infrastructure.dto.SubscriptionDto;
import org.paywall.user.model.UserId;

public record Subscription(SubscriptionId id, Set<UserId> userIds) {

  public Subscription(SubscriptionDto subscriptionDto) {
    this(new SubscriptionId(subscriptionDto.id()), subscriptionDto.userIds().stream().map(
        UserId::new).collect(
        Collectors.toSet()));
  }

  public SubscriptionDto toDto() {
    return new SubscriptionDto(id.value(), userIds.stream().map(UserId::value).collect(Collectors.toSet()));
  }
  public AddUserResult addUser(UserId userId) {
    var users = new HashSet<>(userIds);
    var isNew = users.add(userId);
    return new AddUserResult(new Subscription(this.id,  userIds), isNew);
  }
}
