package com.efurture.wireless.defend.asm;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.util.HashSet;
import java.util.Set;

public class MethodMapperClassAdapter extends ClassVisitor {

    public static Set<String> ignoreMethodsNames = new HashSet<>();
    static {
        ignoreMethodsNames.add("<init>");
        ignoreMethodsNames.add("<clinit>");
    }



    public MethodMapperClassAdapter(final ClassVisitor cv) {
        super(Opcodes.ASM5, cv);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc,
                                     String signature, String[] exceptions) {
        if(ignoreMethodsNames.contains(name)){
            return super.visitMethod(access, name,desc, signature, exceptions);
        }
        System.out.println("access " + name);
        MethodVisitor mv = super.visitMethod(access, name,desc, signature, exceptions);
        return new MethodVisitorAdapter(mv);
    }
}
