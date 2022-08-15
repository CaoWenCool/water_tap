package com.blockchain.watertap.util;

import org.apache.commons.lang3.StringUtils;
import org.web3j.utils.Numeric;

import java.util.Random;

public class AddressCheck {



    public static boolean isETHVaildAddress(String input){
        if(StringUtils.isEmpty(input) || !input.startsWith("0x")){
            return false;
        }
        return isValidAddress(input);
    }

    public static boolean isValidAddress(String input){
        String cleanInput = Numeric.cleanHexPrefix(input);
        try {
            Numeric.toBigIntNoPrefix(cleanInput);
        }catch (NumberFormatException e){
            return false;
        }
        return cleanInput.length() == 40;
    }


    public static void main(String args[]){
        System.out.println(isETHVaildAddress("1111"));
        System.out.println(isETHVaildAddress("Ox1123"));
        System.out.println(isETHVaildAddress("0x3086a6d10161960Df5D9203110f23754b49fFEd8"));
        Random rand = new Random();

        for(int i=0;i<100;i++){
            Integer transfer = 1 + rand.nextInt(19);
            System.out.println(transfer);
        }
    }


}
