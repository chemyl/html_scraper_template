package org.example.dataModel;

import io.restassured.response.Response;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.HashMap;

@Getter
@Setter
@Log4j
@Builder
@NoArgsConstructor
@Configuration
public class DocumentResponse {

    private String price;
    private String image;
    private String title;

    public DocumentResponse(Document document) {
        Elements productElements = document.select("div.s-item__main");
        for (Element productElement: productElements) {
            this.price = productElement.select("span.s-item__price").text();
        }
//        this.price = document.select("div.s-item__info").select("span.s-item__price");
        this.type = response.body().jsonPath().getString("type");
        this.participants = response.body().jsonPath().getInt("participants");
        this.link = response.body().jsonPath().getString("link");
        this.key = response.body().jsonPath().getString("key");
        this.accessibility = response.body().jsonPath().getDouble("accessibility");

    public Document parseHTMLtoJsoupDocument(String html) {
//        Document document = ;
//        // Output the parsed document in a readable format
//        String prettyHtml = document.html();
//        System.out.println(prettyHtml);

        return Jsoup.parse(html);
    }
    @Autowired
    Environment environment;

    public HashMap fetchDataFromDocument(Document document, String Key) {

        Elements titleElements = document.select("div.s-item__info").select("div.s-item__title");
        HashMap dataMap = storeDataToHashMap(titleElements, "Title");

        Elements elements = document.select("div.s-item__info").select("span.s-item__price");
        HashMap dataMap1 = storeDataToHashMap(elements, "Price");


//        List<Elements> elementsList1 = Collections.singletonList(document.select("div.s-item__title"));
//
//        Elements elementsss = document.getAllElements();
//        elementsss.get(0);
//        HashMap<String, String> fetchedData = new HashMap<>();

//        document.select("div.s-item__info").select("div.s-item__title").text();       76 text items
//        document.select("div.s-item__info").select("span.s-item__price").text();      76 price items String
//        document.select("div.s-item__info").select("span.s-item__itemLocation");      locations
//        document.select("div.s-item__info").select("span.s-item__logisticsCost");     75 shipping costs
//        document.select("div.s-item__image").select("img").get(1).attr("src");         link images

//        fetchedData.put("Price", "2");
//        fetchedData.put("title", document.getAllElements().select("div.s-item__title").get(1).text());
////        return fetchedData;

        return
    }

    public HashMap<String, String> storeDataToHashMap(Elements elements, String keyName) {
        HashMap<String, String> elementValues = new HashMap<>();
        for (Element element : elements) {
            String key = keyName;                            // Replace "your-key" with a unique identifier for each element
            String value = element.text();                   // Extract the value from the element
            elementValues.put(key, value);                   // Store the value in the HashMap
        }
        return elementValues;
    }
}

//        String xml = "<root><element>Hello, World!</element></root>";
//
//        // Parse the XML string into a W3C Document
//        Document document = parseXml(xml);
//
//        // Convert the Document into a readable format
//        String readableXml = convertToReadableFormat(document);
//
//        System.out.println(readableXml);
//    }
//
//    private static Document parseXml(String xml) {
//        // Code to parse the XML string into a W3C Document
//        // This can be done using libraries like Jsoup, DOM Parser, etc.
//        // For simplicity, let's assume the XML is already parsed and available as a Document object
//        // Replace this code with your actual parsing implementation
//        return null;
//    }