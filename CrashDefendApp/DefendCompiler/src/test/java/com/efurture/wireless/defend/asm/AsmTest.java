package com.efurture.wireless.defend.asm;

import org.junit.Test;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class AsmTest{



    @Test
    public void testAsm() throws IOException {
        String className = "com.efurture.wireless.defend.asm.BizCallTest";
        ClassReader classReader = new ClassReader(className);
        ClassWriter classWriter = new ClassWriter(classReader,0);
        ClassVisitor classVisitor = new MethodMapperClassAdapter(classWriter);
        classReader.accept(classVisitor, 0);


        System.out.println(classWriter.toByteArray());

        writeClassWrite(className, classWriter);


       // ClassReader innerClass = new ClassReader("com/efurture/wireless/defend/asm/BizCallTest$1");

        //innerClass.accept(classVisitor, 0);


    }

    private void writeClassWrite(String className, ClassWriter classWriter){
        try {
            className = className.replace('.', '/');
            className += ".class";
            File file = new File(className);
            if(file.getParentFile() != null && !file.getParentFile().exists()){
                file.getParentFile().mkdirs();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(className);
            byte[] bts = classWriter.toByteArray();
            fileOutputStream.write(bts, 0, bts.length);
            System.out.println(file.getAbsolutePath());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
