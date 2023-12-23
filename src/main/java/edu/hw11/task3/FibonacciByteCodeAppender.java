package edu.hw11.task3;

import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.implementation.bytecode.ByteCodeAppender;
import net.bytebuddy.jar.asm.Label;
import net.bytebuddy.jar.asm.MethodVisitor;
import net.bytebuddy.jar.asm.Opcodes;

public class FibonacciByteCodeAppender implements ByteCodeAppender {

    @SuppressWarnings({"MagicNumber", "MultipleStringLiterals"})
    @Override
    public Size apply(
        MethodVisitor mv,
        Implementation.Context context,
        MethodDescription methodDescription
    ) {
        mv.visitCode();
        Label label0 = new Label();
        mv.visitLabel(label0);
        mv.visitLineNumber(6, label0);
        mv.visitVarInsn(Opcodes.ILOAD, 1);
        mv.visitInsn(Opcodes.ICONST_3);
        Label label1 = new Label();
        mv.visitJumpInsn(Opcodes.IF_ICMPGE, label1);
        Label label2 = new Label();
        mv.visitLabel(label2);
        mv.visitLineNumber(7, label2);
        mv.visitInsn(Opcodes.LCONST_1);
        mv.visitInsn(Opcodes.LRETURN);
        mv.visitLabel(label1);
        mv.visitLineNumber(9, label1);
        mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
        mv.visitVarInsn(Opcodes.ALOAD, 0);
        mv.visitVarInsn(Opcodes.ILOAD, 1);
        mv.visitInsn(Opcodes.ICONST_1);
        mv.visitInsn(Opcodes.ISUB);
        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL,
            "FibonacciCalc", "calculate", "(I)J", false);
        mv.visitVarInsn(Opcodes.ALOAD, 0);
        mv.visitVarInsn(Opcodes.ILOAD, 1);
        mv.visitInsn(Opcodes.ICONST_2);
        mv.visitInsn(Opcodes.ISUB);
        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL,
            "FibonacciCalc", "calculate", "(I)J", false);
        mv.visitInsn(Opcodes.LADD);
        mv.visitInsn(Opcodes.LRETURN);
        Label label3 = new Label();
        mv.visitLabel(label3);
        mv.visitLocalVariable("this",
            "LFibonacciCalc;", null, label0, label3, 0);
        mv.visitLocalVariable("number",
            "I", null, label0, label3, 1);
        mv.visitMaxs(5, 2);
        mv.visitEnd();
        return new Size(5, 2);
    }



}
