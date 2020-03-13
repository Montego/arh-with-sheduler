package com.arhivator.arhwithsheduler.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.arhivator.arhwithsheduler.services.FileService.*;
import static com.arhivator.arhwithsheduler.services.ZipService.makeZipForEveryFiles;
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

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd HH-mm-ss");

    public void doTasks() {
        System.out.println("The time is now " + dateFormat.format(new Date()));

//        test
        createListOfFiles(directoryTMP + creatingListOfFiles, checkingDirectory, directoryTMP + creatingListOfErrors);

//        createListOfFilesOrFoldersInCheckingDirectory(directoryTMP + creatingListOfFiles, true, checkingDirectory);
        List<String> fileNames = fileReaderFullName(directoryTMP + creatingListOfFiles);
        List<String> errors = fileReaderFullName(directoryTMP + creatingListOfErrors);
        List<String> shortFileNames = fillShortFileNamesByListFullNames(fileNames);
        Set<String> folderNames = new HashSet<>();

        fillSetOfFolderNames(shortFileNames, folderNames);
        createFoldersBySet(folderNames, directoryTMP);

        createListOfFolders(directoryTMP + creatingListOfFolders, directoryTMP);
//        createListOfFilesOrFoldersInCheckingDirectory(directoryTMP + creatingListOfFolders, false, directoryTMP);
        copyFileToDirectory(fileNames, folderNames, checkingDirectory, directoryTMP);

        copyErrFileToERR(errors, checkingDirectory, directoryERR);
        createFoldersBySet(folderNames, directoryOUT);

        makeZipForEveryFolder(folderNames, directoryOUT, directoryTMP);
        makeZipForEveryFiles(directoryLOG, directoryTMP);

    }
}
