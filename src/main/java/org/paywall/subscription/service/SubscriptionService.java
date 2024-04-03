package org.paywall.subscription.service;

import java.util.ArrayList;
import java.util.List;
import org.paywall.subscription.command.Command;
import org.paywall.subscription.command.PublishEventCommand;
import org.paywall.subscription.command.PublishEventCommand.Event;
import org.paywall.subscription.command.UpdateSubscriptionCommand;
import org.paywall.subscription.infrastructure.dto.SubscriptionDto;
import org.paywall.subscription.model.Subscription;
import org.paywall.user.infrastructure.dto.Entity.UserDto;
import org.paywall.user.model.User;

public class SubscriptionService {

  public List<Command> subscribeUser(UserDto userDto, SubscriptionDto subscriptionDto) {
    var user = new User(userDto);
    var subscription = new Subscription(subscriptionDto);
    var subscribedUser = user.subscribe(subscription.id());
    var updatedSubscription = subscription.addUser(user.id());
    var commands = new ArrayList<Command>();
    if(updatedSubscription.hasChanged() && subscribedUser.hasChanged()) {
      commands.add(new UpdateSubscriptionCommand(subscription.toDto(), user.toDto()));
    }
    else {
      commands.add(new PublishEventCommand(new Event("Duplicate subscription attempt")));
    }
    return commands;
  }

}
