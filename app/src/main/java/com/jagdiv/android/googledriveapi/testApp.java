package com.jagdiv.android.googledriveapi;

import java.io.IOException;

/**
 * Created by Dell on 29/07/2016.
 */
public class testApp {
   public static void  main(String arg[]){
       java.io.File DATA_STORE_DIR = new java.io.File("c:\\tmp\\edstart.json");

       try {


           if (DATA_STORE_DIR.createNewFile()){
               System.out.println("File is created!");
           }else{
               System.out.println("File already exists.");
           }

       } catch (IOException e) {
           e.printStackTrace();
       }
   }
}
