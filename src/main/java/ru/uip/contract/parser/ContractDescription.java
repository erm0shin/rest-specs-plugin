package ru.uip.contract.parser;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class ContractDescription {

    private final static String NAME_PREFIX = "validate";
    private final static String NAME_DELIMITER = "_";


    private final String name;
    private final String description;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public ContractDescription(
            @JsonProperty("name") String name,
            @JsonProperty("description") String description) {
        this.name = name;
        this.description = description;
    }

    public String getSpecDirName() {
        return String.format("%s%s%s", NAME_PREFIX, NAME_DELIMITER, generateName());
    }

    private String generateName() {
       return name.toLowerCase().replaceAll("\\s+", NAME_DELIMITER);
    }
}
