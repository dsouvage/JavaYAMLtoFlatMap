package test;

import org.yaml.snakeyaml.Yaml;

import java.util.Map;

public class SnakeYAMLHelper {
    public Map<String, Object> YamlLoad(String str){
        Yaml yaml = new Yaml();
        return yaml.load(str);
    }
}
