package org.example.dataModel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@AllArgsConstructor
@NoArgsConstructor
@Log4j
@Builder
public class FetchedDataModel {
    private String price;
    private String title;
    private String shipCost;
    private String location;
    private String image;
}
