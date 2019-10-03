
public class Driver 
{
	public static void main(String [] args) throws Exception 
	{
		//SourceReader srcR = new SourceReader("src/test.c"); //Source reader takes a file, here we are telling it which file we want it to read
		MipsCCompiler theCompiler  = new MipsCCompiler("src/test.c");
	}
}
