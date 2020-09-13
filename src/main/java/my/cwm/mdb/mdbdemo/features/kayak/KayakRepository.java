package my.cwm.mdb.mdbdemo.features.kayak;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface KayakRepository extends ReactiveMongoRepository<Kayak, Long> {

}
