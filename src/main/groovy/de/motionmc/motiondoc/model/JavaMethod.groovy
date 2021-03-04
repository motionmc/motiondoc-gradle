package de.motionmc.motiondoc.model

import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable

final class JavaMethod implements Serializable {
    JavaMethod(
            @NotNull String name,
            boolean isStatic,
            boolean isSynchronized,
            boolean isAbstract,
            boolean isFinal,
            boolean isStrictfp,
            boolean isNative,
            boolean isDefault,
            @Nullable String doc,
            @NotNull AccessModifier accessModifier,
            @NotNull List<String> isThrowing) {
        this.name = name
        this.isStatic = isStatic
        this.isSynchronized = isSynchronized
        this.isAbstract = isAbstract
        this.isFinal = isFinal
        this.isStrictfp = isStrictfp
        this.isNative = isNative
        this.isDefault = isDefault
        this.doc = doc
        this.accessModifier = accessModifier
        this.isThrowing = isThrowing
    }

    public final String name
    public final boolean isStatic
    public final boolean isSynchronized
    public final boolean isAbstract
    public final boolean isFinal
    public final boolean isStrictfp
    public final boolean isNative
    public final boolean isDefault
    public final String doc
    public final AccessModifier accessModifier
    public final List<String> isThrowing
}
