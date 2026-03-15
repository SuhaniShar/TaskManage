package com.TaskManage.Service;


public interface StorageService {
    // methods to define
    String storeFile(String fileName, byte[] data);
    byte[] loadFile(String fileName);
    void deleteFile(String fileName);
}
