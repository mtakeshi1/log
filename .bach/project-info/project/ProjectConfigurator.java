package project;

import com.github.sormuras.bach.Configurator;
import com.github.sormuras.bach.Main;
import com.github.sormuras.bach.Paths;
import com.github.sormuras.bach.Project;
import com.github.sormuras.bach.ToolCallTweak;
import com.github.sormuras.bach.ToolFinder;

public class ProjectConfigurator implements Configurator {
  @Override
  public Project configureProject(Project project) {
    return project
        .withTweak(
            ToolCallTweak.WORKFLOW_COMPILE_CLASSES_JAVAC,
            javac ->
                javac
                    .with("-g")
                    .with("--release", "18")
                    .with("--enable-preview"));
  }
}
