package foo.bar.RxJavaTest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class HelloService {
    public String getHello(boolean block) throws InterruptedException {
        if (block)
            TimeUnit.SECONDS.sleep(30);
        return "hello world!";
    }
}
