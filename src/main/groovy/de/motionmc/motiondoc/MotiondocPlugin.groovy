package de.motionmc.motiondoc


import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.SourceSet

class MotiondocPlugin implements Plugin<Project> {
    @Override
    void apply(Project target) {
        def extension = target.extensions.create('motiondoc', MotiondocPluginExtension)
        def docGenerator = new DocGenerator()
        target.task('generateDoc') {
            final def sourceSet = target.sourceSets.main as SourceSet
            final def sourceSetDir = new File(sourceSet.java.srcDirs[0], 'java')
            docGenerator.writeToFile(sourceSetDir, new File(project.buildDir, 'doc.json'))
        }
    }
}
