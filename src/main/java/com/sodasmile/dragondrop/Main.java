package com.sodasmile.dragondrop;

public class Main {

    public static void main(String[] args)  {
        ToPdfConverter toPdfConverter = new ToPdfConverter();

        String filename = "/Volumes/Duffers/_Usortert/IU.hio/cube/kopiCA/Engelsk/Written messages in english standard formats.doc";
        toPdfConverter.convertToPdf(filename);

    }
}
