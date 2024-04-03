package org.paywall.subscription.command;

import lombok.Getter;

@Getter
public final class PublishEventCommand extends Command{

  Event payload;
  public record Event(String message){}
  public PublishEventCommand(Event payload) {
    this.payload = payload;
  }
}
