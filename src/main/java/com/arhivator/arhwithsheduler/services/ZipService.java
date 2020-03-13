package com.arhivator.arhwithsheduler.services;


import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


class ZipService {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd HH-mm-ss");

    private static String formatDate() {
        return dateFormat.format(new Date());
    }

    static boolean createZipJustFiles(String nameOfZip, String outputDirectory, String srcFolder) {
        try {
            BufferedInputStream origin = null;
            final int BUFFER = 2048;
            FileOutputStream dest = new FileOutputStream(new File(outputDirectory + nameOfZip + ".zip"));

            ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(dest));
            byte[] data = new byte[BUFFER];

            File dir = new File(srcFolder);
            File[] dirList = dir.listFiles();
            if (dirList != null) {
                for (File sd : dirList) {
                    if (sd.isFile()) {
                        System.out.println("Adding: " + sd.getName());
                        FileInputStream fi = new FileInputStream(srcFolder + "/" + sd.getName());
                        origin = new BufferedInputStream(fi, BUFFER);
                        ZipEntry entry = new ZipEntry(sd.getName());
                        out.putNextEntry(entry);
                        int count;
                        while ((count = origin.read(data, 0, BUFFER)) != -1) {
                            out.write(data, 0, count);
                            out.flush();
                        }
                    }
                }
            }
            if (origin != null) {
                origin.close();
            }
            out.flush();
            out.close();
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    static boolean createZipArchive(String nameOfZip, String outputDirectory, String srcFolder) {
        try {
            BufferedInputStream origin = null;
            final int BUFFER = 2048;
            FileOutputStream dest = new FileOutputStream(new File(outputDirectory + nameOfZip + ".zip"));

            ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(dest));
            byte[] data = new byte[BUFFER];

            File subDir = new File(srcFolder);
            String[] subdirList = subDir.list();
            if (subdirList != null) {
                for (String sd : subdirList) {
                    // get a list of files from current directory
                    File f = new File(srcFolder + "/" + sd);
                    if (f.isDirectory()) {
                        String[] files = f.list();
                        if (files != null) {
                            for (String file : files) {
                                System.out.println("Adding: " + file);
                                FileInputStream fi = new FileInputStream(srcFolder + "/" + sd + "/" + file);
                                origin = new BufferedInputStream(fi, BUFFER);
                                ZipEntry entry = new ZipEntry(sd + "/" + file);
                                out.putNextEntry(entry);
                                int count;
                                while ((count = origin.read(data, 0, BUFFER)) != -1) {
                                    out.write(data, 0, count);
                                    out.flush();
                                }
                            }
                        }
                    } else {
                        FileInputStream fi = new FileInputStream(f);
                        origin = new BufferedInputStream(fi, BUFFER);
                        ZipEntry entry = new ZipEntry(sd);
                        out.putNextEntry(entry);
                        int count;
                        while ((count = origin.read(data, 0, BUFFER)) != -1) {
                            out.write(data, 0, count);
                            out.flush();
                        }
                    }
                }
            }
            if (origin != null) {
                origin.close();
            }
            out.flush();
            out.close();
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    static void makeZipForEveryFolder(Set<String> folderNames, String directoryOUT, String arhivatingDirectory) {
        for (String name : folderNames) {
            String generatingNameZip = "55555_" + name + "_Архив для Комитета по тарифам";
            String generatingOutputDirectory = directoryOUT + name + "/";
            String generatingArhivatingDirectory = arhivatingDirectory + name;
            ZipService.createZipArchive(generatingNameZip, generatingOutputDirectory, generatingArhivatingDirectory);
        }
    }

    static void makeZipForEveryFiles(String directoryOUT, String arhivatingDirectory) {
        String generatingNameZip = formatDate() + " Протокол обработки файлов IN";
        ZipService.createZipJustFiles(generatingNameZip, directoryOUT, arhivatingDirectory);
    }


}
