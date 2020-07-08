package ru.uip.contract.generator;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import ru.uip.contract.parser.ContractDescription;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class SpecGenerator {

    private final MustacheFactory mf;// = new DefaultMustacheFactory();
    private final Mustache m;

    public SpecGenerator(String templateName) {
        this.mf = new DefaultMustacheFactory();
        this.m = mf.compile(templateName);
    }


    public Map<String, String> generateSpecs(Map<String, Set<ContractDescription>> operationContracts) {

        Map<String, String> operationDocs = new HashMap<>();
        for(Map.Entry<String, Set<ContractDescription>> operationContract: operationContracts.entrySet()) {
            final Set<ContractDescription> descriptions = operationContract.getValue();
            final String operationId = operationContract.getKey();
            final String operationDescription = generateOperationDescription(descriptions);
            operationDocs.put(operationId, operationDescription);
        }
        return operationDocs;
    }

    public String generateOperationDescription(Set<ContractDescription> descriptions) {
        StringWriter writer = new StringWriter();
        Map<String, Object> context = new HashMap<>();
        context.put("descriptions", descriptions);
        m.execute(writer, context);
        return writer.toString();
    }

}
