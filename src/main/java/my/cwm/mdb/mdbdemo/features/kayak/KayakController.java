package my.cwm.mdb.mdbdemo.features.kayak;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
@RequestMapping(path = "/kayaks")
public class KayakController {

    private KayakRepository kayakRepository;

    public KayakController(KayakRepository kayakRepository) {
        this.kayakRepository = kayakRepository;
    }

    @Operation(security = { @SecurityRequirement(name = "bearer-key") })
    @PostMapping()
    public @ResponseBody Mono<Kayak> addKayak(@RequestBody Kayak kayak) {
        return kayakRepository.save(kayak);
    }

    @Operation(security = { @SecurityRequirement(name = "bearer-key") })
    @GetMapping()
    public @ResponseBody Flux<Kayak> getAllKayaks() {
        return kayakRepository.findAll();
    }
}
