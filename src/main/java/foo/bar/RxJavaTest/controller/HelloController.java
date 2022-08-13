package foo.bar.RxJavaTest.controller;

import foo.bar.RxJavaTest.HelloService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.ArrayList;

@RestController("/")
@Slf4j
public class HelloController {
    @Autowired
    private HelloService helloService;

    @GetMapping("/hello-world")
    public Mono<String> helloWorld() {
        log.info("Threads created {}", new ArrayList<>(Thread.getAllStackTraces().keySet()));
        return Mono.just("hello world!");
    }

    @GetMapping(value = "/int-streamer", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Integer> intStreamer() {
        log.info("Threads created {}", new ArrayList<>(Thread.getAllStackTraces().keySet()));
        return Flux.range(1, 100).delayElements(Duration.ofSeconds(1));
    }

    @GetMapping("/hello-world2/{block}")
    public Mono<String> helloWorld2(@PathVariable("block") boolean block) {
        log.info("Threads created {}", new ArrayList<>(Thread.getAllStackTraces().keySet()));
        return Mono.fromCallable(() -> helloService.getHello(block)).subscribeOn(Schedulers.boundedElastic());
        //return Mono.just(helloService.getHello());
    }
}
