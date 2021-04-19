package com.efurture.wireless.defend.asm;

import org.objectweb.asm.Handle;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class MethodVisitorAdapter extends MethodVisitor {


    public MethodVisitorAdapter(MethodVisitor mv) {
        super(Opcodes.ASM5, mv);
    }

    @Override
    public void visitCode() {
        System.out.println("visitCode");
        super.visitCode();
    }

    @Override
    public void visitInsn(int opcode) {
        System.out.println("visitInsn" + opcode);
        super.visitInsn(opcode);
    }

    @Override
    public void visitVarInsn(int opcode, int var) {
        System.out.println("visitVarInsn" + opcode);
        super.visitVarInsn(opcode, var);
    }

    @Override
    public void visitFrame(int type, int nLocal, Object[] local, int nStack, Object[] stack) {
        System.out.println("visitFrame " + type);
        super.visitFrame(type, nLocal, local, nStack, stack);
    }

    @Override
    public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
        try {
            String className = owner.replace('/', '.');
            Class<?> targetClass = this.getClass().getClassLoader().loadClass(className);
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("visitMethodInsn " + opcode + " " + owner + "  " + name + " " + desc);
        if(opcode == Opcodes.INVOKEVIRTUAL){
            if(name.equals("post")){
                System.out.println("redirect method");
               // super.visitMethodInsn(Opcodes.INVOKESTATIC, "com/efurture/wireless/defend/asm/RedirectViewUtils",
                //       "redirect", "(Ljava/lang/Object;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/Object;", itf);
                //return;
            }
        }
        super.visitMethodInsn(opcode, owner, name, desc, itf);
    }

    @Override
    public void visitMaxs(int maxStack, int maxLocals) {
        System.out.println("visitMaxs" + maxLocals + " " + maxStack);
        super.visitMaxs(maxStack, maxLocals);
    }

    @Override
    public void visitEnd() {
        System.out.println("visitEnd");
        super.visitEnd();
    }

    @Override
    public void visitInvokeDynamicInsn(String name, String desc, Handle bsm, Object... bsmArgs) {
        System.out.println(name + " visitInvokeDynamicIns " + desc + " " + bsm);
        super.visitInvokeDynamicInsn(name, desc, bsm, bsmArgs);
    }
}
