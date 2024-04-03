package org.paywall.application.service;

import java.util.HashSet;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.paywall.application.dto.RequestDto.AddSubscriptionToUserRequest;
import org.paywall.application.dto.RequestDto.CreateUserRequest;
import org.paywall.application.dto.ResponseDto.SubscriptionResponse;
import org.paywall.application.dto.ResponseDto.UserResponse;
import org.paywall.subscription.command.PublishEventCommand;
import org.paywall.subscription.command.UpdateSubscriptionCommand;
import org.paywall.subscription.infrastructure.SubscriptionClient;
import org.paywall.subscription.infrastructure.dto.SubscriptionDto;
import org.paywall.subscription.model.SubscriptionId;
import org.paywall.subscription.service.SubscriptionService;
import org.paywall.user.infrastructure.UserRepository;
import org.paywall.user.infrastructure.dto.Entity.UserDto;
import org.paywall.user.model.User;
import org.paywall.user.model.User.UserName;
import org.paywall.user.model.UserId;

@AllArgsConstructor
public class SubscriptionCommandHandlerService {

  final UserRepository userRepository;
  final SubscriptionClient subscriptionClient;
  final SubscriptionService subscriptionService;
  public UserResponse createUser(CreateUserRequest request) {
    var user = new User(new UserId(request.id()),new UserName(request.name()),new HashSet<>());
    userRepository.create(user.id().value(), user.userName().value());
    return new UserResponse(user.id().value(), user.userName().value(),
        user.subscriptionIds().stream().map(SubscriptionId::value).collect(Collectors.toSet()));
  }

  public UserDto findUser(UUID id) {
    return userRepository.find(id);
  }
  public SubscriptionResponse subscribeUser(AddSubscriptionToUserRequest subscriptionToUserRequest) {
    var user = userRepository.find(subscriptionToUserRequest.userId());
    var subscription = subscriptionClient.get(subscriptionToUserRequest.subscriptionId());
    var commands = subscriptionService.subscribeUser(user, subscription);
    commands.forEach(c -> {
      switch (c) {
        case PublishEventCommand e -> System.out.println(e.getPayload());
        case UpdateSubscriptionCommand e -> updateUserSubscription(e.getSubscriptionDto(), e.getUserDto());
        default -> throw new IllegalStateException("Unexpected value: " + c);
      }
    });
    return new SubscriptionResponse(subscription.id(), user.id(), true);
  }

  private void updateUserSubscription(SubscriptionDto subscriptionDto, UserDto userDto) {
    try {
      subscriptionClient.update(subscriptionDto);
      userRepository.update(userDto);
    } catch (Exception e) {
      //error handling or propagation
    }
  }
}
