package my.cwm.mdb.mdbdemo.features.kayak;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Kayak {
    private String name;
    private String owner;
    private Number value;
    private String makeModel;
}
