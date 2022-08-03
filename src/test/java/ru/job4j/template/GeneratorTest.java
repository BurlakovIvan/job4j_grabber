package ru.job4j.template;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GeneratorTest {

    @Disabled
    @Test()
    public void whenCorrectString() {
        String template = "template ${template}";
        Template temp = new Template();
        Map<String, String> args = new HashMap<>();
        args.put("template", "test");
        assertThat(temp.produce(template, args)).isEqualTo("template test");
    }

    @Disabled
    @Test()
    public void whenNoKeysInMap() {
        String template = "template ${template} and test ${key}";
        Template temp = new Template();
        Map<String, String> args = new HashMap<>();
        args.put("template", "test");
        assertThrows(IllegalArgumentException.class, () -> temp.produce(template, args));
    }

    @Disabled
    @Test()
    public void whenNotCorrectArgs() {
        String template = "template ${template}";
        Template temp = new Template();
        Map<String, String> args = new HashMap<>();
        args.put("template", "test");
        args.put("test", "test");
        assertThrows(IllegalArgumentException.class, () -> temp.produce(template, args));
    }

    @Disabled
    @Test()
    public void whenIsEmptyMap() {
        String template = "template ${template}";
        Template temp = new Template();
        Map<String, String> args = new HashMap<>();
        assertThrows(IllegalArgumentException.class, () -> temp.produce(template, args));
    }

    @Disabled
    @Test()
    public void whenNotKeysInTemplate() {
        String template = "template";
        Template temp = new Template();
        Map<String, String> args = new HashMap<>();
        assertThrows(IllegalArgumentException.class, () -> temp.produce(template, args));
    }
}