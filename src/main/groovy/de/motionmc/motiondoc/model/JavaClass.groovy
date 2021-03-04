package de.motionmc.motiondoc.model

import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable

final class JavaClass implements JavaType, Serializable {
    JavaClass(@Nullable String packageName,
              @NotNull String name,
              @Nullable String doc,
              @NotNull List<JavaMethod> methods,
              @NotNull AccessModifier accessModifier,
              boolean isStatic,
              boolean isAbstract,
              boolean isFinal,
              boolean isStrictfp) {
        this.packageName = packageName
        this.name = name
        this.doc = doc
        this.methods = methods
        this.accessModifier = accessModifier
        this.isStatic = isStatic
        this.isAbstract = isAbstract
        this.isFinal = isFinal
        this.isStrictfp = isStrictfp
    }

    public final String packageName
    public final String name
    public final String doc
    public final List<JavaMethod> methods
    public final AccessModifier accessModifier

    public final boolean isStatic
    public final boolean isAbstract
    public final boolean isFinal
    public final boolean isStrictfp
}
