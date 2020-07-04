package ru.uip.contract.parser;

import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@Tag("unit")
public class ContractsParserTest {

    private Map<String, Set<File>> contracts = new HashMap<>();

    @BeforeEach
    public void setUp() {
        File getAccountContract = new File("./src/test/contracts/getAccounts.yml");
        Set<File> contractFiles = new HashSet<>();
        contractFiles.add(getAccountContract);
        contracts.put("GetAccounts", contractFiles);
    }

    @Test
    public void testConstruct() {
        final ContractsParser parser = new ContractsParser(contracts);
        assertThat(parser, notNullValue());
    }

    @SneakyThrows
    @Test
    public void testParseFile() {
        final ContractContentParser contractContentParser = mock(ContractContentParser.class);
        final ContractsParser parser = new ContractsParser(contracts, contractContentParser);

        final ContractDescription item = new ContractDescription("accounts", "Get all accounts");
        Set<ContractDescription> expected = new HashSet<>(Arrays.asList(item));
        when(contractContentParser.parse(anyString())).thenReturn(expected);

        File getAccountContract = new File("./src/test/contracts/getAccounts.yml");
        final Set<ContractDescription> contractDescriptions = parser.parseContractFile(getAccountContract);
        assertThat(contractDescriptions, containsInAnyOrder(item));

        verify(contractContentParser, times(1)).parse(anyString());
    }

    @SneakyThrows
    @Test
    public void testParseNonExistedFile() {
        final ContractContentParser contractContentParser = mock(ContractContentParser.class);
        final ContractsParser parser = new ContractsParser(contracts, contractContentParser);

        File getAccountContract = new File("./src/test/contracts/nonExists.yml");
        assertThrows(
                IllegalArgumentException.class,
                () -> parser.parseContractFile(getAccountContract));

        verify(contractContentParser, times(0)).parse(anyString());
    }

}