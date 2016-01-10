package com.qunar.homework;

import com.qunar.homework.count.CountLines;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by zhangzhi on 16-1-4.
 * 程序入口
 */
public class MainProc {

    /**
     * 程序的入口
     *
     * @param files 待统计的文件或目录(若输入为目录路径则递归统计目录下所有java源文件)
     */
    public static void main(String[] files) {
        ApplicationContext applicationContext =
                new ClassPathXmlApplicationContext("./applicationContext.xml");
        CountLines countLines = (CountLines) applicationContext.getBean("countlines");
        //执行任务的线程池
        ExecutorService service = Executors.newCachedThreadPool();

        try {
            for (String path : files) {
                service.submit(() -> {
                    return countLines.countValidLines(getContent(path));
                }).get();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 根据给定的路径名获取文件内容
     *
     * @param path 路径名
     * @return 路径名所在文件的内容(若发生异常返回null)
     */
    public static String getContent(String path) {
        try (FileInputStream fis = new FileInputStream(path)) {
            StringBuilder sb = new StringBuilder();
            FileChannel fc = fis.getChannel();
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            while (true) {
                buffer.clear();
                if (fc.read(buffer) == -1)
                    break;
                buffer.flip();
                sb.append(new String(buffer.array(), "UTF-8"));
            }
            return sb.toString();
        } catch (IOException e) {
            return null;
        }
    }

}
