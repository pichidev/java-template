package ar.com.pichidev.template;

import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.modulith.docs.Documenter;

public class ModuleTests {
    ApplicationModules modules = ApplicationModules.of(TemplateApplication.class);

    @Test
    void writeDocumentationSnippets() {
        new Documenter(modules)
                .writeModulesAsPlantUml()
                .writeIndividualModulesAsPlantUml();
    }

    @Test
    public void applicationModules() {
        modules.forEach(System.out::println);
        modules.verify();
    }
}
