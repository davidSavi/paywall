package org.paywall.user.infrastructure;

import java.util.UUID;
import org.paywall.user.infrastructure.dto.Entity.UserDto;

public interface UserRepository {
  void create (UUID id, String name);
  UserDto find(UUID id);
  void delete (UUID id);
  void update (UserDto request);
}
