package org.kenanselimovic.glas.dictionary.infrastructure;

import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.core.Vertx;
import io.vertx.mutiny.ext.web.client.HttpResponse;
import io.vertx.mutiny.ext.web.client.WebClient;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;
import org.jsoup.Jsoup;
import org.kenanselimovic.glas.dictionary.domain.Dictionary;
import org.kenanselimovic.glas.dictionary.domain.Translation;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.net.URLEncoder;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;

@ApplicationScoped
public class DictCC implements Dictionary {
    private final Logger logger = Logger.getLogger(DictCC.class);
    private final WebClient webClient;

    @ConfigProperty(name = "glas.dictionaries.dictcc.url")
    String url;

    @Inject
    public DictCC(Vertx vertx) {
        webClient = WebClient.create(vertx);
    }

    @Override
    public Uni<List<Translation>> getTranslation(String phrase) {
        final String formattedUrl = "%s/?s=%s".formatted(url, URLEncoder.encode(phrase, UTF_8));
        logger.debug(formattedUrl);

        return webClient.getAbs(formattedUrl).send()
                .onItem().transform(HttpResponse::bodyAsString)
                .onItem().transform(Jsoup::parse)
                .onItem().invoke(logger::debug)
                .onItem().transform(doc -> new DocumentParser(doc).parse().stream().map(dp -> new Translation(dp.translation(), dp.source(), phrase)).toList())
                .onFailure().retry().atMost(1);
    }
}
