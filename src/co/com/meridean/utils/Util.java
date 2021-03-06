package co.com.meridean.utils;


import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.util.*;

public class Util {

    private JFileChooser fileSelector;
    private FileNameExtensionFilter filter;
    private File file;
    private FileWriter writer;
    private FileReader reader;
    private BufferedWriter bufferWriter;
    private BufferedReader bufferedReader;

    public String getPathFile(String tipo) {
        int opcionSeleccionada;
        String rutaArchivo = "";
        fileSelector = new JFileChooser("/home/meridean-hp/repositorios/RestServicesPeopleVector/data");
        filter = new FileNameExtensionFilter("Archivos de texto (."+tipo+")", tipo);
        fileSelector.setFileFilter(filter);
        opcionSeleccionada = fileSelector.showOpenDialog(fileSelector);

        if(opcionSeleccionada == JFileChooser.APPROVE_OPTION) {
            rutaArchivo = fileSelector.getSelectedFile().getAbsolutePath();
        }
        else {
            System.exit(0);
        }

        return rutaArchivo;
    }

    public ArrayList<String> readGoldFile(String pathFile) {

        ArrayList<String> linesGoldFile = new ArrayList<String>();
        try{
            String path = "";
            if(pathFile == null){
                path = getPathFile("txt");
            }
            else {
                path = pathFile;
            }
            file = new File(path);
            reader = new FileReader(file);
            bufferedReader = new BufferedReader(reader);
            String line;
            while((line = bufferedReader.readLine()) != null){
                linesGoldFile.add(line);
            }

        } catch (Exception e){
            System.err.println("Problema leyendo archivo\n" + e.getMessage());
        }

        return linesGoldFile;
    }

    public void closeTextFile(){
        try{
            bufferWriter.close();
        }
        catch(Exception e){
            System.err.println("Error al cerrar Archivo texto: " + e.getMessage());
        }
    }

    public void writeWordInFile(String line){
        try{
            bufferWriter.write(line);
        }
        catch(Exception e){
            System.err.println("Error al escribir linea Archivo texto: " + e.getMessage());
        }
    }

    public void writeLineInFile(String line){
        try{
            bufferWriter.write(line);
            bufferWriter.newLine();
        }
        catch(Exception e){
            System.err.println("Error al escribir linea Archivo texto: " + e.getMessage());
        }
    }

    public static float getPecentageFromAmountOfTotal(float amount, float total) {

        float percentage = 0f;
        if(total != 0) {
            percentage = (amount / total) * 100f;
        }

        return percentage;
    }

}
