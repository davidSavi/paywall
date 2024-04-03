package org.paywall.user.model;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import org.paywall.subscription.model.SubscriptionId;
import org.paywall.user.infrastructure.dto.Entity.UserDto;
import org.paywall.user.model.exception.InvalidUserNameException;



public record User(UserId id, UserName userName, Set<SubscriptionId> subscriptionIds) {
  public record UserName(String value){
    public UserName {
      //regex validation
      if(value == null) {
        throw new InvalidUserNameException("");
      }
    }
  }

  public User(UserDto userDto) {
    this(new UserId(userDto.id()),new UserName(userDto.name()), userDto.subscriptionIds().stream().map(
        SubscriptionId::new).collect(
        Collectors.toSet()));
  }

  public UserDto toDto() {
    return new UserDto(id.value(),userName.value(), subscriptionIds.stream()
        .map(SubscriptionId::value).collect(Collectors.toSet()));
  }
  public SubscriptionResult subscribe(SubscriptionId subscriptionId) {
      var subscriptions = new HashSet<>(subscriptionIds);
      var isNew = subscriptions.add(subscriptionId);
      return new SubscriptionResult(new User(this.id, this.userName, subscriptions), isNew);
  }
}
