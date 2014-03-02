package com.sodasmile.dragondrop;

import static org.fest.assertions.Assertions.assertThat;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.junit.Test;

public class ToPdfConverterTest {

    @Test
    public void getExtensionNoDot() {
        assertThat(ToPdfConverter.getExtension("name")).isEqualTo("");
    }

    @Test
    public void getExtensionDotAndNoMore() {
        assertThat(ToPdfConverter.getExtension("name.")).isEqualTo("");
    }

    @Test
    public void getExtensionEllipsis() {
        assertThat(ToPdfConverter.getExtension("name....")).isEqualTo("");
    }

    @Test
    public void getExtensionEllipsisAndExtension() {
        assertThat(ToPdfConverter.getExtension("name....ext")).isEqualTo("ext");
    }

    @Test
    public void getExtensionOneDotOneLetter() {
        assertThat(ToPdfConverter.getExtension("name.a")).isEqualTo("a");
    }

    @Test
    public void getExtensionMultipleDotsOneLetter() {
        assertThat(ToPdfConverter.getExtension("name.a.b.c")).isEqualTo("c");
    }

    @Test
    public void getExtensionMultipleDotsMultipleLetters() {
        assertThat(ToPdfConverter.getExtension("name.a.b.cdef")).isEqualTo("cdef");
    }

    @Test
    public void name() throws UnsupportedEncodingException {
        String path = "/c:/foo%20bar/baz.jpg";
        path = URLDecoder.decode(path, "utf-8");
        path = new File(path).getPath();
        System.out.println(path); // prints: c:\foo bar\baz.jpg
    }

    @Test
    public void name2() throws UnsupportedEncodingException {
        String path = "/Volumes/Duffers/_Usortert/IU.hio/cube/kopiCA/Matte%202.doc";
        File originalPath = new File(path);
        File fileFromDecodedPath = ToPdfConverter.getFileWithDecodedPath(originalPath);
        String pathAfterDecdoe = fileFromDecodedPath.getPath();
        System.out.println(pathAfterDecdoe); // prints: c:\foo bar\baz.jpg

    }
}
