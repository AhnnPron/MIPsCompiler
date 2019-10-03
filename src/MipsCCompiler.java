
public class MipsCCompiler 
{
	private DataMemory theDataMemory;
	private RegisterCollection theRegisterCollection;
	private SourceReader theSource;
	private String filename;
	
	private String secondRegisterAccessed;
	private String lastRegisterAccessed;
	
	public MipsCCompiler(String filename)
	{
		this.filename = filename;
		this.theSource = new SourceReader(filename);
		this.theDataMemory = new DataMemory(200000);
		this.theRegisterCollection = new RegisterCollection(16); // $zero to $t15
		this.lastRegisterAccessed = lastRegisterAccessed; 
		this.secondRegisterAccessed = secondRegisterAccessed; 
		
		
		//System.out.println(this.theSource.getNextLine());
		//System.out.println(this.theSource.getNextLine());
		//System.out.println(this.theSource.getNextLine());
		
		

		
		
		for(int i = 0; i < this.theSource.getTheHighLevelCode().size(); i++)
		{
			if(this.theSource.getTheHighLevelCode().get(0) == "int")
			{
				this.lastRegisterAccessed = this.theRegisterCollection.getNextAvailableRegisterName();
				System.out.println("addi " + this.lastRegisterAccessed + ", $zero, " + this.theDataMemory.getAddressForNewMemory());
			//else if(this.theSource.getNextLine() == char)
				//{
				System.out.println("addi " + this.theRegisterCollection.getNextAvailableRegisterName() + ", $zero, " + this.theSource.getTheHighLevelCode().get(5));
				this.secondRegisterAccessed = this.theRegisterCollection.getNextAvailableRegisterName();
				System.out.println("sw " + this.secondRegisterAccessed + "0(" + this.lastRegisterAccessed + ")");
				//}
			}
			else break; 
			}
		}
	
}
