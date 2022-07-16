package utils;

import exceptions.ConfigDataNotFoundException;
import exceptions.NotFoundByRegexException;
import exceptions.StreamReadingException;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.URL;
import java.util.stream.Collectors;

public class ConfigReader {
    private final static String portRegex = "(?<=port:\\s{1,5})[^\\s]*";
    private final static String hostNameRegex = "(?<=hostname:\\s{1,5})[^\\s]*";

    public static InetSocketAddress readAddress(String path) throws StreamReadingException, FileNotFoundException {
        return readAddress(createFileReader(path));
    }

    private static Reader createFileReader(String path) throws FileNotFoundException {
        return new FileReader(path);
    }

    private static Reader createFileReader(URL url) throws FileNotFoundException {
        return createFileReader(url.getPath());
    }

    public static InetSocketAddress readAddress(URL resourceURL) throws StreamReadingException, FileNotFoundException {
        return readAddress(createFileReader(resourceURL));
    }

    public static InetSocketAddress readAddress(Reader reader) throws StreamReadingException {
        String lines = readLines(reader);

        String hostname = readHostnameOnLines(lines);
        int port = readPortOnLines(lines);

        return new InetSocketAddress(hostname, port);
    }

    private static String readLines(Reader reader) {
        StringBuilder strBuilder = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(reader)) {
            for (String line : bufferedReader.lines().collect(Collectors.toList()))
                strBuilder.append(line).append("\n");
        } catch (IOException e) {
            throw new StreamReadingException("Address reading is failed");
        }
        return strBuilder.toString();
    }

    public static int readPort(String path) throws StreamReadingException, FileNotFoundException {
        return readPort(createFileReader(path));
    }

    public static int readPort(URL resourceURL) throws StreamReadingException, FileNotFoundException {
        return readPort(createFileReader(resourceURL));
    }

    public static int readPort(Reader reader) {
        return readPortOnLines(readLines(reader));
    }

    private static int readPortOnLines(String lines) {
        try {
            return Integer.parseInt(RegexSearcher.searchFirst(portRegex, lines));
        } catch (NotFoundByRegexException e) {
            throw new ConfigDataNotFoundException("Port not found");
        }
    }

    private static String readHostnameOnLines(String lines) {
        try {
            return RegexSearcher.searchFirst(hostNameRegex, lines);
        } catch (NotFoundByRegexException e) {
            throw new ConfigDataNotFoundException("Hostname not found");
        }
    }
}
