package com.sodasmile.dragondrop;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.artofsolving.jodconverter.OfficeDocumentConverter;
import org.artofsolving.jodconverter.office.DefaultOfficeManagerConfiguration;
import org.artofsolving.jodconverter.office.OfficeException;
import org.artofsolving.jodconverter.office.OfficeManager;

public class ToPdfConverter {

    public static class ConvertResult {
        private boolean success = false;
        private String message;

        public String getMessage() {
            return message;
        }
    }

    private OfficeManager officeManager;
    private final int numberOfInstances = 1;
    private String officeHome;

    public ToPdfConverter() {
        for (int retry = 0; retry < 2; retry++) {
            try {
                int[] ports = new int[numberOfInstances];
                for (int i = 4000, ix = 0; i < 4000 + numberOfInstances; i++) {
                    ports[ix++] = i;
                }
                DefaultOfficeManagerConfiguration builder = new DefaultOfficeManagerConfiguration().setPortNumbers(ports);
                if (!(officeHome == null || officeHome.isEmpty())) {
                    builder = builder.setOfficeHome(officeHome);
                }
                officeManager = builder.buildOfficeManager();
                officeManager.start();
                break;
            } catch (OfficeException e) {
                if (retry == 0) {
                    try {
                        Process process = Runtime.getRuntime().exec("killall -9 soffice.bin");
                        process.waitFor();
                    } catch (IOException | InterruptedException e1) {

                    }
                }
            }
        }
    }

    private ConvertResult failure(String message) {
        ConvertResult convertResult = new ConvertResult();
        convertResult.message = message;
        return convertResult;
    }

    private ConvertResult success(String message) {
        ConvertResult convertResult = new ConvertResult();
        convertResult.message = message;
        convertResult.success = true;
        return convertResult;
    }


    public ConvertResult convertToPdf(File originalFile) {
        originalFile = getFileWithDecodedPath(originalFile);
        if (!originalFile.exists()) {
            System.err.println("Cannot find the file: " + originalFile + ". Dunno whattato");
            return failure("Cannot find the file: " + originalFile + ". Dunno whattato");
        }

        File parentDirectory = originalFile.getParentFile();
        if (!parentDirectory.isDirectory()) {
            System.err.println(originalFile + " is not in a directory. Dunno whattato");
            return failure(originalFile + " is not in a directory. Dunno whattato");
        }

        System.out.println("Parent directory: " + parentDirectory);
        String name = originalFile.getName();

        if (getExtension(name).equalsIgnoreCase(".pdf")) {
            System.out.println(originalFile + " already looks like a pdf file. Dunnot whattato");
            return failure(originalFile + " already looks like a pdf file. Dunnot whattato");
        }

        System.out.println("Original filename: " + name);
        String pdfFileName = makePdfExtension(name);
        System.out.println("Pdf filename: " + pdfFileName);
        File pdfOutputFile = new File(parentDirectory, pdfFileName);
        System.out.println("Pdf output path: " + pdfOutputFile);
        if (pdfOutputFile.exists()) {
            System.err.println(pdfOutputFile + " already exists. Dunno whattato");
            return failure(pdfOutputFile + " already exists. Dunno whattato");
        }

        convertToPdf(originalFile, pdfOutputFile);
        return success(originalFile + " converted to " + pdfOutputFile);
    }

    static File getFileWithDecodedPath(File originalPath) {
        String encodedAbsolutePath = originalPath.getAbsolutePath();
        String decodedAbsolutePath;
        try {
            decodedAbsolutePath = URLDecoder.decode(encodedAbsolutePath, "utf-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("I have no clue what that utf-8 means...", e);
        }
        return new File(decodedAbsolutePath);
    }

    public void convertToPdf(String filename) {
        File originalFile = new File(filename);
        convertToPdf(originalFile);
    }

    static String getExtension(String name) {
        if (name.lastIndexOf('.') == -1) {
            return "";
        }
        return name.substring(name.lastIndexOf('.') + 1, name.length());
    }

    static String makePdfExtension(String name) {
        if (name.lastIndexOf('.') == -1) {
            return name + ".pdf";
        }
        return name.substring(0, name.lastIndexOf('.')) + ".pdf";
    }

    public void convertToPdf(File tmpInFile1, final File tmpOut1) {

        OfficeDocumentConverter converter = new OfficeDocumentConverter(officeManager);
        converter.convert(tmpInFile1, tmpOut1);
    }
}
