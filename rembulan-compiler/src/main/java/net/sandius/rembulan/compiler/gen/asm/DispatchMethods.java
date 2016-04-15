package net.sandius.rembulan.compiler.gen.asm;

import net.sandius.rembulan.core.Dispatch;
import net.sandius.rembulan.core.LuaState;
import net.sandius.rembulan.core.ObjectSink;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;

import java.util.ArrayList;
import java.util.Arrays;

import static org.objectweb.asm.Opcodes.INVOKESTATIC;

public class DispatchMethods {

	private DispatchMethods() {
		// not to be instantiated
	}

	public static final String OP_ADD = "add";
	public static final String OP_SUB = "sub";
	public static final String OP_MUL = "mul";
	public static final String OP_DIV = "div";
	public static final String OP_MOD = "mod";
	public static final String OP_POW = "pow";
	public static final String OP_UNM = "unm";
	public static final String OP_IDIV = "idiv";

	public static final String OP_BAND = "band";
	public static final String OP_BOR = "bor";
	public static final String OP_BXOR = "bxor";
	public static final String OP_BNOT = "bnot";
	public static final String OP_SHL = "shl";
	public static final String OP_SHR = "shr";

	public static final String OP_CONCAT = "concat";
	public static final String OP_LEN = "len";

	public static final String OP_EQ = "eq";
	public static final String OP_LT = "lt";
	public static final String OP_LE = "le";

	public static final String OP_INDEX = "index";
	public static final String OP_NEWINDEX = "newindex";

	public static final String OP_CALL = "call";


	public static AbstractInsnNode dynamic(String methodName, int numArgs) {
		ArrayList<Type> args = new ArrayList<>();
		args.add(Type.getType(LuaState.class));
		args.add(Type.getType(ObjectSink.class));
		for (int i = 0; i < numArgs; i++) {
			args.add(Type.getType(Object.class));
		}
		return new MethodInsnNode(
				INVOKESTATIC,
				Type.getInternalName(Dispatch.class),
				methodName,
				Type.getMethodDescriptor(
						Type.VOID_TYPE,
						args.toArray(new Type[0])),
				false);
	}

	public static AbstractInsnNode numeric(String methodName, int numArgs) {
		Type[] args = new Type[numArgs];
		Arrays.fill(args, Type.getType(Number.class));
		return new MethodInsnNode(
				INVOKESTATIC,
				Type.getInternalName(Dispatch.class),
				methodName,
				Type.getMethodDescriptor(
						Type.getType(Number.class),
						args),
				false);
	}

	public static AbstractInsnNode index() {
		return dynamic(OP_INDEX, 2);
	}

	public static AbstractInsnNode newindex() {
		return dynamic(OP_NEWINDEX, 3);
	}

	public static AbstractInsnNode call(int kind) {
		return new MethodInsnNode(
				INVOKESTATIC,
				Type.getInternalName(Dispatch.class),
				OP_CALL,
				InvokeKind.staticMethodType(kind).getDescriptor(),
				false);
	}

	public static AbstractInsnNode continueLoop() {
		return new MethodInsnNode(
				INVOKESTATIC,
				Type.getInternalName(Dispatch.class),
				"continueLoop",
				Type.getMethodDescriptor(
						Type.BOOLEAN_TYPE,
						Type.getType(Number.class),
						Type.getType(Number.class),
						Type.getType(Number.class)),
				false);
	}

}