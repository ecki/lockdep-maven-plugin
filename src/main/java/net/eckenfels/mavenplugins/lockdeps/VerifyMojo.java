/*
 * Copyright 2014 Bernd Eckenfels, Germany.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *   http://www.apache.org/licenses/LICENSE-2.0
 */
package net.eckenfels.mavenplugins.lockdeps;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import org.apache.maven.MavenExecutionException;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.apache.maven.project.MavenProject;


@Mojo( name = "verify", defaultPhase = LifecyclePhase.VERIFY, requiresDependencyCollection=ResolutionScope.TEST )
public class VerifyMojo
    extends AbstractMojo
{
    @Parameter( defaultValue = "${project}", required = true, readonly = true )
    protected MavenProject project;

    @Parameter( property =  "lockdep.lockFile", defaultValue = ".dependencies.lock", required = true)
    protected File lockFile;

    @Parameter( property =  "lockdep.currentFile", defaultValue = ".dependencies.current", required = true)
    protected File currentFile;

    @Parameter( property = "lockdep.createCurrent", defaultValue = "true", required = true)
    protected boolean createCurrent;


    public void execute()
        throws MojoExecutionException
    {
        final Log log = getLog();

        if (lockFile == null)
        {
            lockFile = new File(".dependencies.lock");
        }

        if (currentFile == null)
        {
            currentFile = new File(".dependencies.current");
        }

        if (!lockFile.exists())
        {
            if (createCurrent)
            {
                log.info("We do not have a lockfile " + lockFile + " will create "+ currentFile);
                try {
                    ProjectScanner projectScanner = new ProjectScanner(project, "SHA-1");
                    projectScanner.scan();
                    projectScanner.writeTo(currentFile);
                }
                catch (IOException ioe)
                {
                    throw new MojoExecutionException("I/O Error while scanning artifacts", ioe);
                }
                catch (NoSuchAlgorithmException nsa)
                {
                    throw new MojoExecutionException("Cannot use algorithm SHA-1", nsa);
                }
            }
        } else {
            log.debug("Found version lockfile " + lockFile + " will compare hashes...");
            //readDependencies(project);
            //readLockfile();
        }
    }
}
