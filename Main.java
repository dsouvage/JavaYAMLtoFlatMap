package test;

public class Main {

    public static void main(String[] args){
        final String url = "https://raw.githubusercontent.com/Praqma/helmsman/master/examples/example.yaml";
        String filename = "./test";
        // we use temp file to download and store the downloaded non local .yaml file
        String tempname = "./test_raw";

        YAMLReader yamlR = new YAMLReader(url, tempname);
        yamlR.printMap();
        yamlR.createMapFile(filename);
    }
}
