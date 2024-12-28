package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {

    private static final ObjectMapper obj = new ObjectMapper();

    public static void ensureFolderExists(String folderpath)
    {
        File dbFolder = new File(folderpath);

        if(!dbFolder.exists()){
            dbFolder.mkdir();
        }
    }

    public static void ensureFileExistsWithDefaultvalue(File file, int initValue)
    {
        if(!file.exists()){
            try {
                file.createNewFile();
                writeValueToFile(file, initValue);
            } catch (IOException e){
                System.out.println("파일 생성에 실패했습니다.");
            }
        }
    }

    public static void writeValueToFile(File file,Object object)
    {
        try{
            obj.writeValue(file,object);
        } catch (IOException e) {
            System.out.println("파일에 쓸 수 없습니다.");
        }
    }

    public static <T> T readValueFromFile(File file,Class<T> valueType, T defaultValue)
    {
        try {
            return obj.readValue(file,valueType);
        } catch (IOException e){
            return defaultValue;
        }
    }

    public static List<WiseSaying> readWiseSayingFromFolder(String folderPath)
    {
        List<WiseSaying> wiseSayings = new ArrayList<>();
        File folder = new File(folderPath);
        File[] files = folder.listFiles(((dir, name) -> name.endsWith(".json")));

        if(files != null){
            for (File file : files) {
                WiseSaying wiseSaying = readValueFromFile(file, WiseSaying.class,null);
                if(wiseSaying != null){
                    wiseSayings.add(wiseSaying);
                }
            }
        }

        return wiseSayings;
    }
}
