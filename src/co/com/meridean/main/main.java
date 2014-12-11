package co.com.meridean.main;


import co.com.meridean.utils.Indicators;

import java.io.File;
import java.io.FileNotFoundException;


public class main {



    public static void main(String[] args) throws Exception {

        Indicators indicators = new Indicators();

        if(args.length == 0) {
            indicators.generateIndicators(null);
        }
        else {
            try {
                File file = new File(args[0]);
                if(file.exists()) {
                    indicators.generateIndicators(args[0]);
                }
                else{
                    throw new FileNotFoundException();
                }

            } catch (Exception e){
                System.out.println("El archivo no existe");
            }
        }
    }
}
