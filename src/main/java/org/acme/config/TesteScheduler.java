package org.acme.config;

import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class TesteScheduler {

    private final Logger LOG = LoggerFactory.getLogger(TesteScheduler.class);

    @Scheduled(every = "05s")
    void primeiraTarefa() {
        //LOG.info("Executando tarefa com Scheduler");
    }
}
