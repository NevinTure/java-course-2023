package edu.hw11.task3;

import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.implementation.bytecode.ByteCodeAppender;
import net.bytebuddy.jar.asm.Label;
import net.bytebuddy.jar.asm.MethodVisitor;
import net.bytebuddy.jar.asm.Opcodes;

public class FibonacciByteCodeAppender implements ByteCodeAppender {

    public final static String CLASS_NAME = "FibonacciCalc";
    public final static String METHOD_NAME = "calculate";
    public final static String DESCRIPTOR = "(I)J";

    @SuppressWarnings("MagicNumber")
    @Override
    public Size apply(
        MethodVisitor mv,
        Implementation.Context context,
        MethodDescription methodDescription
    ) {
        Label label = new Label();
        mv.visitVarInsn(Opcodes.ILOAD, 0);
        mv.visitInsn(Opcodes.ICONST_3);
        mv.visitJumpInsn(Opcodes.IF_ICMPGE, label);
        mv.visitInsn(Opcodes.LCONST_1);
        mv.visitInsn(Opcodes.LRETURN);
        mv.visitLabel(label);
        mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
        mv.visitVarInsn(Opcodes.ILOAD, 0);
        mv.visitInsn(Opcodes.ICONST_1);
        mv.visitInsn(Opcodes.ISUB);
        mv.visitMethodInsn(Opcodes.INVOKESTATIC,
            CLASS_NAME, METHOD_NAME, DESCRIPTOR, false);
        mv.visitVarInsn(Opcodes.ILOAD, 0);
        mv.visitInsn(Opcodes.ICONST_2);
        mv.visitInsn(Opcodes.ISUB);
        mv.visitMethodInsn(Opcodes.INVOKESTATIC,
            CLASS_NAME, METHOD_NAME, DESCRIPTOR, false);
        mv.visitInsn(Opcodes.LADD);
        mv.visitInsn(Opcodes.LRETURN);
        return new Size(4, methodDescription.getStackSize());
    }



}
