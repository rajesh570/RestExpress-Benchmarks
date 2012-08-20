package com.echo.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jboss.netty.buffer.ChannelBuffer;

import com.echo.DelayResponse;
import com.strategicgains.restexpress.Request;
import com.strategicgains.restexpress.Response;

public class FileIOController {
	boolean serialize = false;
	//ObjectMapper mapper = new ObjectMapper();
	Logger logger = Logger.getLogger(FileIOController.class.getName());
	String path;
	File fileRepo;
	String fileDirectory;
	
	public FileIOController(String fileDir){
		this.fileDirectory = fileDir;
	}
	
	
/*	@Override
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		super.init(config);
		//serialize = new Boolean(config.getInitParameter("serialize"));
		serialize = new Boolean(config.getServletContext().getInitParameter("serialize"));
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		super.doGet(req, resp);
	}
*/

	public void fileIOBenchmark(Request req, Response resp)
			throws IOException {

		//InputStream is = null;
		String newPath = null;
		File newFile = null;
		FileOutputStream fos = null;
		init();
		if (!fileRepo.exists()){
			boolean bool = fileRepo.mkdir();
			if(!bool){
				throw new RuntimeException("directory create error");
			}
		}
		
		try{
			ChannelBuffer is = (ChannelBuffer)req.getBody();
			//is = req.get.getInputStream();
			
			newPath = fileRepo.getAbsolutePath() + "\\" + UUID.randomUUID().toString() + ".txt";
			newFile = new File(newPath);

			boolean bc = newFile.createNewFile();
			if (bc){
				fos = new FileOutputStream(newFile);
				int c = 0;
				while((c = is.readChar()) >= 0) {
					fos.write(c);
				}
				//logger.log(Level.INFO,"Success!! - now deleting");
			}
			else{
				logger.log(Level.INFO, "There was a problem Creating File: " + newFile.getName());
			}
		}
		catch(Exception e){
			logger.log(Level.WARNING,"Problem writing file: "+ newPath);
		}
		finally{
			fos.flush();
			fos.close();
			fos = null;
			boolean l = newFile.delete();
			//System.out.println("File delete status = " + l);
			/*if(serialize){
				
				DelayResponse dr = new DelayResponse(this.getClass().getName(), 0);
				String foo = mapper.writeValueAsString(dr);
				resp.getOutputStream().write(foo.getBytes());
				
			}
			else { */
				//resp.getOutputStream().write("Posted data to filesystem".getBytes());
				
			//}
			
			deleteDirectory(fileRepo);
			
		}
	}

	public void init()  {
		logger.log(Level.INFO,"*************  INITIALIZING  ********************");
		String path = fileDirectory;
		logger.log(Level.INFO,"PATH = " + path);
		fileRepo = new File(path);
	}
	
	private boolean deleteDirectory(File fileRepo)
	{
		if (fileRepo.isDirectory()) {
	        String[] children = fileRepo.list();
	        for (int i=0; i<children.length; i++) {
	            boolean success = deleteDirectory(new File(fileRepo, children[i]));
	            if (!success) {
	                return false;
	            }
	        }
	    }
	    // The directory is now empty so delete it
	    return fileRepo.delete();
	}

/*	@Override
	public void destroy() {

		logger.log(Level.INFO,"*************  DESTROYING  ********************");
		logger.log(Level.INFO,"  (cleaning up dumpdir) ");
		super.destroy();
		deleteDirectory(fileRepo);
	}
*/	

}
