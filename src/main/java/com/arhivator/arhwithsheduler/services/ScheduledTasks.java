package com.arhivator.arhwithsheduler.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.arhivator.arhwithsheduler.services.FileService.*;
import static com.arhivator.arhwithsheduler.services.ZipService.makeZipForEveryFolder;

@Service
public class ScheduledTasks {

    @Value("${file.name.list-filenames}")
    private String creatingListOfFiles;
    @Value("${file.name.list-folders}")
    private String creatingListOfFolders;
    @Value("${file.name.list-errors}")
    private String creatingListOfErrors;
    @Value("${directory.name.checking-directory}")
    private String checkingDirectory;
    @Value("${directory.name.out}")
    private String directoryOUT;
    @Value("${directory.name.log}")
    private String directoryLOG;
    @Value("${directory.name.tmp}")
    private String directoryTMP;
    @Value("${directory.name.err}")
    private String directoryERR;

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd kk-mm-ss");
//    каждые сутки

    @Scheduled(fixedRate = 86400000)
    public void doTasks() {
        System.out.println("The time is : " + dateFormat.format(new Date()));

        createListOfFiles(directoryTMP + creatingListOfFiles, checkingDirectory, directoryTMP + creatingListOfErrors);

        List<String> fileNames = fileReaderFullName(directoryTMP + creatingListOfFiles);
        List<String> errors = fileReaderFullName(directoryTMP + creatingListOfErrors);
        List<String> shortFileNames = fillShortFileNamesByListFullNames(fileNames);
        Set<String> folderNames = new HashSet<>();

        fillSetOfFolderNames(shortFileNames, folderNames);
        createFoldersBySet(folderNames, directoryTMP);
        createListOfFolders(directoryTMP + creatingListOfFolders, directoryTMP);
        copyFileToDirectory(fileNames, folderNames, directoryTMP);
        copyErrFileToERR(errors, directoryERR);
        createFoldersBySet(folderNames, directoryOUT);

        makeZipForEveryFolder(folderNames, directoryOUT, directoryTMP);
        ZipService.makeZipForEveryFiles(directoryLOG, directoryTMP);

    }
}
