import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

public class SourceReader 
{
	private String filename;
	private LinkedList<String> theHighLevelCode;
	private int linePosition;
	
	public SourceReader(String filename) //constructor
	{
		this.theHighLevelCode = new LinkedList<String>();
		this.linePosition = 0; 
		
		this.filename = filename;
		try
		{
			Scanner input = new Scanner(new File(this.filename));
			while(input.hasNext())
			{
				//System.out.println(input.nextLine());
				this.theHighLevelCode.add(input.nextLine());
			}
		}
		
		catch(IOException e) //error message out to the system if something bad happens
		{
			e.printStackTrace();
		}
	}
	
	public String getNextLine()
	{
		if(this.linePosition < this.theHighLevelCode.size())
		{
			this.linePosition++;
			return this.theHighLevelCode.get(this.linePosition - 1);
		}
		return "End of File"; 
	}

	public LinkedList<String> getTheHighLevelCode() {
		return theHighLevelCode;
	}

	public int getLinePosition() {
		return linePosition;
	}
	
}
