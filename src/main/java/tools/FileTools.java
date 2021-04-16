package tools;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FileTools {

    //проверяет существует ли путь до файла и если не существует создает весь путь
    public static boolean checkDirectory(File file) {
        String directory = file.getPath().replace(file.getName(), "");
        File dirFile = new File(directory);
        try {
            if (!dirFile.exists()) {
                return dirFile.mkdirs();
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    //проверяет существует ли путь до файла и если не существует создает весь путь
    public static boolean checkDirectory(String fileName) {
        File file = new File(fileName);
        return checkDirectory(file);
    }

    //проверяет существует ли путь до файла и если не существует создает весь путь
    public static boolean checkDirrectory(Path filePath) {
        return checkDirectory(filePath.toString());
    }

    //Получение списка только файлов из указанной дирректории с поддиректориями
    public static List<Path> getFilesListPaths(String path) {
        try {
            return Files.walk(Paths.get(path))
                    .filter(filename -> ((Files.isRegularFile(filename))))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //Получение списка только файлов из указанной дирректории с поддиректориями
    public static List<Path> getFilesListPaths(Path path) {
        return getFilesListPaths(path.toString());
    }

    //Получение списка только файлов из указанной дирректории с поддиректориями
    public static List<Path> getfilesListPaths(File path) {
        return getFilesListPaths(path.getPath());
    }

    //копирование файла
    public static boolean cpFile(Path source, Path dest){
        if(!Files.exists(source)){
            return false;
        }
        try{
            Files.copy(source,dest);
            Runtime.getRuntime().exec("chmod 777 "+dest.toString());
            return Files.exists(dest);
        } catch (IOException e){
            e.printStackTrace();
            return false;
        }
    }
    //копирование файла
    public static boolean cpFile(String source,String dest){
        return cpFile(Paths.get(source),Paths.get(dest));
    }

    //удаление файла или дирректории
    public static boolean rmFile(Path path){
        if(Files.exists(path)){
            try{
                Files.delete(path);
                return true;
            } catch (Exception e){
                return false;
            }
        } else return false;
    }

    //удаление файла или дирректории
    public static boolean rmFile(String path){
        return rmFile(Paths.get(path));
    }

    //получение списка файлов только в указанной дирректории без вложенных дирректорий
    public static List<Path> getFilesListPathsWhithoutDirs(File file){
        if(file.exists()) {
            if (file.isDirectory()) {
                return Arrays.stream(file.listFiles()).filter(filename -> ((Files.isRegularFile(Paths.get(filename.getPath())))))
                        .map(item -> Paths.get(item.getPath()))
                        .collect(Collectors.toList());
            } else return null;
        } else return null;
    }
    //получение списка файлов только в указанной дирректории без вложенных дирректорий
    public static List<Path> getFilesListPathsWhithoutDirs(Path file){
        return getFilesListPathsWhithoutDirs(new File(file.toString()));
    }

    //получение списка файлов только в указанной дирректории без вложенных дирректорий
    public static List<Path> getFilesListPathsWhithoutDirs(String file){
        return getFilesListPathsWhithoutDirs(new File(file));
    }

}
