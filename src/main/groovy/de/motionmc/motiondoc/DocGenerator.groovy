package de.motionmc.motiondoc

import com.github.javaparser.StaticJavaParser
import com.github.javaparser.ast.Node as AstNode
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration
import com.github.javaparser.ast.body.MethodDeclaration
import com.google.gson.Gson
import de.motionmc.motiondoc.model.*

import java.util.function.Consumer
import java.util.stream.Collectors

final class DocGenerator {
    private Gson gson = new Gson()

    void writeToFile(File inputDirectory, File outputFile) {
        final def jsonString = getJsonString(inputDirectory)

        if(outputFile.exists()) {
            outputFile.delete()
        }
        outputFile.createNewFile()

        outputFile.write(jsonString)
    }

    String getJsonString(File inputDirectory) {
        final def documentation = generateDocumentation(inputDirectory)
        return gson.toJson(documentation)
    }

    private Documentation generateDocumentation(File directory) {
        final def interfaces = new ArrayList<JavaInterface>()
        final def classes = new ArrayList<JavaClass>()

        eachInDirectory(directory) { javaType ->
            if(javaType instanceof JavaInterface) {
                interfaces.add(javaType as JavaInterface)
            } else if(javaType instanceof JavaClass) {
                classes.add(javaType as JavaClass)
            }
        }

        return new Documentation(interfaces, classes)
    }

    private void eachInDirectory(File directory, Consumer<JavaType> eachCallback) {
        directory.eachFile {file ->
            if(file.isDirectory()) {
                eachInDirectory(file) {
                    eachCallback(it)
                }
            } else {
                final def compilationUnit = StaticJavaParser.parse(file)
                eachClassOrInterface(compilationUnit) {
                    eachCallback(it)
                }
            }
        }
    }

    private static void eachClassOrInterface(AstNode node, Consumer<JavaType> eachCallback) {
        node.findAll(ClassOrInterfaceDeclaration.class).stream().forEach { clazz ->
            clazz.fullyQualifiedName.ifPresent {fullQualifiedClassName ->
                final def packageName = fullQualifiedClassName.substring(0, fullQualifiedClassName.lastIndexOf('.'))
                final def methods = ArrayList<JavaMethod>

                eachFunction(clazz) { method ->
                    methods.add(method)
                }
                if(clazz.interface) {
                    def javaInterface = new JavaInterface(
                            packageName,
                            clazz.nameAsString,
                            clazz.hasJavaDocComment() ? clazz.javadoc.get().toText() : "",
                            methods as List<JavaMethod>,
                            clazz.public ? AccessModifier.PUBLIC :
                                    (clazz.protected ? AccessModifier.PROTECTED :
                                            (clazz.private ? AccessModifier.PRIVATE : AccessModifier.PACKAGE_PRIVATE)),
                    )
                    eachCallback(javaInterface)
                } else {
                    def javaClass = new JavaClass(
                            packageName,
                            clazz.nameAsString,
                            clazz.hasJavaDocComment() ? clazz.javadoc.get().toText() : "",
                            methods as List<JavaMethod>,
                            clazz.public ? AccessModifier.PUBLIC :
                                    (clazz.protected ? AccessModifier.PROTECTED :
                                            (clazz.private ? AccessModifier.PRIVATE : AccessModifier.PACKAGE_PRIVATE)),
                            clazz.static,
                            clazz.abstract,
                            clazz.final,
                            clazz.strictfp
                    )
                    eachCallback(javaClass)
                }
            }
        }
    }

    private static void eachFunction(AstNode node, Consumer<JavaMethod> eachCallback) {
        node.findAll(MethodDeclaration.class).stream().forEach { method ->
            def javaMethod = new JavaMethod(method.name.asString(),
                    method.static,
                    method.synchronized,
                    method.abstract,
                    method.final,
                    method.strictfp,
                    method.native,
                    method.default,
                    method.hasJavaDocComment() ? method.javadoc.get().toText() : "",
                    method.public ? AccessModifier.PUBLIC :
                            (method.protected ? AccessModifier.PROTECTED :
                                    (method.private ? AccessModifier.PRIVATE : AccessModifier.PACKAGE_PRIVATE)),
                    method.thrownExceptions.stream().map(ex -> ex.toDescriptor()).collect(Collectors.toList())
            )
            eachCallback(javaMethod)
        }
    }
}
