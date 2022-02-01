package com.amit.testmavenproject;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.errors.TransportException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

public class App {
 
	  private static final String REMOTE_URL = "https://github.com/ilovejava-amit/test";
		 
		 public static void main(String[] args)throws IOException, InvalidRemoteException, TransportException, GitAPIException
		 {
			 
		//public static void pushToRemote(String xmlViewContent) throws IOException, InvalidRemoteException, TransportException, GitAPIException{
	        // prepare a new folder for the cloned repository
	        File localPath = File.createTempFile("GitRepository", "");
	        if(!localPath.delete()) {
	            throw new IOException("Could not delete temporary file " + localPath);
	        }

	        // then clone
	        System.out.println("Cloning from " + REMOTE_URL + " to " + localPath);
	        try (Git git = Git.cloneRepository()
	                .setURI(REMOTE_URL)
	                .setCredentialsProvider(new UsernamePasswordCredentialsProvider("amit89ranjan@gmail.com", "Amitranjan@1234567890"))
	                .setDirectory(localPath)
	                .call()) {

	            Repository repository = git.getRepository();
	            // create the folder
	            File theDir = new File(repository.getDirectory().getParent(), "webapp");
	                theDir.mkdir();

	         // create the file
	            File myfile = new File(theDir, "InputView.view.xml");
	                myfile.createNewFile();

	            // Stage all files in the repo including new files
	            git.add().addFilepattern(".").call();

	            // and then commit the changes.
	            git.commit().setMessage("Commit all changes including additions").call();

	            try(PrintWriter writer = new PrintWriter(myfile)) {
	                writer.append( "Hello, world!" );
	            }
	            // Stage all changed files, omitting new files, and commit with one command
	            git.commit()
	                    .setAll(true)
	                    .setMessage("Commit changes to all files")
	                    .call();
	            git.add().addFilepattern("*").call();
	            RevCommit result = git.commit().setMessage("initial commit").call();
	            git.push()
	                .setCredentialsProvider(new UsernamePasswordCredentialsProvider("amit89ranjan@gmail.com", "Amitranjan@1234567890"))
	                .call();
	            System.out.println("Pushed with commit: "+ result);

	        }
	    }
  }

