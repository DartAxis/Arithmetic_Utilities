package tools;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FileLog {
    public static final String path = "/log/";

    public static void INFO(Class currentClass,String message){
        createMessage(currentClass," INFO", message);
    }

    public static void WARN(Class currentClass,String message){
        createMessage(currentClass," WARN", message);
    }

    public static void ERROR(Class currentClass,String message){
        createMessage(currentClass,"ERROR", message);
    }

    private static String getFilePath(){
        return System.getProperty("user.dir")+path;
    }

    private static String getFileName(String filePath){
        LocalDateTime date = LocalDateTime.now();
        DateTimeFormatter formatters = DateTimeFormatter.ofPattern("d-MM-yyyy");
        return filePath+date.toLocalDate().format(formatters)+".log";
    }

    private static void saveMessage(String filename,String message){
        try {
            FileWriter writer = new FileWriter(filename, true);
            BufferedWriter bufferWriter = new BufferedWriter(writer);
            bufferWriter.write(message);
            bufferWriter.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void createMessage(Class currentClass, String type, String message){
        //Получение дирректории с логами
        String filePath = getFilePath();
        //Создание имени файла по текущей дате
        String filename = getFileName(filePath);
        //Проверка существует ли дирректория и если не существует создать
        FileTools.checkDirectory(filename);
        //Собираем сообщение
        String line;
        LocalDateTime date = LocalDateTime.now();
        line = date.format(DateTimeFormatter.ofPattern("d/MM/uuuu HH:mm:ss"))+": " + type + "--- "+currentClass.getSimpleName()+"   "+message + "\n";
        saveMessage(filename,line);
    }
}
