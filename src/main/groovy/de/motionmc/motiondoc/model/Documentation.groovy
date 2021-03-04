package de.motionmc.motiondoc.model

import org.jetbrains.annotations.NotNull

final class Documentation implements Serializable {
    Documentation(@NotNull List<JavaInterface> interfaces, @NotNull List<JavaClass> classes) {
        this.interfaces = interfaces
        this.classes = classes
    }

    final List<JavaInterface> interfaces
    final List<JavaClass> classes
}
