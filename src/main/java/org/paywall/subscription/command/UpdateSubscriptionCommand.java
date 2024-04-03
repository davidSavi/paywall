package org.paywall.subscription.command;

import lombok.Getter;
import org.paywall.subscription.infrastructure.dto.SubscriptionDto;
import org.paywall.user.infrastructure.dto.Entity.UserDto;

@Getter
public final class UpdateSubscriptionCommand extends Command {

  private final SubscriptionDto subscriptionDto;
  private final UserDto userDto;
  public UpdateSubscriptionCommand(SubscriptionDto subscriptionDto, UserDto userDto) {
    this.subscriptionDto = subscriptionDto;
    this.userDto = userDto;
  }
}
