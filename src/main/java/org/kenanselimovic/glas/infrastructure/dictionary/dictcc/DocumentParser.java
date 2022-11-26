package org.kenanselimovic.glas.infrastructure.dictionary.dictcc;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.kenanselimovic.glas.domain.dictionary.Translation;

import java.util.ArrayList;
import java.util.List;

public class DocumentParser {
    private final Document document;

    public DocumentParser(Document document) {
        this.document = document;
    }

    public List<Translation> parse() {
        final Elements elements = document.select("td.td7nl");
        final List<String> translations = new ArrayList<>();
        final List<String> sources = new ArrayList<>();
        for (int i = 0; i < elements.size(); i++) {
            final String parsedElement = elements.get(i).select("a, var").text();
            if (i % 2 == 0) {
                translations.add(parsedElement);
            } else {
                sources.add(parsedElement);
            }
        }

        final List<Translation> results = new ArrayList<>();
        for (int i = 0; i < translations.size(); i++) {
            results.add(new Translation(translations.get(i), sources.get(i)));
        }

        return results;
    }
}
