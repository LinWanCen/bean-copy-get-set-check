package io.github.linwancen.bean.check.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Constant {
    private Constant() {}

    public static Set<String> getJavaType() {
        return JAVA_TYPE;
    }

    private static final Set<String> JAVA_TYPE = new HashSet<>(Arrays.asList(
            "void",
            "boolean",
            "int",
            "long",
            "char",
            "double",
            "byte",
            "short",
            "float",
            "AbstractMethodError",
            "AbstractStringBuilder",
            "Appendable",
            "ApplicationShutdownHooks",
            "ArithmeticException",
            "ArrayIndexOutOfBoundsException",
            "ArrayStoreException",
            "AssertionError",
            "AssertionStatusDirectives",
            "AutoCloseable",
            "Boolean",
            "BootstrapMethodError",
            "Byte",
            "Character",
            "CharacterData",
            "CharacterData0E",
            "CharacterData00",
            "CharacterData01",
            "CharacterData02",
            "CharacterDataLatin1",
            "CharacterDataPrivateUse",
            "CharacterDataUndefined",
            "CharacterName",
            "CharSequence",
            "Class",
            "ClassCastException",
            "ClassCircularityError",
            "ClassFormatError",
            "ClassLoader",
            "ClassLoaderHelper",
            "ClassNotFoundException",
            "ClassValue",
            "Cloneable",
            "CloneNotSupportedException",
            "Comparable",
            "Compiler",
            "ConditionalSpecialCasing",
            "Deprecated",
            "Double",
            "Enum",
            "EnumConstantNotPresentException",
            "Error",
            "Exception",
            "ExceptionInInitializerError",
            "Float",
            "FunctionalInterface",
            "IllegalAccessError",
            "IllegalAccessException",
            "IllegalArgumentException",
            "IllegalMonitorStateException",
            "IllegalStateException",
            "IllegalThreadStateException",
            "IncompatibleClassChangeError",
            "IndexOutOfBoundsException",
            "InheritableThreadLocal",
            "InstantiationError",
            "InstantiationException",
            "Integer",
            "InternalError",
            "InterruptedException",
            "Iterable",
            "LinkageError",
            "Long",
            "Math",
            "NegativeArraySizeException",
            "NoClassDefFoundError",
            "NoSuchFieldError",
            "NoSuchFieldException",
            "NoSuchMethodError",
            "NoSuchMethodException",
            "NullPointerException",
            "Number",
            "NumberFormatException",
            "Object",
            "OutOfMemoryError",
            "Override",
            "Package",
            "Process",
            "ProcessBuilder",
            "ProcessEnvironment",
            "ProcessImpl",
            "Readable",
            "ReflectiveOperationException",
            "Runnable",
            "Runtime",
            "RuntimeException",
            "RuntimePermission",
            "SafeVarargs",
            "SecurityException",
            "SecurityManager",
            "Short",
            "Shutdown",
            "StackOverflowError",
            "StackTraceElement",
            "StrictMath",
            "String",
            "StringBuffer",
            "StringBuilder",
            "StringCoding",
            "StringIndexOutOfBoundsException",
            "SuppressWarnings",
            "System",
            "SystemClassLoaderAction",
            "Terminator",
            "Thread",
            "ThreadDeath",
            "ThreadGroup",
            "ThreadLocal",
            "Throwable",
            "TypeNotPresentException",
            "UnknownError",
            "UnsatisfiedLinkError",
            "UnsupportedClassVersionError",
            "UnsupportedOperationException",
            "VerifyError",
            "VirtualMachineError",
            "Void"
    ));
}