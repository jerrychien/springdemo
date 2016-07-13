package com.jerry.socket.nio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * TestFileChannel
 *
 * @author jerrychien
 * @create 2016-07-04 17:08
 */
public class TestFileChannel {

    private static final Logger logger = LoggerFactory.getLogger(TestFileChannel.class);

    public static void main(String[] args) {
        try {

//            RandomAccessFile accessFile = new RandomAccessFile("/Users/jerrychien/Documents/23213.txt", "rw");
            RandomAccessFile accessFile = new RandomAccessFile("/Users/jerrychien/Documents/27582639.txt", "rw");
//            RandomAccessFile accessFileToW = new RandomAccessFile("/Users/jerrychien/Documents/27582639_2.txt", "rw");
            FileChannel fileChannel = accessFile.getChannel();
//            FileChannel fileChannel1 = accessFileToW.getChannel();
            ByteBuffer buf = ByteBuffer.allocate(1024);
            int bytesRead = fileChannel.read(buf);
            logger.info("bytesRead:" + bytesRead);
            while (bytesRead != -1) {
                buf.flip();
//                fileChannel1.write(buf);
                while (buf.hasRemaining()) {
                    System.out.print((char) buf.get());
                }
                buf.clear();
                bytesRead = fileChannel.read(buf);
            }
            accessFile.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
