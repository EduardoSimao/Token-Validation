package com.tokenvalidator.app.domain.user;

import java.util.ArrayList;
import java.util.Arrays;

public class ValidateTokenDTO {
    

    public ValidateTokenDTO() {
    }
    
    public static String validateToken(String jsonTest) {
 		String[] strArr = jsonTest.split(","); // Splitting using whitespace
        ArrayList<String> list = new ArrayList<String>(Arrays.asList(strArr));
  
        //verificar se tem mais/menos que 3 claims
        if (list.size() != 3){
            return "Falso";
        }
        else{
            for (int i = 0; i < list.size(); i++) {
                if(list.get(i).contains("Role")){
                    var texto =  list.get(i);
                    var value = GetValue(texto);
                    //verificar se claim Role deve conter um dos três valores (Admin, Member e External)
                    if (roleValidation(value) == "Falso"){
                        return "Falso";
                    }

                }else if(list.get(i).contains("Name")){
                    var texto =  list.get(i);
    
                    var value = GetValue(texto);
                    //Verificar Name:
                    //*máximo de 256 caracteres.
                    //*não pode ter números
                    if (nameValidation(value) == "Falso"){
                        return "Falso";
                    }
                }
                else if(list.get(i).contains("Seed")){
                    var texto =  list.get(i);
    
                    var value = GetValue(texto);
                    //Verificar se o  Seed é um número primo.
                    if (seedValidation(value) == "Falso"){
                        return "Falso";
                    }
                    
                }
            }
    


    
            return "Verdadeiro";
        }

    }

    public static String GetValue(String text){
       
        text = text.replaceAll("\"", "");
        text = text.replaceAll("}", "");
        String[] strSplit = text.split(":");
        ArrayList<String> list = new ArrayList<String>(Arrays.asList(strSplit));
        
        return list.get(1);
    }

    public static String nameValidation(String text){
        if(text.length() > 256){
            return "Falso";
        }

        for (int i = 0; i < text.length(); i++) {
            if(Character.isDigit(text.charAt(i))){
                return "Falso";
            }
        }
       
        return "Verdadeiro";
    }

    public static String roleValidation(String text){
        String[] roles = new String[] {"Admin", "Member", "External"};

        if(Arrays.asList(roles).contains(text)){
            return "Verdadeiro";
        }
        else{
            return "Falso";
        }
    }

    public static String seedValidation(String text){
        int numero = Integer.parseInt(text);
        if (numero <= 1) {
            return "Falso";
        }
        for (int i = 2; i * i <= numero; i++) {
            if (numero % i == 0) {
                return "Falso";
            }
        }
        return "Verdadeiro";
    }
}
