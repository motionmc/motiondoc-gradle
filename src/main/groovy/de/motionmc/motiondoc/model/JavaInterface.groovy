package de.motionmc.motiondoc.model

import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable

final class JavaInterface implements JavaType, Serializable {
    JavaInterface(String packageName,
                  String name,
                  String doc,
                  List<JavaMethod> methods,
                  AccessModifier accessModifier) {
        this.packageName = packageName
        this.name = name
        this.doc = doc
        this.methods = methods
        this.accessModifier = accessModifier
    }

    @Nullable
    public final String packageName

    @NotNull
    public final String name

    @Nullable
    public final String doc

    @NotNull
    public final List<JavaMethod> methods

    public final AccessModifier accessModifier
}
