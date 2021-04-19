package com.cainiao.wireless.crashdefend.asm;

import org.junit.Test;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import java.io.IOException;

public class AsmTest{



    @Test
    public void testAsm() throws IOException {
        ClassReader classReader = new ClassReader("com.cainiao.wireless.crashdefend.plugin.ClassHelper");
        ClassVisitor classVisitor = new ClassVisitor(Opcodes.ASM5){

        };
        classReader.accept(classVisitor, 0);

    }

}
