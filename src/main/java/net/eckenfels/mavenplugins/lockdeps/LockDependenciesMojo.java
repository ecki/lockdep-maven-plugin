/*
 * Copyright 2014 Bernd Eckenfels, Germany.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *   http://www.apache.org/licenses/LICENSE-2.0
 */
package net.eckenfels.mavenplugins.lockdeps;

import java.util.Set;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.artifact.handler.ArtifactHandler;
import org.apache.maven.artifact.repository.ArtifactRepository;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.apache.maven.project.MavenProject;


@Mojo( name = "lockdeps", defaultPhase = LifecyclePhase.VERIFY, requiresDependencyCollection=ResolutionScope.TEST )
public class LockDependenciesMojo
    extends AbstractMojo
{
    @Parameter( defaultValue = "${project}", required = true, readonly = true )
    protected MavenProject project;


    public void execute()
        throws MojoExecutionException
    {
        Log log = getLog();

        log.debug("Now scanning dependencies..." + project);

        Set<Artifact> deps = project.getArtifacts();
        for(Artifact a : deps)
        {
            ArtifactRepository rep = a.getRepository();
            log.info("artifact: " + a.getId() + " (" + a.getScope() +") from " +  a.getDownloadUrl() + " " + a.getFile() + " repid=" + ((rep!=null)?rep.getId():null));
        }

        deps = project.getPluginArtifacts();
        for(Artifact a : deps)
        {
            log.info("plugin artifact: " + a.getId() + " (" + a.getScope() +") from " +  a.getDownloadUrl() + " " + a.getFile());
        }
    }
}
