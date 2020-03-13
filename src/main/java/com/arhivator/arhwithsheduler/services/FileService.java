package com.arhivator.arhwithsheduler.services;

import com.arhivator.arhwithsheduler.dtos.SeparatedName;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class FileService {
    static SeparatedName separateName(String fullname) {
        String firstPart = fullname.split("_|\\.")[0];
        String secondPart = fullname.split("_|\\.")[1];
        String thirdPart = fullname.split("_|\\.")[2];
        return new SeparatedName(firstPart, secondPart, thirdPart);
    }


    private static boolean isValidFile(String fileName) {
        String reg = "^[A-Za-z]{1}[0-9A-Za-z]{1}[_]{1}[0-9]{4}[.]{1}[A-Z]{1}[0-9]{2}$";
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(fileName);
        return matcher.find();
    }


    private static boolean isValidDBF(String fileName) {
        String regularDBF = "^[A-Za-z]{2}[_]{1}[0-9]{4}[.]{1}[A-Z]{3}$";
        Pattern pattern = Pattern.compile(regularDBF);
        Matcher matcher = pattern.matcher(fileName);
        return matcher.find();
    }


    private static String fileNameByPath(String pathName) {
        String[] splitResult = pathName.split(File.separator);
        return splitResult[splitResult.length - 1];
    }

    private static List<File> getFilesFromNested(File[] listFiles, List<File> list) {
        if (listFiles != null && listFiles.length != 0) {
            for (File file : listFiles) {
                if (file.isDirectory()) {
                    getFilesFromNested(file.listFiles(), list);
                }
                if (file.isFile()) {
                    list.add(file);
                }
            }
        }
        return list;
    }


    static void createListOfFiles(String creatingFileName, String checkingDirectory, String creatingFileError) {
        File directory = new File(checkingDirectory);
        File[] listOfFiles = directory.listFiles();
        List<File> test = new ArrayList<>();
        getFilesFromNested(listOfFiles, test);
        List<String> listOfErrors = new ArrayList<>();
        int countAllFiles = 0;
        int countValidFiles = 0;

        try (FileWriter writer = new FileWriter(creatingFileName, false)) {
            if (test.size() != 0) {
                for (File file : test) {
                    if (isValidFile(file.getName()) | isValidDBF(file.getName())) {
                        countValidFiles++;
//                                System.out.println("In regular: =============================" + file.getName() + "  counter: " + countValidFiles);
                        writer.write(String.valueOf(file.getAbsoluteFile()));
                        writer.append('\n');
                        writer.flush();
                    } else {
                        listOfErrors.add(String.valueOf(file.getAbsoluteFile()));
                    }
                }
            } else {
                System.out.println("0 files in chosen directory");
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        if (countAllFiles - countValidFiles != 0) {
            try (FileWriter writerError = new FileWriter(creatingFileError, false)) {
                for (String file : listOfErrors) {
                    System.out.println("ERROR : " + file);
                    writerError.write(file);
                    writerError.append('\n');
                    writerError.flush();
                }
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    static void createListOfFolders(String creatingFileName, String checkingDirectory) {
        File directory = new File(checkingDirectory);
        File[] listOfFiles = directory.listFiles();

        try (FileWriter writer = new FileWriter(creatingFileName, false)) {
            if (listOfFiles != null) {
                for (File file : listOfFiles) {
                    if (file.isDirectory()) {
                        writer.write(String.valueOf(file.getAbsoluteFile()));
                        writer.append('\n');
                        writer.flush();
                    }
                }
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

}

//    static void createListOfFilesOrFoldersInCheckingDirectory(String creatingFileName, boolean isListFile, String checkingDirectory) {
//        String creatingFileError = "/home/danil/Documents/test_arhives/TMP/listerr.txt";
//        File directory = new File(checkingDirectory);
//        File[] listOfFiles = directory.listFiles();
//        List<String> listOfErrors = new ArrayList<>();
//        int countAllFiles = 0;
//        int countValidFiles = 0;
//
//        if (listOfFiles != null) {
//            countAllFiles = listOfFiles.length;
//        }
//
//        try (FileWriter writer = new FileWriter(creatingFileName, false)) {
//            if (listOfFiles != null) {
//                for (File file : listOfFiles) {
//                    if (isListFile) {
//                        if (file.isFile()) {
//                            if (isValidFile(file.getName()) | isValidDBF(file.getName())) {
//                                countValidFiles++;
////                                System.out.println("In regular: =============================" + file.getName() + "  counter: " + countValidFiles);
//                                writer.write(String.valueOf(file.getAbsoluteFile()));
//                                writer.append('\n');
//                                writer.flush();
//                            } else {
//                                listOfErrors.add(String.valueOf(file.getAbsoluteFile()));
//                            }
//                        }
//                    } else {
//                        if (file.isDirectory()) {
//                            writer.write(String.valueOf(file.getAbsoluteFile()));
//                            writer.append('\n');
//                            writer.flush();
//                        }
//                    }
//                }
//            } else {
//                System.out.println("0 files in chosen directory");
//            }
//        } catch (IOException ex) {
//            System.out.println(ex.getMessage());
//        }
////        System.out.println("COUNT ===--------> " + countAllFiles);
//
//        if (isListFile & (countAllFiles - countValidFiles != 0)) {
//            try (FileWriter writerError = new FileWriter(creatingFileError, false)) {
//                for (String file : listOfErrors) {
//                    System.out.println("ERROR : " + file);
//                    writerError.write(file);
//                    writerError.append('\n');
//                    writerError.flush();
//                }
//            } catch (IOException ex) {
//                System.out.println(ex.getMessage());
//            }
//        }
//    }


    static List<String> fileReaderFullName(String readingFile){
        Reader reader = null;
        BufferedReader buffReader = null;
        List<String> fileNames = new ArrayList<>();
        try {
            reader = new FileReader(readingFile);
            buffReader = new BufferedReader(reader);
            while (buffReader.ready()) {
                fileNames.add(buffReader.readLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
                if (buffReader != null) {
                    buffReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return fileNames;
    }


    static List<String> fileReaderShortName(String readingFile) {
        Reader reader = null;
        BufferedReader buffReader = null;
        List<String> fileNames = new ArrayList<>();
        try {
            reader = new FileReader(readingFile);
            buffReader = new BufferedReader(reader);
            while (buffReader.ready()) {
                fileNames.add(fileNameByPath(buffReader.readLine()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
                if (buffReader != null) {
                    buffReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return fileNames;
    }

    static void createFolders(String nameOfNewFolder, String directoryOfNewFolder) {
        File file = new File(directoryOfNewFolder + nameOfNewFolder);
        boolean bool = file.mkdir();
        if (bool) {
            System.out.println("Directory created successfully");
        } else {
            System.out.println("Sorry couldn’t create specified directory");
        }
    }


    static String getSecondPartOfName(String fileName) {
        return fileName != null ? FileService.separateName(fileName).getSecondPart() : "fileName doesn't exist!";
    }

    static void fillSetOfFolderNames(List<String> listOfFileNames, Set<String> folderNames) {
        for (String listOfFileName : listOfFileNames) {
            if (!listOfFileName.isEmpty()) {
                folderNames.add(getSecondPartOfName(listOfFileName));
            }
        }
    }

    static List<String> fillShortFileNamesByListFullNames(List<String> fullnames) {
        List<String> shortFileNames = new ArrayList<>();
        for (String name : fullnames) {
            shortFileNames.add(FileService.fileNameByPath(name));
        }
        return shortFileNames;
    }

    static void createFoldersBySet(Set<String> folderNames, String directoryForNewFoldersByListOfFiles) {
        if (!folderNames.isEmpty()) {
            for (String foldersNameS : folderNames) {
                FileService.createFolders(foldersNameS, directoryForNewFoldersByListOfFiles);
            }
        }
    }


    static void copyFileToDirectory(List<String> listFileNames, Set<String> setFolderNames, String checkingDirectory, String directoryForNewFoldersByListOfFiles) {
        for (String fileName : listFileNames) {
            for (String folderName : setFolderNames) {
                if (getSecondPartOfName(fileNameByPath(fileName)).equals(folderName)) {
                    File copingFile = new File(fileName);
                    File dir = new File(directoryForNewFoldersByListOfFiles + File.separator + folderName);
                    try {
                        FileUtils.copyFileToDirectory(copingFile, dir);
                    } catch (IOException e) {
//TODO logging here
                        e.printStackTrace();
                    }
                }
            }
        }
    }


    static void copyErrFileToERR(List<String> listErr, String checkingDirectory, String directoryERR) {
        for (String error : listErr) {
            File copingFile = new File(error);
            File dir = new File(directoryERR);
            try {
                FileUtils.copyFileToDirectory(copingFile, dir);
            } catch (IOException e) {
//TODO logging here
                e.printStackTrace();
            }
        }
    }

//    public List deleteAllFilesFromDirectory(String directory) {
////        File[] listOfFiles = foundAllFilesInDirectory(directory);
//        List<DeleteResult> deleteResults = new ArrayList<>();
//
//        for (File file : listOfFiles) {
//            DeleteResult deleteResult = new DeleteResult(file.getName(), file.delete());
//            deleteResults.add(deleteResult);
//        }
//        return deleteResults;
//    }
}
