package test;

import org.springframework.vault.support.JsonMapFlattener;

import java.io.*;
import java.net.URL;
import java.util.Map;

public class YAMLReader {
    private final Map<String, String> flattenedMap;
    private final String tempname;
    private final StringBuilder content = new StringBuilder();
    private final SnakeYAMLHelper syh = new SnakeYAMLHelper();

    public YAMLReader(String url, String tempname) {
        this.tempname = tempname;
        flattenedMap = getFlattenedStringMapURL(url);
    }

    private Map<String, String> getFlattenedStringMapURL(String url){
        return JsonMapFlattener.flattenToStringMap(syh.YamlLoad(loadYamlFromURLtoString(url)));
    }

    public YAMLReader(File file, String tempname) {
        this.tempname = tempname;
        flattenedMap = getFlattenedStringMapFile(file);
    }

    private Map<String, String> getFlattenedStringMapFile(File file){
        return JsonMapFlattener.flattenToStringMap(syh.YamlLoad(loadLocalYaml(file)));
    }

    public Map<String, String> getMap(){
        return flattenedMap;
    }

    public void printMap(){
        for(var item : flattenedMap.entrySet()){
            System.out.println(item);
        }
    }

    public void createMapFile(String outputFilePath){
        StringBuilder stringToWrite = new StringBuilder();
        for(var item : flattenedMap.entrySet()){
            stringToWrite.append(item).append("\n");
        }
        File file = new File(outputFilePath);

        try(FileOutputStream fos = new FileOutputStream(file);
            BufferedOutputStream bos = new BufferedOutputStream(fos)) {
            byte[] bytes = stringToWrite.toString().getBytes();
            bos.write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String loadLocalYaml(File file){
        try (FileInputStream input = new FileInputStream(file)) {
            BufferedReader br = new BufferedReader(new InputStreamReader(input));
            String line;
            while ((line = br.readLine()) != null) {
                content.append(line).append(System.lineSeparator());
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return content.toString();
    }

    private String loadYamlFromURLtoString(String url){
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(tempname)) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
            }
            return loadLocalYaml(new File(tempname));
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
}

