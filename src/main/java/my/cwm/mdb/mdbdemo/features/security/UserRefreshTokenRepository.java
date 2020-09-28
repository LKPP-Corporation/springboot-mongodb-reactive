package my.cwm.mdb.mdbdemo.features.security;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface UserRefreshTokenRepository extends ReactiveCrudRepository<UserRefreshToken, Long> {

}
