package org.example.dataModel;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Log4j
@Configuration
public class DocumentResponsePOJO {
    private String location;
    private String price;
    private String image;
    private String title;
    private String shipCost;

    public List<FetchedDataModel> documentResponsePOJO(Document document) {
        List<FetchedDataModel> docs = new ArrayList<>();
        Elements productElements = document.getElementById("srp-river-results").getElementsByAttribute("data-viewport");
        for (Element productElement : productElements) {
            this.price = productElement.select("div.s-item__details").select("span.s-item__price").text();
            this.title = productElement.select("div.s-item__title").text();
            this.shipCost = productElement.select("span.s-item__logisticsCost").text();
            this.location = productElement.select("span.s-item__itemLocation").text();
            this.image = productElement.select("img").attr("src").toString();
            FetchedDataModel product = new FetchedDataModel(price, title, shipCost, location, image);
            docs.add(product);
        }
        return docs;
    }
}