package ntnu.group03.idata2900.ams.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DatasheetDto {
    private String name;
    private String referenceNumber;
    private String pdfUrl;

    public DatasheetDto(String name, String referenceNumber, String pdfUrl) {
        this.name = name;
        this.referenceNumber = referenceNumber;
        this.pdfUrl = pdfUrl;
    }

    public DatasheetDto(){

    }
}
