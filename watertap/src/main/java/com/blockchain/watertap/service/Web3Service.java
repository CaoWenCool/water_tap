package com.blockchain.watertap.service;

import jep.Jep;
import jep.JepException;
import jep.SharedInterpreter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
public class Web3Service {

    @Value("${water.tab.test.python:}")
    public String pythonPath;

    private ThreadLocal<Jep> tlInterp = new ThreadLocal<>();

    private Jep getPythonInterp() {
        if (tlInterp.get() == null) {
            Jep interp = null;
            try {
                interp = new SharedInterpreter();
                InputStream inputStream = getClass().getClassLoader().getResourceAsStream(pythonPath);
                int length = inputStream.available();
                byte[] b = new byte[length];
                inputStream.read(b);
                inputStream.close();
                ;
                String pythonData = new String(b);
                interp.exec(pythonData);
                tlInterp.set(interp);
            } catch (Exception e) {
                if (interp != null) {
                    try {
                        interp.close();
                    } catch (JepException jepException) {
                        jepException.printStackTrace();
                    }
                }
                e.printStackTrace();
            }
        }
        return tlInterp.get();
    }

    public Object getTest(Integer a, Integer b) {
        Jep jep = getPythonInterp();
        try {
            jep.set("a", a);
            jep.set("b", b);
            Object ret = jep.getValue("add_num(a,b)");
            return ret;
        } catch (JepException jepException) {
            jepException.printStackTrace();
        }
        return null;
    }


}
